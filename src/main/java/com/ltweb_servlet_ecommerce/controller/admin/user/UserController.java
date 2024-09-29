package com.ltweb_servlet_ecommerce.controller.admin.user;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ltweb_servlet_ecommerce.service.IUserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/user/password", name = "UseAdminServlet")
public class UserController extends HttpServlet {

    @Inject
    IUserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(request.getReader()).getAsJsonObject();
        // Lấy giá trị password và confirmPassword từ JsonObject
        String password = jsonObject.get("password").getAsString();
        String confirmPassword = jsonObject.get("confirmPassword").getAsString();
        long userId = jsonObject.get("id").getAsLong();
        if (!password.equals(confirmPassword)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }else {
            boolean isChange =  userService.changePassword(userId, password);
            if (!isChange) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
