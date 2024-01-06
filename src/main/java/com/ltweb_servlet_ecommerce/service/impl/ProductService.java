package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IProductDAO;
import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.SizeModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IProductService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ProductService implements IProductService {
    @Inject
    IProductDAO productDAO;

    @Override
    public List<ProductModel> findAllWithFilter(ProductModel model, Pageble pageble) throws SQLException {
        return productDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public ProductModel findWithFilter(ProductModel model) throws SQLException {
        return productDAO.findWithFilter(model);
    }

    @Override
    public List<ProductModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return productDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return productDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public ProductModel update(ProductModel model) throws SQLException {
        ProductModel oldModel = productDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        productDAO.update(model);
        return productDAO.findById(model.getId());
    }

    @Override
    public ProductModel delete(Long id) throws SQLException {
        ProductModel oldModel = productDAO.findById(id);
        productDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<ProductModel> findAll(Pageble pageble) throws SQLException {
        return productDAO.findAll(pageble);
    }

    @Override
    public ProductModel softDelete(Long id) throws SQLException {
        ProductModel model = productDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        productDAO.update(model);
        return productDAO.findById(model.getId());
    }

    @Override
    public ProductModel findById(Long id) throws SQLException {
        return productDAO.findById(id);
    }

    @Override
    public ProductModel save(ProductModel model) throws SQLException {
        Long productId = productDAO.save(model);
        return productDAO.findById(productId);
    }
}
