package com.ltweb_servlet_ecommerce.controller.admin.user;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

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
@WebServlet(urlPatterns = {"/admin/user/list"})
public class UserListController extends HttpServlet {
    @Inject
    IUserService userService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NotifyUtil.setUp(req);
            List<UserModel> listUser = userService.findAll(null);
            if (listUser!=null) {
                req.setAttribute(SystemConstant.LIST_MODEL, listUser);
            }
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/user/list.jsp");
            rd.forward(req,resp);
        } catch (
                SQLException e) {
            resp.sendRedirect("/admin/category/list?message=error&toast=danger");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if (action!=null && action.equals("delete")){
                Long id = Long.parseLong(req.getParameter("id"));
                userService.softDelete(id);
                resp.sendRedirect("/admin/user/list?message=delete_success&toast=success");
            } else {
                UserModel model = FormUtil.toModel(UserModel.class,req);
                model = userService.save(model);
                resp.sendRedirect("/admin/user/list?message=add_success&toast=success");
            }
        } catch (Exception e) {
            resp.sendRedirect("/admin/user/list?message=error&toast=danger");
        }

    }
}
