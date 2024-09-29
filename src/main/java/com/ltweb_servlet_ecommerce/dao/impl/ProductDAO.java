package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IProductDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.ProductMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.JDBCUtil;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDAO extends AbstractDAO<ProductModel> implements IProductDAO {
    @Override
    public List<ProductModel> findAllWithFilter(ProductModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM products WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new ProductMapper().mapSQLAndParams(sqlStrBuilder, model, "select", pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<ProductModel> result = query(sql.toString(), new ProductMapper(), params, ProductModel.class);
        return result;
    }

    @Override
    public List<ProductModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM products");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder, pageble);
        return query(sqlStrBuilder.toString(), new ProductMapper(), null, ProductModel.class);
    }

    @Override
    public ProductModel findById(Long id) throws SQLException {
        String sql = "select * from products where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<ProductModel> result = query(sql, new ProductMapper(), params, ProductModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public ProductModel findWithFilter(ProductModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM products WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new ProductMapper().mapSQLAndParams(sqlStrBuilder, model, "select", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<ProductModel> result = query(sql.toString(), new ProductMapper(), params, ProductModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<ProductModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM products WHERE 1=1 ");
        List<ProductModel> result = queryWithSubQuery(sqlStrBuilder, new ProductMapper(), subQueryList, "in", ProductModel.class, pageble);
        return result;
    }

    @Override
    public Long save(ProductModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO products SET ");
        MapSQLAndParamsResult sqlAndParams = new ProductMapper().mapSQLAndParams(sqlStrBuilder, model, "insert", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql, params);
    }

    @Override
    public int update(ProductModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE products SET ");
        MapSQLAndParamsResult sqlAndParams = new ProductMapper().mapSQLAndParams(sqlStrBuilder, model, "update", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return update(sql, params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from products where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql, params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql, params);
    }

    @Override
    public void updateProductTotalView(Long id) throws SQLException {
        List<Object> params = new ArrayList<>();
        params.add(id);
        updateCustom("UPDATE `products` SET totalViewAndSearch=totalViewAndSearch+1 WHERE id=?", params);
    }

    @Override
    public List<ProductModel> findRemainingProduct() {
        StringBuilder builder = new StringBuilder();
        builder.append("WITH ImportedProducts AS (");
        builder.append("SELECT iod.productSizeId, SUM(iod.quantityImport) AS quantityImport ");
        builder.append("FROM import_order_details iod WHERE iod.isDeleted = 0 ");
        builder.append("GROUP BY iod.productSizeId), ");
        builder.append("SoldProducts AS (");
        builder.append("SELECT od.productSizeId, SUM(od.quantity) AS quantity ");
        builder.append("FROM order_details od ");
        builder.append("WHERE od.isDeleted = 0 AND od.orderId IS NOT NULL ");
        builder.append("GROUP BY od.productSizeId) ");
        builder.append("SELECT ip.productSizeId, ip.quantityImport - COALESCE(sp.quantity, 0) AS quantity ");
        builder.append("FROM ImportedProducts ip LEFT JOIN SoldProducts sp ");
        builder.append("ON ip.productSizeId = sp.productSizeId ORDER BY quantity asc ");

        List<ProductModel> result = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(builder.toString());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ProductModel productModel = new ProductModel();
                productModel.setProductSizeId(resultSet.getLong("productSizeId"));
                productModel.setQuantity(resultSet.getInt("quantity"));
                result.add(productModel);
            }
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProductModel getInfoProduct(ProductModel product) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT p.id, p.name, p.thumbnail, s.name AS sizeName ");
        builder.append("FROM products p INNER JOIN product_sizes ps ON p.id = ps.productId ");
        builder.append("INNER JOIN sizes s ON s.id = ps.sizeId ");
        builder.append("WHERE ps.isDeleted=0 and ps.id = ?");

        try  {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(builder.toString());
            preparedStatement.setLong(1, product.getProductSizeId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.setName(resultSet.getString("name"));
                product.setThumbnail(resultSet.getString("thumbnail"));
                product.setSizeName(resultSet.getString("sizeName"));
            }
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Map<Long, ProductModel> findProductsByProductSizeIds(List<Long> productSizeIds) {
        Map<Long, ProductModel> result = new HashMap<>();
        String sql = "SELECT ps.id AS productSizeId, p.id, p.name FROM products p " +
                "INNER JOIN product_sizes ps ON p.id = ps.productId WHERE ps.id IN (" +
                productSizeIds.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long productSizeId = resultSet.getLong("productSizeId");
                ProductModel productModel = new ProductModel();
                productModel.setId(resultSet.getLong("id"));
                productModel.setName(resultSet.getString("name"));
                result.put(productSizeId, productModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateThumbnail(long productId, String url) {
        String sql = "UPDATE products SET thumbnail=? WHERE id=?";
        List<Object> params = new ArrayList<>();
        params.add(url);
        params.add(productId);
        try {
            update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
