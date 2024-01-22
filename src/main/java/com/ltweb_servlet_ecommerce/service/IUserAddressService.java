package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.UserAddressModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IUserAddressService {
    List<UserAddressModel> findAllWithFilter(UserAddressModel model, Pageble pageble) throws SQLException;
    UserAddressModel findWithFilter(UserAddressModel model) throws SQLException;
    List<UserAddressModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    UserAddressModel save(UserAddressModel model) throws SQLException;
    UserAddressModel delete(Long id) throws SQLException;
    UserAddressModel update(UserAddressModel model) throws SQLException;
    UserAddressModel findById(Long id) throws SQLException;
    List<UserAddressModel> findAll(Pageble pageble) throws SQLException;
    UserAddressModel softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
