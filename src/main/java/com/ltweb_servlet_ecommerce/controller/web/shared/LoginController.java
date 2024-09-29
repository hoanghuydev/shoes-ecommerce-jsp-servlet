package com.ltweb_servlet_ecommerce.controller.web.shared;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.ICartService;
import com.ltweb_servlet_ecommerce.service.IOrderDetailsService;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.utils.*;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet(urlPatterns = {"/sign-in", "/sign-out"})
public class LoginController extends HttpServlet {
    @Inject
    private IUserService userService;
    @Inject
    private ICartService cartService;
    @Inject
    private IOrderDetailsService orderDetailsService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().startsWith("/sign-out")) {
            UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, SystemConstant.USER_MODEL);
            SessionUtil.getInstance().removeValue(req, "USER_MODEL");
            SessionManager.removeSession(user.getId());
            resp.sendRedirect(req.getContextPath() + "/home?message=logout_success&toast=success");
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
                tmpUser = userService.findWithFilter(tmpUser);
                if (tmpUser != null && BCrypt.verifyer().verify(userModel.getPassword().toCharArray(), tmpUser.getPassword()).verified) {
                    SessionUtil.getInstance().putValue(req, SystemConstant.USER_MODEL, tmpUser);
                    SessionManager.addSession(tmpUser.getId(), req.getSession());
                    CartUtil.setCartFromSessionForUser(SessionUtil.getInstance(), req, orderDetailsService, cartService, tmpUser.getId());
                    UserModel updateUserLogged = new UserModel();
                    updateUserLogged.setLastLogged(new Timestamp(System.currentTimeMillis()));
                    updateUserLogged.setId(tmpUser.getId());
                    userService.update(updateUserLogged);

                    // Logging successful login
                    JSONObject logValue = new JSONObject();
                    logValue.put(SystemConstant.STATUS_LOG, "Authentication successful");
                    logValue.put(SystemConstant.VALUE_LOG, new JSONObject().put("email", tmpUser.getEmail()).put("id", tmpUser.getId()));
                    LoggerHelper.log(SystemConstant.INFO_LEVEL, "SELECT", RuntimeInfo.getCallerClassNameAndLineNumber(), logValue);

                    if (tmpUser.getRole().getValue().equalsIgnoreCase(SystemConstant.ADMIN_ROLE) || tmpUser.getRole().getValue().equalsIgnoreCase(SystemConstant.MODERATOR_ROLE))
                        resp.sendRedirect(req.getContextPath() + "/admin/home");
                    else
                        resp.sendRedirect(req.getContextPath() + "/home");
                } else {
                    // Logging login error
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(SystemConstant.STATUS_LOG, "Authentication Failure. Email or password is invalid");
                    jsonObject.put(SystemConstant.VALUE_LOG, new JSONObject().put("email", userModel.getEmail()));
                    LoggerHelper.log(SystemConstant.WARN_LEVEL, "SELECT", RuntimeInfo.getCallerClassNameAndLineNumber(), jsonObject);

                    resp.sendRedirect(req.getContextPath() + "/sign-in?message=username_password_invalid&toast=danger");
                }
            } else {
                // Logging requires filling out complete information
                JSONObject logValue = new JSONObject();
                logValue.put(SystemConstant.STATUS_LOG, "Authentication Failure. Email or password is null");
                LoggerHelper.log(SystemConstant.WARN_LEVEL, "SELECT", RuntimeInfo.getCallerClassNameAndLineNumber(), logValue);

                resp.sendRedirect(req.getContextPath() + "/sign-in?message=fill_all_fields&toast=danger");

            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
