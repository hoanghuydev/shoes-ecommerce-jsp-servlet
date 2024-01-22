package com.ltweb_servlet_ecommerce.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

public class UrlUtil {
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ENCODED_LENGTH = 10;
    public static String getIdFromUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String result = pathParts[pathParts.length-1];
        return result;
    }
    public static String encodeNumber(long number) {
        StringBuilder encodedString = new StringBuilder();
        Random random = new Random(number);

        for (int i = 0; i < ENCODED_LENGTH; i++) {
            int index = random.nextInt(BASE62_CHARS.length());
            encodedString.append(BASE62_CHARS.charAt(index));
        }
        return encodedString.toString();
    }


}
