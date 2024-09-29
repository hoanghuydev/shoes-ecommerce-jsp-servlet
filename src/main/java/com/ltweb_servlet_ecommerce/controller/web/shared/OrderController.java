package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/orders"})
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
    @Inject
    ISizeService sizeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Set up notifications for the request
            NotifyUtil.setUp(req);

            // Set up paging and sorting for the order list
//            Pageble orderPaging = PagingUtil.setUpPagingAndSort(req);

            // Retrieve all orders with the specified paging and sorting
            Long userId = ((UserModel)SessionUtil.getValue(req, SystemConstant.USER_MODEL)).getId();
            List<OrderModel> listOrder = orderService.findByUserId(userId);

            // Iterate over each order
//            for (int i = 0; i < listOrder.size(); i++) {
//                // Get the current order
//                OrderModel orderModel = listOrder.get(i);
//
//                // Find the address associated with the current order
//                AddressModel addressModel = addressService.findById(orderModel.getAddressId());
//
//                // Create a new OrderDetailsModel and set its orderId to the current order's id
//                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
//                orderDetailsModel.setOrderId(orderModel.getId());
//
//                // Find all order details associated with the current order
//                List<OrderDetailsModel> listOrderDetails = orderDetailsService.findAllWithFilter(orderDetailsModel, null);
//
//                // Create a new list to hold the products associated with the current order
//                List<ProductModel> listProduct = new ArrayList<>();
//
//                // Iterate over each order detail
//                for (OrderDetailsModel orderDetails : listOrderDetails) {
//                    // Add the product associated with the current order detail to the product list
//                    new CartUtil().setListProductFromOrderDetails(listProduct, orderDetails, productSizeService, productService, sizeService);
//                }
//
//                // Set the address of the current order
//                orderModel.setAddressModel(addressModel);
//
//                // Set the product list of the current order
//                orderModel.setListProduct(listProduct);
//
//                // Update the current order in the order list
//                listOrder.set(i, orderModel);
//            }
            req.setAttribute("orders", listOrder);
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/dash-my-order.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect("/home?message=error&toast=danger");
        }
    }
}
