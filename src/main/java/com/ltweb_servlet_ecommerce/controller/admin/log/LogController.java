package com.ltweb_servlet_ecommerce.controller.admin.log;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.service.ILogService;
import com.ltweb_servlet_ecommerce.utils.AuthRole;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/admin/logs")
public class LogController extends HttpServlet {
    @Inject
    ILogService logService;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NotifyUtil.setUp(request);
//        check permission
        if(!AuthRole.checkPermission(response, request, SystemConstant.ADMIN_ROLE)) return;
        request.setAttribute("logs", logService.findAll(null));
        request.getRequestDispatcher("/views/admin/log/list.jsp").forward(request,response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!AuthRole.checkPermission(response, request, SystemConstant.ADMIN_ROLE)) return;
        Long[] ids = HttpUtil.of(request.getReader()).toModel(Long[].class);
        if (ids.length != 0) {
            boolean result = logService.delete(ids);
            response.setStatus(result ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
