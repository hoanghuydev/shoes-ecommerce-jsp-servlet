package com.ltweb_servlet_ecommerce.controller.web.ajax.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.service.IProductService;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.UrlUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/product/view/*"})
public class ProductView extends HttpServlet {
    @Inject
    IProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idProduct = Long.parseLong(UrlUtil.getIdFromUrl(req,resp));
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProductModel productModel = productService.updateProductTotalView(idProduct);
            objectMapper.writeValue(resp.getOutputStream(), productModel);
        } catch (Exception e) {
            HttpUtil.returnError500Json(objectMapper,resp,e.toString());
        }
    }
}
