package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IOrderDetailsDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.OrderDetailsMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.OrderDetailsModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDetailsDAO extends AbstractDAO<OrderDetailsModel> implements IOrderDetailsDAO {
    @Override
    public List<OrderDetailsModel> findAllWithFilter(OrderDetailsModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM orderDetails WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new OrderDetailsMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<OrderDetailsModel> result = query(sql.toString(), new OrderDetailsMapper(),params,OrderDetailsModel.class);
        return result;
    }
    @Override
    public List<OrderDetailsModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM orderDetails");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new OrderDetailsMapper(),null, OrderDetailsModel.class);
    }

    @Override
    public OrderDetailsModel findById(Long id) throws SQLException {
        String sql = "select * from orderDetails where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<OrderDetailsModel> result =  query(sql,new OrderDetailsMapper(),params,OrderDetailsModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public OrderDetailsModel findWithFilter(OrderDetailsModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM orderDetails WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new OrderDetailsMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<OrderDetailsModel> result = query(sql.toString(), new OrderDetailsMapper(),params,OrderDetailsModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<OrderDetailsModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM orderDetails WHERE 1=1 ");
        List<OrderDetailsModel> result = queryWithSubQuery(sqlStrBuilder,new OrderDetailsMapper(),subQueryList,"in",OrderDetailsModel.class,pageble);
        return result;
    }

    @Override
    public Long save(OrderDetailsModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO orderDetails SET ");
        MapSQLAndParamsResult sqlAndParams = new OrderDetailsMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(OrderDetailsModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE orderDetails SET ");
        MapSQLAndParamsResult sqlAndParams = new OrderDetailsMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from orderDetails where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        OrderDetailsModel model = new OrderDetailsModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE orderDetails SET ");
        MapSQLAndParamsResult sqlAndParams = new OrderDetailsMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }


}