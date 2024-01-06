package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IProductImageDAO {
    List<ProductImageModel> findAll(Pageble pageble) throws SQLException;
    ProductImageModel findById(Long id) throws SQLException;
    ProductImageModel findWithFilter(ProductImageModel model) throws SQLException;
    List<ProductImageModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<ProductImageModel> findAllWithFilter(ProductImageModel model,Pageble pageble) throws SQLException;
    Long save(ProductImageModel model) throws SQLException;
    void update(ProductImageModel model) throws SQLException;
    void delete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;
}
