package com.ltweb_servlet_ecommerce.controller.admin.ajax.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.service.IProductService;
import com.ltweb_servlet_ecommerce.utils.AuthRole;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet(urlPatterns = "/ajax/admin/product")
public class ProductAjaxAdmin extends HttpServlet {
    @Inject
    IProductService productService;
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthRole.checkPermissionAjax(resp,req,objectMapper, SystemConstant.ADMIN_ROLE);
        try {
            String productIdStr = req.getParameter("productId");
            Long productId = Long.parseLong(productIdStr);
            ProductModel product = productService.softDelete(productId);
            if (product != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"status\":\"success\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"status\":\"error\"}");
            }
        } catch (SQLException e) {
            HttpUtil.returnError500Json(objectMapper,resp,e.getMessage());
        }

    }
}
