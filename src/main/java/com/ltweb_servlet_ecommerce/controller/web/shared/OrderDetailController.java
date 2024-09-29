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

@WebServlet("/order-details/*")
public class OrderDetailController extends HttpServlet {
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
    @Inject
    ISizeService sizeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Set up notifications for the request
            NotifyUtil.setUp(req);

            // Extract the slug from the URL
            String slug = UrlUtil.getIdFromUrl(req, resp);

            // Create a list to hold parameters for the order service
            List<Object> params = new ArrayList<>();
            params.add(slug);

            // Find the order by its slug
            Map<String, Object> result = orderService.findIdBySlug(params);

            // If the order was found
            if (result.get("id") != null) {
                // Retrieve the order by its ID
                OrderModel order = orderService.findById((Long) result.get("id"));

                // Set the order as an attribute of the request
                req.setAttribute("ORDER_MODEL", order);

                // Find the address associated with the order
                AddressModel addressModel = addressService.findById(order.getAddressId());

                // Set the address as an attribute of the request
                req.setAttribute("ADDRESS_MODEL", addressModel);

                // Create a new OrderDetailsModel and set its orderId to the current order's id
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setOrderId(order.getId());

                // Find all order details associated with the current order
                List<OrderDetailsModel> listOrderDetails = orderDetailsService.findAllWithFilter(orderDetailsModel, null);

                // Create a new list to hold the products associated with the current order
                List<ProductModel> listProduct = new ArrayList<>();

                // For each order detail, add the associated product to the product list
                for (OrderDetailsModel orderDetails : listOrderDetails) {
                    new CartUtil().setListProductFromOrderDetails(listProduct, orderDetails, productSizeService, productService, sizeService);
                }

                // Set the product list as an attribute of the request
                req.setAttribute("LIST_PRODUCT", listProduct);

                // Forward the request to the order view
                RequestDispatcher rd = req.getRequestDispatcher("/views/web/order.jsp");
                rd.forward(req, resp);
            } else {
                // If the order was not found, redirect to the 404 error page
                resp.sendRedirect("/error/404");
            }
        } catch (Exception e) {
            // If an exception occurs, print the stack trace and redirect to the home page with an error message
            System.out.println(e.toString());
            resp.sendRedirect("/home?message=error&toast=danger");
        }
    }
}
