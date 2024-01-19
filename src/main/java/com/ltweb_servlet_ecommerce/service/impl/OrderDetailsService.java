package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IOrderDetailsDAO;
import com.ltweb_servlet_ecommerce.model.OrderDetailsModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IOrderDetailsService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class OrderDetailsService implements IOrderDetailsService {
    @Inject
    IOrderDetailsDAO opinionDAO;

    @Override
    public List<OrderDetailsModel> findAllWithFilter(OrderDetailsModel model, Pageble pageble) throws SQLException {
        return opinionDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public OrderDetailsModel findWithFilter(OrderDetailsModel model) throws SQLException {
        return opinionDAO.findWithFilter(model);
    }

    @Override
    public List<OrderDetailsModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return opinionDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return opinionDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public OrderDetailsModel update(OrderDetailsModel model) throws SQLException {
        OrderDetailsModel oldModel = opinionDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public OrderDetailsModel delete(Long id) throws SQLException {
        OrderDetailsModel oldModel = opinionDAO.findById(id);
        opinionDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<OrderDetailsModel> findAll(Pageble pageble) throws SQLException {
        return opinionDAO.findAll(pageble);
    }

    @Override
    public OrderDetailsModel softDelete(Long id) throws SQLException {
        OrderDetailsModel model = opinionDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public OrderDetailsModel findById(Long id) throws SQLException {
        return opinionDAO.findById(id);
    }

    @Override
    public OrderDetailsModel save(OrderDetailsModel model) throws SQLException {
        Long productId = opinionDAO.save(model);
        return opinionDAO.findById(productId);
    }
}