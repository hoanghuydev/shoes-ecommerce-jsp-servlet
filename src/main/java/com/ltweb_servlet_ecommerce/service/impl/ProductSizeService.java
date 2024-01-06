package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IProductSizeDAO;
import com.ltweb_servlet_ecommerce.dao.ISizeDAO;
import com.ltweb_servlet_ecommerce.model.ProductSizeModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IProductSizeService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ProductSizeService implements IProductSizeService {
    @Inject
    IProductSizeDAO productSizeDAO;

    @Override
    public List<ProductSizeModel> findAllWithFilter(ProductSizeModel model, Pageble pageble) throws SQLException {
        return productSizeDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public ProductSizeModel findWithFilter(ProductSizeModel model) throws SQLException {
        return productSizeDAO.findWithFilter(model);
    }

    @Override
    public List<ProductSizeModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return productSizeDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return productSizeDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public ProductSizeModel update(ProductSizeModel model) throws SQLException {
        ProductSizeModel oldModel = productSizeDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        productSizeDAO.update(model);
        return productSizeDAO.findById(model.getId());
    }

    @Override
    public ProductSizeModel delete(Long id) throws SQLException {
        ProductSizeModel oldModel = productSizeDAO.findById(id);
        productSizeDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<ProductSizeModel> findAll(Pageble pageble) throws SQLException {
        return productSizeDAO.findAll(pageble);
    }

    @Override
    public ProductSizeModel softDelete(Long id) throws SQLException {
        ProductSizeModel model = productSizeDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        productSizeDAO.update(model);
        return productSizeDAO.findById(model.getId());
    }

    @Override
    public ProductSizeModel findById(Long id) throws SQLException {
        return productSizeDAO.findById(id);
    }

    @Override
    public ProductSizeModel save(ProductSizeModel model) throws SQLException {
        Long productId = productSizeDAO.save(model);
        return productSizeDAO.findById(productId);
    }
}
