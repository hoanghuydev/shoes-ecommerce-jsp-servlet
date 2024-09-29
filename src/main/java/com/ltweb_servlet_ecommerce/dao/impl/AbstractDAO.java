package com.ltweb_servlet_ecommerce.dao.impl;


import com.ltweb_servlet_ecommerce.dao.GenericDAO;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.mapper.RowMapper;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.JDBCUtil;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractDAO<T> implements GenericDAO<T> {
    private String getInClauseParameters(int size) {

        StringBuilder parameters = new StringBuilder();
        for (int i = 0; i < size; i++) {
            parameters.append("?");
            if (i < size - 1) {
                parameters.append(",");
            }
        }
        return parameters.toString();
    }

    @Override
    public Map<String, Object> queryCustom(String sql, List<Object> parameters) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            if (parameters != null) {
                setParameter(preparedStatement, parameters);
            }
            resultSet = preparedStatement.executeQuery();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    Object values = resultSet.getObject(columnName);
                    resultMap.put(columnName, values);
                }
            }
            return resultMap;
        } catch (SQLException e) {
            LoggerHelper.logDetailedDangerMessage(e, "SELECT");
            return null;
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
        }
    }
    @Override
    public int updateCustom(String sql, List<Object> parameters) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            if (parameters != null) {
                setParameter(preparedStatement, parameters);
            }
            affectedRows = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LoggerHelper.logDetailedDangerMessage(e, "UPDATE");
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, null);
        }
        return affectedRows;
    }

    @Override
    public <T> List<T> queryWithSubQuery(StringBuilder sqlBuilder, RowMapper<T> rowMapper, List<SubQuery> subQueryList, String type, Class<T> modelClass, Pageble pageble) {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "";
        try {
            connection = JDBCUtil.getConnection();
            List<Object> parameters = new ArrayList<>();
            for (SubQuery subQuery : subQueryList) {
                String dataQuery = "";
//                Nếu subquery có select thì append string, nếu không thì append ? và add vào parameters
                if (String.valueOf(subQuery.getDatasQuery().get(0)).contains("SELECT")) {
                    List<Object> datas = subQuery.getDatasQuery();
                    dataQuery = String.valueOf(datas.get(0));
                } else {
                    dataQuery = getInClauseParameters(subQuery.getDatasQuery().size());
                    for (Object param : subQuery.getDatasQuery()) {
                        parameters.add(param);
                    }
                }
                sqlBuilder.append(" and " + subQuery.getColumnName() + " " + subQuery.getType() + " (" + dataQuery + ")");
            }
            SqlPagebleUtil.addSQlPageble(sqlBuilder, pageble);
            sql = sqlBuilder.toString();
            preparedStatement = connection.prepareStatement(sql);

            if (parameters != null) {
                setParameter(preparedStatement, parameters);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                results.add(rowMapper.mapRow(resultSet, modelClass));
            }
            return results;
        } catch (Exception e) {
            LoggerHelper.logDetailedDangerMessage(e, "SELECT");
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, List<Object> parameters, Class<T> modelClass) {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (parameters != null) {
                setParameter(preparedStatement, parameters);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                results.add(rowMapper.mapRow(resultSet, modelClass));
            }
            return results;
        } catch (Exception e) {
            LoggerHelper.logDetailedDangerMessage(e, "SELECT");
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
        }

    }

    @Override
    public Long insert(String sql, List<Object> parameters) throws SQLException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameter(preparedStatement, parameters);
            preparedStatement.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                connection.commit();
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LoggerHelper.logDetailedDangerMessage(e, "INSERT");
            if (connection != null) {
                connection.rollback();
            }
            return null;
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, null);
        }
        return null;
    }

    @Override
    public int update(String sql, List<Object> parameters) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            setParameter(preparedStatement, parameters);
            affectedRows = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LoggerHelper.logDetailedDangerMessage(e, "UPDATE");
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, null);
        }
        return affectedRows;
    }

    @Override
    public int delete(String sql, List<Object> parameters) throws SQLException {
        return this.update(sql, parameters);
    }


    protected void setParameter(PreparedStatement preparedStatement, List<Object> parameters) {
        try {
            for (int i = 0; i < parameters.size(); i++) {
                int index = i + 1;
                if (parameters.get(i) instanceof JSONObject)
                    preparedStatement.setObject(index, parameters.get(i).toString());
                else
                    preparedStatement.setObject(index, parameters.get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}