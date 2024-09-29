package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IVoucherDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.VoucherMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.VoucherModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VoucherDAO extends AbstractDAO<VoucherModel> implements IVoucherDAO {
    @Override
    public List<VoucherModel> findAllWithFilter(VoucherModel model,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM vouchers WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new VoucherMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<VoucherModel> result = query(sql.toString(), new VoucherMapper(),params,VoucherModel.class);
        return result;
    }
    @Override
    public List<VoucherModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM vouchers");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new VoucherMapper(),null, VoucherModel.class);
    }

    @Override
    public VoucherModel findById(Long id) throws SQLException {
        String sql = "select * from vouchers where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<VoucherModel> result =  query(sql,new VoucherMapper(),params,VoucherModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public VoucherModel findWithFilter(VoucherModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM vouchers WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new VoucherMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<VoucherModel> result = query(sql.toString(), new VoucherMapper(),params,VoucherModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<VoucherModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM vouchers WHERE 1=1 ");
        List<VoucherModel> result = queryWithSubQuery(sqlStrBuilder,new VoucherMapper(),subQueryList,"in",VoucherModel.class,pageble);
        return result;
    }

    @Override
    public Long save(VoucherModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO vouchers SET ");
        MapSQLAndParamsResult sqlAndParams = new VoucherMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(VoucherModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE vouchers SET ");
        MapSQLAndParamsResult sqlAndParams = new VoucherMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from vouchers where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        VoucherModel model = new VoucherModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE vouchers SET ");
        MapSQLAndParamsResult sqlAndParams = new VoucherMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }


}
