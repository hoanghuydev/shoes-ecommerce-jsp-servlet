package com.ltweb_servlet_ecommerce.controller.web.shared;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IProductService;
import com.ltweb_servlet_ecommerce.sort.Sorter;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.RuntimeInfo;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home"})
public class HomeController extends HttpServlet {
    @Inject
    IProductService productService;

    //9488
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("tab", SystemConstant.HOME_TAB);
        Pageble pagebleProduct = new PageRequest(1,3,new Sorter("totalViewAndSearch", "desc"));
        try {
            NotifyUtil.setUp(req);
            List<ProductModel> productList = productService.findAll(pagebleProduct);
            req.setAttribute(SystemConstant.LIST_MODEL,productList);
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");

            //logging
            JSONObject value = new JSONObject().put(SystemConstant.STATUS_LOG, "Access the path "+req.getRequestURL().toString());
            LoggerHelper.log(SystemConstant.INFO_LEVEL, "SELECT", RuntimeInfo.getCallerClassNameAndLineNumber(), value);

            rd.forward(req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
