package com.ltweb_servlet_ecommerce.mapper.impl;

import com.ltweb_servlet_ecommerce.mapper.RowMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.ImportOrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportOrderMapper extends AbstractMapper<ImportOrderModel> implements RowMapper<ImportOrderModel> {
    @Override
    public ImportOrderModel mapRow(ResultSet resultSet, Class<ImportOrderModel> modelClass) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return mapper(resultSet,modelClass);
    }

    @Override
    public MapSQLAndParamsResult mapSQLAndParams(StringBuilder sql, ImportOrderModel model, String typeSQL, Pageble pageble) {
        return mapSQL(sql, model, typeSQL,pageble);
    }
}
