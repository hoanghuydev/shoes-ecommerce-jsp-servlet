package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IProductImageService {
    List<ProductImageModel> findAllWithFilter(ProductImageModel model, Pageble pageble) throws SQLException;
    ProductImageModel findWithFilter(ProductImageModel model) throws SQLException;
    List<ProductImageModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    ProductImageModel save(ProductImageModel model) throws SQLException;
    ProductImageModel delete(Long id) throws SQLException;
    ProductImageModel update(ProductImageModel model) throws SQLException;
    ProductImageModel findById(Long id) throws SQLException;
    List<ProductImageModel> findAll(Pageble pageble) throws SQLException;
    ProductImageModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
    void saveAll(List<ProductImageModel> imageModels);
}
