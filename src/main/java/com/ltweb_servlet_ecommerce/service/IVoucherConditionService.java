package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.VoucherConditionModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IVoucherConditionService {
    List<VoucherConditionModel> findAllWithFilter(VoucherConditionModel model,Pageble pageble) throws SQLException;
    VoucherConditionModel findWithFilter(VoucherConditionModel model) throws SQLException;
    List<VoucherConditionModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    VoucherConditionModel save(VoucherConditionModel model) throws SQLException;
    VoucherConditionModel delete(Long id) throws SQLException;
    VoucherConditionModel update(VoucherConditionModel model) throws SQLException;
    VoucherConditionModel findById(Long id) throws SQLException;
    List<VoucherConditionModel> findAll(Pageble pageble) throws SQLException;
    VoucherConditionModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
