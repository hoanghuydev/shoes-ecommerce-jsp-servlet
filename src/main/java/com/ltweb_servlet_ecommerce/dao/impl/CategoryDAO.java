package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.ICategoryDAO;
import com.ltweb_servlet_ecommerce.dao.IOrderDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.*;
import com.ltweb_servlet_ecommerce.mapper.impl.CategoryMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.CategoryMapper;
import com.ltweb_servlet_ecommerce.mapper.impl.CategoryMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.*;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryDAO extends AbstractDAO<CategoryModel> implements ICategoryDAO {
    @Override
    public List<CategoryModel> findAllWithFilter(CategoryModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM category WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new CategoryMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<CategoryModel> result = query(sql.toString(), new CategoryMapper(),params,CategoryModel.class);
        return result;
    }
    @Override
    public List<CategoryModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM category");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new CategoryMapper(),null, CategoryModel.class);
    }

    @Override
    public CategoryModel findById(Long id) throws SQLException {
        String sql = "select * from category where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<CategoryModel> result =  query(sql,new CategoryMapper(),params,CategoryModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public CategoryModel findWithFilter(CategoryModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM category WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new CategoryMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<CategoryModel> result = query(sql.toString(), new CategoryMapper(),params,CategoryModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<CategoryModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM category WHERE 1=1 ");
        List<CategoryModel> result = queryWithSubQuery(sqlStrBuilder,new CategoryMapper(),subQueryList,"in",CategoryModel.class,pageble);
        return result;
    }

    @Override
    public Long save(CategoryModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO category SET ");
        MapSQLAndParamsResult sqlAndParams = new CategoryMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(CategoryModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE category SET ");
        MapSQLAndParamsResult sqlAndParams = new CategoryMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from category where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }
}