package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.LogModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.util.List;
import java.util.Map;

public interface ILogDAO {
    List<LogModel> findAll(Pageble pageble) ;
    LogModel findById(Long id) ;
    LogModel findWithFilter(LogModel model) ;
    List<LogModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) ;
    List<LogModel> findAllWithFilter(LogModel model,Pageble pageble) ;
    Long save(LogModel model) ;
    int update(LogModel model) ;
    boolean delete(Long id) ;
    boolean softDelete(Long id) ;
    List<LogModel> checkAccessCount();
}
