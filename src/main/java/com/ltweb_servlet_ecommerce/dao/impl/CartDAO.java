package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.ICartDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.CartMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.CartModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartDAO extends AbstractDAO<CartModel> implements ICartDAO {
    @Override
    public List<CartModel> findAllWithFilter(CartModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM cart WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new CartMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<CartModel> result = query(sql.toString(), new CartMapper(),params,CartModel.class);
        return result;
    }
    @Override
    public List<CartModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM cart");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new CartMapper(),null, CartModel.class);
    }

    @Override
    public CartModel findById(Long id) throws SQLException {
        String sql = "select * from cart where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<CartModel> result =  query(sql,new CartMapper(),params,CartModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public CartModel findWithFilter(CartModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM cart WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new CartMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<CartModel> result = query(sql.toString(), new CartMapper(),params,CartModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<CartModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM cart WHERE 1=1 ");
        List<CartModel> result = queryWithSubQuery(sqlStrBuilder,new CartMapper(),subQueryList,"in",CartModel.class,pageble);
        return result;
    }

    @Override
    public Long save(CartModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO cart SET ");
        MapSQLAndParamsResult sqlAndParams = new CartMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(CartModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE cart SET ");
        MapSQLAndParamsResult sqlAndParams = new CartMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from cart where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        CartModel model = new CartModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE cart SET ");
        MapSQLAndParamsResult sqlAndParams = new CartMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }


}