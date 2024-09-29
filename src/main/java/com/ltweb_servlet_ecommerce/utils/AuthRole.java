package com.ltweb_servlet_ecommerce.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AuthRole {
    public static void checkPermissionAjax(HttpServletResponse resp, HttpServletRequest req, ObjectMapper objectMapper, String... permissions) throws IOException {
        UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, SystemConstant.USER_MODEL);
        List<String> permissionList = Arrays.asList(permissions);
        if (!permissionList.contains(user.getRole().getValue())) {
            ResourceBundle message = ResourceBundle.getBundle("message");
            HttpUtil.returnError500Json(objectMapper,resp, message.getString("not_permission"));
            return;
        }
    }
    public static boolean checkPermission(HttpServletResponse resp, HttpServletRequest req, String... permissions) throws IOException {
        UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, SystemConstant.USER_MODEL);
        if (user!=null) {
            List<String> permissionList = Arrays.asList(permissions);
            if (!permissionList.contains(user.getRole().getValue())) {
                resp.sendRedirect(req.getContextPath()+"/sign-in?action=login&message=not_permission&toast=danger");
                return false;
            }
            return true;
        }
        resp.sendRedirect(req.getContextPath()+"/sign-in?action=login&message=not_permission&toast=danger");
        return false;
    }
}
