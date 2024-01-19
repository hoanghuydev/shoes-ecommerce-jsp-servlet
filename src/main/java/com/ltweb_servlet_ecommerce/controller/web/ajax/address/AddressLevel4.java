package com.ltweb_servlet_ecommerce.controller.web.ajax.address;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.cloudinary.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
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
            HttpClient httpClient = HttpClients.createDefault();
            String encodedProvince = URLEncoder.encode(province, "UTF-8");
            String encodedDistrict = URLEncoder.encode(district, "UTF-8");
            String encodedCommune = URLEncoder.encode(commune, "UTF-8");
            HttpGet getAddressLevel4 = new HttpGet("https://services.giaohangtietkiem.vn/services/address/getAddressLevel4?province=" + encodedProvince + "&district=" + encodedDistrict + "&ward_street=" + encodedCommune);
            ResourceBundle rb = ResourceBundle.getBundle("env");
            getAddressLevel4.addHeader("Token",rb.getString("TOKEN_GHTK"));
            HttpResponse addressResp = httpClient.execute(getAddressLevel4);
            BufferedReader readerToken = new BufferedReader(new InputStreamReader(addressResp.getEntity().getContent()));
            JsonNode addressJson = HttpUtil.of(readerToken).toJsonNode(objectMapper);
            objectMapper.writeValue(resp.getOutputStream(),addressJson);
        } catch (Exception e) {
            HttpUtil.returnError500Json(objectMapper,resp,e.toString());
        }

    }
}
