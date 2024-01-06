package com.ltweb_servlet_ecommerce.controller.admin.category;
import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.service.ICategoryService;
import com.ltweb_servlet_ecommerce.utils.FormUtil;
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
import java.util.List;
@WebServlet(urlPatterns = {"/admin/category/list"})
public class CategoryListController extends HttpServlet {
    @Inject
    ICategoryService categoryService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NotifyUtil.setUp(req);
            List<CategoryModel> listCategory = categoryService.findAll(null);
            if (listCategory!=null) {
                req.setAttribute(SystemConstant.LIST_MODEL, listCategory);
            }
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/category/list.jsp");
            rd.forward(req,resp);
        } catch (
                SQLException e) {
            resp.sendRedirect("/admin/category/list?message=error&toast=danger");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if (action!=null && action.equals("delete")){
                Long id = Long.parseLong(req.getParameter("id"));
                categoryService.delete(id);
                resp.sendRedirect("/admin/category/list?message=delete_success&toast=success");
            } else {
                CategoryModel categoryModel = FormUtil.toModel(CategoryModel.class,req);
                categoryModel = categoryService.save(categoryModel);
                resp.sendRedirect("/admin/category/list?message=add_success&toast=success");
            }
        } catch (Exception e) {
            resp.sendRedirect("/admin/category/list?message=error&toast=danger");
        }

    }
}
