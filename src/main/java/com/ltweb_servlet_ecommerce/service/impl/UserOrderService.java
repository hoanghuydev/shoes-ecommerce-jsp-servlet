package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IUserOrderDAO;
import com.ltweb_servlet_ecommerce.model.UserOrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IUserOrderService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class UserOrderService implements IUserOrderService {
    @Inject
    IUserOrderDAO opinionDAO;

    @Override
    public List<UserOrderModel> findAllWithFilter(UserOrderModel model, Pageble pageble) throws SQLException {
        return opinionDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public UserOrderModel findWithFilter(UserOrderModel model) throws SQLException {
        return opinionDAO.findWithFilter(model);
    }

    @Override
    public List<UserOrderModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return opinionDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return opinionDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public UserOrderModel update(UserOrderModel model) throws SQLException {
        UserOrderModel oldModel = opinionDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public UserOrderModel delete(Long id) throws SQLException {
        UserOrderModel oldModel = opinionDAO.findById(id);
        opinionDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<UserOrderModel> findAll(Pageble pageble) throws SQLException {
        return opinionDAO.findAll(pageble);
    }

    @Override
    public UserOrderModel softDelete(Long id) throws SQLException {
        UserOrderModel model = opinionDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public UserOrderModel findById(Long id) throws SQLException {
        return opinionDAO.findById(id);
    }

    @Override
    public UserOrderModel save(UserOrderModel model) throws SQLException {
        Long productId = opinionDAO.save(model);
        return opinionDAO.findById(productId);
    }
}
