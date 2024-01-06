package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IOrderDAO;
import com.ltweb_servlet_ecommerce.dao.IProductImageDAO;
import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.model.ProductImageModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IProductImageService;
import com.ltweb_servlet_ecommerce.service.IProductService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ProductImageService implements IProductImageService {
    @Inject
    IProductImageDAO productImageDAO;

    @Override
    public List<ProductImageModel> findAllWithFilter(ProductImageModel model, Pageble pageble) throws SQLException {
        return productImageDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public ProductImageModel findWithFilter(ProductImageModel model) throws SQLException {
        return productImageDAO.findWithFilter(model);
    }

    @Override
    public List<ProductImageModel> findByColumnValues(List<SubQuery> subQueryList,Pageble pageble) throws SQLException {
        return productImageDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return productImageDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public ProductImageModel update(ProductImageModel model) throws SQLException {
        ProductImageModel oldModel = productImageDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        productImageDAO.update(model);
        return productImageDAO.findById(model.getId());
    }

    @Override
    public ProductImageModel delete(Long id) throws SQLException {
        ProductImageModel oldModel = productImageDAO.findById(id);
        productImageDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<ProductImageModel> findAll(Pageble pageble) throws SQLException {
        return productImageDAO.findAll(pageble);
    }

    @Override
    public ProductImageModel softDelete(Long id) throws SQLException {
        ProductImageModel model = productImageDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        productImageDAO.update(model);
        return productImageDAO.findById(model.getId());
    }

    @Override
    public ProductImageModel findById(Long id) throws SQLException {
        return productImageDAO.findById(id);
    }

    @Override
    public ProductImageModel save(ProductImageModel model) throws SQLException {
        Long productId = productImageDAO.save(model);
        return productImageDAO.findById(productId);
    }
}
