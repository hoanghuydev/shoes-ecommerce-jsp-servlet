package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ICategoryService {
    List<CategoryModel> findAllWithFilter(CategoryModel model, Pageble pageble) throws SQLException;
    CategoryModel findWithFilter(CategoryModel model) throws SQLException;
    List<CategoryModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    CategoryModel save(CategoryModel model) throws SQLException;
    CategoryModel delete(Long id) throws SQLException;
    CategoryModel update(CategoryModel model) throws SQLException;
    CategoryModel findById(Long id) throws SQLException;
    List<CategoryModel> findAll(Pageble pageble) throws SQLException;
    CategoryModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
