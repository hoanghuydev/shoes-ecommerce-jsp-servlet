package com.ltweb_servlet_ecommerce.mapper;

import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.paging.Pageble;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
    T mapRow(ResultSet resultSet,Class<T> modelClass) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException;
    MapSQLAndParamsResult mapSQLAndParams(StringBuilder sql, T model, String typeSQL, Pageble pageble);
}
