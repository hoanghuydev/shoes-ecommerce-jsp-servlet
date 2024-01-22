package com.ltweb_servlet_ecommerce.controller.web.ajax.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/address/4"})
public class AddressLevel4 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String province = req.getParameter("province");
            String district = req.getParameter("district");
            String commune = req.getParameter("commune");
            String encodedProvince = URLEncoder.encode(province, "UTF-8");
            String encodedDistrict = URLEncoder.encode(district, "UTF-8");
            String encodedCommune = URLEncoder.encode(commune, "UTF-8");
            String urlGetAddressLv4 = "https://services.giaohangtietkiem.vn/services/address/getAddressLevel4?province=" + encodedProvince + "&district=" + encodedDistrict + "&ward_street=" + encodedCommune;
            ResourceBundle rb = ResourceBundle.getBundle("env");
            Map<String,Object> headerReq = new HashMap<String,Object>(){{
                put("Token",rb.getString("TOKEN_GHTK"));
            }};
            org.json.JSONObject addressJson = HttpUtil.doGet(urlGetAddressLv4,headerReq);
            resp.getWriter().write(addressJson.toString());
        } catch (Exception e) {
            HttpUtil.returnError500Json(objectMapper,resp,e.toString());
        }

    }
}
