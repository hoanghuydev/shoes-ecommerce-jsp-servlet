package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.*;
import com.ltweb_servlet_ecommerce.validate.Validator;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    @Inject
    IUserOrderService userOrderService;
    @Inject
    IAddressService addressService;
    @Inject
    IOrderService orderService;
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
    @Inject
    IVoucherService voucherService;
    @Inject
    IVoucherConditionService voucherConditionService;
    @Inject
    IVoucherUsageService voucherUsageService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NotifyUtil.setUp(req);
            List<ProductModel> productModelList = new ArrayList<>();
            if (StringUtils.isNotBlank(req.getParameter("productId")) && StringUtils.isNotBlank(req.getParameter("quantity")) && StringUtils.isNotBlank(req.getParameter("sizeId"))) {
                Long productId = Long.parseLong(req.getParameter("productId"));
                Long sizeId = Long.parseLong(req.getParameter("sizeId"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                setListProductOfCart(productModelList, productId, sizeId, quantity);
            } else {
                UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, "USER_MODEL");
                if (user == null) {
                    List<OrderDetailsModel> orderDetailsModelList = (List<OrderDetailsModel>) SessionUtil.getInstance().getValue(req, "LIST_ORDER_DETAILS");
                    if (orderDetailsModelList != null) {
                        for (OrderDetailsModel orderDetailsModel : orderDetailsModelList) {
                            ProductSizeModel productSizeModel = productSizeService.findById(orderDetailsModel.getProductSizeId());
                            setListProductOfCart(productModelList, productSizeModel.getProductId(), productSizeModel.getSizeId(), orderDetailsModel.getQuantity());
                        }
                    }
                } else {
                    CartModel cartModelSql = new CartModel();
                    cartModelSql.setUserId(user.getId());
                    List<CartModel> cartModelList = cartService.findAllWithFilter(cartModelSql, null);
                    List<OrderDetailsModel> orderDetailsModelList = new ArrayList<>();
                    for (CartModel cart : cartModelList) {
                        OrderDetailsModel orderDetailsModel = orderDetailsService.findById(cart.getOrderDetailsId());
                        ProductSizeModel productSizeModel = productSizeService.findById(orderDetailsModel.getProductSizeId());
                        setListProductOfCart(productModelList, productSizeModel.getProductId(), productSizeModel.getSizeId(), orderDetailsModel.getQuantity());
                    }
                }
            }
            req.setAttribute("LIST_PRODUCT_OF_CART", productModelList);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProduct = objectMapper.writeValueAsString(productModelList);
            req.setAttribute("JSON_LIST_PRODUCT_OF_CART", jsonProduct);

            if (productModelList.isEmpty()) {
                resp.sendRedirect("/home?message=cart_empty&toast=danger");
                return;
            }
//            Get voucher
            List<VoucherModel> vouchers = voucherService.findAll(null);
            req.setAttribute("LIST_VOUCHER", vouchers);
            String jsonVoucher = objectMapper.writeValueAsString(vouchers);
            req.setAttribute("JSON_LIST_VOUCHER", jsonVoucher);
            //Render View
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/checkout.jsp");

            //logging
            JSONObject value = new JSONObject().put(SystemConstant.STATUS_LOG, "Access the path " + req.getRequestURL().toString());
            LoggerHelper.log(SystemConstant.INFO_LEVEL, "SELECT", RuntimeInfo.getCallerClassNameAndLineNumber(), value);

            rd.forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setListProductOfCart(List<ProductModel> productModelList, Long productId, Long sizeId, int quantity) throws SQLException {
        SizeModel sizeModel = sizeService.findById(sizeId);
        ProductModel productModel = productService.findById(productId);
        ProductSizeModel productSizeModel = new ProductSizeModel();
        productSizeModel.setSizeId(sizeId);
        productSizeModel.setProductId(productId);
        productSizeModel = productSizeService.findWithFilter(productSizeModel);
        productModel.setPrice(productSizeModel.getPrice());
        productModel.setQuantity(quantity);
        productModel.setSizeName(sizeModel.getName());
        productModel.setSizeId(sizeModel.getId());
        productModel.setSubTotal(productSizeModel.getPrice() * quantity);
        productModelList.add(productModel);
    }

    private boolean isValidateForm(HttpServletRequest req) throws UnsupportedEncodingException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AddressModel addressModel = FormUtil.toModel(AddressModel.class, req);
        // use Validator.java to check if the address is valid
        if (!Validator.isValidPhoneNumber(addressModel.getPhoneNumber())
                || !Validator.isNotNullOrEmpty(addressModel.getFullName())
                || !Validator.isMaxLengthValid(req.getParameter("hamlet1"), 100)
        )
            return false;

        return true;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!isValidateForm(req)) {
                resp.sendRedirect("/checkout?message=error&toast=danger");
                return;
            }

            // Fetching necessary data from request
            String[] productListStr = req.getParameterValues("product[]");
            UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, "USER_MODEL");
            AddressModel addressModel = FormUtil.toModel(AddressModel.class, req);
            String hamlet2 = req.getParameter("hamlet2") != "" ? req.getParameter("hamlet2") + " " : "";
            addressModel.setHamlet(hamlet2 + req.getParameter("hamlet1"));
            AddressModel tmpAddress = addressService.findWithFilter(addressModel);
            if (tmpAddress == null) {
                addressModel = addressService.save(addressModel);
            } else {
                addressModel = tmpAddress;
            }
            //Add order model
            String note = req.getParameter("note");
            OrderModel order = new OrderModel();
            order.setNote(note);
            order.setAddressId(addressModel.getId());
            order = orderService.save(order);
            List<Object> productIds = new ArrayList<>();
            for (String productStr : productListStr) {
                String[] productStrSplit = productStr.split("-");
                Long productId = Long.parseLong(productStrSplit[0]);
                productIds.add(productId);
                Long sizeId = Long.parseLong(productStrSplit[1]);
                int quantity = Integer.parseInt(productStrSplit[2]);
                //Find product size and price
                Map<String, Object> resultProductSizeId = productService.findProductWithSql(productId, sizeId);
//                Map<String, Object> resultProductSizeId = productSizeService.findWithCustomSQL(sqlProductSizeId, params);
                Long productSizeId = Long.parseLong(resultProductSizeId.get("productSizeId").toString());
                Double price = Double.parseDouble(resultProductSizeId.get("priceProduct").toString());
                Double subTotal = price * quantity;
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setProductSizeId(productSizeId);
                orderDetailsModel.setQuantity(quantity);
                order.setTotalAmount(order.getTotalAmount() + subTotal);
                orderDetailsModel.setSubTotal(subTotal);
                orderDetailsModel.setOrderId(order.getId());
                orderDetailsService.save(orderDetailsModel);
//                cartService.deleteByUserId(((UserModel) SessionUtil.getValue(req, SystemConstant.USER_MODEL)).getId());
            }
            String voucherApply = req.getParameter("voucherApply");
            if ( voucherApply!=null && !voucherApply.isBlank()) {
                VoucherModel voucher = voucherService.findById(Long.parseLong(voucherApply));
                if (!voucher.getEndDate().after(new Timestamp(System.currentTimeMillis()))) {
                    resp.sendRedirect("/home?message=voucher_expired&toast=danger");
                    return;
                }

                SubQuery queryProductIds = new SubQuery("id", "in", productIds);
                List<SubQuery> subQueryProduct = new ArrayList<>();
                subQueryProduct.add(queryProductIds);
                List<ProductModel> products = productService.findByColumnValues(subQueryProduct, null);
                VoucherConditionModel voucherConditionModel = new VoucherConditionModel();
                voucherConditionModel.setVoucherId(voucher.getId());
                List<VoucherConditionModel> voucherConditions = voucherConditionService.findAllWithFilter(voucherConditionModel, null);
                if (CheckVoucher.canApplyVoucher(products, voucherConditions, user)) {
                    VoucherUsageModel voucherUsageModel = new VoucherUsageModel();
                    voucherUsageModel.setOrderId(order.getId());
                    voucherUsageModel.setVoucherId(voucher.getId());
                    voucherUsageService.save(voucherUsageModel);
                    order.setTotalAmount(order.getTotalAmount() * (1 - voucher.getDiscount() / 100));
                }
            }
            order.setTotalAmount(order.getTotalAmount() + 20000);
            String slugEncodedFromId = UrlUtil.encodeNumber(order.getId());
            order.setSlug(slugEncodedFromId);

            order = orderService.update(order);
            if (user != null) {
                //Save user order
                UserOrderModel userOrderModel = new UserOrderModel();
                userOrderModel.setOrderId(order.getId());
                userOrderModel.setUserId(user.getId());
                userOrderService.save(userOrderModel);
            }
            resp.sendRedirect("/success-order/" + order.getSlug());
        } catch (Exception e) {
            resp.sendRedirect("/checkout?message=error&toast=danger");
        }

    }

}
