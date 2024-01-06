package com.ltweb_servlet_ecommerce.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

public class AuthenticationAjax {
    private UserModel user;

    public AuthenticationAjax(UserModel user) {
        this.user = user;
    }

    public static boolean auth(ObjectMapper objectMapper, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, "USER_MODEL");

        if (user == null) {
            ResourceBundle message = ResourceBundle.getBundle("message");
            HttpUtil.returnError500Json(objectMapper, resp, message.getString("not_login_yet"));
            return false; // Authentication failed
        } else {
            new AuthenticationAjax(user);
            return true; // Authentication successful
        }
    }
    public boolean authUser(ObjectMapper objectMapper, HttpServletRequest req, HttpServletResponse resp,Long userId) throws IOException {
        if (!auth(objectMapper,req,resp) || user.getId() != userId) {
            ResourceBundle message = ResourceBundle.getBundle("message");
            HttpUtil.returnError500Json(objectMapper,resp, message.getString("not_permission"));
            return false;
        }
        return true;
    }
    public boolean authAdmin(ObjectMapper objectMapper, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (!auth(objectMapper,req,resp) || !user.getAdmin()) {
            ResourceBundle message = ResourceBundle.getBundle("message");
            HttpUtil.returnError500Json(objectMapper,resp, message.getString("not_permission"));
            return false;
        }
        return true;
    }
    public static AuthenticationAjax of ( HttpServletRequest req) {
        UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, "USER_MODEL");
        if (user == null) {
            return new AuthenticationAjax(null);
        } else {
            return new AuthenticationAjax(user);
        }
    }

}
