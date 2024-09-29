package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.service.IOrderService;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.UrlUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/success-order/*"})
public class OrderSuccessController extends HttpServlet {
    @Inject
    IOrderService orderService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        String orderSlug = UrlUtil.getIdFromUrl(req, resp);
        req.setAttribute("orderSlug",orderSlug);
        OrderModel order = new OrderModel();
        order.setSlug(orderSlug);
        try {
            order = orderService.findWithFilter(order);
            if (order!=null) {
                RequestDispatcher rd = req.getRequestDispatcher("/views/web/order-success.jsp");
                rd.forward(req, resp);
            } else {
                resp.sendRedirect("/error/404");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String orderSlug = req.getParameter("orderSlug");
            OrderModel order = new OrderModel();
            order.setSlug(orderSlug);
            order = orderService.findWithFilter(order);
            if (order!=null) {
                String paymentMethod = req.getParameter("payment");
                if (!paymentMethod.equals("cod"))
                    resp.sendRedirect("/payment/"+req.getParameter("payment")+"/"+orderSlug);
                else
                    resp.sendRedirect("/order-details/"+orderSlug);
            } else {
                resp.sendRedirect("/error/404");
            }
        } catch (SQLException e) {
           resp.sendRedirect("/success-order?message=error&toast=danger");
        }
    }
}
