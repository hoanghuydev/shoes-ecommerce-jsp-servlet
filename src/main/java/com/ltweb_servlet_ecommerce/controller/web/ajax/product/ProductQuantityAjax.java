package com.ltweb_servlet_ecommerce.controller.web.ajax.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltweb_servlet_ecommerce.model.ProductSizeModel;
import com.ltweb_servlet_ecommerce.service.IProductSizeService;
import com.ltweb_servlet_ecommerce.utils.HttpUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/ajax/product-quantity"})
public class ProductQuantityAjax extends HttpServlet {
    @Inject
    IProductSizeService sizeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();

//        JsonNode dataJson = HttpUtil.of(req.getReader()).toJsonNode(objectMapper);
        Long productId = Long.parseLong(req.getParameter("productId"));
        Long sizeId = Long.parseLong(req.getParameter("sizeId"));
//        Long productId = Long.parseLong(String.valueOf(dataJson.get("productId")));
//        Long sizeId = Long.parseLong(String.valueOf(dataJson.get("sizeId")));

        ProductSizeModel sizeModel = new ProductSizeModel();
        sizeModel.setProductId(productId);
        sizeModel.setSizeId(sizeId);
        sizeModel = sizeService.findWithFilter(sizeModel);

        int quantity = sizeService.getAvailableProducts(sizeModel.getId());

        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(), quantity);
    }
}
