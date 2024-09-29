package com.ltweb_servlet_ecommerce.controller.web.ajax.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.VoucherModel;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IVoucherService;
import com.ltweb_servlet_ecommerce.sort.Sorter;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/ajax/voucher/list")
public class VoucherListAjax extends HttpServlet {
    @Inject
    IVoucherService voucherService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Integer page = req.getParameter("page")==null ? 1 : Integer.parseInt(req.getParameter("page").toString());
        Integer maxPageItem =req.getParameter("maxPageItem")==null ? 10 : Integer.parseInt(req.getParameter("maxPageItem").toString());
        String sortName = req.getParameter("sortName")==null ? "totalViewAndSearch" : req.getParameter("sortName");
        String sortBy = req.getParameter("sortBy")==null ? "desc" : req.getParameter("sortBy");
        Pageble pageble = new PageRequest(page,maxPageItem,new Sorter(sortName, sortBy));
        try {
            String productName = req.getParameter("productName");
            List<VoucherModel> voucherModels = null;
            if (StringUtils.isNotBlank(productName)) {
                List<SubQuery> subQueryList = new ArrayList<>();
                List<Object> dataSubQuery = new ArrayList<>();
                dataSubQuery.add("%" + productName.toLowerCase() + "%");
                subQueryList.add(new SubQuery("name", "like", dataSubQuery));
                voucherModels = voucherService.findByColumnValues(subQueryList, pageble);
                objectMapper.writeValue(resp.getOutputStream(), voucherModels);
            } else {
                voucherModels = voucherService.findAll(pageble);
                objectMapper.writeValue(resp.getOutputStream(), voucherModels);
            }
        } catch (Exception e) {
            HttpUtil.returnError500Json(objectMapper,resp,e.toString());
        }
    }
}
