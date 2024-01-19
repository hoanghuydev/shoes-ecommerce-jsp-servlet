package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    @Inject
    IProductSizeService productSizeService;
    @Inject
    IProductService productService;
    @Inject
    ICartService cartService;
    @Inject
    IOrderDetailsService orderDetailsService;
    @Inject
    ISizeService sizeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<ProductModel> productModelList = new ArrayList<>();
            if (StringUtils.isNotBlank(req.getParameter("productId")) && StringUtils.isNotBlank(req.getParameter("quantity")) && StringUtils.isNotBlank(req.getParameter("sizeId"))) {
                Long productId = Long.parseLong(req.getParameter("productId"));
                Long sizeId = Long.parseLong(req.getParameter("sizeId"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                setListProductOfCart(productModelList,productId,sizeId,quantity);
            } else {
                UserModel user = (UserModel) SessionUtil.getInstance().getValue(req,"USER_MODEL");
                if (user==null) {
                    List<OrderDetailsModel> orderDetailsModelList = (List<OrderDetailsModel>) SessionUtil.getInstance().getValue(req,"LIST_ORDER_DETAILS");
                    if (orderDetailsModelList!=null) {
                        for ( OrderDetailsModel orderDetailsModel : orderDetailsModelList) {
                            ProductSizeModel productSizeModel = productSizeService.findById(orderDetailsModel.getProductSizeId());
                            setListProductOfCart(productModelList,productSizeModel.getProductId(),productSizeModel.getSizeId(),orderDetailsModel.getQuantity());
                        }
                    }
                } else {
                    CartModel cartModelSql = new CartModel();
                    cartModelSql.setUserId(user.getId());
                    List<CartModel> cartModelList = cartService.findAllWithFilter(cartModelSql,null);
                    List<OrderDetailsModel> orderDetailsModelList = new ArrayList<>();
                    for (CartModel cart : cartModelList) {
                        OrderDetailsModel orderDetailsModel = orderDetailsService.findById(cart.getOrderDetailsId());
                        ProductSizeModel productSizeModel = productSizeService.findById(orderDetailsModel.getProductSizeId());
                        setListProductOfCart(productModelList,productSizeModel.getProductId(),productSizeModel.getSizeId(),orderDetailsModel.getQuantity());
                    }
                }
            }
            req.setAttribute("LIST_PRODUCT_OF_CART",productModelList);
            if (productModelList.isEmpty()) {
                resp.sendRedirect("/home?message=cart_empty&toast=danger");
                return;
            }
            //                Render View
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/checkout.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void setListProductOfCart(List<ProductModel> productModelList, Long productId, Long sizeId,int quantity) throws SQLException {
        SizeModel sizeModel = sizeService.findById(sizeId);
        ProductModel productModel = productService.findById(productId);
        productModel.setQuantity(quantity);
        productModel.setSizeName(sizeModel.getName());
        productModel.setSizeId(sizeModel.getId());
        productModel.setSubTotal(productModel.getPrice()*quantity);
        productModelList.add(productModel);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] products = req.getParameterValues("product[]");
        UserModel user = (UserModel) SessionUtil.getInstance().getValue(req,"USER_MODEL");
        if (user!=null) {

        } else {

        }
    }
}
