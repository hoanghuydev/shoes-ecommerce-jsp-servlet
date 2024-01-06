package com.ltweb_servlet_ecommerce.controller.web.shared;


import com.ltweb_servlet_ecommerce.constant.SystemConstant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/about"})
public class AboutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tab", SystemConstant.ABOUT_TAB);
        RequestDispatcher rd = req.getRequestDispatcher("/views/web/about.jsp");
        rd.forward(req,resp);

    }
}
