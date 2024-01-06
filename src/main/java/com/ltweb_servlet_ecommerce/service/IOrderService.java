package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IOrderService {
    List<OrderModel> findAllWithFilter(OrderModel model, Pageble pageble) throws SQLException;
    OrderModel findWithFilter(OrderModel model) throws SQLException;
    List<OrderModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    OrderModel save(OrderModel model) throws SQLException;
    OrderModel delete(Long id) throws SQLException;
    OrderModel update(OrderModel model) throws SQLException;
    OrderModel findById(Long id) throws SQLException;
    List<OrderModel> findAll(Pageble pageble) throws SQLException;
    OrderModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
