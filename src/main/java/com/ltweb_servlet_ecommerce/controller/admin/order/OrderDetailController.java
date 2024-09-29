package com.ltweb_servlet_ecommerce.controller.admin.order;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.utils.CartUtil;
import com.ltweb_servlet_ecommerce.utils.StatusMapUtil;
import com.ltweb_servlet_ecommerce.validate.Validator;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/admin/orders/detail", name = "orderAdminServlet")
public class OrderDetailController extends HttpServlet {
    @Inject
    IOrderService orderService;
    @Inject
    IAddressService addressService;
    @Inject
    IOrderDetailsService orderDetailsService;
    @Inject
    IProductService productService;
    @Inject
    IProductSizeService productSizeService;
    @Inject
    ISizeService sizeService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            OrderModel orderModel = orderService.findById(id);
            AddressModel addressModel = addressService.findById(orderModel.getAddressId());
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setOrderId(orderModel.getId());
            List<OrderDetailsModel> listOrderDetails = orderDetailsService.findAllWithFilter(orderDetailsModel, null);

            if (listOrderDetails.isEmpty()) {
                response.sendRedirect("/admin/order/list");
                return;
            }

            List<ProductModel> listProduct = new ArrayList<>();
            for (OrderDetailsModel orderDetails : listOrderDetails) {
                new CartUtil().setListProductFromOrderDetails(listProduct, orderDetails, productSizeService, productService, sizeService);
            }
            orderModel.setAddressModel(addressModel);
            orderModel.setListProduct(listProduct);
            request.setAttribute("order", orderModel);
            request.setAttribute("status", StatusMapUtil.getStatusMap());
        } catch (SQLException | NumberFormatException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            response.sendRedirect("/admin/order/list");
            return;
        }
        request.getRequestDispatcher("/views/admin/order/admin-order-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();

        // Parse the JSON object
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);

        // Extract the status and orderId
        String status = jsonObject.get("status").getAsString();
        Long orderId = jsonObject.get("orderId").getAsLong();
        Type listProductType = new TypeToken<List<ProductModel>>() {
        }.getType();
        List<ProductModel> listProduct = gson.fromJson(jsonObject.get("listProduct"), listProductType);
        if (!isValidate(status, orderId, listProduct)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            OrderModel order = validateAndGetOrder(orderId, status);
            updateOrderDetails(order, listProduct);
            updateOrderStatus(order, status);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidate(String status, Long orderId, List<ProductModel> listProduct) {
        if (!Validator.isNotNullOrEmpty(status) || orderId == null || listProduct == null) {
            return false;
        }
        return true;

    }

    private void updateOrderStatus(OrderModel order, String status) throws SQLException {
        order.setStatus(status);
        orderService.update(order);
    }

    private void updateOrderDetails(OrderModel order, List<ProductModel> listProduct) throws SQLException {
        for (ProductModel product : listProduct) {
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setOrderId(order.getId());
            orderDetailsModel.setProductSizeId(product.getProductSizeId());
            orderDetailsModel = orderDetailsService.findWithFilter(orderDetailsModel);

            if (orderDetailsModel == null) {
                return;
            }
            if (orderDetailsModel.getQuantity().intValue() != product.getQuantity().intValue()) {
                ProductSizeModel productSize = productSizeService.findById(product.getProductSizeId());
                double oldSubTotal = orderDetailsModel.getSubTotal();
                double newSubTotal = product.getQuantity() * productSize.getPrice();

                orderDetailsModel.setSubTotal(newSubTotal);
                orderDetailsModel.setQuantity(product.getQuantity());

                order.setTotalAmount(order.getTotalAmount() - oldSubTotal + newSubTotal);
                orderDetailsService.update(orderDetailsModel);
            }
        }
    }

    private OrderModel validateAndGetOrder(Long orderId, String status) throws IOException, SQLException {
        OrderModel order = orderService.findById(orderId);
        if (order == null) {
            throw new IOException("Order not found");
        }
        if (order.getStatus().equals("ORDER_DELIVERED") || order.getStatus().equals("ORDER_CANCEL")) {
            throw new IOException("Cannot modify a finalized order");
        }
        if (order.getStatus().equals("ORDER_TRANSPORTING") && status.equals("ORDER_PROCESSING")) {
            throw new IOException("Invalid status change");
        }
        return order;
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(request.getReader());

        try {
            if (jsonTree.isJsonObject()) {
                long orderId = jsonTree.getAsJsonObject().get("orderId").getAsLong();
                long productSizeId = jsonTree.getAsJsonObject().get("productSizeId").getAsLong();
                if (orderId == 0 || productSizeId == 0) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                    // Soft delete the order details (set isDeleted to true)
                    boolean isDeleted = orderDetailsService.softDelete(orderId, productSizeId);

                    response.setStatus(isDeleted ? HttpServletResponse.SC_NO_CONTENT : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
