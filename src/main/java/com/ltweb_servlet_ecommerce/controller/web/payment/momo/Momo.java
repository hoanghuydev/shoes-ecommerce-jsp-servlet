package com.ltweb_servlet_ecommerce.controller.web.payment.momo;

import com.ltweb_servlet_ecommerce.controller.web.payment.zalopay.HMACUtil;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.service.IOrderService;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.UrlUtil;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/payment/momo/*","/payment/momo*"})
public class Momo extends HttpServlet {
    private final String accessKey = ResourceBundle.getBundle("env").getString("ACCESS_KEY_MOMO");
    private final String secretkey = ResourceBundle.getBundle("env").getString("SECERT_KEY_MOMO");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("transId")==null) {
                initiatePayment(req,resp);
            } else {
                checkResultPayment(req,resp);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void checkResultPayment(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
        String slug = UrlUtil.getIdFromUrl(req, resp);
       String orderId = req.getParameter("orderId");
       String partnerCode = req.getParameter("partnerCode");
       String requestId = req.getParameter("requestId");
        String rawSignature =
                "accessKey=" +
                        accessKey +
                        "&orderId=" +
                        orderId +
                        "&partnerCode=" +
                        partnerCode +
                        "&requestId=" +
                        requestId;
        String signature = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, secretkey, rawSignature);
        Map<String, Object> dataRequestMomo = new HashMap<String, Object>(){{
            put("partnerCode",partnerCode);
            put("requestId",requestId);
            put("orderId",orderId);
            put("signature",signature);
            put("lang","en");
        }};
        JSONObject jsonResult = HttpUtil.doPost("https://test-payment.momo.vn/v2/gateway/api/query",dataRequestMomo,null);
       int resultCode = (int) jsonResult.get("resultCode");
       if (resultCode==0) {
           OrderModel order = new OrderModel();
           order.setSlug(slug);
           order = orderService.findWithFilter(order);
           if (order!=null) {
               order.setIsPaid(true);
               order = orderService.update(order);
           }
           resp.sendRedirect("/order-details/"+slug+"?message=paid_order&toast=success");
       } else {
           resp.sendRedirect("/success-order/"+slug+"?message=error&toast=danger");
       }



    }
    @Inject
    IOrderService orderService;
    public void initiatePayment(HttpServletRequest req,HttpServletResponse resp)
            throws Exception {
        String slug = UrlUtil.getIdFromUrl(req, resp);
        OrderModel order = new OrderModel();
        order.setSlug(slug);
        order = orderService.findWithFilter(order);
        if (order==null) {
            resp.sendRedirect("/error/404");
            return;
        }
        String requestId = "MOMO"+System.currentTimeMillis();
        String orderId = requestId;
        String extraData = "";
        String redirectUrl = "http://localhost:8080/payment/momo/"+slug;
        // Auto call ipn url when payment is done
        // But to same concept with mono and zalo pay > should use redirect url to check payment status and insert new order to database
        String ipnUrl = "http://localhost:8080/payment/momo/"+slug;
        String orderInfo = slug;
        String partnerCode = "MOMO";

        String requestType = "payWithATM";
        int amountInt = (int) (order.getTotalAmount()*1);
        String amount = amountInt +"";
        String data =
                "accessKey=" +
                        accessKey +
                        "&amount=" +
                        amount +
                        "&extraData=" +
                        extraData +
                        "&ipnUrl=" +
                        ipnUrl +
                        "&orderId=" +
                        orderId +
                        "&orderInfo=" +
                        orderInfo +
                        "&partnerCode=" +
                        partnerCode +
                        "&redirectUrl=" +
                        redirectUrl +
                        "&requestId=" +
                        requestId +
                        "&requestType=" +
                        requestType;


        String signature = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, secretkey, data);
        Map<String, Object> orderPayMomo = new HashMap<String, Object>(){{
            put("partnerCode", partnerCode);
            put("accessKey", accessKey);
            put("requestId",requestId ); // miliseconds
            put("amount", amount);
            put("orderId", requestId);
            put("orderInfo", orderInfo);
            put("redirectUrl", redirectUrl);
            put("ipnUrl", ipnUrl);
            put("extraData", extraData);
            put("requestType", requestType);
            put("signature", signature);
            put("lang", "en");
        }};
        JSONObject jsonResult = HttpUtil.doPost("https://test-payment.momo.vn/v2/gateway/api/create",orderPayMomo,null);
        resp.sendRedirect((String) jsonResult.get("payUrl"));
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
