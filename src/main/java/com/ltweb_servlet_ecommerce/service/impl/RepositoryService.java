package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.service.*;
import com.ltweb_servlet_ecommerce.sort.Sorter;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryService implements IRepositoryService {
    @Inject
    IProductService productService;
    @Inject
    ISizeService sizeService;
    @Inject
    IProductSizeService productSizeService;
    @Inject
    IOrderDetailsService orderDetailsService;
    @Inject
    IImportOrderDetailService importOrderDetailService;

    @Override
    public List<RepositoryModel> findAll() throws SQLException {
        List<ProductSizeModel> productSizes;
        List<RepositoryModel> repositories = new ArrayList<>();

        productSizes = productSizeService.findAll(null);
        System.out.println(productSizes.size());
        for (ProductSizeModel productSize : productSizes) {
            ProductModel productModel = productService.findById(productSize.getProductId());
            SizeModel sizeModel = sizeService.findById(productSize.getSizeId());

            int importQuantity = 0;
            List<ImportOrderDetailModel> listImportDetail = importOrderDetailService.findByProductSizeId(productSize.getId() + "");
            for (ImportOrderDetailModel importOrderDetail : listImportDetail) {
                importQuantity += importOrderDetail.getQuantityImport();
            }

            int orderQuantity = 0;
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setProductSizeId(productSize.getId());
            List<OrderDetailsModel> listOrderDetail = orderDetailsService.findAllWithFilter(orderDetailsModel, null);
            for (OrderDetailsModel orderDetail : listOrderDetail) {
                orderQuantity += orderDetail.getQuantity();
            }

            RepositoryModel repositoryModel = new RepositoryModel();
            repositoryModel.setProductName(productModel.getName());
            repositoryModel.setSize(sizeModel.getName());
            repositoryModel.setQuantity(importQuantity - orderQuantity);
            repositories.add(repositoryModel);
        }

        return repositories;
    }



}
