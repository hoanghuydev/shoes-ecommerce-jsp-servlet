package com.ltweb_servlet_ecommerce.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.type.TypeReference;
import org.cloudinary.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpUtil {

    private String value;

    public HttpUtil (String value) {
        this.value = value;
    }
// Convert from req
    public static HttpUtil of (BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return new HttpUtil(sb.toString());
    }

    public JSONObject toJSONObject() throws UnsupportedEncodingException {

        JSONObject result = new JSONObject(new String(value.getBytes("ISO-8859-1"), StandardCharsets.UTF_8));
        return result;
    }


    public <T> T toModel(Class<T> tClass) {
        try {
            return new ObjectMapper().readValue(value, tClass);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return null;
    }
    public JsonNode toJsonNode(ObjectMapper objectMapper) {
        try {
           return objectMapper.readTree(value);
        } catch (Exception e) {
            return null;
        }
    }
//    End convert from req
    public static <T> List<T> toListModel(String jsonData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> listModel = objectMapper.readValue(jsonData, new TypeReference<List<T>>(){});
        return listModel;
    }
    public static <T> String toJson(T tClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tClass);
    }

    public static void returnError500Json(ObjectMapper objectMapper, HttpServletResponse resp, String error) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ObjectNode jsonError = objectMapper.createObjectNode();
        jsonError.put("error","An error has occurred!");
        jsonError.put("error_desc",error);
        objectMapper.writeValue(resp.getOutputStream(),jsonError);
    }
    public static void returnError404Json(ObjectMapper objectMapper, HttpServletResponse resp,String error) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        ObjectNode jsonError = objectMapper.createObjectNode();
        jsonError.put("error","Not found!");
        jsonError.put("error_desc",error);
        objectMapper.writeValue(resp.getOutputStream(),jsonError);
    }
}
