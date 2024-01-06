package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.mapper.RowMapper;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface GenericDAO<T> {
    Map<String,Object> queryCustom(String sql,List<Object> parameters) throws SQLException;
    <T> List<T> queryWithSubQuery(StringBuilder sqlBuilder, RowMapper<T> rowMapper, List<SubQuery> subQueryList, String type, Class<T> modelClass, Pageble pageble) throws SQLException;
    <T> List<T> query(String sql, RowMapper<T> rowMapper, List<Object> parameters, Class<T> modelClass) throws SQLException;
    Long insert(String sql, List<Object> parameters) throws SQLException;
    void update(String sql,  List<Object> parameters) throws SQLException;
    void delete(String sql,  List<Object> parameters) throws SQLException;
}

