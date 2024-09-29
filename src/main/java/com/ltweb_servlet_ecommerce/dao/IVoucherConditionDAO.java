package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.VoucherConditionModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IVoucherConditionDAO {
    List<VoucherConditionModel> findAll(Pageble pageble) throws SQLException;
    VoucherConditionModel findById(Long id) throws SQLException;
    VoucherConditionModel findWithFilter(VoucherConditionModel model) throws SQLException;
    List<VoucherConditionModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<VoucherConditionModel> findAllWithFilter(VoucherConditionModel model,Pageble pageble) throws SQLException;
    Long save(VoucherConditionModel model) throws SQLException;
    void update(VoucherConditionModel model) throws SQLException;
    void delete(Long id) throws SQLException;

    void softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;
}
