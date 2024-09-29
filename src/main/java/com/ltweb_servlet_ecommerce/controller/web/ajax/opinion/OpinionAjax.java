package com.ltweb_servlet_ecommerce.controller.web.ajax.opinion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ltweb_servlet_ecommerce.model.OpinionModel;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IOpinionService;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.sort.Sorter;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/ajax/product/opinion"})
public class OpinionAjax extends HttpServlet {
    @Inject
    IOpinionService opinionService;
    @Inject
    IUserService userService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Long productId = Long.parseLong(req.getParameter("productId"));
        OpinionModel opinionModel = new OpinionModel();
        opinionModel.setProductId(productId);
        try {
            Pageble pageble = new PageRequest(Integer.parseInt(req.getParameter("page")),Integer.parseInt(req.getParameter("maxPageItem")),
                    new Sorter("createAt", "desc"));
            List<OpinionModel> opinionModelList =opinionService.findAllWithFilter(opinionModel,pageble);
            ObjectNode jsonResult = objectMapper.createObjectNode();
            ArrayNode opinionsArray = objectMapper.createArrayNode();
//                Return json list opinions
            for (OpinionModel model : opinionModelList) {
                String sql = " select fullName from users where id = ?";
                List<Object> params = new ArrayList<>();
                params.add(model.getUserId());
                Map<String,Object> sqlMap = userService.findWithCustomSQL(sql,params);
                String fullName = sqlMap.get("fullName").toString();
                model.setUserName(fullName);
                JsonNode json = objectMapper.readTree(HttpUtil.toJson(model));
                opinionsArray.add(json);
            }
            jsonResult.put("opinions", opinionsArray);
//                Get amount opinion of that product
            String sql = " select count(*) as total from opinions where productId = ? and isDeleted = false";
            List<Object> params = new ArrayList<>();
            params.add(productId);
            Map<String,Object> countRating = opinionService.findWithCustomSQL(sql,params);
            int totalCountRating = Integer.parseInt(countRating.get("total").toString());
            jsonResult.put("countRating", totalCountRating);
//                Get sum rating of that product
            String sqlRating = "select sum(rating) as sumRating from opinions where productId =? and isDeleted = false";
            List<Object> params2 = new ArrayList<>();
            params2.add(productId);
            Map<String,Object> sumRating = opinionService.findWithCustomSQL(sqlRating,params2);
            int totalRating = Integer.parseInt(sumRating.get("sumRating").toString());
            jsonResult.put("totalRating", totalRating);
//                Return json
            objectMapper.writeValue(resp.getOutputStream(),jsonResult);
        } catch (Exception e) {
//            return error json
            HttpUtil.returnError500Json(objectMapper,resp,e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       ObjectMapper objectMapper = new ObjectMapper();
        try {
           OpinionModel model = HttpUtil.of(req.getReader()).toModel(OpinionModel.class);
           model = opinionService.save(model);
            objectMapper.writeValue(resp.getOutputStream(),model);
       } catch (Exception e) {
            HttpUtil.returnError500Json(objectMapper,resp,e.toString());
       }

    }
}
