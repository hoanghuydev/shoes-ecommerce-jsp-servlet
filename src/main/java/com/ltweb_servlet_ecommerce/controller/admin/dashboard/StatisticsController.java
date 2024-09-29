package com.ltweb_servlet_ecommerce.controller.admin.dashboard;

import com.google.gson.Gson;
import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.service.IOrderDetailsService;
import com.ltweb_servlet_ecommerce.service.IOrderService;
import com.ltweb_servlet_ecommerce.service.IUserOrderService;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/dashboard/statictics"})
public class StatisticsController extends HttpServlet {
    @Inject
    IOrderService orderService;
    @Inject
    IUserOrderService userOrderService;
    @Inject
    IUserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String year = req.getParameter("year");
        String month = req.getParameter("month");

        if (year.isEmpty()) year = String.valueOf(LocalDate.now().getYear());
        if (month.isEmpty()) month = String.valueOf(LocalDate.now().getMonth());

        List<StatisticModel> statisticList = new ArrayList<>();
        int count = 0;

        try {
            List<UserOrderModel> userOrderList = userOrderService.findAll(null);
            for (UserOrderModel userOrderModel : userOrderList) {
                LocalDate orderDate = userOrderModel.getCreateAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (orderDate.getYear() == Integer.parseInt(year)
                        && orderDate.getMonthValue() == Integer.parseInt(month))
                {
                    UserModel userModel = userService.findById(userOrderModel.getUserId());
                    OrderModel orderModel = orderService.findById(userOrderModel.getOrderId());

                    StatisticModel statisticModel = new StatisticModel();
                    count++;
                    statisticModel.setStt(count);
                    statisticModel.setUserId(userModel.getId());
                    statisticModel.setUserFullName(userModel.getFullName());
                    statisticModel.setOrderId(orderModel.getId());
                    statisticModel.setOrderPrice(orderModel.getTotalAmount());
                    statisticModel.setOrderTime(orderModel.getCreateAt());

                    statisticList.add(statisticModel);
                }
            }

            //gson
            PrintWriter out = resp.getWriter();
            Gson gson = new Gson();
            String json = gson.toJson(statisticList);
            out.println(json);
            out.flush();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
