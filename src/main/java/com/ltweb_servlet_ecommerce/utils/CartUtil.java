package com.ltweb_servlet_ecommerce.utils;

import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.service.*;

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
    public void setListProductFromOrderDetails(List<ProductModel> productModelList, OrderDetailsModel orderDetailsModel,IProductSizeService productSizeService,IProductService productService,ISizeService sizeService) throws SQLException {
        ProductSizeModel productSizeModel = productSizeService.findById(orderDetailsModel.getProductSizeId());
        SizeModel sizeModel = sizeService.findById(productSizeModel.getSizeId());
        ProductModel productModel = productService.findById(productSizeModel.getProductId());
        productModel.setQuantity(orderDetailsModel.getQuantity());
        productModel.setAvailable(productSizeService.getAvailableProducts(orderDetailsModel.getProductSizeId()));
        productModel.setSizeName(sizeModel.getName());
        productModel.setSizeId(sizeModel.getId());
        productModel.setSubTotal(orderDetailsModel.getSubTotal());
        productModel.setPrice(productSizeModel.getPrice());
        productModel.setProductSizeId(productSizeModel.getId());
        productModel.setAvailable(productSizeService.getAvailableProducts(productSizeModel.getId()));
        productModelList.add(productModel);
    }
}
