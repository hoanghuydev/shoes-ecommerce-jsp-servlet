package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IUserOrderDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.UserOrderMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.UserOrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserOrderDAO extends AbstractDAO<UserOrderModel> implements IUserOrderDAO {
    @Override
    public List<UserOrderModel> findAllWithFilter(UserOrderModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM userOrder WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new UserOrderMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<UserOrderModel> result = query(sql.toString(), new UserOrderMapper(),params,UserOrderModel.class);
        return result;
    }
    @Override
    public List<UserOrderModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM userOrder");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new UserOrderMapper(),null, UserOrderModel.class);
    }

    @Override
    public UserOrderModel findById(Long id) throws SQLException {
        String sql = "select * from userOrder where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<UserOrderModel> result =  query(sql,new UserOrderMapper(),params,UserOrderModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public UserOrderModel findWithFilter(UserOrderModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM userOrder WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new UserOrderMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<UserOrderModel> result = query(sql.toString(), new UserOrderMapper(),params,UserOrderModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<UserOrderModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM userOrder WHERE 1=1 ");
        List<UserOrderModel> result = queryWithSubQuery(sqlStrBuilder,new UserOrderMapper(),subQueryList,"in",UserOrderModel.class,pageble);
        return result;
    }

    @Override
    public Long save(UserOrderModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO userOrder SET ");
        MapSQLAndParamsResult sqlAndParams = new UserOrderMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(UserOrderModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE userOrder SET ");
        MapSQLAndParamsResult sqlAndParams = new UserOrderMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from userOrder where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        UserOrderModel model = new UserOrderModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE userOrder SET ");
        MapSQLAndParamsResult sqlAndParams = new UserOrderMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }


}
