package com.ltweb_servlet_ecommerce.controller.web.ajax.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.model.VoucherModel;
import com.ltweb_servlet_ecommerce.service.IVoucherService;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.UrlUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/ajax/voucher")
public class VoucherAjax extends HttpServlet {
    @Inject
    IVoucherService voucherService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String code = req.getParameter("code");
            VoucherModel voucher = new VoucherModel();
            voucher.setCode(code);
            voucher = voucherService.findWithFilter(voucher);
            if (voucher == null) {
                HttpUtil.returnError404Json(objectMapper,resp,"Không tìm thấy voucher");
            } else {
                objectMapper.writeValue(resp.getOutputStream(),voucher);
            }
        } catch (Exception e) {
            HttpUtil.returnError404Json(objectMapper,resp,e.toString());
        }
    }
}
