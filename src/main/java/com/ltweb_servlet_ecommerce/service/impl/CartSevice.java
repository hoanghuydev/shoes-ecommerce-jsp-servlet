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
    ICartDAO cartDAO;

    @Override
    public List<CartModel> findAllWithFilter(CartModel model, Pageble pageble) throws SQLException {
        return cartDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public CartModel findWithFilter(CartModel model) throws SQLException {
        return cartDAO.findWithFilter(model);
    }

    @Override
    public List<CartModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return cartDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return cartDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public CartModel update(CartModel model) throws SQLException {
        CartModel oldModel = cartDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        cartDAO.update(model);
        return cartDAO.findById(model.getId());
    }

    @Override
    public CartModel delete(Long id) throws SQLException {
        CartModel oldModel = cartDAO.findById(id);
        cartDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<CartModel> findAll(Pageble pageble) throws SQLException {
        return cartDAO.findAll(pageble);
    }

    @Override
    public CartModel softDelete(Long id) throws SQLException {
        CartModel model = cartDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        cartDAO.update(model);
        return cartDAO.findById(model.getId());
    }

    @Override
    public CartModel findById(Long id) throws SQLException {
        return cartDAO.findById(id);
    }

    @Override
    public CartModel save(CartModel model) throws SQLException {
        Long productId = cartDAO.save(model);
        return cartDAO.findById(productId);
    }
}