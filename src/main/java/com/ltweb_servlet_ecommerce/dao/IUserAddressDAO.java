package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.UserAddressModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IUserAddressDAO {
    List<UserAddressModel> findAll(Pageble pageble) throws SQLException;
    UserAddressModel findById(Long id) throws SQLException;
    UserAddressModel findWithFilter(UserAddressModel model) throws SQLException;
    List<UserAddressModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    List<UserAddressModel> findAllWithFilter(UserAddressModel model,Pageble pageble) throws SQLException;
    Long save(UserAddressModel model) throws SQLException;
    void update(UserAddressModel model) throws SQLException;
    void delete(Long id) throws SQLException;

    void softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
}
