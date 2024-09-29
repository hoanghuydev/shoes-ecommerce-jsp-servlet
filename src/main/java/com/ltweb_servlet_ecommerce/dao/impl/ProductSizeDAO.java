package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IProductSizeDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.ProductSizeMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.ProductSizeModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductSizeDAO extends AbstractDAO<ProductSizeModel> implements IProductSizeDAO {
    @Override
    public List<ProductSizeModel> findAllWithFilter(ProductSizeModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM product_sizes WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new ProductSizeMapper().mapSQLAndParams(sqlStrBuilder, model, "select", pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<ProductSizeModel> result = query(sql.toString(), new ProductSizeMapper(), params, ProductSizeModel.class);
        return result;
    }

    @Override
    public List<ProductSizeModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM product_sizes");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder, pageble);
        return query(sqlStrBuilder.toString(), new ProductSizeMapper(), null, ProductSizeModel.class);
    }

    @Override
    public ProductSizeModel findById(Long id) throws SQLException {
        String sql = "select * from product_sizes where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<ProductSizeModel> result = query(sql, new ProductSizeMapper(), params, ProductSizeModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public ProductSizeModel findWithFilter(ProductSizeModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM product_sizes WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new ProductSizeMapper().mapSQLAndParams(sqlStrBuilder, model, "select", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<ProductSizeModel> result = query(sql.toString(), new ProductSizeMapper(), params, ProductSizeModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<ProductSizeModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM product_sizes WHERE 1=1 ");
        List<ProductSizeModel> result = queryWithSubQuery(sqlStrBuilder, new ProductSizeMapper(), subQueryList, "in", ProductSizeModel.class, pageble);
        return result;
    }

    @Override
    public Long save(ProductSizeModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO product_sizes SET ");
        MapSQLAndParamsResult sqlAndParams = new ProductSizeMapper().mapSQLAndParams(sqlStrBuilder, model, "insert", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql, params);
    }

    @Override
    public void update(ProductSizeModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE product_sizes SET ");
        MapSQLAndParamsResult sqlAndParams = new ProductSizeMapper().mapSQLAndParams(sqlStrBuilder, model, "update", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql, params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from product_sizes where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql, params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql, params);
    }

    @Override
    public int getAvailableProducts(Long productSizeId) {
        String sql = "SELECT (SELECT SUM(quantityImport) FROM import_order_details WHERE productSizeId=?) - " +
                "COALESCE((SELECT SUM(quantity) FROM order_details WHERE productSizeId=? AND orderId IS NOT NULL),0) AS available";
        List<Object> params = new ArrayList<>();
        params.add(productSizeId);
        params.add(productSizeId);
        Map<String, Object> result = queryCustom(sql, params);
        return result.get("available") != null ? ((BigDecimal) result.get("available")).intValue() : 0;
    }

    @Override
    public ProductSizeModel findByProductId(Long productId) throws SQLException {
        String sql = "select * from product_sizes where productId=?";
        List<Object> params = new ArrayList<>();
        params.add(productId);
        List<ProductSizeModel> result = query(sql, new ProductSizeMapper(), params, ProductSizeModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
  
    public void deleteByProductId(Long id) {
        String sql = "UPDATE product_sizes set isDeleted = 1 where productId=?";
        List<Object> params = List.of(id);
        try {
            delete(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

