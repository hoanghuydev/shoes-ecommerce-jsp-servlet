package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.AddressModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IAddressService {
    List<AddressModel> findAllWithFilter(AddressModel model, Pageble pageble) throws SQLException;
    AddressModel findWithFilter(AddressModel model) throws SQLException;
    List<AddressModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    AddressModel save(AddressModel model) throws SQLException;
    AddressModel delete(Long id) throws SQLException;
    AddressModel update(AddressModel model) throws SQLException;
    AddressModel findById(Long id) throws SQLException;
    List<AddressModel> findAll(Pageble pageble) throws SQLException;
    AddressModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
