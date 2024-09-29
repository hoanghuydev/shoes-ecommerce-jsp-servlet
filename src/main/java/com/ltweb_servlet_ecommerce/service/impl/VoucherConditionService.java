package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IVoucherConditionDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.model.VoucherConditionModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IVoucherConditionService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class VoucherConditionService implements IVoucherConditionService {
    @Inject
    IVoucherConditionDAO voucherConditionDAO;

    @Override
    public List<VoucherConditionModel> findAllWithFilter(VoucherConditionModel model, Pageble pageble) throws SQLException {
        return voucherConditionDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public VoucherConditionModel findWithFilter(VoucherConditionModel model) throws SQLException {
        return voucherConditionDAO.findWithFilter(model);
    }

    @Override
    public List<VoucherConditionModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        return voucherConditionDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return voucherConditionDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public VoucherConditionModel update(VoucherConditionModel model) throws SQLException {
        VoucherConditionModel oldModel = voucherConditionDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        voucherConditionDAO.update(model);
        return voucherConditionDAO.findById(model.getId());
    }

    @Override
    public VoucherConditionModel delete(Long id) throws SQLException {
        VoucherConditionModel oldModel = voucherConditionDAO.findById(id);
        voucherConditionDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<VoucherConditionModel> findAll(Pageble pageble) throws SQLException {
        return voucherConditionDAO.findAll(pageble);
    }

    @Override
    public VoucherConditionModel softDelete(Long id) throws SQLException {
        VoucherConditionModel model = voucherConditionDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        voucherConditionDAO.update(model);
        return voucherConditionDAO.findById(model.getId());
    }

    @Override
    public VoucherConditionModel findById(Long id) throws SQLException {
        return voucherConditionDAO.findById(id);
    }

    @Override
    public VoucherConditionModel save(VoucherConditionModel model) throws SQLException {
        Long productId = voucherConditionDAO.save(model);
        return voucherConditionDAO.findById(productId);
    }
}
