package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

@WebServlet(urlPatterns = {"/forgot-password"})
public class ForgotPasswordController extends HttpServlet {
    @Inject
    IUserService userService;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NotifyUtil.setUp(request);
        RequestDispatcher rd = request.getRequestDispatcher("/views/shared/forgot-pass.jsp");
        rd.forward(request, response);
//        response.sendRedirect("/views/shared/forgot-pass.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = (String) request.getParameter("email");

            if (email.equals("") || email == null) {
                response.sendRedirect(request.getContextPath() + "/forgot-password?message=enter_your_email&toast=danger");
                return;
            }

            UserModel tmpUser = new UserModel();
            tmpUser.setEmail(email);
            tmpUser = userService.findWithFilter(tmpUser);
            if (tmpUser != null) {
                try {
                    Random random = new Random();
                    Integer OTP = 100_000 + random.nextInt(900_000);
                    SendMailUtil.sendMail(email, "Vertify your email", SendMailUtil.templateOTPMail(OTP + ""));
                    SessionUtil.putValue(request, "OTP", OTP);
                    SessionUtil.getInstance().putValue(request, "FORGET_PASS", tmpUser);
                    response.sendRedirect("/vertify-email");

                } catch (Exception mex) {
                    mex.printStackTrace();
                }

            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/views/shared/forgot-pass.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}