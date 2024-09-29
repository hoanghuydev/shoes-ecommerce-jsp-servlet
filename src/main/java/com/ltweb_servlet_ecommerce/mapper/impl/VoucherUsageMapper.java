package com.ltweb_servlet_ecommerce.mapper.impl;

import com.ltweb_servlet_ecommerce.mapper.RowMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.VoucherUsageModel;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoucherUsageMapper extends AbstractMapper<VoucherUsageModel> implements RowMapper<VoucherUsageModel> {
    @Override
    public VoucherUsageModel mapRow(ResultSet resultSet, Class<VoucherUsageModel> modelClass) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return mapper(resultSet,modelClass);
    }

    @Override
    public MapSQLAndParamsResult mapSQLAndParams(StringBuilder sql, VoucherUsageModel model, String typeSQL, Pageble pageble) {
        return mapSQL(sql, model, typeSQL,pageble);
    }
}
