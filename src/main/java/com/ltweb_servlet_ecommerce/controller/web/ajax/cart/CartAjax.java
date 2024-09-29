package com.ltweb_servlet_ecommerce.controller.web.ajax.cart;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.model.CartModel;
import com.ltweb_servlet_ecommerce.model.OrderDetailsModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.ICartService;
import com.ltweb_servlet_ecommerce.service.IOrderDetailsService;
import com.ltweb_servlet_ecommerce.service.IProductSizeService;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.RuntimeInfo;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/ajax/cart"})
public class CartAjax extends HttpServlet {
    @Inject
    IProductSizeService productSizeService;
    @Inject
    ICartService cartService;
    @Inject
    IOrderDetailsService orderDetailsService;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addAndUpdateCart(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addAndUpdateCart(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addAndUpdateCart(req, resp);
    }

    private void addAndUpdateCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode dataJson = HttpUtil.of(req.getReader()).toJsonNode(objectMapper);
            Long productId = Long.parseLong(String.valueOf(dataJson.get("productId")));
            Long sizeId = Long.parseLong(String.valueOf(dataJson.get("sizeId")));
            Integer quantity = Integer.parseInt(String.valueOf(dataJson.get("quantity")));
            UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, "USER_MODEL");
            List<OrderDetailsModel> orderDetailsModelList = null;
            //Get id product size and subTotal
            String sqlProductSizeId = " select product_sizes.id as productSizeId, product_sizes.price as priceProduct from product_sizes,products where product_sizes.productId = products.id and product_sizes.productId = ? and product_sizes.sizeId = ?";
            List<Object> params = new ArrayList<>();
            params.add(productId);
            params.add(sizeId);
            Map<String, Object> resultProductSizeId = productSizeService.findWithCustomSQL(sqlProductSizeId, params);
            Long productSizeId = Long.parseLong(resultProductSizeId.get("productSizeId").toString());
            Double price = Double.parseDouble(resultProductSizeId.get("priceProduct").toString());
            Double subTotal = price * quantity;
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setProductSizeId(productSizeId);
            orderDetailsModel.setQuantity(quantity);
            orderDetailsModel.setSubTotal(subTotal);
            if (user != null) {
                String sqlExistCartItem = "select order_details.id as id from carts,order_details,product_sizes " +
                        "where carts.orderDetailsId = order_details.id " +
                        "and order_details.productSizeId = ? " +
                        "and carts.userId = ?";
                List<Object> paramsExistCartItem = new ArrayList<>();
                paramsExistCartItem.add(productSizeId);
                paramsExistCartItem.add(user.getId());
                Map<String, Object> resultExistCartItem = orderDetailsService.findWithCustomSQL(sqlExistCartItem, paramsExistCartItem);
//            Update quantity and subTotal if already exist product and size in cart
                CartModel cartModel = new CartModel();

                if (resultExistCartItem.get("id") != null) {
                    Long orderDetailsId = Long.parseLong(resultExistCartItem.get("id").toString());
                    if (req.getMethod().equalsIgnoreCase("DELETE") || quantity < 1) {
                        cartModel.setOrderDetailsId(orderDetailsId);
                        cartModel = cartService.findWithFilter(cartModel);
                        cartService.delete(cartModel.getId());
                        orderDetailsModel = orderDetailsService.delete(orderDetailsId);
                        objectMapper.writeValue(resp.getOutputStream(), orderDetailsModel);

                    } else {
                        orderDetailsModel.setQuantity(quantity);
                        orderDetailsModel.setId(orderDetailsId);
                        orderDetailsModel = orderDetailsService.update(orderDetailsModel);
                        objectMapper.writeValue(resp.getOutputStream(), orderDetailsModel);
                    }
                } else {
                    //Save new cart if not exist
                    orderDetailsModel = orderDetailsService.save(orderDetailsModel);
                    cartModel.setOrderDetailsId(orderDetailsModel.getId());
                    cartModel.setUserId(user.getId());
                    cartModel = cartService.save(cartModel);
                    objectMapper.writeValue(resp.getOutputStream(), cartModel);
                }
            } else {
                OrderDetailsModel existOrderDetails = null;
                orderDetailsModelList = (List<OrderDetailsModel>) SessionUtil.getInstance().getValue(req, "LIST_ORDER_DETAILS");
                List<OrderDetailsModel> orderDetailsOld = null;
                if (orderDetailsModelList == null) {
                    orderDetailsModelList = new ArrayList<>();
                    orderDetailsModelList.add(orderDetailsModel);
                    orderDetailsOld = new ArrayList<>();
                } else {
                    orderDetailsOld = new ArrayList<>(orderDetailsModelList);
                    existOrderDetails = orderDetailsModelList.stream()
                            .filter(orderDetails -> orderDetails.getProductSizeId().equals(productSizeId))
                            .findFirst()
                            .orElse(null);
//                    If exist orderDetails in session then update quantity
                    if (existOrderDetails != null) {
                        Integer indexOfOrderDetails = orderDetailsModelList.indexOf(existOrderDetails);
                        if (req.getMethod().equalsIgnoreCase("DELETE") || quantity < 1) {
                            orderDetailsModelList.remove(indexOfOrderDetails.intValue());
                        } else {
                            orderDetailsModelList.set(indexOfOrderDetails, orderDetailsModel);
                        }
                    } else {
                        orderDetailsModelList.add(orderDetailsModel);
                    }
                }
                SessionUtil.getInstance().putValue(req, "LIST_ORDER_DETAILS", orderDetailsModelList);

                // LogController the activity of adding or updating order details
                logging(existOrderDetails, orderDetailsOld, orderDetailsModelList);

                objectMapper.writeValue(resp.getOutputStream(), orderDetailsModel);
            }
        } catch (Exception e) {
            HttpUtil.returnError500Json(objectMapper, resp, e.toString());
        }
    }

    private void logging(OrderDetailsModel existOrderDetails, List<OrderDetailsModel> orderDetailsOld,
                         List<OrderDetailsModel> orderDetailsModel) {
        String logStatus;
        String logAction;
        if (existOrderDetails != null) {
            logStatus = "Updated cart";
            logAction = "UPDATE";
        } else {
            logStatus = "Added into cart";
            logAction = "INSERT";
        }

        JSONArray preValueArray = new JSONArray();
        for (OrderDetailsModel oldOrderDetail : orderDetailsOld) {
            preValueArray.put(new JSONObject(oldOrderDetail));
        }

        JSONArray valueArray = new JSONArray();
        for (OrderDetailsModel newOrderDetail : orderDetailsModel) {
            valueArray.put(new JSONObject(newOrderDetail));
        }

        JSONObject preValue = new JSONObject()
                .put(SystemConstant.VALUE_LOG, preValueArray);

        JSONObject value = new JSONObject()
                .put(SystemConstant.STATUS_LOG, logStatus)
                .put(SystemConstant.VALUE_LOG, valueArray);

        LoggerHelper.log(SystemConstant.WARN_LEVEL, logAction,
                RuntimeInfo.getCallerClassNameAndLineNumber(), preValue, value);
    }

}
