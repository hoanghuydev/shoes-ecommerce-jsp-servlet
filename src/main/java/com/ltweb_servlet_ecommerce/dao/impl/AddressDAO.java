package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IAddressDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.AddressMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.AddressModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddressDAO extends AbstractDAO<AddressModel> implements IAddressDAO {
    @Override
    public List<AddressModel> findAllWithFilter(AddressModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM address WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new AddressMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<AddressModel> result = query(sql.toString(), new AddressMapper(),params,AddressModel.class);
        return result;
    }
    @Override
    public List<AddressModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM address");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new AddressMapper(),null, AddressModel.class);
    }

    @Override
    public AddressModel findById(Long id) throws SQLException {
        String sql = "select * from address where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<AddressModel> result =  query(sql,new AddressMapper(),params,AddressModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public AddressModel findWithFilter(AddressModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM address WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new AddressMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<AddressModel> result = query(sql.toString(), new AddressMapper(),params,AddressModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<AddressModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM address WHERE 1=1 ");
        List<AddressModel> result = queryWithSubQuery(sqlStrBuilder,new AddressMapper(),subQueryList,"in",AddressModel.class,pageble);
        return result;
    }

    @Override
    public Long save(AddressModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO address SET ");
        MapSQLAndParamsResult sqlAndParams = new AddressMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(AddressModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE address SET ");
        MapSQLAndParamsResult sqlAndParams = new AddressMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from address where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        AddressModel model = new AddressModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE address SET ");
        MapSQLAndParamsResult sqlAndParams = new AddressMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }


}
