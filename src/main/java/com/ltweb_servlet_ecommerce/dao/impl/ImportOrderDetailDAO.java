package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IImportOrderDetailDAO;
import com.ltweb_servlet_ecommerce.log.LoggerHelper;
import com.ltweb_servlet_ecommerce.mapper.impl.ImportOrderDetailMapper;
import com.ltweb_servlet_ecommerce.model.ImportOrderDetailModel;
import com.ltweb_servlet_ecommerce.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImportOrderDetailDAO extends AbstractDAO<ImportOrderDetailModel> implements IImportOrderDetailDAO {

    @Override
    public double getTotalPriceByImportId(String importId) {
        String sql = "SELECT SUM(priceImport * quantityImport) AS totalPrice FROM import_order_details where importOrderId = ?";
        Map<String, Object> stringObjectMap = queryCustom(sql, List.of(importId));
        Object totalPrice = stringObjectMap.get("totalPrice");
        if (totalPrice != null) {
            return (double) totalPrice;
        } else {
            return 0;
        }
    }

    @Override
    public boolean softDeleteByImportId(String id) {
        String sql = "update import_order_details set isDeleted=? where importOrderId=?";
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
    public List<ImportOrderDetailModel> findByImportId(String importId) {
        String sql = "SELECT * FROM import_order_details where importOrderId = ? and isDeleted = 0";
        return query(sql, new ImportOrderDetailMapper(), List.of(importId), ImportOrderDetailModel.class);
    }

    @Override
    public List<ImportOrderDetailModel> findByProductSizeId() {
        String sql = "SELECT * FROM import_order_details";
        return query(sql, new ImportOrderDetailMapper(), null, ImportOrderDetailModel.class);

    }

    @Override
    public List<ImportOrderDetailModel> findByProductSizeId(String id) {
        String sql = "SELECT * FROM import_order_details WHERE productSizeId = ? and isDeleted = 0";
        return query(sql, new ImportOrderDetailMapper(), List.of(id), ImportOrderDetailModel.class);
    }

    public long save(ImportOrderDetailModel newModel) {
        String sql = "INSERT INTO import_order_details(importOrderId, productSizeId,quantityImport,priceImport) VALUES(?,?,?,?)";
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, newModel.getImportOrderId());
            preparedStatement.setLong(2, newModel.getProductSizeId());
            preparedStatement.setInt(3, newModel.getQuantityImport());
            preparedStatement.setDouble(4, newModel.getPriceImport());

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
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, rs);
        }
        return 0;
    }
}
