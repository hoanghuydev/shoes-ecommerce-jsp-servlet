package com.ltweb_servlet_ecommerce.controller.admin.dashboard;

import com.google.gson.Gson;
import com.ltweb_servlet_ecommerce.dao.impl.OrderDAO;
import com.ltweb_servlet_ecommerce.model.OrderModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/dashboard/barchart"})
public class BarChartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String year = req.getParameter("year");
        if (year == null || year.isEmpty()) {
            year = String.valueOf(LocalDate.now().getYear());
        }

        OrderDAO orderDAO = new OrderDAO();
        List<Integer> list = new ArrayList<>();

        try {
            for (int i = 1; i <= 12; i++) {
                List<OrderModel> models = orderDAO.findByYear(year, String.valueOf(i));
                if (models != null) {
                    list.add(models.size());
                } else {
                    list.add(0);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Convert list to json
        String json = new Gson().toJson(list);

        // Set the response type to JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Send the JSON response
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
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
