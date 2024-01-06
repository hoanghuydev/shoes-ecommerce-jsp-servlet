package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IProductDAO;
import com.ltweb_servlet_ecommerce.dao.IProductImageDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.ProductImageMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.ProductImageMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.ProductImageMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductImageDAO extends AbstractDAO<ProductImageModel> implements IProductImageDAO {
    @Override
    public List<ProductImageModel> findAllWithFilter(ProductImageModel model,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM productImage WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new ProductImageMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<ProductImageModel> result = query(sql.toString(), new ProductImageMapper(),params,ProductImageModel.class);
        return result;
    }
    @Override
    public List<ProductImageModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM productImage");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new ProductImageMapper(),null, ProductImageModel.class);
    }

    @Override
    public ProductImageModel findById(Long id) throws SQLException {
        String sql = "select * from productImage where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<ProductImageModel> result =  query(sql,new ProductImageMapper(),params,ProductImageModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public ProductImageModel findWithFilter(ProductImageModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM productImage WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new ProductImageMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<ProductImageModel> result = query(sql.toString(), new ProductImageMapper(),params,ProductImageModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<ProductImageModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM productImage WHERE 1=1 ");
        List<ProductImageModel> result = queryWithSubQuery(sqlStrBuilder,new ProductImageMapper(),subQueryList,"in",ProductImageModel.class,pageble);
        return result;
    }

    @Override
    public Long save(ProductImageModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO productImage SET ");
        MapSQLAndParamsResult sqlAndParams = new ProductImageMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(ProductImageModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE productImage SET ");
        MapSQLAndParamsResult sqlAndParams = new ProductImageMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from productImage where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }

}