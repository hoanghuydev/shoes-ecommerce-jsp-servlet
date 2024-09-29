package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.RoleModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IRoleDAO {
    List<RoleModel> findAll(Pageble pageble) throws SQLException;
    RoleModel findById(Long id) throws SQLException;
    RoleModel findWithFilter(RoleModel model) throws SQLException;
    List<RoleModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<RoleModel> findAllWithFilter(RoleModel model,Pageble pageble) throws SQLException;
    Long save(RoleModel model) throws SQLException;
    void update(RoleModel model) throws SQLException;
    void delete(Long id) throws SQLException;

    void softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;
}
