package com.ltweb_servlet_ecommerce.dao.impl;

import com.ltweb_servlet_ecommerce.dao.IVoucherConditionDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.mapper.impl.VoucherConditionMapper;
import com.ltweb_servlet_ecommerce.mapper.result.MapSQLAndParamsResult;
import com.ltweb_servlet_ecommerce.model.VoucherConditionModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;
import com.ltweb_servlet_ecommerce.utils.SqlPagebleUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VoucherConditionDAO extends AbstractDAO<VoucherConditionModel> implements IVoucherConditionDAO {
    @Override
    public List<VoucherConditionModel> findAllWithFilter(VoucherConditionModel model, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM voucher_conditions WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new VoucherConditionMapper().mapSQLAndParams(sqlStrBuilder, model, "select", pageble);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<VoucherConditionModel> result = query(sql.toString(), new VoucherConditionMapper(), params, VoucherConditionModel.class);
        return result;
    }

    @Override
    public List<VoucherConditionModel> findAll(Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM voucher_conditions");
        SqlPagebleUtil.addSQlPageble(sqlStrBuilder, pageble);
        return query(sqlStrBuilder.toString(), new VoucherConditionMapper(), null, VoucherConditionModel.class);
    }

    @Override
    public VoucherConditionModel findById(Long id) throws SQLException {
        String sql = "select * from voucher_conditions where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        List<VoucherConditionModel> result = query(sql, new VoucherConditionMapper(), params, VoucherConditionModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public VoucherConditionModel findWithFilter(VoucherConditionModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM voucher_conditions WHERE 1=1 ");
        MapSQLAndParamsResult sqlAndParams = new VoucherConditionMapper().mapSQLAndParams(sqlStrBuilder, model, "select", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        List<VoucherConditionModel> result = query(sql.toString(), new VoucherConditionMapper(), params, VoucherConditionModel.class);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<VoucherConditionModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("SELECT * FROM voucher_conditions WHERE 1=1 ");
        List<VoucherConditionModel> result = queryWithSubQuery(sqlStrBuilder, new VoucherConditionMapper(), subQueryList, "in", VoucherConditionModel.class, pageble);
        return result;
    }

    @Override
    public Long save(VoucherConditionModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("INSERT INTO voucher_conditions SET ");
        MapSQLAndParamsResult sqlAndParams = new VoucherConditionMapper().mapSQLAndParams(sqlStrBuilder, model, "insert", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        return insert(sql, params);
    }

    @Override
    public void update(VoucherConditionModel model) throws SQLException {
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE voucher_conditions SET ");
        MapSQLAndParamsResult sqlAndParams = new VoucherConditionMapper().mapSQLAndParams(sqlStrBuilder, model, "update", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql, params);
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "delete from voucher_conditions where id=?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        delete(sql, params);
    }

    @Override
    public void softDelete(Long id) throws SQLException {
        VoucherConditionModel model = new VoucherConditionModel();
        model.setId(id);
        model.setIsDeleted(true);
        StringBuilder sqlStrBuilder = new StringBuilder("UPDATE voucher_conditions SET ");
        MapSQLAndParamsResult sqlAndParams = new VoucherConditionMapper().mapSQLAndParams(sqlStrBuilder, model, "update", null);
        String sql = sqlAndParams.getSql();
        List<Object> params = sqlAndParams.getParams();
        update(sql, params);
    }

    @Override
    public Map<String, Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return queryCustom(sql, params);
    }


}
