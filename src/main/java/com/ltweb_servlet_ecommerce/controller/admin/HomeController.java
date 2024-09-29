package com.ltweb_servlet_ecommerce.controller.admin;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/admin", "/admin/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Integer> listYear = listYear();
        req.setAttribute("listYear", listYear);
        RequestDispatcher rd = req.getRequestDispatcher("/views/admin/home.jsp");

        rd.forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    private List<Integer> listYear() {
        List<Integer> list = new ArrayList<>();
        int firstYear = 2020;
        int currentYear = LocalDate.now().getYear();
        for (int i = firstYear; i <= currentYear; i++) {
            list.add(i);
        }
        return list;
    }
}
