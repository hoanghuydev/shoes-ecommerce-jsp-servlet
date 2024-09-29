package com.ltweb_servlet_ecommerce.service;


import com.ltweb_servlet_ecommerce.model.ProductSizeModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IProductSizeService {
    List<ProductSizeModel> findAllWithFilter(ProductSizeModel model, Pageble pageble) throws SQLException;
    ProductSizeModel findWithFilter(ProductSizeModel model) ;
    List<ProductSizeModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    ProductSizeModel save(ProductSizeModel model) throws SQLException;
    ProductSizeModel delete(Long id) throws SQLException;
    ProductSizeModel update(ProductSizeModel model) throws SQLException;
    ProductSizeModel findById(Long id) throws SQLException;
    List<ProductSizeModel> findAll(Pageble pageble) throws SQLException;
    ProductSizeModel softDelete(Long id) throws SQLException;
    int getAvailableProducts(Long productSizeId);
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
    double getTotalProfit();
    ProductSizeModel findByProductId(Long id) throws SQLException;

    void deleteByProductId(Long id);
}
