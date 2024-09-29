package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IImportOrderDAO;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.mapper.impl.ImportOrderMapper;
import com.ltweb_servlet_ecommerce.model.ImportOrderModel;
import com.ltweb_servlet_ecommerce.model.LogModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.utils.JDBCUtil;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportOrderDAO extends AbstractDAO<LogModel> implements IImportOrderDAO {

    @Override
    public List<ImportOrderModel> findAll(Pageble pageble) {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM import_orders where isDeleted = 0");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder, pageble);
        return query(sqlStrBuilder.toString(), new ImportOrderMapper(), null, ImportOrderModel.class);
    }

    @Override
    public boolean softDelete(String id) {
        String sql = "update import_orders set isDeleted=? where id=?";
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

    @Override
    public String save(ImportOrderModel model) {
        String sql = "INSERT INTO import_orders (id,supplier,createAt) VALUES(?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, model.getId());
            preparedStatement.setString(2, model.getSupplier());
            preparedStatement.setTimestamp(3, new Timestamp(model.getCreateAt().getTime()));
            if (preparedStatement.executeUpdate() > 0)
                connection.commit();
            rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LoggerHelper.logDetailedDangerMessage(e, "INSERT");
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return null;
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, rs);
        }
        return null;
    }

    @Override
    public ImportOrderModel findById(String id) {
        String sql = "select * from import_orders where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<ImportOrderModel> result = query(sql, new ImportOrderMapper(), params, ImportOrderModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
}
