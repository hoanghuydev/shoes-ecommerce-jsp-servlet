package com.ltweb_servlet_ecommerce.controller.web.ajax.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ltweb_servlet_ecommerce.model.OpinionModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IProductService;
import com.ltweb_servlet_ecommerce.sort.Sorter;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;
import com.ltweb_servlet_ecommerce.utils.SqlUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/search/product/*"})
public class ProductSearchAjax extends HttpServlet {
    @Inject
    IProductService productService;
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
            String[] categories = req.getParameterValues("categories[]") ;
            String[] sizes = req.getParameterValues("sizes[]") ;
            List<ProductModel> productModelList = null;
            List<SubQuery> subQueryList = new ArrayList<>();
//            Subquery lọc cateogory
           if (categories!=null) {
               List<Object> dataSubQueryCateogories = new ArrayList<>(Arrays.asList(categories));
               subQueryList.add(new SubQuery("categoryId", "in", dataSubQueryCateogories));
           }

//            Subquery lọc size
            if (sizes!=null) {
                List<Object> dataSubQuerySizes = new ArrayList<>();
                dataSubQuerySizes.add(SqlUtil.subQuerySizeWithProduct(sizes));
                subQueryList.add(new SubQuery("EXISTS", " ",dataSubQuerySizes));
            }

//            Nếu có productName thì lọc productName
            if (StringUtils.isNotBlank(productName)) {
                List<Object> dataSubQuery = new ArrayList<>();
                dataSubQuery.add("%" + productName.toLowerCase() + "%");
                subQueryList.add(new SubQuery("name", "like", dataSubQuery));
            }
//            Tìm sản phẩm phù hợp

            productModelList = productService.findByColumnValues(subQueryList,pageble);
            objectMapper.writeValue(resp.getOutputStream(), productModelList);
        } catch (Exception e) {
            HttpUtil.returnError500Json(objectMapper,resp,e.toString());
        }
    }
}
