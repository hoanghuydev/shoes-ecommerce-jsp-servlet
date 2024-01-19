package com.ltweb_servlet_ecommerce.controller.web.shared;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ltweb_servlet_ecommerce.model.CartModel;
import com.ltweb_servlet_ecommerce.model.OrderDetailsModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.ICartService;
import com.ltweb_servlet_ecommerce.service.IOrderDetailsService;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.utils.CartUtil;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.SessionUtil;
import com.restfb.types.User;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/sign-in","/sign-out"})
public class LoginController extends HttpServlet {
    @Inject
    IUserService userService;
    @Inject
    ICartService cartService;
    @Inject
    IOrderDetailsService orderDetailsService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (req.getRequestURI().startsWith("/sign-out")){
        SessionUtil.getInstance().removeValue(req,"USER_MODEL");
        resp.sendRedirect(req.getContextPath()+"/home?message=logout_success&toast=success");
    } else {
        NotifyUtil.setUp(req);
        RequestDispatcher rd = req.getRequestDispatcher("/views/shared/login.jsp");
        rd.forward(req, resp);
    }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserModel userModel = FormUtil.toModel(UserModel.class, req);
            if (userModel.getPassword() != null && userModel.getEmail() != null) {
                UserModel tmpUser = new UserModel();
                tmpUser.setEmail(userModel.getEmail());
                tmpUser =userService.findWithFilter(tmpUser);
                if (tmpUser != null && BCrypt.verifyer().verify(userModel.getPassword().toCharArray(), tmpUser.getPassword()).verified) {
                    SessionUtil.getInstance().putValue(req, "USER_MODEL", tmpUser);
                    CartUtil.setCartFromSessionForUser(SessionUtil.getInstance(),req,orderDetailsService,cartService,tmpUser.getId());
                    UserModel updateUserLogged = new UserModel();
                    updateUserLogged.setLastLogged(new Timestamp(System.currentTimeMillis()));
                    updateUserLogged.setId(tmpUser.getId());
                    userService.update(updateUserLogged);
                    resp.sendRedirect(req.getContextPath() + "/home");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/sign-in?message=username_password_invalid&toast=danger");
                }
            } else {
                resp.sendRedirect(req.getContextPath() + "/sign-in?message=fill_all_fields&toast=danger");
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
