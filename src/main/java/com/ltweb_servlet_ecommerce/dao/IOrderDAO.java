package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IOrderDAO {
    List<OrderModel> findAll(Pageble pageble) throws SQLException;
    OrderModel findById(Long id) throws SQLException;
    OrderModel findWithFilter(OrderModel model) throws SQLException;
    List<OrderModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<OrderModel> findAllWithFilter(OrderModel model,Pageble pageble) throws SQLException;
    Long save(OrderModel model) throws SQLException;
    void update(OrderModel model) throws SQLException;
    void delete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;
}
