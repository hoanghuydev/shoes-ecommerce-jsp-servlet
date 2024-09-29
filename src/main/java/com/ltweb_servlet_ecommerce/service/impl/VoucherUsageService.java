package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IVoucherUsageDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.model.VoucherUsageModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IVoucherUsageService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class VoucherUsageService implements IVoucherUsageService {
    @Inject
    IVoucherUsageDAO voucherUsageDAO;

    @Override
    public List<VoucherUsageModel> findAllWithFilter(VoucherUsageModel model, Pageble pageble) throws SQLException {
        return voucherUsageDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public VoucherUsageModel findWithFilter(VoucherUsageModel model) throws SQLException {
        return voucherUsageDAO.findWithFilter(model);
    }

    @Override
    public List<VoucherUsageModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        return voucherUsageDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return voucherUsageDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public VoucherUsageModel update(VoucherUsageModel model) throws SQLException {
        VoucherUsageModel oldModel = voucherUsageDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        voucherUsageDAO.update(model);
        return voucherUsageDAO.findById(model.getId());
    }

    @Override
    public VoucherUsageModel delete(Long id) throws SQLException {
        VoucherUsageModel oldModel = voucherUsageDAO.findById(id);
        voucherUsageDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<VoucherUsageModel> findAll(Pageble pageble) throws SQLException {
        return voucherUsageDAO.findAll(pageble);
    }

    @Override
    public VoucherUsageModel softDelete(Long id) throws SQLException {
        VoucherUsageModel model = voucherUsageDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        voucherUsageDAO.update(model);
        return voucherUsageDAO.findById(model.getId());
    }

    @Override
    public VoucherUsageModel findById(Long id) throws SQLException {
        return voucherUsageDAO.findById(id);
    }

    @Override
    public VoucherUsageModel save(VoucherUsageModel model) throws SQLException {
        Long productId = voucherUsageDAO.save(model);
        return voucherUsageDAO.findById(productId);
    }
}
