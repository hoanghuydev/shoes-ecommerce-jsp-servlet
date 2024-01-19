package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.CartModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ICartService {
    List<CartModel> findAllWithFilter(CartModel model, Pageble pageble) throws SQLException;
    CartModel findWithFilter(CartModel model) throws SQLException;
    List<CartModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    CartModel save(CartModel model) throws SQLException;
    CartModel delete(Long id) throws SQLException;
    CartModel update(CartModel model) throws SQLException;
    CartModel findById(Long id) throws SQLException;
    List<CartModel> findAll(Pageble pageble) throws SQLException;
    CartModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
