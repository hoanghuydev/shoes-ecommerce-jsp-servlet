package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.ILogDAO;
import com.ltweb_servlet_ecommerce.mapper.RowMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.LogMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.LogModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.JDBCUtil;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//@Dependent
//@Alternative
public class LogDAO extends AbstractDAO<LogModel> implements ILogDAO {

    @Override
    public List<LogModel> findAllWithFilter(LogModel model, Pageble pageble) {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM logs WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new LogMapper().mapSQLAndParams(sqlStrBuilder, model, "select", pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<LogModel> result = query(sql, null, params, null);
        return result;
    }

    @Override
    public List<LogModel> findAll(Pageble pageble) {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM logs where isDeleted = 0");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder, pageble);
        return query(sqlStrBuilder.toString(), null, null, null);
    }

    @Override
    public LogModel findById(Long id) {
        String sql = "select * from logs where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<LogModel> result = query(sql, null, params, null);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, List<Object> parameters, Class<T> modelClass) {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (parameters != null) {
                setParameter(preparedStatement, parameters);
            }
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String ip = rs.getString("ip");
                String location = rs.getString("location");
                String level = rs.getString("level");
                String action = rs.getString("action");
                String resource = rs.getString("resource");
                String preValueString = rs.getString("preValue");

                JSONObject preValue = null;
                if (preValueString != null)
                    preValue = new JSONObject(preValueString);

                JSONObject value = null;
                if (rs.getString("value") != null)
                    value = new JSONObject(rs.getString("value"));

                Timestamp createdAt = rs.getTimestamp("createAt");
                Timestamp updatedAt = rs.getTimestamp("updateAt");
                LogModel logModel = LogModel.builder().ip(ip).location(location).level(level).action(action).resource(resource)
                        .preValue(preValue).value(value).build();
                logModel.setId(id);
                logModel.setCreateAt(createdAt);
                logModel.setUpdateAt(updatedAt);
                results.add((T) logModel);
            }
            return results;
        } catch (
                Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, rs);
        }

    }

    @Override
    public List<LogModel> checkAccessCount() {
        List<LogModel> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT ip, resource, location, COUNT(ip) AS accessCount, createAt " +
                "FROM logs " +
                "WHERE createAt > DATE_SUB(NOW() , INTERVAL 1 MINUTE) AND isDeleted = 0 " +
                "GROUP BY ip, resource, createAt " +
                "HAVING COUNT(ip) > 10";
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String ip = rs.getString("ip");
                String resource = rs.getString("resource");
                String location = rs.getString("location");
                int accessCount = rs.getInt("accessCount");
                Timestamp createAt = rs.getTimestamp("createAt");
                LogModel logModel = LogModel.builder().ip(ip).location(location).resource(resource).accessCount(accessCount).build();
                logModel.setCreateAt(createAt);
                results.add(logModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, rs);
        }
        return results;
    }

    @Override
    public LogModel findWithFilter(LogModel model) {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM logs WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new LogMapper().mapSQLAndParams(sqlStrBuilder, model, "select", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<LogModel> result = query(sql, null, params, null);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<LogModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM logs WHERE 1=1 ");
        List<LogModel> result = queryWithSubQuery(sqlStrBuilder, new LogMapper(), subQueryList, "in", LogModel.class, pageble);
        return result;
    }

    @Override
    public Long save(LogModel model) {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO logs SET ");
        MapSQLAndParamsResult sqlAndParams = new LogMapper().mapSQLAndParams(sqlStrBuilder, model, "insert", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        try {
            return insert(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int update(LogModel model) {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE logs SET ");
        MapSQLAndParamsResult sqlAndParams = new LogMapper().mapSQLAndParams(sqlStrBuilder, model, "update", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        try {
            return update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from logs where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        try {
            return delete(sql, params) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean softDelete(Long id) {
        String sql = "update logs set isDeleted=? where id=?";
        List<Object> params = new ArrayList<>();
        params.add(1);
        params.add(id);
        try {
            return delete(sql, params) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
