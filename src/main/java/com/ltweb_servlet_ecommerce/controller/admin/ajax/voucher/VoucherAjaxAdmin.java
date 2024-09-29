package com.ltweb_servlet_ecommerce.controller.admin.ajax.voucher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.VoucherConditionModel;
import com.ltweb_servlet_ecommerce.model.VoucherModel;
import com.ltweb_servlet_ecommerce.service.IVoucherConditionService;
import com.ltweb_servlet_ecommerce.service.IVoucherService;
import com.ltweb_servlet_ecommerce.utils.AuthRole;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.UrlUtil;
import com.ltweb_servlet_ecommerce.validate.Validator;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/ajax/voucher/add")
public class VoucherAjaxAdmin extends HttpServlet {
    @Inject
    IVoucherService voucherService;
    @Inject
    IVoucherConditionService voucherConditionService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthRole.checkPermissionAjax(resp,req,objectMapper, SystemConstant.ADMIN_ROLE);
        try {
            String jsonData = HttpUtil.of(req.getReader()).getJson();
            VoucherModel voucher = objectMapper.readValue(jsonData, VoucherModel.class);
            if (!Validator.isNotNullOrEmpty(voucher.getName()) || !Validator.isNotNullOrEmpty(voucher.getCode())
                || !Validator.isNotNullOrEmpty(voucher.getContent()) || !Validator.isNotNullOrEmpty(voucher.getShortDescription())
                    || voucher.getDiscount()==null || voucher.getDiscount()==0)
            {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"status\":\"error\",\"message\":\"Vui lòng nhập đủ thông tin\"}");
                return;
            }
            if (voucher.getStartDate().after(voucher.getEndDate())) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"status\":\"error\",\"message\":\"Ngày bắt đầu không thể sau ngày kết thúc\"}");
                return;
            }
            List<VoucherConditionModel> voucherConditions = voucher.getConditions();
            for (VoucherConditionModel voucherCondition : voucherConditions) {
                if (!Validator.isNotNullOrEmpty(voucherCondition.getTableName())
                || !Validator.isNotNullOrEmpty(voucherCondition.getColumnName())
                || !Validator.isNotNullOrEmpty(voucherCondition.getConditionValue())) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"status\":\"error\",\"message\":\"Điều kiện voucher bị thiếu. Vui lòng điền đầy đủ thông tin\"}");
                    return;
                }
            }
            voucher.setConditions(null);
            voucher = voucherService.save(voucher);
            for (VoucherConditionModel voucherCondition : voucherConditions) {
                voucherCondition.setVoucherId(voucher.getId());
                voucherConditionService.save(voucherCondition);
            }
            resp.getWriter().write("{\"status\":\"success\"}");
        } catch ( Exception e) {
            HttpUtil.returnError500Json(objectMapper,resp,e.getMessage());
        }

    }
}