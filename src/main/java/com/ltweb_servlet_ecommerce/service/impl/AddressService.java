package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IAddressDAO;
import com.ltweb_servlet_ecommerce.model.AddressModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IAddressService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class AddressService implements IAddressService {
    @Inject
    IAddressDAO addressDAO;

    @Override
    public List<AddressModel> findAllWithFilter(AddressModel model, Pageble pageble) throws SQLException {
        return addressDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public AddressModel findWithFilter(AddressModel model) throws SQLException {
        return addressDAO.findWithFilter(model);
    }

    @Override
    public List<AddressModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return addressDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return addressDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public AddressModel update(AddressModel model) throws SQLException {
        AddressModel oldModel = addressDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        addressDAO.update(model);
        return addressDAO.findById(model.getId());
    }

    @Override
    public AddressModel delete(Long id) throws SQLException {
        AddressModel oldModel = addressDAO.findById(id);
        addressDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<AddressModel> findAll(Pageble pageble) throws SQLException {
        return addressDAO.findAll(pageble);
    }

    @Override
    public AddressModel softDelete(Long id) throws SQLException {
        AddressModel model = addressDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        addressDAO.update(model);
        return addressDAO.findById(model.getId());
    }

    @Override
    public AddressModel findById(Long id) throws SQLException {
        return addressDAO.findById(id);
    }

    @Override
    public AddressModel save(AddressModel model) throws SQLException {
        Long productId = addressDAO.save(model);
        return addressDAO.findById(productId);
    }
}