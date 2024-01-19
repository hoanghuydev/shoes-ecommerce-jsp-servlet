package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.SizeModel;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.sort.Sorter;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/shop"})
public class ShopController extends HttpServlet {
    @Inject
    IProductService productService;
    @Inject
    ICategoryService categoryService;
    @Inject
    ISizeService sizeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(req);
        Integer page = req.getParameter("page")==null ? 1 : Integer.parseInt(req.getParameter("page").toString());
        Integer maxPageItem =req.getParameter("maxPageItem")==null ? 8 : Integer.parseInt(req.getParameter("maxPageItem").toString());
        String sortName = req.getParameter("sortName")==null ? "createAt" : req.getParameter("sortName");
        String sortBy = req.getParameter("sortBy")==null ? "desc" : req.getParameter("sortBy");
        Pageble pagebleProduct = new PageRequest(page,maxPageItem,new Sorter(sortName, sortBy));

        try {
            String productName = req.getParameter("productName");
            if (StringUtils.isNotBlank(productName)) {
                req.setAttribute("productName",productName);
            }
            List<SizeModel> listSize = sizeService.findAll(null);
            req.setAttribute("LIST_CATEGORY",categoryService.findAll(null));
            req.setAttribute("LIST_SIZE",listSize);
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/shop.jsp");
            rd.forward(req,resp);
        } catch (Exception e) {
            resp.sendRedirect("/shop?message=error&toast=danger");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
