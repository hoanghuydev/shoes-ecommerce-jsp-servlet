package com.ltweb_servlet_ecommerce.utils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class NotifyUtil {
    public static void setUp(HttpServletRequest req) {
        String message = req.getParameter("message");
        String toast = req.getParameter("toast");
        if (message != null && toast != null) {
            ResourceBundle resourceMsg = ResourceBundle.getBundle("message", new UTF8ControlUtil());
            ResourceBundle resourceToast = ResourceBundle.getBundle("toast", new UTF8ControlUtil());
            String msgContent = new String(resourceMsg.getString(message).getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            req.setAttribute("message", msgContent);
            req.setAttribute("toast", resourceToast.getString(toast));
        }
    }
}
