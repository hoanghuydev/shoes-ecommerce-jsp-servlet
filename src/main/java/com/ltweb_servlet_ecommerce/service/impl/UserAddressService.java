package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IUserAddressDAO;
import com.ltweb_servlet_ecommerce.model.UserAddressModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IUserAddressService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class UserAddressService implements IUserAddressService {
    @Inject
    IUserAddressDAO userAddressDAO;

    @Override
    public List<UserAddressModel> findAllWithFilter(UserAddressModel model, Pageble pageble) throws SQLException {
        return userAddressDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public UserAddressModel findWithFilter(UserAddressModel model) throws SQLException {
        return userAddressDAO.findWithFilter(model);
    }

    @Override
    public List<UserAddressModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return userAddressDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return userAddressDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public UserAddressModel update(UserAddressModel model) throws SQLException {
        UserAddressModel oldModel = userAddressDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        userAddressDAO.update(model);
        return userAddressDAO.findById(model.getId());
    }

    @Override
    public UserAddressModel delete(Long id) throws SQLException {
        UserAddressModel oldModel = userAddressDAO.findById(id);
        userAddressDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<UserAddressModel> findAll(Pageble pageble) throws SQLException {
        return userAddressDAO.findAll(pageble);
    }

    @Override
    public UserAddressModel softDelete(Long id) throws SQLException {
        UserAddressModel model = userAddressDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        userAddressDAO.update(model);
        return userAddressDAO.findById(model.getId());
    }

    @Override
    public UserAddressModel findById(Long id) throws SQLException {
        return userAddressDAO.findById(id);
    }

    @Override
    public UserAddressModel save(UserAddressModel model) throws SQLException {
        Long productId = userAddressDAO.save(model);
        return userAddressDAO.findById(productId);
    }
}