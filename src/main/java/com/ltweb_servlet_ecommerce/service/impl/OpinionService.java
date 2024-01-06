package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IOpinionDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.model.OpinionModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IOpinionService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class OpinionService implements IOpinionService {
    @Inject
    IOpinionDAO opinionDAO;

    @Override
    public List<OpinionModel> findAllWithFilter(OpinionModel model, Pageble pageble) throws SQLException {
        return opinionDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public OpinionModel findWithFilter(OpinionModel model) throws SQLException {
        return opinionDAO.findWithFilter(model);
    }

    @Override
    public List<OpinionModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        return opinionDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return opinionDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public OpinionModel update(OpinionModel model) throws SQLException {
        OpinionModel oldModel = opinionDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public OpinionModel delete(Long id) throws SQLException {
        OpinionModel oldModel = opinionDAO.findById(id);
        opinionDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<OpinionModel> findAll(Pageble pageble) throws SQLException {
        return opinionDAO.findAll(pageble);
    }

    @Override
    public OpinionModel softDelete(Long id) throws SQLException {
        OpinionModel model = opinionDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public OpinionModel findById(Long id) throws SQLException {
        return opinionDAO.findById(id);
    }

    @Override
    public OpinionModel save(OpinionModel model) throws SQLException {
        Long productId = opinionDAO.save(model);
        return opinionDAO.findById(productId);
    }
}
