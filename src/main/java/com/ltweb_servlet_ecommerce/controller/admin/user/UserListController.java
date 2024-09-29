package com.ltweb_servlet_ecommerce.controller.admin.user;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.RoleModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.service.IRoleService;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.utils.*;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet(urlPatterns = {"/admin/user/list"})
public class UserListController extends HttpServlet {
    @Inject
    IUserService userService;
    @Inject
    IRoleService roleService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NotifyUtil.setUp(req);
            List<UserModel> listUser = userService.findAll(null);
            req.setAttribute(SystemConstant.LIST_MODEL, listUser);
            List<RoleModel> listRole = roleService.findAll(null);
            req.setAttribute("LIST_ROLE", listRole);
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
            if(!AuthRole.checkPermission(resp, req, SystemConstant.ADMIN_ROLE)) return;
            String action = req.getParameter("action");
            String detailAccount = req.getParameter("detailAccount");
            if (action!=null && action.equalsIgnoreCase("put") && detailAccount!=null && detailAccount.equalsIgnoreCase("changeRole")) {
                Long roleId = Long.parseLong(req.getParameter("roleId"));
                Long id = Long.parseLong(req.getParameter("id"));
                UserModel userModel = new UserModel();
                userModel.setRoleId(roleId);
                userModel.setId(id);
                userService.update(userModel);
                invalidateUserSession(id);
                resp.sendRedirect("/admin/user/list?message=update_success&toast=success");
            }
        } catch (Exception e) {
            resp.sendRedirect("/admin/user/list?message=error&toast=danger");
        }

    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!AuthRole.checkPermission(response, request, SystemConstant.ADMIN_ROLE)) return;
        Long[] ids = HttpUtil.of(request.getReader()).toModel(Long[].class);
        if (ids.length != 0) {
            boolean flag = userService.softDelete(ids);
            response.setStatus(flag ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    private void invalidateUserSession(Long userId) {
        HttpSession session = SessionManager.getSession(userId);
        if (session != null) {
            session.removeAttribute(SystemConstant.USER_MODEL);
        }
    }
}
