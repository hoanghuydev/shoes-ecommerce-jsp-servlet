package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.CartModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ICartDAO {
    List<CartModel> findAll(Pageble pageble) throws SQLException;
    CartModel findById(Long id) throws SQLException;
    CartModel findWithFilter(CartModel model) throws SQLException;
    List<CartModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    List<CartModel> findAllWithFilter(CartModel model,Pageble pageble) throws SQLException;
    Long save(CartModel model) throws SQLException;
    int update(CartModel model) throws SQLException;
    int delete(Long id) throws SQLException;

    void softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;

    void deleteByUserId(Long id);
}