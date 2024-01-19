package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
import com.ltweb_servlet_ecommerce.utils.SendMailUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet(urlPatterns = {"/contact"})
public class ContactController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tab", SystemConstant.CONTACT_TAB);
        RequestDispatcher rd = req.getRequestDispatcher("/views/web/contact.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserModel userModel = FormUtil.toModel(UserModel.class,req);
            String message = req.getParameter("message");
            SendMailUtil.sendMail("tranvohoanghuy12ab@gmail.com","New support mail send to us",SendMailUtil.templateMailContact(userModel.getFullName(),userModel.getEmail(),message));
            resp.sendRedirect("/contact?message=send_mail_contact_success&toast=success");
        } catch (Exception e) {
            resp.sendRedirect("/contact?message=error&toast=danger");

        }
    }
}
