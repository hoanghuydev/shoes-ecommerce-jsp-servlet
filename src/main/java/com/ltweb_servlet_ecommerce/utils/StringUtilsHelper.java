package com.ltweb_servlet_ecommerce.utils;

public class StringUtilsHelper {
    public static String insertHtmlBreaks(String input, int chunkSize) {
        StringBuilder result = new StringBuilder();
        int length = input.length();

        for (int i = 0; i < length; i += chunkSize) {
            // Xác định độ dài của phần cắt
            int end = Math.min(length, i + chunkSize);

            // Cắt phần con của chuỗi từ vị trí i đến end
            String chunk = input.substring(i, end);

            // Nối phần cắt vào kết quả
            result.append(chunk);

            // Nếu không phải là phần cuối cùng, thêm "<br/>"
            if (end < length) {
                result.append("<br/>");
            }
        }

        // Kết quả cuối cùng
        return result.toString();
    }

}
