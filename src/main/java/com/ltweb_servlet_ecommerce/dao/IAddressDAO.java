package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.AddressModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IAddressDAO {
    List<AddressModel> findAll(Pageble pageble) throws SQLException;
    AddressModel findById(Long id) throws SQLException;
    AddressModel findWithFilter(AddressModel model) throws SQLException;
    List<AddressModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    List<AddressModel> findAllWithFilter(AddressModel model,Pageble pageble) throws SQLException;
    Long save(AddressModel model) throws SQLException;
    void update(AddressModel model) throws SQLException;
    void delete(Long id) throws SQLException;

    void softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
