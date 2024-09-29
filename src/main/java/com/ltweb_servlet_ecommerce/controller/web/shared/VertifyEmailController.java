package com.ltweb_servlet_ecommerce.controller.web.shared;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/vertify-email"})
public class VertifyEmailController extends HttpServlet {
    @Inject
    IUserService userService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        RequestDispatcher rd = req.getRequestDispatcher("/views/shared/vertify-email.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String otp = req.getParameter("otp");
        String otpOrigin = SessionUtil.getInstance().getValue(req,"OTP")+"";

        if (otp.equals(otpOrigin)) {
            //forgot password
            UserModel tmpUser = (UserModel) SessionUtil.getInstance().getValue(req,"FORGET_PASS");
            if (tmpUser != null) {
                resp.sendRedirect("/views/shared/new-password.jsp");
            } else {

                UserModel userModel = (UserModel) SessionUtil.getInstance().getValue(req, "REGISTER_USER");
                BCrypt.Hasher hasher = BCrypt.withDefaults();
                String hashedPassword = hasher.hashToString(12, userModel.getPassword().toCharArray());
                userModel.setPassword(hashedPassword);
                //set default roleId = 3 (user role)
                userModel.setRoleId(Long.valueOf(3));
                try {
                    userService.save(userModel);
                    resp.sendRedirect(req.getContextPath() + "/sign-in?message=register_success&toast=success");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            resp.sendRedirect(req.getContextPath()+"/vertify-email?message=otp_invalid&toast=danger");

        }
    }
}
