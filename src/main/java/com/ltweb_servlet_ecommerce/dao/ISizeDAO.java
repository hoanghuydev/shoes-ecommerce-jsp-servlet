package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.SizeModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ISizeDAO {
    List<SizeModel> findAll(Pageble pageble) throws SQLException;
    SizeModel findById(Long id) throws SQLException;
    SizeModel findWithFilter(SizeModel model) throws SQLException;
    List<SizeModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<SizeModel> findAllWithFilter(SizeModel model,Pageble pageble) throws SQLException;
    Long save(SizeModel model) throws SQLException;
    void update(SizeModel model) throws SQLException;
    void delete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;
}
