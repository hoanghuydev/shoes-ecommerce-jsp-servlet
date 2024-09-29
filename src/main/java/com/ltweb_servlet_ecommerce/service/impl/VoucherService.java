package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IVoucherDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.model.VoucherConditionModel;
import com.ltweb_servlet_ecommerce.model.VoucherModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IVoucherService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class VoucherService implements IVoucherService {
    @Inject
    IVoucherDAO voucherDAO;

    @Override
    public List<VoucherModel> findAllWithFilter(VoucherModel model, Pageble pageble) throws SQLException {
        return voucherDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public VoucherModel findWithFilter(VoucherModel model) throws SQLException {
        return voucherDAO.findWithFilter(model);
    }

    @Override
    public List<VoucherModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        return voucherDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return voucherDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public VoucherModel update(VoucherModel model) throws SQLException {
        VoucherModel oldModel = voucherDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        voucherDAO.update(model);
        return voucherDAO.findById(model.getId());
    }

    @Override
    public VoucherModel delete(Long id) throws SQLException {
        VoucherModel oldModel = voucherDAO.findById(id);
        voucherDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<VoucherModel> findAll(Pageble pageble) throws SQLException {
        return voucherDAO.findAll(pageble);
    }

    @Override
    public VoucherModel softDelete(Long id) throws SQLException {
        VoucherModel model = voucherDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        voucherDAO.update(model);
        return voucherDAO.findById(model.getId());
    }

    @Override
    public VoucherModel findById(Long id) throws SQLException {
        return voucherDAO.findById(id);
    }

    @Override
    public VoucherModel save(VoucherModel model) throws SQLException {
        Long productId = voucherDAO.save(model);
        return voucherDAO.findById(productId);
    }
}
