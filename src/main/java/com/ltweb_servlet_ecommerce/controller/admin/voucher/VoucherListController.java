package com.ltweb_servlet_ecommerce.controller.admin.voucher;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.ProductSizeModel;
import com.ltweb_servlet_ecommerce.model.VoucherModel;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.util.Base64;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@WebServlet(urlPatterns = {"/admin/voucher/list"})
@MultipartConfig
public class VoucherListController extends HttpServlet {
    @Inject
    IVoucherService voucherService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        try {
            List<VoucherModel> vouchers = voucherService.findAll(null);
            req.setAttribute(SystemConstant.LIST_MODEL,vouchers);
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/voucher/list.jsp");
            rd.forward(req,resp);
        } catch (Exception e) {
            resp.sendRedirect("/admin/voucher/list?message=delete_success&toast=danger");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
           String action = req.getParameter("action");
           switch (action) {
               case "delete" :
                   Long id = Long.parseLong(req.getParameter("idDelete"));
                   voucherService.softDelete(id);
                   resp.sendRedirect("/admin/voucher/list?toast=success&message=add_success");
                   break;
               default :
                   resp.sendRedirect("/admin/voucher/list?toast=danger&message=error");
                   break;
           }
       } catch (Exception e) {
           resp.sendRedirect("/admin/voucher/list?toast=danger&message=error");
       }
    }
}