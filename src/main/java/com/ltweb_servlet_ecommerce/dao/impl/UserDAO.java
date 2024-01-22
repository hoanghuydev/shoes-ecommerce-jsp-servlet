package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IUserDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.UserMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.ProductMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.mapper.impl.UserMapper;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import com.restfb.types.User;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDAO extends AbstractDAO<UserModel> implements IUserDAO {
    @Override
    public List<UserModel> findAllWithFilter(UserModel model,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM `user` WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new UserMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<UserModel> result = query(sql.toString(), new UserMapper(),params,UserModel.class);
        return result;
    }
    @Override
    public List<UserModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM `user`");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new UserMapper(),null, UserModel.class);
    }

    @Override
    public UserModel findById(Long id) throws SQLException {
        String sql = "select * from `user` where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<UserModel> result =  query(sql,new UserMapper(),params,UserModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public UserModel findWithFilter(UserModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM `user` WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new UserMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<UserModel> result = query(sql.toString(), new UserMapper(),params,UserModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<UserModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM `user` WHERE 1=1 ");
        List<UserModel> result = queryWithSubQuery(sqlStrBuilder,new UserMapper(),subQueryList,"in",UserModel.class,pageble);
        return result;
    }

    @Override
    public Long save(UserModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO `user` SET ");
        MapSQLAndParamsResult sqlAndParams = new UserMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(UserModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE `user` SET ");
        MapSQLAndParamsResult sqlAndParams = new UserMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from `user` where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }
}
