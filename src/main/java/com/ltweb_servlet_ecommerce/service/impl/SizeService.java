package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IOrderDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.model.SizeModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.ISizeService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class SizeService implements ISizeService {
    @Inject
    ISizeDAO sizeDAO;

    @Override
    public List<SizeModel> findAllWithFilter(SizeModel model, Pageble pageble) throws SQLException {
        return sizeDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public SizeModel findWithFilter(SizeModel model) throws SQLException {
        return sizeDAO.findWithFilter(model);
    }

    @Override
    public List<SizeModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        return sizeDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return sizeDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public SizeModel update(SizeModel model) throws SQLException {
        SizeModel oldModel = sizeDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        sizeDAO.update(model);
        return sizeDAO.findById(model.getId());
    }

    @Override
    public SizeModel delete(Long id) throws SQLException {
        SizeModel oldModel = sizeDAO.findById(id);
        sizeDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<SizeModel> findAll(Pageble pageble) throws SQLException {
        return sizeDAO.findAll(pageble);
    }

    @Override
    public SizeModel softDelete(Long id) throws SQLException {
        SizeModel model = sizeDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        sizeDAO.update(model);
        return sizeDAO.findById(model.getId());
    }

    @Override
    public SizeModel findById(Long id) throws SQLException {
        return sizeDAO.findById(id);
    }

    @Override
    public SizeModel save(SizeModel model) throws SQLException {
        Long productId = sizeDAO.save(model);
        return sizeDAO.findById(productId);
    }
}
