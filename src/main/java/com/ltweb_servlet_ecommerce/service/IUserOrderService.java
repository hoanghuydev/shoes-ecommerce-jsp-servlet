package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.UserOrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IUserOrderService {
    List<UserOrderModel> findAllWithFilter(UserOrderModel model, Pageble pageble) throws SQLException;
    UserOrderModel findWithFilter(UserOrderModel model) throws SQLException;
    List<UserOrderModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    UserOrderModel save(UserOrderModel model) throws SQLException;
    UserOrderModel delete(Long id) throws SQLException;
    UserOrderModel update(UserOrderModel model) throws SQLException;
    UserOrderModel findById(Long id) throws SQLException;
    List<UserOrderModel> findAll(Pageble pageble) throws SQLException;
    UserOrderModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}

