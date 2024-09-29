package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.google.gson.Gson;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.sort.Sorter;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.UrlUtil;
import org.apache.commons.text.StringEscapeUtils;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/product-details/*"})
public class ProductController extends HttpServlet {
    @Inject
    IProductService productService;
    @Inject
    IProductSizeService productSizeService;
    @Inject
    ISizeService sizeService;
    @Inject
    IOpinionService opinionService;
    @Inject
    IProductImageService productImageService;
    @Inject ICategoryService categoryService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idProduct = Long.parseLong(UrlUtil.getIdFromUrl(req,resp));
        try {
            ProductModel productModel = new ProductModel();
            productModel = productService.findById(idProduct);
            if (productModel!=null) {
                productModel.setContent(StringEscapeUtils.unescapeHtml4(productModel.getContent()));
                req.setAttribute(SystemConstant.MODEL,productModel);
//                Get List Image
                ProductImageModel productImageModel = new ProductImageModel();
                productImageModel.setProductId(productModel.getId());
                req.setAttribute("LIST_IMAGE",productImageService.findAllWithFilter(productImageModel,null));
//                Get category product
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setId(productModel.getCategoryId());
                req.setAttribute("CATEGORY",categoryService.findWithFilter(categoryModel));
//                Get Size Product
                ProductSizeModel productSizeModel = new ProductSizeModel();
                productSizeModel.setProductId(productModel.getId());
                List<ProductSizeModel> productSizeModelList = productSizeService.findAllWithFilter(productSizeModel,null);
                Gson gson = new Gson();
                String jsonList = gson.toJson(productSizeModelList);
                req.setAttribute("LIST_PRODUCT_SIZE", jsonList);
                List<Object> listSizeId = new ArrayList<>();
                for (ProductSizeModel productSizeModel1 : productSizeModelList) {
                    listSizeId.add(productSizeModel1.getSizeId());
                }
                List<SubQuery> subQueryList = new ArrayList<>();
                subQueryList.add(new SubQuery("id","in",listSizeId));
                Pageble pagebleSizename = new PageRequest(1,8,new Sorter("name", "asc"));
                List<SizeModel> sizeModelList = sizeService.findByColumnValues(subQueryList,pagebleSizename);
                req.setAttribute("LIST_SIZE",sizeModelList);
//                Get recommned product
                ProductModel recommnedProductModel = new ProductModel();
                recommnedProductModel.setCategoryId(productModel.getCategoryId());
                Pageble pagebleRecommnedProduct = new PageRequest(1,8,new Sorter("totalViewAndSearch", "desc"));
                List<ProductModel> recommnedProductList = productService.findAllWithFilter(recommnedProductModel,pagebleRecommnedProduct);
                req.setAttribute("LIST_RECOMMED_PRODUCT",recommnedProductList);
//                Render View
                RequestDispatcher rd = req.getRequestDispatcher("/views/web/product-details.jsp");
                rd.forward(req, resp);
            } else {
                resp.sendRedirect("/error/404");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
