package com.ltweb_servlet_ecommerce.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SqlUtil {
    public static String subQuerySizeWithProduct (String[] sizes) {
        String sizesList = Arrays.stream(sizes)
                .map(String::trim)
                .collect(Collectors.joining(", "));
        return "SELECT 1\n" +
                "FROM product_sizes ps\n" +
                "WHERE ps.productId = products.id\n" +
                "AND ps.sizeId IN ("+sizesList+")";
    }
}
