package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IProductDAO {
    List<ProductModel> findAll(Pageble pageble) throws SQLException;
    ProductModel findById(Long id) throws SQLException;
    ProductModel findWithFilter(ProductModel model) throws SQLException;
    List<ProductModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<ProductModel> findAllWithFilter(ProductModel model,Pageble pageble) throws SQLException;
    Long save(ProductModel model) throws SQLException;
    void update(ProductModel model) throws SQLException;
    void delete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;

}
