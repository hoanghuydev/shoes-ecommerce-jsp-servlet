package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IProductDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.ProductMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.ProductImageMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.UserMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.mapper.impl.ProductMapper;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDAO extends AbstractDAO<ProductModel> implements IProductDAO {
    @Override
    public List<ProductModel> findAllWithFilter(ProductModel model,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM product WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new ProductMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<ProductModel> result = query(sql.toString(), new ProductMapper(),params,ProductModel.class);
        return result;
    }
    @Override
    public List<ProductModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM product");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new ProductMapper(),null, ProductModel.class);
    }

    @Override
    public ProductModel findById(Long id) throws SQLException {
        String sql = "select * from product where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<ProductModel> result =  query(sql,new ProductMapper(),params,ProductModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public ProductModel findWithFilter(ProductModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM product WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new ProductMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<ProductModel> result = query(sql.toString(), new ProductMapper(),params,ProductModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<ProductModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM product WHERE 1=1 ");
        List<ProductModel> result = queryWithSubQuery(sqlStrBuilder,new ProductMapper(),subQueryList,"in",ProductModel.class,pageble);
        return result;
    }

    @Override
    public Long save(ProductModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO product SET ");
        MapSQLAndParamsResult sqlAndParams = new ProductMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(ProductModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE product SET ");
        MapSQLAndParamsResult sqlAndParams = new ProductMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from product where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }
}
