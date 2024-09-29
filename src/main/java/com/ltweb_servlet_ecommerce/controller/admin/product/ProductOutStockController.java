package com.ltweb_servlet_ecommerce.controller.admin.product;

import com.ltweb_servlet_ecommerce.service.IProductService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/product/out-of-stock"})
public class ProductOutStockController extends HttpServlet {
    @Inject
    private IProductService productService;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productService.findOutOfStock());
        request.getRequestDispatcher("/views/admin/product/out-of-stock.jsp").forward(request, response);
    }
}
