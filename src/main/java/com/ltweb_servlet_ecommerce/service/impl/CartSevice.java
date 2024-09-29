package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.dao.ICartDAO;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.model.CartModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.ICartService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.ObjectComparator;
import com.ltweb_servlet_ecommerce.utils.RuntimeInfo;
import org.json.JSONObject;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CartSevice implements ICartService {
    @Inject
    ICartDAO cartDAO;

    @Override
    public List<CartModel> findAllWithFilter(CartModel model, Pageble pageble) throws SQLException {
        return cartDAO.findAllWithFilter(model, pageble);
    }

    @Override
    public CartModel findWithFilter(CartModel model) throws SQLException {
        return cartDAO.findWithFilter(model);
    }

    @Override
    public List<CartModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return cartDAO.findByColumnValues(subQueryList, pageble);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return cartDAO.findWithCustomSQL(sql, params);
    }

    @Override
    public void deleteByUserId(Long id) {
        cartDAO.deleteByUserId(id);
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
        boolean isDeleted = cartDAO.delete(id) > 0;

        //logging
        JSONObject preValue = new JSONObject().put(SystemConstant.VALUE_LOG, new JSONObject(oldModel));

        String status = String.format("Deleted cart with id = %d %s", id, isDeleted ? "successfully" : "failed");
        JSONObject value = new JSONObject().put(SystemConstant.STATUS_LOG, status);

        LoggerHelper.log(isDeleted ? SystemConstant.WARN_LEVEL : SystemConstant.ERROR_LEVEL,
                "DELETE", RuntimeInfo.getCallerClassNameAndLineNumber(), preValue, value);

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
        boolean isDeleted = cartDAO.update(model) > 0;
        CartModel newModel = cartDAO.findById(model.getId());
        LinkedHashMap<String, String>[] results = ObjectComparator.compareObjects(model, newModel);
        //logging
        JSONObject preValue = new JSONObject().put(SystemConstant.VALUE_LOG, new JSONObject(results[0]));
        String status = String.format("Deleted cart with id = %d %s", id, isDeleted ? "successfully" : "failed");
        JSONObject value = new JSONObject().put(SystemConstant.STATUS_LOG, status)
                .put(SystemConstant.VALUE_LOG, new JSONObject(results[1]));

        LoggerHelper.log(isDeleted ? SystemConstant.WARN_LEVEL : SystemConstant.ERROR_LEVEL,
                "DELETE", RuntimeInfo.getCallerClassNameAndLineNumber(), preValue, value);

        return newModel;
    }

    @Override
    public CartModel findById(Long id) throws SQLException {
        return cartDAO.findById(id);
    }

    @Override
    public CartModel save(CartModel model) throws SQLException {
        Long productId = cartDAO.save(model);
        CartModel result = cartDAO.findById(productId);

        //logging
        String status = String.format("Saved cart %s",  result !=null ? "successfully" : "failed");
        JSONObject value = new JSONObject().put(SystemConstant.STATUS_LOG, status)
                .put(SystemConstant.VALUE_LOG, new JSONObject(result));

        LoggerHelper.log(result != null ? SystemConstant.WARN_LEVEL : SystemConstant.ERROR_LEVEL,
                "INSERT", RuntimeInfo.getCallerClassNameAndLineNumber(), value);

        return result;
    }
}