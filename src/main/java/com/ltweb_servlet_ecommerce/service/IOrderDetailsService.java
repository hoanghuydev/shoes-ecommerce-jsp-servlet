package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.OrderDetailsModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IOrderDetailsService {
    List<OrderDetailsModel> findAllWithFilter(OrderDetailsModel model, Pageble pageble) throws SQLException;
    OrderDetailsModel findWithFilter(OrderDetailsModel model) throws SQLException;
    List<OrderDetailsModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException;
    OrderDetailsModel save(OrderDetailsModel model) throws SQLException;
    OrderDetailsModel delete(Long id) throws SQLException;
    OrderDetailsModel update(OrderDetailsModel model) throws SQLException;
    OrderDetailsModel findById(Long id) throws SQLException;
    List<OrderDetailsModel> findAll(Pageble pageble) throws SQLException;
    boolean softDelete(Long id) throws SQLException;
    Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException;
    public List<OrderDetailsModel> findAllByOrderId(Long orderId) throws SQLException;

    boolean softDelete(long orderId, long productSizeId);


//    List<OrderDetailsModel> findByOrderId(Long id);

}
