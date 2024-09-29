package com.ltweb_servlet_ecommerce.utils;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatusMapUtil {
    public static Map<String, String> getStatusMap() {
        LinkedHashMap<String, String> status = new LinkedHashMap<>();
        status.put("ORDER_PROCESSING", SystemConstant.ORDER_PROCESSING);
        status.put("ORDER_TRANSPORTING", SystemConstant.ORDER_TRANSPORTING);
        status.put("ORDER_DELIVERED", SystemConstant.ORDER_DELIVERED);
        status.put("ORDER_CANCEL", SystemConstant.ORDER_CANCEL);
        return status;
    }

    public static String getStatusValue(String value) {
        for (Map.Entry<String, String> entry : getStatusMap().entrySet()) {
            if (entry.getKey().equals(value)) {
                return entry.getValue();
            }
        }
        return "";
    }

    public static String getStatusKey(String value) {
        for (Map.Entry<String, String> entry : getStatusMap().entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return "";
    }

}
