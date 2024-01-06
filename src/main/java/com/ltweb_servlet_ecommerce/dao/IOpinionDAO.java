package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.OpinionModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IOpinionDAO {
    List<OpinionModel> findAll(Pageble pageble) throws SQLException;
    OpinionModel findById(Long id) throws SQLException;
    OpinionModel findWithFilter(OpinionModel model) throws SQLException;
    List<OpinionModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException;
    List<OpinionModel> findAllWithFilter(OpinionModel model,Pageble pageble) throws SQLException;
    Long save(OpinionModel model) throws SQLException;
    void update(OpinionModel model) throws SQLException;
    void delete(Long id) throws SQLException;

    void softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql,List<Object> params) throws SQLException;
}
