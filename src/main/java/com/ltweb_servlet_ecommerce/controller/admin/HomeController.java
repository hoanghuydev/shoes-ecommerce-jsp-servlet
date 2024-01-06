package com.ltweb_servlet_ecommerce.controller.admin;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin", "/admin/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/views/admin/home.jsp");
        rd.forward(req,resp);

    }
}
