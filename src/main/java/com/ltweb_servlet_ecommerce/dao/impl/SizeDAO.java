package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IOrderDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.mapper.RowMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.SizeMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.SizeMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.SizeModel;
import com.ltweb_servlet_ecommerce.model.SizeModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SizeDAO extends AbstractDAO<SizeModel> implements ISizeDAO {
    @Override
    public List<SizeModel> findAllWithFilter(SizeModel model,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM size WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new SizeMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<SizeModel> result = query(sql.toString(), new SizeMapper(),params,SizeModel.class);
        return result;
    }
    @Override
    public List<SizeModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM size");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new SizeMapper(),null, SizeModel.class);
    }

    @Override
    public SizeModel findById(Long id) throws SQLException {
        String sql = "select * from size where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<SizeModel> result =  query(sql,new SizeMapper(),params,SizeModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public SizeModel findWithFilter(SizeModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM size WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new SizeMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<SizeModel> result = query(sql.toString(), new SizeMapper(),params,SizeModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<SizeModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM size WHERE 1=1 ");
        List<SizeModel> result = queryWithSubQuery(sqlStrBuilder,new SizeMapper(),subQueryList,"in",SizeModel.class,pageble);
        return result;
    }

    @Override
    public Long save(SizeModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO size SET ");
        MapSQLAndParamsResult sqlAndParams = new SizeMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(SizeModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE size SET ");
        MapSQLAndParamsResult sqlAndParams = new SizeMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from size where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }


}
