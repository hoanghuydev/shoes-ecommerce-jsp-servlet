package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.VoucherModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IVoucherDAO {
    List<VoucherModel> findAll(Pageble pageble) throws SQLException;
    VoucherModel findById(Long id) throws SQLException;
    VoucherModel findWithFilter(VoucherModel model) throws SQLException;
    List<VoucherModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    List<VoucherModel> findAllWithFilter(VoucherModel model,Pageble pageble) throws SQLException;
    Long save(VoucherModel model) throws SQLException;
    void update(VoucherModel model) throws SQLException;
    void delete(Long id) throws SQLException;
    void softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
