package com.ltweb_servlet_ecommerce.controller.admin.product;

import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.ProductSizeModel;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/product/update"})
@MultipartConfig
public class ProductUpdateController extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NotifyUtil.setUp(request);
        try {
            long id = Long.parseLong(request.getParameter("id"));
            request.setAttribute("productSizes",
                    productSizeService.findAllWithFilter(ProductSizeModel.builder().productId(id).build(), null));
            request.setAttribute("images",
                    productImageService.findAllWithFilter(ProductImageModel.builder().productId(id).build(), null));
            request.setAttribute("product", productService.findById(id));
            request.setAttribute("LIST_CATEGORY", categoryService.findAll(null));
            request.setAttribute("LIST_SIZE", sizeService.findAll(null));
            request.getRequestDispatcher("/views/admin/product/edit-product.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/admin/product/list?message=error&toast=danger");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Convert the request parameters to a ProductModel object
        ProductModel productModel = null;
        try {
            productModel = FormUtil.toModel(ProductModel.class, request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Get the thumbnail image from the request
        Part thumbnailPart = request.getPart("thumbnailProduct");

        // Get the sizes and prices from the request
        String[] sizesId = request.getParameterValues("sizeId[]");
        String[] listSizePrice = request.getParameterValues("sizePrice[]");

        // Get the product images from the request
        List<Part> images = request.getParts().stream().filter(part -> part.getName().equals("imageProduct") &&
                !part.getSubmittedFileName().isEmpty()).toList();

        // Get the IDs of the images to be removed from the request
        long[] removeImgs = null;
        try {
            String ids = request.getParameter("removeImgs");
            removeImgs = ids == null || ids.isEmpty() ? null :
                    Arrays.stream(ids.split(",")).mapToLong(Long::parseLong).toArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Validate the input data
        boolean validate = validate(productModel, sizesId, listSizePrice);
        if (!validate) {
            response.sendRedirect("/admin/product/list?message=error&toast=danger");
            return;
        }

        // Update the product
        boolean isUpdate = productService.updateProduct(productModel, thumbnailPart,
                sizesId, listSizePrice, images, removeImgs);

        // Redirect to the product list page with a success or error message
        if (isUpdate)
            response.sendRedirect("/admin/product/list?message=add_success&toast=success");
        else
            response.sendRedirect("/admin/product/list?message=error&toast=danger");
    }

    private boolean validate(ProductModel productModel, String[] sizesId, String[] listSizePrice) {
        if (productModel == null || sizesId == null || listSizePrice == null) {
            return false;
        }
        for (String sizeId : sizesId) {
            if (sizeId.isEmpty()) {
                return false;
            }
        }
        for (String price : listSizePrice) {
            if (price.isEmpty()) {
                return false;
            }
        }
        return true;
    }


}
