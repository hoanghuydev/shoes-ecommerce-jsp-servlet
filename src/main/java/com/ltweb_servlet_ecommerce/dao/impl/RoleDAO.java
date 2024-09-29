package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IRoleDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.RoleMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.RoleModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoleDAO extends AbstractDAO<RoleModel> implements IRoleDAO {
    @Override
    public List<RoleModel> findAllWithFilter(RoleModel model,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM roles WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new RoleMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<RoleModel> result = query(sql.toString(), new RoleMapper(),params,RoleModel.class);
        return result;
    }
    @Override
    public List<RoleModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM roles");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new RoleMapper(),null, RoleModel.class);
    }

    @Override
    public RoleModel findById(Long id) throws SQLException {
        String sql = "select * from roles where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<RoleModel> result =  query(sql,new RoleMapper(),params,RoleModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public RoleModel findWithFilter(RoleModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM roles WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new RoleMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<RoleModel> result = query(sql.toString(), new RoleMapper(),params,RoleModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<RoleModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM roles WHERE 1=1 ");
        List<RoleModel> result = queryWithSubQuery(sqlStrBuilder,new RoleMapper(),subQueryList,"in",RoleModel.class,pageble);
        return result;
    }

    @Override
    public Long save(RoleModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO roles SET ");
        MapSQLAndParamsResult sqlAndParams = new RoleMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(RoleModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE roles SET ");
        MapSQLAndParamsResult sqlAndParams = new RoleMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from roles where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        RoleModel model = new RoleModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE roles SET ");
        MapSQLAndParamsResult sqlAndParams = new RoleMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }


}
