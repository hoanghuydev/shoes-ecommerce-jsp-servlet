package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IUserAddressDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.UserAddressMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.UserAddressModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserAddressDAO extends AbstractDAO<UserAddressModel> implements IUserAddressDAO {
    @Override
    public List<UserAddressModel> findAllWithFilter(UserAddressModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM user_addresses WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new UserAddressMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<UserAddressModel> result = query(sql.toString(), new UserAddressMapper(),params,UserAddressModel.class);
        return result;
    }
    @Override
    public List<UserAddressModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM user_addresses");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new UserAddressMapper(),null, UserAddressModel.class);
    }

    @Override
    public UserAddressModel findById(Long id) throws SQLException {
        String sql = "select * from user_addresses where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<UserAddressModel> result =  query(sql,new UserAddressMapper(),params,UserAddressModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public UserAddressModel findWithFilter(UserAddressModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM user_addresses WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new UserAddressMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<UserAddressModel> result = query(sql.toString(), new UserAddressMapper(),params,UserAddressModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<UserAddressModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM user_addresses WHERE 1=1 ");
        List<UserAddressModel> result = queryWithSubQuery(sqlStrBuilder,new UserAddressMapper(),subQueryList,"in",UserAddressModel.class,pageble);
        return result;
    }

    @Override
    public Long save(UserAddressModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO user_addresses SET ");
        MapSQLAndParamsResult sqlAndParams = new UserAddressMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(UserAddressModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE user_addresses SET ");
        MapSQLAndParamsResult sqlAndParams = new UserAddressMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from user_addresses where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        UserAddressModel model = new UserAddressModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE user_addresses SET ");
        MapSQLAndParamsResult sqlAndParams = new UserAddressMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }


}
