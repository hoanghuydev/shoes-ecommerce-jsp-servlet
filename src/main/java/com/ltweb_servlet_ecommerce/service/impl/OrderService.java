package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import com.ltweb_servlet_ecommerce.dao.IOrderDAO;
import com.ltweb_servlet_ecommerce.dao.impl.OrderDAO;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.model.OrderDetailsModel;
import com.ltweb_servlet_ecommerce.model.OrderModel;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IOrderDetailsService;
import com.ltweb_servlet_ecommerce.service.IOrderService;
import com.ltweb_servlet_ecommerce.sort.Sorter;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.ObjectComparator;
import com.ltweb_servlet_ecommerce.utils.RuntimeInfo;
import com.ltweb_servlet_ecommerce.utils.StatusMapUtil;
import org.json.JSONObject;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderService implements IOrderService {
    @Inject
    IOrderDAO orderDAO;
    @Inject
    IOrderDetailsService orderDetailsService;

    @Override
    public List<OrderModel> findAllWithFilter(OrderModel model, Pageble pageble) throws SQLException {
        return orderDAO.findAllWithFilter(model, pageble);
    }

    @Override
    public OrderModel findWithFilter(OrderModel model) throws SQLException {
        return orderDAO.findWithFilter(model);
    }

    @Override
    public List<OrderModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return orderDAO.findByColumnValues(subQueryList, pageble);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return orderDAO.findWithCustomSQL(sql, params);
    }

    @Override
    public OrderModel update(OrderModel model) throws SQLException {
        OrderModel oldModel = orderDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        boolean isUpdated = orderDAO.update(model) > 0;
        OrderModel newModel = orderDAO.findById(model.getId());
        LinkedHashMap<String, String>[] results = ObjectComparator.compareObjects(oldModel, newModel);

        // logging
        JSONObject preValue = new JSONObject()
                .put(SystemConstant.VALUE_LOG, new JSONObject(results[0]));

        JSONObject value = new JSONObject()
                .put(SystemConstant.STATUS_LOG, String.format("Update orderDetails %s", isUpdated ? "successfully" : "failed"))
                .put(SystemConstant.VALUE_LOG, new JSONObject(results[1]));

        LoggerHelper.log(SystemConstant.WARN_LEVEL, "UPDATE",
                RuntimeInfo.getCallerClassNameAndLineNumber(), preValue, value);

        return isUpdated ? newModel : null;
    }

    @Override
    public OrderModel delete(Long id) throws SQLException {
        OrderModel oldModel = orderDAO.findById(id);
        orderDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<OrderModel> findAll(Pageble pageble) throws SQLException {
        if (orderDAO == null) {
            orderDAO = new OrderDAO();
        }

        return orderDAO.findAll(pageble);
    }

    @Override
    public List<OrderModel> findByUserId(Long id) {
        return orderDAO.findByUserId(id);
    }

    @Override
    public boolean softDelete(Long id) {
        OrderModel model = null;
        try {
            model = orderDAO.findById(id);
            if (model == null) {
                return false;
            }

            model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
            model.setIsDeleted(true);
            boolean isDeleted = orderDAO.update(model) > 0;

            boolean flag = true;
            List<OrderDetailsModel> all = orderDetailsService.findAllByOrderId(id);
            for (OrderDetailsModel orderDetailsModel : all) {
                flag = flag && orderDetailsService.softDelete(orderDetailsModel.getId());
            }

            return isDeleted && flag;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OrderModel findById(Long id) throws SQLException {
        return orderDAO.findById(id);
    }

    @Override
    public OrderModel save(OrderModel model) throws SQLException {
        model.setStatus(StatusMapUtil.getStatusKey(SystemConstant.ORDER_PROCESSING));
        Long productId = orderDAO.save(model);
        OrderModel result = orderDAO.findById(productId);

        //logging
        String status = String.format("Saved order %s", result != null ? "successfully" : "failed");
        JSONObject value = new JSONObject().put(SystemConstant.STATUS_LOG, status)
                .put(SystemConstant.VALUE_LOG, result != null ? new JSONObject(result) : new JSONObject());

        LoggerHelper.log(result != null ? SystemConstant.WARN_LEVEL : SystemConstant.ERROR_LEVEL,
                "INSERT", RuntimeInfo.getCallerClassNameAndLineNumber(), value);

        return result;
    }

    @Override
    public Map<String, Object> findIdBySlug(List<Object> params) throws SQLException {
        return orderDAO.findIdBySlug(params);
    }

    @Override
    public double getTotalPrice() {
        OrderDAO o = new OrderDAO();
        double totalPrice = 0;
        List<OrderModel> listOrder = null;
        try {
            listOrder = o.findAll(null);
            for (OrderModel i : listOrder) {
                if (!i.getStatus().equals("ORDER_CANCEL")) {
//                    OrderDetailsService orderDetailsService = new OrderDetailsService();
//                    List<OrderDetailsModel> listDetail = orderDetailsService.findAllByOrderId(i.getId());
//                    for (OrderDetailsModel j : listDetail) {
//                        double pricePerOrder = j.getSubTotal() * j.getQuantity();
//                        totalPrice += pricePerOrder;
//                    }
                    double pricePerOrder = i.getTotalAmount();
                    totalPrice += pricePerOrder;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalPrice;
    }
}
