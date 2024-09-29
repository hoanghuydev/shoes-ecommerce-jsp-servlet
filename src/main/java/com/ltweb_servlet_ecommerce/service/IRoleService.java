package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.RoleModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IRoleService {
    List<RoleModel> findAllWithFilter(RoleModel model,Pageble pageble) throws SQLException;
    RoleModel findWithFilter(RoleModel model) throws SQLException;
    List<RoleModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    RoleModel save(RoleModel model) throws SQLException;
    RoleModel delete(Long id) throws SQLException;
    RoleModel update(RoleModel model) throws SQLException;
    RoleModel findById(Long id) throws SQLException;
    List<RoleModel> findAll(Pageble pageble) throws SQLException;
    RoleModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
