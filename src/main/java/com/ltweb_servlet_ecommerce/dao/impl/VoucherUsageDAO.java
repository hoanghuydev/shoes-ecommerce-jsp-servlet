package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IVoucherUsageDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.VoucherUsageMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.VoucherUsageModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VoucherUsageDAO extends AbstractDAO<VoucherUsageModel> implements IVoucherUsageDAO {
    @Override
    public List<VoucherUsageModel> findAllWithFilter(VoucherUsageModel model,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM voucher_usage WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new VoucherUsageMapper().mapSQLAndParams(sqlStrBuilder,model,"select",pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<VoucherUsageModel> result = query(sql.toString(), new VoucherUsageMapper(),params,VoucherUsageModel.class);
        return result;
    }
    @Override
    public List<VoucherUsageModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM voucher_usage");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder,pageble);
        return query(sqlStrBuilder.toString(),new VoucherUsageMapper(),null, VoucherUsageModel.class);
    }

    @Override
    public VoucherUsageModel findById(Long id) throws SQLException {
        String sql = "select * from voucher_usage where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<VoucherUsageModel> result =  query(sql,new VoucherUsageMapper(),params,VoucherUsageModel.class);
        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public VoucherUsageModel findWithFilter(VoucherUsageModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM voucher_usage WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new VoucherUsageMapper().mapSQLAndParams(sqlStrBuilder,model,"select",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<VoucherUsageModel> result = query(sql.toString(), new VoucherUsageMapper(),params,VoucherUsageModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<VoucherUsageModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM voucher_usage WHERE 1=1 ");
        List<VoucherUsageModel> result = queryWithSubQuery(sqlStrBuilder,new VoucherUsageMapper(),subQueryList,"in",VoucherUsageModel.class,pageble);
        return result;
    }

    @Override
    public Long save(VoucherUsageModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO voucher_usage SET ");
        MapSQLAndParamsResult sqlAndParams = new VoucherUsageMapper().mapSQLAndParams(sqlStrBuilder,model,"insert",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql,params);
    }

    @Override
    public void update(VoucherUsageModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE voucher_usage SET ");
        MapSQLAndParamsResult sqlAndParams = new VoucherUsageMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from voucher_usage where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql,params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        VoucherUsageModel model = new VoucherUsageModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE voucher_usage SET ");
        MapSQLAndParamsResult sqlAndParams = new VoucherUsageMapper().mapSQLAndParams(sqlStrBuilder,model,"update",null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql,params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql,params);
    }


}
