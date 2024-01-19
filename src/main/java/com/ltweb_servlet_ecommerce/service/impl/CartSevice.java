package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.ICartDAO;
import com.ltweb_servlet_ecommerce.model.CartModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.ICartService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class CartSevice implements ICartService {
    @Inject
    ICartDAO opinionDAO;

    @Override
    public List<CartModel> findAllWithFilter(CartModel model, Pageble pageble) throws SQLException {
        return opinionDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public CartModel findWithFilter(CartModel model) throws SQLException {
        return opinionDAO.findWithFilter(model);
    }

    @Override
    public List<CartModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return opinionDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return opinionDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public CartModel update(CartModel model) throws SQLException {
        CartModel oldModel = opinionDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public CartModel delete(Long id) throws SQLException {
        CartModel oldModel = opinionDAO.findById(id);
        opinionDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<CartModel> findAll(Pageble pageble) throws SQLException {
        return opinionDAO.findAll(pageble);
    }

    @Override
    public CartModel softDelete(Long id) throws SQLException {
        CartModel model = opinionDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        opinionDAO.update(model);
        return opinionDAO.findById(model.getId());
    }

    @Override
    public CartModel findById(Long id) throws SQLException {
        return opinionDAO.findById(id);
    }

    @Override
    public CartModel save(CartModel model) throws SQLException {
        Long productId = opinionDAO.save(model);
        return opinionDAO.findById(productId);
    }
}