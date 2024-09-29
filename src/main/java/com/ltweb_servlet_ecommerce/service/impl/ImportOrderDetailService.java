package com.ltweb_servlet_ecommerce.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ltweb_servlet_ecommerce.dao.*;
import com.ltweb_servlet_ecommerce.dao.impl.ImportOrderDetailDAO;
import com.ltweb_servlet_ecommerce.dao.impl.ProductDAO;
import com.ltweb_servlet_ecommerce.dao.impl.SizeDAO;
import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.service.IImportOrderDetailService;
import com.ltweb_servlet_ecommerce.utils.CloudinarySingleton;
import com.ltweb_servlet_ecommerce.utils.ReadImportOrderFile;

import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImportOrderDetailService implements IImportOrderDetailService {
    @Inject
    private IImportOrderDetailDAO orderDetailDAO;
    @Inject
    private IProductDAO productDAO;
    @Inject
    private ISizeDAO sizeDAO;
    @Inject
    private ICategoryDAO categoryDAO;
    @Inject
    private IProductSizeDAO productSizeDAO;
    @Inject
    ProductImageService imageService;

    @Override
    public double getTotalPriceByImportId(String importId) {
        //do máy ko tự inject nên tạo tay
        if (orderDetailDAO == null) {
            orderDetailDAO = new ImportOrderDetailDAO();
        }
        return orderDetailDAO.getTotalPriceByImportId(importId);
    }

    @Override
    public List<ImportOrderDetailModel> findByImportId(String importId) {
        //do máy ko tự inject nên tạo tay
        if (orderDetailDAO == null || productDAO == null || sizeDAO == null) {
            orderDetailDAO = new ImportOrderDetailDAO();
            productDAO = new ProductDAO();
            sizeDAO = new SizeDAO();
        }

        // Call the DAO to get the list of ImportOrderDetailModel by importId
        List<ImportOrderDetailModel> importList = orderDetailDAO.findByImportId(importId);

        // Extract the productSizeIds from the importList
        List<Long> productSizeIds = importList.stream().map(ImportOrderDetailModel::getProductSizeId).toList();

        // Find the products by their productSizeIds
        Map<Long, ProductModel> productMap = productDAO.findProductsByProductSizeIds(productSizeIds);

        // Find the sizes by their productSizeIds
        Map<Long, SizeModel> sizeMap = sizeDAO.findSizesByProductSizeIds(productSizeIds);

        // For each importOrderDetailModel in the importList
        importList.forEach(importOrderDetailModel -> {
            // Get the product and size associated with the current importOrderDetailModel
            ProductModel product = productMap.get(importOrderDetailModel.getProductSizeId());
            SizeModel size = sizeMap.get(importOrderDetailModel.getProductSizeId());

            // Set the productId, productName, and size of the current importOrderDetailModel
            importOrderDetailModel.setProductId(product.getId());
            importOrderDetailModel.setProductName(product.getName());
            importOrderDetailModel.setSize(size.getName());
        });

        // Return the updated importList
        return importList;
    }

    @Override
    public List<ImportOrderDetailModel> findByProductSizeId() {
        if (orderDetailDAO == null) {
            orderDetailDAO = new ImportOrderDetailDAO();
        }
        return orderDetailDAO.findByProductSizeId();
    }

    @Override
    public List<ImportOrderDetailModel> findByProductSizeId(String productSizeId) {
        if (orderDetailDAO == null) {
            orderDetailDAO = new ImportOrderDetailDAO();
        }
        return orderDetailDAO.findByProductSizeId(productSizeId);
    }

    // This method processes the category of the imported product
    private long processCategory(ImportOrderDetailModel model, HashMap<String, CategoryModel> cacheCategory) throws SQLException {
        if (cacheCategory.containsKey(model.getCategoryCode())) {
            return cacheCategory.get(model.getCategoryCode()).getId();
        }

        // Initialize the category ID
        long cateId = 0;
        // Try to find the category in the database using the category code from the model
        CategoryModel category = categoryDAO.findWithFilter(CategoryModel.builder().code(model.getCategoryCode()).build());

        // If the category exists, get its ID
        if (category != null) {
            cateId = category.getId();
        } else {
            // If the category does not exist, create a new category with the code and name from the model
            category = CategoryModel.builder().code(model.getCategoryCode()).name(model.getCategoryName()).build();
            // Save the new category in the database and get its ID
            cateId = categoryDAO.save(category);
        }

        cacheCategory.put(model.getCategoryCode(), category);
        // Return the category ID
        return cateId;
    }

    // This method processes the size of the imported product
    private long processSize(ImportOrderDetailModel model, HashMap<String, SizeModel> cacheSize) throws SQLException {
        if (cacheSize.containsKey(model.getSize())) {
            return cacheSize.get(model.getSize()).getId();
        }

        // Try to find the size in the database using the size name from the model
        SizeModel size = sizeDAO.findWithFilter(SizeModel.builder().name(model.getSize()).build());
        long sizeId;
        // If the size does not exist, create a new size with the name from the model
        if (size == null) {
            size = SizeModel.builder().name(model.getSize()).build();
            // Save the new size in the database and get its ID
            sizeId = sizeDAO.save(size);
        } else {
            // If the size exists, get its ID
            sizeId = size.getId();
        }
        cacheSize.put(model.getSize(), size);
        // Return the size ID
        return sizeId;
    }

    // This method processes the product size of the imported product
    private long processProductSize(long productId, long sizeId, ImportOrderDetailModel model, HashMap<String, ProductSizeModel> cacheProductSize) throws SQLException {
        String key = productId + "_" + sizeId;
        if (cacheProductSize.containsKey(key)) {
            return cacheProductSize.get(key).getId();
        }

        // Try to find the product size in the database using the product ID and size ID
        ProductSizeModel withFilter = productSizeDAO.findWithFilter(
                ProductSizeModel.builder().productId(productId).sizeId(sizeId).build());
        long productSizeId = 0;
        // If the product size does not exist, create a new product size with the product ID, size ID, and price from the model
        if (withFilter == null) {
            productSizeId = productSizeDAO.save(ProductSizeModel.builder().productId(productId).sizeId(sizeId)
                    .price(model.getPriceImport() * 1.3).build());
        } else {
            // If the product size exists, get its ID
            productSizeId = withFilter.getId();
        }
        cacheProductSize.put(key, withFilter);
        // Return the product size ID
        return productSizeId;
    }


    // This method processes the product of the imported order
    private long processProduct(ImportOrderDetailModel model, HashMap<Long, ProductModel> cacheProduct, HashMap<String, CategoryModel> cacheCategory) throws SQLException {
        if (cacheProduct.containsKey(model.getProductId())) {
            return cacheProduct.get(model.getProductId()).getId();
        }

        // Initialize the product ID
        long productId;

        // Try to find the product in the database using the product ID from the model
        ProductModel product = productDAO.findById(model.getProductId());

        // If the product does not exist in the database
        if (product == null) {
            // Get the product ID from the model
            productId = model.getProductId();

            // Process the category of the product and get the category ID
            long cateId = processCategory(model, cacheCategory);

            // Create a new product with the product ID, product name, and category ID
            product = new ProductModel();
            product.setId(productId);
            product.setName(model.getProductName());
            product.setCategoryId(cateId);

            // Save the new product in the database and get its ID
            productId = productDAO.save(product);
        } else {
            // If the product exists in the database, get its ID
            productId = product.getId();
        }
        cacheProduct.put(productId, product);

        // Return the product ID
        return productId;
    }

    private ImportOrderDetailModel createImportOrderDetail(String importOrderId, ImportOrderDetailModel model, long productSizeId) {
        ImportOrderDetailModel newModel = new ImportOrderDetailModel();
        newModel.setImportOrderId(importOrderId);
        newModel.setPriceImport(model.getPriceImport());
        newModel.setQuantityImport(model.getQuantityImport());
        newModel.setProductSizeId(productSizeId);
        return newModel;

    }

    private boolean saveOrderDetail(ImportOrderDetailModel newModel) throws SQLException {
        return orderDetailDAO.save(newModel) > 0;
    }

    @Override
    // This method imports an Excel file and processes its contents
    public boolean importFileExcel(String importOrderId, Part importFile, Collection<Part> parts) throws IOException {
        // Read the Excel file and convert its contents into a list of ImportOrderDetailModel objects
        List<ImportOrderDetailModel> list = ReadImportOrderFile.readExcel(importFile.getInputStream());

        // Create a list to store the parts of the request that contain product images
        List<Part> partList = new ArrayList<>();
        parts.forEach(part -> {
            // If the part contains a product image, add it to the list
            if (part.getName().equals("imageProducts")) {
                partList.add(part);
            }
        });

        // Create a cache to store the product IDs that have been processed
        HashMap<Long, Boolean> cacheIsUploadImg = new HashMap<>();
        HashMap<Long, ProductModel> cacheProduct = new HashMap<>();
        HashMap<String, CategoryModel> cacheCategory = new HashMap<>();
        HashMap<String, SizeModel> cacheSize = new HashMap<>();
        HashMap<String, ProductSizeModel> cacheProductSize = new HashMap<>();


        try {
            // Process each ImportOrderDetailModel in the list
            for (ImportOrderDetailModel model : list) {
                long start = System.currentTimeMillis();
                // Process the product, size, and product size of the model
                long productId = processProduct(model, cacheProduct, cacheCategory);
                long sizeId = processSize(model, cacheSize);
                long productSizeId = processProductSize(productId, sizeId, model, cacheProductSize);

                // Create a new ImportOrderDetailModel with the processed information
                ImportOrderDetailModel newModel = createImportOrderDetail(importOrderId, model, productSizeId);

                // Save the new model in the database
                // If saving fails, return false to indicate that the import was not successful
                if (!saveOrderDetail(newModel)) {
                    return false;
                }

                // If the product ID is not in the cache, add it to the cache and process the product images
                if (!cacheIsUploadImg.containsKey(productId)) {
                    cacheIsUploadImg.put(productId, true);
                    processProductImages(productId, partList);
                }
                System.out.println("Time import: " + (System.currentTimeMillis() - start) + "ms");
            }

            // If all models were processed and saved successfully, return true
            return true;
        } catch (SQLException e) {
            // If a SQLException occurs, print the stack trace and return false
            e.printStackTrace();
        }

        // If the method reaches this point, something went wrong, so return false
        return false;
    }

    // This method processes the product images associated with a given product ID
    private void processProductImages(long productId, List<Part> partList) {
        // Create an ExecutorService with a fixed thread pool of size 5
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Create a list to store Future objects representing the results of asynchronous computations
        List<Future<ProductImageModel>> futures = new ArrayList<>();

        // Get an instance of Cloudinary for image uploading
        Cloudinary cloudinary = CloudinarySingleton.getInstance().getCloudinary();

        // Create an iterator for the list of parts
        Iterator<Part> iterator = partList.iterator();

        // Create an AtomicBoolean flag and set its initial value to true
        AtomicBoolean flag = new AtomicBoolean(true);

        // Iterate over the parts
        while (iterator.hasNext()) {
            Part part = iterator.next();

            // If the submitted file name of the part contains the product ID
            if (part.getSubmittedFileName().contains(productId + "_")) {
                // Submit a task to the executor service to process the image
                futures.add(executor.submit(() -> {
                    // Get the input stream of the part
                    InputStream inputStream = part.getInputStream();

                    // Read all bytes from the input stream
                    byte[] fileBytes = inputStream.readAllBytes();

                    // Upload the image to Cloudinary and get the result
                    Map<String, Object> uploadThumbnail = cloudinary.uploader().upload(fileBytes, ObjectUtils.emptyMap());

                    // Get the URL of the uploaded image
                    String url = uploadThumbnail.get("url").toString();

                    // If the flag is true, update the thumbnail of the product and set the flag to false
                    if (flag.getAndSet(false)) {
                        productDAO.updateThumbnail(productId, url);
                    }

                    // Close the input stream
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            // Handle the exception...
                        }
                    }
                    // Build a new ProductImageModel with the product ID and image URL, and return it
                    return ProductImageModel.builder().productId(productId).imageUrl(url).build();
                }));

                // Remove the current part from the iterator
                iterator.remove();
            }
        }

        // Create a list to store the ProductImageModels
        List<ProductImageModel> imageModels = new ArrayList<>();

        // For each future in the futures list
        for (Future<ProductImageModel> future : futures) {
            try {
                // Add the result of the future to the imageModels list
                imageModels.add(future.get());
            } catch (Exception e) {
                // If an exception occurs, print the stack trace
                e.printStackTrace();
            }
        }

        // Submit a task to the executor service to save all image models
        executor.submit(() -> {
            try {
                imageService.saveAll(imageModels);
            } catch (Exception e) {
                // If an exception occurs during image saving, print the stack trace
                e.printStackTrace();
            }
        });

        // Shut down the executor service
        executor.shutdown();
    }
}
