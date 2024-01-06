package com.ltweb_servlet_ecommerce.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UrlUtil {
    public static String getIdFromUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String result = pathParts[pathParts.length-1];
        return result;
    }
}
