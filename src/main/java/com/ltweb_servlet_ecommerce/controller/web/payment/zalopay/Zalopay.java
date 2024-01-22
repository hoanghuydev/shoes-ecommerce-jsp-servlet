package com.ltweb_servlet_ecommerce.controller.web.payment.zalopay;

import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.UrlUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(urlPatterns = {"/payment/zalopay/*","/payment/zalo*"})
public class Zalopay extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderSlug = UrlUtil.getIdFromUrl(req,resp);
        try {
            createPaymentZalo(req,resp);
        } catch (Exception e) {
            resp.sendRedirect("/success-order/"+orderSlug+"?message=error&toast=danger");
        }
    }
    private static Map<String, String> config = new HashMap(){{
        put("app_id", "2553");
        put("key1", "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL");
        put("key2", "kLtgPl8HHhfvMuDHPwKfgfsY4Ydm9eIz");
        put("endpoint", "https://sb-openapi.zalopay.vn/v2/create");
    }};


    public static String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    public static void createPaymentZalo(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        final Map embeddata = new HashMap(){{
            put("merchantinfo", "embeddata123");
        }};

        final Map[] item = {
                new HashMap(){{
                    put("itemid", "knb");
                    put("itemname", "kim nguyen bao");
                    put("itemprice", 198400);
                    put("itemquantity", 1);
                }}
        };

        Map<String, Object> order = new HashMap<String, Object>(){{
            put("app_id", config.get("app_id"));
            put("app_trans_id", getCurrentTimeString("yyMMdd") +"_"+ UUID.randomUUID()); // mã giao dich có định dạng yyMMdd_xxxx
            put("app_time", System.currentTimeMillis()); // miliseconds
            put("app_user", "demo");
            put("amount", 50000);
            put("description", "ZaloPay Intergration Demo");
            put("bank_code", "");
            put("item", new JSONObject(item).toString());
            put("embed_data", new JSONObject(embeddata).toString());
        }};

        // appid +”|”+ apptransid +”|”+ appuser +”|”+ amount +"|" + apptime +”|”+ embeddata +"|" +item
        String data = order.get("app_id") +"|"+ order.get("app_trans_id") +"|"+ order.get("app_user") +"|"+ order.get("amount")
                +"|"+ order.get("app_time") +"|"+ order.get("embed_data") +"|"+ order.get("item");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data));
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(config.get("endpoint"));
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }

        // Content-Type: application/x-www-form-urlencoded
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(post);
        JSONObject jsonResult = HttpUtil.httpClientRespToJSONObject(response);
        resp.sendRedirect((String) jsonResult.get("orderurl"));
    }
}
