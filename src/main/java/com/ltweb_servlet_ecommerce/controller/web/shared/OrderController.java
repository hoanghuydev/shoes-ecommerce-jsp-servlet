package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.AddressModel;
import com.ltweb_servlet_ecommerce.model.OrderDetailsModel;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.utils.CartUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/order-details*","/order-details/*"})
public class OrderController extends HttpServlet {
    @Inject
    IOrderService orderService;
    @Inject
    IOrderDetailsService orderDetailsService;
    @Inject
    IAddressService addressService;
    @Inject
    IProductService productService;
    @Inject
    IProductSizeService productSizeService;
    @Inject ISizeService sizeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NotifyUtil.setUp(req);
            String slug = UrlUtil.getIdFromUrl(req, resp);
            List<Object> params = new ArrayList<>();
            params.add(slug);
            Map<String,Object> result = orderService.findWithCustomSQL("select id from `order` where slug=?",params);

            if (result.get("id")!=null) {
                OrderModel order = orderService.findById((Long) result.get("id"));
                req.setAttribute("ORDER_MODEL",order);
                AddressModel addressModel = addressService.findById(order.getAddressId());
                req.setAttribute("ADDRESS_MODEL",addressModel);
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setOrderId(order.getId());
                List<OrderDetailsModel> listOrderDetails = orderDetailsService.findAllWithFilter(orderDetailsModel,null);
                List<ProductModel> listProduct = new ArrayList<>();
                for (OrderDetailsModel orderDetails : listOrderDetails) {
                    new CartUtil().setListProductFromOrderDetails(listProduct,orderDetails,productSizeService,productService,sizeService);
                }
                req.setAttribute("LIST_PRODUCT",listProduct);
                RequestDispatcher rd = req.getRequestDispatcher("/views/web/order.jsp");
                rd.forward(req, resp);
            } else {
                resp.sendRedirect("/error/404");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            resp.sendRedirect("/home?message=error&toast=danger");
        }
    }
}
