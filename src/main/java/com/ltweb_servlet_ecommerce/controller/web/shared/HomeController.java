package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.ICategoryService;
import com.ltweb_servlet_ecommerce.service.IOrderService;
import com.ltweb_servlet_ecommerce.service.IProductService;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.sort.Sorter;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/home"})
public class HomeController extends HttpServlet {
    @Inject
    ICategoryService categoryService;
    @Inject
    IUserService userService;
    @Inject
    IOrderService orderService;
    @Inject
    IProductService productService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tab", SystemConstant.HOME_TAB);
        Pageble pagebleProduct = new PageRequest(1,3,new Sorter("totalViewAndSearch", "desc"));
        try {
            NotifyUtil.setUp(req);
            List<ProductModel> productList = productService.findAll(pagebleProduct);
            req.setAttribute(SystemConstant.LIST_MODEL,productList);
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");
            rd.forward(req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
