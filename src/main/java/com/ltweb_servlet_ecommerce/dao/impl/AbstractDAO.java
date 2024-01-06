package com.ltweb_servlet_ecommerce.dao.impl;


import com.ltweb_servlet_ecommerce.dao.GenericDAO;
import com.ltweb_servlet_ecommerce.mapper.RowMapper;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class AbstractDAO<T> implements GenericDAO<T> {
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            Lưu trong /bin/setenv.bat của tomcat
            ResourceBundle resourceBundle = ResourceBundle.getBundle("env");
            return DriverManager.getConnection(resourceBundle.getString("DB_URL"), resourceBundle.getString("DB_USERNAME"), resourceBundle.getString("DB_PASSWORD"));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return null;
        }

    }
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
    public Map<String, Object> queryCustom(String sql, List<Object> parameters) throws SQLException {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            if (parameters != null) {
                setParameter(preparedStatement, parameters);
            }
            resultSet = preparedStatement.executeQuery();
            Map<String,Object> resultMap = new HashMap<String,Object>();
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    List<String> colVal = new ArrayList<>();
                    Object values =  resultSet.getObject(columnName);
                    resultMap.put(columnName, values);
                }
            }
            return resultMap;
        } catch (Exception e) {
            return null;
        } finally {
            connection.close();
            if (results != null && preparedStatement != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
    }

    @Override
    public <T> List<T> queryWithSubQuery(StringBuilder sqlBuilder, RowMapper<T> rowMapper, List<SubQuery> subQueryList, String type, Class<T> modelClass, Pageble pageble) throws SQLException {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "";
        try {
            connection = getConnection();
            List<Object> parameters = new ArrayList<>();
            for (SubQuery subQuery : subQueryList) {
                sqlBuilder.append(" and "+subQuery.getColumnName()+" "+subQuery.getType()+" ("+getInClauseParameters(subQuery.getDatasQuery().size())+")");
                for (Object param : subQuery.getDatasQuery()) {
                    parameters.add(param);
                }
            }
           SqlPagebleUtil.addSQlPageble(sqlBuilder,pageble);
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
            return null;
        } finally {
            connection.close();
            if (results != null && preparedStatement != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, List<Object> parameters, Class<T> modelClass) throws SQLException {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
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
            return null;
        } finally {
            connection.close();
            if (results != null && preparedStatement != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }

    }

    @Override
    public Long insert(String sql, List<Object> parameters) throws SQLException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
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
            if (connection != null) {
                connection.rollback();
            }
            return null;
        } finally {
            connection.close();
            if (rs != null && preparedStatement != null) {
                preparedStatement.close();
                rs.close();
            }
        }
        return null;
    }

    @Override
    public void update(String sql, List<Object> parameters) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            setParameter(preparedStatement, parameters);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
        } finally {
            connection.close();
            if (preparedStatement != null) {
                preparedStatement.close();

            }
        }
    }

    @Override
    public void delete(String sql, List<Object> parameters) throws SQLException {
        this.update(sql, parameters);
    }


    private void setParameter(PreparedStatement preparedStatement, List<Object> parameters) {
        try {
            for (int i = 0; i < parameters.size(); i++) {
                int index = i + 1;
                preparedStatement.setObject(index, parameters.get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}