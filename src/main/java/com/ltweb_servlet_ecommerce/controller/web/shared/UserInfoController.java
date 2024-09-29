package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/user-info"})
public class UserInfoController extends HttpServlet {
    @Inject
    private IUserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        UserModel userModel = (UserModel) SessionUtil.getValue(req, "USER_MODEL");
        if (userModel != null) {
            req.setAttribute("userModel", userModel);
            RequestDispatcher rd = req.getRequestDispatcher("/views/shared/user-info.jsp");
            rd.forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String birthday = req.getParameter("dateOfBirth");

        UserModel userModel = (UserModel) SessionUtil.getValue(req, "USER_MODEL");
        if (userModel == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
        }

        //check empty
        fullName = checkEmpty(userModel, "fullname", fullName);
        email = checkEmpty(userModel, "email", email);
        birthday = checkEmpty(userModel, "birthday", birthday);

        try {
            List<UserModel> listUser = userService.findAll(null);

            for (UserModel user : listUser) {
                if (!userModel.getEmail().equals(user.getEmail())) {
                    if (user.getEmail().equals(email)) {
                        resp.sendRedirect(req.getContextPath() + "/user-info?message=email_is_exist&toast=danger");
                        return; // Exit the doPost method after redirect
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //setter
        userModel.setFullName(fullName);
        userModel.setEmail(email);
        userModel.setBirthDay(birthday);
        userModel.setPhone(phone);

        //update in database
        try {
            userService.update(userModel);
            resp.sendRedirect(req.getContextPath() + "/user-info?message=update_info_success&toast=success");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String checkEmpty(UserModel model, String typeString, String value) {
        String result = "";
        if (value.isEmpty()) {
            switch (typeString) {
                case "fullname":
                    result = model.getFullName();
                    break;
                case "phone":
                    result = "0000";
                    break;
                case "email":
                    result = model.getEmail();
                    break;
                case "birthday":
                    result = model.getBirthDay();
                    break;
            }
        } else
            return value;

        return result;
    }
}
