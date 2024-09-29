package com.ltweb_servlet_ecommerce.controller.admin.importOrder;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.ImportOrderDetailModel;
import com.ltweb_servlet_ecommerce.service.IImportOrderDetailService;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin/import-order-details")
public class ImportOrderDetailController extends HttpServlet {
    @Inject
    private IImportOrderDetailService detailService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NotifyUtil.setUp(request);
        String importId = request.getParameter("id");
        List<ImportOrderDetailModel> list = detailService.findByImportId(importId);
        request.setAttribute(SystemConstant.LIST_MODEL, list);
        request.setAttribute("importId", importId);
        request.getRequestDispatcher("/views/admin/import-order/list-detail.jsp").forward(request, response);
    }

}
