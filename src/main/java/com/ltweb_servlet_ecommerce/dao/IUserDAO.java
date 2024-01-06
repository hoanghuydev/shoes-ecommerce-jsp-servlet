package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IUserDAO {
    List<UserModel> findAll(Pageble pageble) throws SQLException;
    UserModel findById(Long id) throws SQLException;
    UserModel findWithFilter(UserModel model) throws SQLException;
    List<UserModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<UserModel> findAllWithFilter(UserModel model,Pageble pageble) throws SQLException;
    Long save(UserModel model) throws SQLException;
    void update(UserModel model) throws SQLException;
    void delete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;
}
