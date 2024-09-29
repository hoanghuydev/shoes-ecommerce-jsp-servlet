package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.VoucherConditionModel;
import com.ltweb_servlet_ecommerce.model.VoucherModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IVoucherService {
    List<VoucherModel> findAllWithFilter(VoucherModel model,Pageble pageble) throws SQLException;
    VoucherModel findWithFilter(VoucherModel model) throws SQLException;
    List<VoucherModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    VoucherModel save(VoucherModel model) throws SQLException;
    VoucherModel delete(Long id) throws SQLException;
    VoucherModel update(VoucherModel model) throws SQLException;
    VoucherModel findById(Long id) throws SQLException;
    List<VoucherModel> findAll(Pageble pageble) throws SQLException;
    VoucherModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
