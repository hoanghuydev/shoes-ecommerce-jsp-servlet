package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.LogModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.util.List;

public interface ILogService {
    List<LogModel> findAllWithFilter(LogModel model, Pageble pageble);
    LogModel findWithFilter(LogModel model);
    List<LogModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble);
    LogModel save(LogModel model);
    boolean delete(Long[] ids);
    LogModel update(LogModel model);
    LogModel findById(Long id);
    List<LogModel> findAll(Pageble pageble);
}
