package com.ltweb_servlet_ecommerce.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class FormUtil {
    public static <T> T toModel(Class<T> tClass, HttpServletRequest request)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String[]> paramMap = escapeParameterMap(request.getParameterMap());
        T object = tClass.newInstance();
        Map<String, String[]> resultMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValue = entry.getValue();
            // Check if the class has a property with the same name
            if (PropertyUtils.isWriteable(object, paramName) && !paramName.endsWith("[]")) {
                resultMap.put(paramName, paramValue);
            }
        }
        try {
            BeanUtils.populate(object, resultMap);
        } catch (Exception e) {
            // Handle exceptions if needed
            e.printStackTrace();
        }

        return object;
    }



    // prevent XSS
    private static Map<String, String[]> escapeParameterMap(Map<String, String[]> parameterMap) {
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String[] values = entry.getValue();
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = StringEscapeUtils.escapeHtml4(values[i]);
                }
            }
        }
        return parameterMap;
    }
}

