package com.ltweb_servlet_ecommerce.controller.web.shared;


import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.utils.NotifyUtil;
import com.ltweb_servlet_ecommerce.utils.RuntimeInfo;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/about"})
public class AboutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        NotifyUtil.setUp(request);
        request.setAttribute("tab", SystemConstant.ABOUT_TAB);
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/about.jsp");
        rd.forward(request,resp);

        //logging
        JSONObject value = new JSONObject().put(SystemConstant.STATUS_LOG, "Access the path "+request.getRequestURL().toString());
        LoggerHelper.log(SystemConstant.INFO_LEVEL, null, RuntimeInfo.getCallerClassNameAndLineNumber(), value);
    }
}
