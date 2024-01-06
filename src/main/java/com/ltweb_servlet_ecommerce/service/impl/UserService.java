package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IUserDAO;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IUserService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class UserService implements IUserService {
    @Inject
    IUserDAO opinionDAO;

    @Override
    public List<UserModel> findAllWithFilter(UserModel model, Pageble pageble) throws SQLException {
        return opinionDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public UserModel findWithFilter(UserModel model) throws SQLException {
        return opinionDAO.findWithFilter(model);
    }

    @Override
    public List<UserModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return opinionDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return opinionDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public UserModel update(UserModel model) throws SQLException {
        UserModel oldModel = opinionDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public UserModel delete(Long id) throws SQLException {
        UserModel oldModel = opinionDAO.findById(id);
        opinionDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<UserModel> findAll(Pageble pageble) throws SQLException {
        return opinionDAO.findAll(pageble);
    }

    @Override
    public UserModel softDelete(Long id) throws SQLException {
        UserModel model = opinionDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public UserModel findById(Long id) throws SQLException {
        return opinionDAO.findById(id);
    }

    @Override
    public UserModel save(UserModel model) throws SQLException {
        Long productId = opinionDAO.save(model);
        return opinionDAO.findById(productId);
    }
}
