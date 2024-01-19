package com.ltweb_servlet_ecommerce.utils;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    private static SessionUtil sessionUtil = null;
    public static SessionUtil getInstance() {
        if (sessionUtil==null) {
            return new SessionUtil();
        }
        return sessionUtil;
    }
    public static void putValue(HttpServletRequest req, String key, Object value) {
        req.getSession().setAttribute(key,value);
    }
    public static Object getValue(HttpServletRequest req,String key) {
        return req.getSession().getAttribute(key);
    }
    public static void removeValue(HttpServletRequest req,String key) {
        req.getSession().removeAttribute(key);
    }

}
