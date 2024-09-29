package com.ltweb_servlet_ecommerce.controller.admin.size;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.model.SizeModel;
import com.ltweb_servlet_ecommerce.service.ISizeService;
import com.ltweb_servlet_ecommerce.utils.AuthRole;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.validate.Validator;

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

@WebServlet(urlPatterns = {"/admin/size/list"})
public class SizeListController extends HttpServlet {
    @Inject
    ISizeService sizeService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NotifyUtil.setUp(req);
            List<SizeModel> listSize = sizeService.findAll(null);
            if (listSize!=null) {
                req.setAttribute(SystemConstant.LIST_MODEL, listSize);
            }
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/size/list.jsp");
            rd.forward(req,resp);
        } catch (SQLException e) {
            resp.sendRedirect("/admin/product/list?message=error&toast=danger");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if(!AuthRole.checkPermission(resp, req, SystemConstant.ADMIN_ROLE)) return;
            String action = req.getParameter("action");
            if (action!=null && action.equals("delete")){
                Long id = Long.parseLong(req.getParameter("id"));
                sizeService.delete(id);
                resp.sendRedirect("/admin/size/list?message=delete_success&toast=success");
            } else if (action!=null && action.equals("add")) {
                SizeModel sizeModel = FormUtil.toModel(SizeModel.class,req);
                if (!checkSize(sizeModel,false)) {
                    resp.sendRedirect("/admin/size/list?message=error&toast=danger");
                    return;
                }
                sizeModel = sizeService.save(sizeModel);
                resp.sendRedirect("/admin/size/list?message=add_success&toast=success");
            } else if (action!=null && action.equals("update")) {
                SizeModel sizeModel = FormUtil.toModel(SizeModel.class,req);
                if (!checkSize(sizeModel,true)) {
                    resp.sendRedirect("/admin/size/list?message=error&toast=danger");
                    return;
                }
                sizeModel = sizeService.update(sizeModel);
                resp.sendRedirect("/admin/size/list?message=update_success&toast=success");
            }

        } catch (Exception e) {
            resp.sendRedirect("/admin/size/list?message=error&toast=danger");
        }

    }
    private boolean checkSize(SizeModel size, boolean isUpdate) {
        if (size == null) {
            return false;
        }
        if (isUpdate && size.getId() == null) {
            return false;
        }

        if ( !Validator.isNotNullOrEmpty(size.getName())) {
            return false;
        }
        return true;
    }
}
