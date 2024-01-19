package com.ltweb_servlet_ecommerce.utils;

import com.ltweb_servlet_ecommerce.model.CartModel;
import com.ltweb_servlet_ecommerce.model.OrderDetailsModel;
import com.ltweb_servlet_ecommerce.service.ICartService;
import com.ltweb_servlet_ecommerce.service.IOrderDetailsService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class CartUtil {

    public static void setCartFromSessionForUser(SessionUtil sessionUtil, HttpServletRequest req, IOrderDetailsService orderDetailsService, ICartService cartService, Long userId) throws SQLException {
        List<OrderDetailsModel> orderDetailsModelList = (List<OrderDetailsModel>) SessionUtil.getInstance().getValue(req,"LIST_ORDER_DETAILS");
        if (orderDetailsModelList!=null) {
            for (OrderDetailsModel orderDetailsModel : orderDetailsModelList) {
                orderDetailsModel.setId(null);
                orderDetailsModel = orderDetailsService.save(orderDetailsModel);
                CartModel cartModel = new CartModel();
                cartModel.setOrderDetailsId(orderDetailsModel.getId());
                cartModel.setUserId(userId);
                cartService.save(cartModel);
            }
            SessionUtil.getInstance().removeValue(req,"LIST_ORDER_DETAILS");
        }
    }
}
