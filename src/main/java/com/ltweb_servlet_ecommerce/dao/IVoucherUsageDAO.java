package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.VoucherUsageModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IVoucherUsageDAO {
    List<VoucherUsageModel> findAll(Pageble pageble) throws SQLException;
    VoucherUsageModel findById(Long id) throws SQLException;
    VoucherUsageModel findWithFilter(VoucherUsageModel model) throws SQLException;
    List<VoucherUsageModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<VoucherUsageModel> findAllWithFilter(VoucherUsageModel model,Pageble pageble) throws SQLException;
    Long save(VoucherUsageModel model) throws SQLException;
    void update(VoucherUsageModel model) throws SQLException;
    void delete(Long id) throws SQLException;

    void softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;
}
