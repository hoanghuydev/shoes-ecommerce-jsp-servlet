package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.model.CartModel;
import com.ltweb_servlet_ecommerce.model.OrderDetailsModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.utils.CartUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.RuntimeInfo;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;
import org.json.JSONObject;

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

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
    @Inject
    ICartService cartService;
    @Inject
    IOrderDetailsService orderDetailsService;
    @Inject
    IProductService productService;
    @Inject
    IProductSizeService productSizeService;
    @Inject
    ISizeService sizeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NotifyUtil.setUp(req);
            UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, "USER_MODEL");
            if (user == null) {
                List<OrderDetailsModel> orderDetailsModelList = (List<OrderDetailsModel>) SessionUtil.getInstance().getValue(req, "LIST_ORDER_DETAILS");
                List<ProductModel> productModelList = new ArrayList<>();
                if (orderDetailsModelList != null) {
                    for (OrderDetailsModel orderDetailsModel : orderDetailsModelList) {
                        new CartUtil().setListProductFromOrderDetails(productModelList, orderDetailsModel, productSizeService, productService, sizeService);
                    }
                }
                req.setAttribute("LIST_PRODUCT_OF_CART", productModelList);
            } else {
                CartModel cartModelSql = new CartModel();
                cartModelSql.setUserId(user.getId());
                List<CartModel> cartModelList = cartService.findAllWithFilter(cartModelSql, null);
                List<ProductModel> productModelList = new ArrayList<>();
                for (CartModel cart : cartModelList) {
                    OrderDetailsModel orderDetailsModel = orderDetailsService.findById(cart.getOrderDetailsId());
                    new CartUtil().setListProductFromOrderDetails(productModelList, orderDetailsModel, productSizeService, productService, sizeService);
                }
                req.setAttribute("LIST_PRODUCT_OF_CART", productModelList);
            }
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/cart.jsp");
            rd.forward(req, resp);

            //logging
            JSONObject value = new JSONObject().put(SystemConstant.STATUS_LOG, "Access the path "+req.getRequestURL().toString());
            LoggerHelper.log(SystemConstant.INFO_LEVEL, "SELECT", RuntimeInfo.getCallerClassNameAndLineNumber(), value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
