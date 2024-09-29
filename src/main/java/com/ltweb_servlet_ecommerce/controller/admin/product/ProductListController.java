package com.ltweb_servlet_ecommerce.controller.admin.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.ProductSizeModel;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.validate.Validator;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Base64;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@WebServlet(urlPatterns = {"/admin/product/list"})
@MultipartConfig
public class ProductListController  extends HttpServlet {
    @Inject
    IProductService productService;
    @Inject
    IProductImageService productImageService;
    @Inject
    ICategoryService categoryService;
    @Inject
    ISizeService sizeService;
    @Inject
    IProductSizeService productSizeService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        try {
            req.setAttribute(SystemConstant.LIST_MODEL,productService.findAll(null));
            req.setAttribute("LIST_CATEGORY",categoryService.findAll(null));
            req.setAttribute("LIST_SIZE",sizeService.findAll(null));
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/product/list.jsp");
            rd.forward(req,resp);
        } catch (Exception e) {
            resp.sendRedirect("/admin/product/list?message=error&toast=danger");
        }

    }
    private static String encodeFileToBase64(Part filePart) throws IOException {
        InputStream fileInputStream = filePart.getInputStream();
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        String encoded = Base64.getEncoder().encodeToString(bytes);
        String base64Url = "data:" + filePart.getContentType() + ";base64," + encoded;
        return base64Url;
    }
    private boolean validateAddProduct(ProductModel product,String... others) {
        if (!Validator.isNotNullOrEmpty(product.getName())
        || !Validator.isNotNullOrEmpty(product.getContent())
        || !Validator.isNotNullOrEmpty(product.getShortDescription())
        || product.getPrice()==null || product.getPrice() == 0
        || product.getCategoryId()==null || product.getCategoryId() == 0
        ) return false;
        for (String other : others) {
            if (!Validator.isNotNullOrEmpty(other)) return false;
        }
        return true;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "da5wewzih",
                "api_key", "594116454729532",
                "api_secret", "ZmY7c6OjD1xJImXIH2eYGRSayQs"
        ));
        cloudinary.config.secure = true;
        try {

            ProductModel productModel = FormUtil.toModel(ProductModel.class,req);
            Part thumbnailPart = req.getPart("thumbnailProduct");
            String thumbnailProduct = encodeFileToBase64(thumbnailPart);
            if (validateAddProduct(productModel,thumbnailProduct)) {
                resp.sendRedirect("/admin/product/list?message=field_is_blank&toast=danger");
                return;
            }
            Map<String, Object> uploadThumbnail = cloudinary.uploader().upload(thumbnailProduct, ObjectUtils.emptyMap());
            String thumbnailProductUrl = (String) uploadThumbnail.get("url");
            productModel.setThumbnail(thumbnailProductUrl);
            productModel = productService.save(productModel);
            if (productModel!=null) {
                String[] sizesId = req.getParameterValues("sizeId[]");
                String[] listSizePrice = req.getParameterValues("sizePrice[]");
                for (int i = 0; i < sizesId.length; i++) {
                    String sizeId = sizesId[i];
                    String price = listSizePrice[i];
                    ProductSizeModel productSizeModel = new ProductSizeModel();
                    productSizeModel.setProductId(productModel.getId());
                    productSizeModel.setSizeId(Long.parseLong(sizeId));
                    productSizeModel.setPrice(Double.parseDouble(price));
                    productSizeModel = productSizeService.save(productSizeModel);
                    if (productSizeModel==null) {
                        resp.sendRedirect("/admin/product/list?message=error&toast=danger");
                        return;
                    }
                }
                Collection<Part> fileParts = req.getParts();
                for (Part part : fileParts) {
                    if (part.getName().equals("imageProduct")) {
                        String imageUrl = encodeFileToBase64(part);
                        Map<String, Object> uploadResult = cloudinary.uploader().upload(imageUrl, ObjectUtils.emptyMap());
                        String imageProductUrl = (String) uploadResult.get("url");
                        ProductImageModel productImageModel = new ProductImageModel();
                        productImageModel.setProductId(productModel.getId());
                        productImageModel.setImageUrl(imageProductUrl);
                        productImageModel= productImageService.save(productImageModel);
                        if (productImageModel==null) {
                            resp.sendRedirect("/admin/product/list?message=error&toast=danger");
                            return;
                        }
                    } else if (part.getName().equals("model")) {
                        if (part!=null) {
                            productModel.setModelUrl("");
                            productModel = productService.update(productModel);
                        }
                    }
                }
                resp.sendRedirect("/admin/product/list?message=add_success&toast=success");
            } else {
                resp.sendRedirect("/admin/product/list?message=error&toast=danger");
            }
        } catch ( Exception e) {
            System.out.println(e.getMessage());
            resp.sendRedirect("/admin/product/list?message=error&toast=danger");
        }
    }
    private String uploadFileToFirebase(Part partModel) throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
//                InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountFirebase.json");
            FileInputStream serviceAccount = new FileInputStream("E:\\New\\Become Dev\\LTWEB_Servlet_Ecommerce\\src\\main\\resources\\serviceAccountFirebase.json");
            // Use the InputStream as needed
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        }
        if (FirebaseApp.getApps().isEmpty()) {
            System.out.println("Empty");
        }
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get("lovepoint-744b7.appspot.com");
        String fileName = "test.obj";
        String filePath = "shoes_ecommerce_servlet/model/" + fileName;
        String contentType = partModel.getContentType();
        InputStream fileStream = partModel.getInputStream();
        BlobId blobId = BlobId.of("lovepoint-744b7.appspot.com", filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
        Blob blob = storage.create(blobInfo, fileStream);
        String objectUrl = blob.getMediaLink();
        return objectUrl;
    }
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        if(!contentDisposition.contains("filename=")) {
            return null;
        }
        int beginIndex = contentDisposition.indexOf("filename=")+10;
        int endIndex = contentDisposition.length()-1;
        return contentDisposition.substring(beginIndex,endIndex);
    }



}
