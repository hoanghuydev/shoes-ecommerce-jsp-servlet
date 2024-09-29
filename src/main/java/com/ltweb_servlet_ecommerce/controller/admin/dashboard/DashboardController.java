package com.ltweb_servlet_ecommerce.controller.admin.dashboard;

import com.google.gson.Gson;
import com.ltweb_servlet_ecommerce.dao.impl.OrderDAO;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/admin/dashboard"})
public class DashboardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NotifyUtil.setUp(request);
        List<Integer> listYear = listYear();
        request.setAttribute("listYear", listYear);
        RequestDispatcher rd = request.getRequestDispatcher("/views/admin/home.jsp");
        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
