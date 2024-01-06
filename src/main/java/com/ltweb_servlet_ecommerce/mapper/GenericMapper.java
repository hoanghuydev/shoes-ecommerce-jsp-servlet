package com.ltweb_servlet_ecommerce.mapper;

import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.paging.Pageble;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface GenericMapper<T> {
    //    Map model thành câu lệnh sql update
    //StringBuilder sql = new StringBuilder("UPDATE news SET ");
    //    thay news thành bảng cần update
    MapSQLAndParamsResult mapSQL(StringBuilder sql, T model, String typeSQL, Pageble pageble);
    //Map row của result set vào model
    T mapper(ResultSet resultSet, Class<T> modelClass) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
