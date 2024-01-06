package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.ProductSizeModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IProductSizeDAO {
    List<ProductSizeModel> findAll(Pageble pageble) throws SQLException;
    ProductSizeModel findById(Long id) throws SQLException;
    ProductSizeModel findWithFilter(ProductSizeModel model) throws SQLException;
    List<ProductSizeModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<ProductSizeModel> findAllWithFilter(ProductSizeModel model,Pageble pageble) throws SQLException;
    Long save(ProductSizeModel model) throws SQLException;
    void update(ProductSizeModel model) throws SQLException;
    void delete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;
}
