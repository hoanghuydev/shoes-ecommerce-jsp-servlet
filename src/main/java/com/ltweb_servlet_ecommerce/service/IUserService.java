package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IUserService {
    List<UserModel> findAllWithFilter(UserModel model, Pageble pageble) throws SQLException;
    UserModel findWithFilter(UserModel model) throws SQLException;
    List<UserModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    UserModel save(UserModel model) throws SQLException;
    UserModel delete(Long id) throws SQLException;
    UserModel update(UserModel model) throws SQLException;
    UserModel findById(Long id) throws SQLException;
    List<UserModel> findAll(Pageble pageble) throws SQLException;
    UserModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;

}
