package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.dao.IUserOrderDAO;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.model.UserOrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IUserOrderService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.RuntimeInfo;
import org.json.JSONObject;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class UserOrderService implements IUserOrderService {
    @Inject
    IUserOrderDAO userDAO;

    @Override
    public List<UserOrderModel> findAllWithFilter(UserOrderModel model, Pageble pageble) throws SQLException {
        return userDAO.findAllWithFilter(model, pageble);
    }

    @Override
    public UserOrderModel findWithFilter(UserOrderModel model) throws SQLException {
        return userDAO.findWithFilter(model);
    }

    @Override
    public List<UserOrderModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return userDAO.findByColumnValues(subQueryList, pageble);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return userDAO.findWithCustomSQL(sql, params);
    }

    @Override
    public UserOrderModel update(UserOrderModel model) throws SQLException {
        UserOrderModel oldModel = userDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        userDAO.update(model);
        return userDAO.findById(model.getId());
    }

    @Override
    public UserOrderModel delete(Long id) throws SQLException {
        UserOrderModel oldModel = userDAO.findById(id);
        userDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<UserOrderModel> findAll(Pageble pageble) throws SQLException {
        return userDAO.findAll(pageble);
    }

    @Override
    public UserOrderModel softDelete(Long id) throws SQLException {
        UserOrderModel model = userDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        userDAO.update(model);
        return userDAO.findById(model.getId());
    }

    @Override
    public UserOrderModel findById(Long id) throws SQLException {
        return userDAO.findById(id);
    }

    @Override
    public UserOrderModel save(UserOrderModel model) throws SQLException {
        Long productId = userDAO.save(model);
        UserOrderModel result = userDAO.findById(productId);

        //logging
        String status = String.format("Saved userOrder %s", result != null ? "successfully" : "failed");
        JSONObject value = new JSONObject().put(SystemConstant.STATUS_LOG, status)
                .put(SystemConstant.VALUE_LOG, result != null ? new JSONObject(result) : new JSONObject());

        LoggerHelper.log(result != null ? SystemConstant.WARN_LEVEL : SystemConstant.ERROR_LEVEL,
                "INSERT", RuntimeInfo.getCallerClassNameAndLineNumber(), value);

        return result;
    }
}
