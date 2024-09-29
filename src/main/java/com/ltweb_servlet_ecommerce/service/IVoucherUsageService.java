package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.VoucherUsageModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IVoucherUsageService {
    List<VoucherUsageModel> findAllWithFilter(VoucherUsageModel model,Pageble pageble) throws SQLException;
    VoucherUsageModel findWithFilter(VoucherUsageModel model) throws SQLException;
    List<VoucherUsageModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    VoucherUsageModel save(VoucherUsageModel model) throws SQLException;
    VoucherUsageModel delete(Long id) throws SQLException;
    VoucherUsageModel update(VoucherUsageModel model) throws SQLException;
    VoucherUsageModel findById(Long id) throws SQLException;
    List<VoucherUsageModel> findAll(Pageble pageble) throws SQLException;
    VoucherUsageModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
