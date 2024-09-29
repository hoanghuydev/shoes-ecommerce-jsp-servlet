package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IRoleDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.model.RoleModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IRoleService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class RoleService implements IRoleService {
    @Inject
    IRoleDAO roleDAO;

    @Override
    public List<RoleModel> findAllWithFilter(RoleModel model, Pageble pageble) throws SQLException {
        return roleDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public RoleModel findWithFilter(RoleModel model) throws SQLException {
        return roleDAO.findWithFilter(model);
    }

    @Override
    public List<RoleModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        return roleDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return roleDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public RoleModel update(RoleModel model) throws SQLException {
        RoleModel oldModel = roleDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        roleDAO.update(model);
        return roleDAO.findById(model.getId());
    }

    @Override
    public RoleModel delete(Long id) throws SQLException {
        RoleModel oldModel = roleDAO.findById(id);
        roleDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<RoleModel> findAll(Pageble pageble) throws SQLException {
        return roleDAO.findAll(pageble);
    }

    @Override
    public RoleModel softDelete(Long id) throws SQLException {
        RoleModel model = roleDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        roleDAO.update(model);
        return roleDAO.findById(model.getId());
    }

    @Override
    public RoleModel findById(Long id) throws SQLException {
        return roleDAO.findById(id);
    }

    @Override
    public RoleModel save(RoleModel model) throws SQLException {
        Long productId = roleDAO.save(model);
        return roleDAO.findById(productId);
    }
}
