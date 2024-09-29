package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IOpinionDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.OpinionMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.OpinionModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpinionDAO extends AbstractDAO<OpinionModel> implements IOpinionDAO {
    @Override
    public List<OpinionModel> findAllWithFilter(OpinionModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM opinions WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new OpinionMapper().mapSQLAndParams(sqlStrBuilder, model, "select", pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<OpinionModel> result = query(sql.toString(), new OpinionMapper(), params, OpinionModel.class);
        return result;
    }

    @Override
    public List<OpinionModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM opinions");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder, pageble);
        return query(sqlStrBuilder.toString(), new OpinionMapper(), null, OpinionModel.class);
    }

    @Override
    public OpinionModel findById(Long id) throws SQLException {
        String sql = "select * from opinions where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<OpinionModel> result = query(sql, new OpinionMapper(), params, OpinionModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public OpinionModel findWithFilter(OpinionModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM opinions WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new OpinionMapper().mapSQLAndParams(sqlStrBuilder, model, "select", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<OpinionModel> result = query(sql.toString(), new OpinionMapper(), params, OpinionModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<OpinionModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM opinions WHERE 1=1 ");
        List<OpinionModel> result = queryWithSubQuery(sqlStrBuilder, new OpinionMapper(), subQueryList, "in", OpinionModel.class, pageble);
        return result;
    }

    @Override
    public Long save(OpinionModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO opinions SET ");
        MapSQLAndParamsResult sqlAndParams = new OpinionMapper().mapSQLAndParams(sqlStrBuilder, model, "insert", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql, params);
    }

    @Override
    public void update(OpinionModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE opinions SET ");
        MapSQLAndParamsResult sqlAndParams = new OpinionMapper().mapSQLAndParams(sqlStrBuilder, model, "update", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql, params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from opinions where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql, params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        OpinionModel model = new OpinionModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE opinions SET ");
        MapSQLAndParamsResult sqlAndParams = new OpinionMapper().mapSQLAndParams(sqlStrBuilder, model, "update", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql, params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql, params);
    }


}
