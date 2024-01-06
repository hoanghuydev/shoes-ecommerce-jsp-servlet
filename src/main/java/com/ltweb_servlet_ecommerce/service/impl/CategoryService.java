package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.ICategoryDAO;
import com.ltweb_servlet_ecommerce.dao.ICategoryDAO;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.model.CategoryModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.ICategoryService;
import com.ltweb_servlet_ecommerce.subquery.SubQuery;

import javax.inject.Inject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class CategoryService implements ICategoryService {
    @Inject
    ICategoryDAO categoryDAO;

    @Override
    public List<CategoryModel> findAllWithFilter(CategoryModel model, Pageble pageble) throws SQLException {
        return categoryDAO.findAllWithFilter(model,pageble);
    }

    @Override
    public CategoryModel findWithFilter(CategoryModel model) throws SQLException {
        return categoryDAO.findWithFilter(model);
    }

    @Override
    public List<CategoryModel> findByColumnValues(List<SubQuery> subQueryList, Pageble pageble) throws SQLException {
        return categoryDAO.findByColumnValues(subQueryList,pageble);
    }
    @Override
    public Map<String,Object> findWithCustomSQL(String sql, List<Object> params) throws SQLException {
        return categoryDAO.findWithCustomSQL(sql,params);
    }

    @Override
    public CategoryModel update(CategoryModel model) throws SQLException {
        CategoryModel oldModel = categoryDAO.findById(model.getId());
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        categoryDAO.update(model);
        return categoryDAO.findById(model.getId());
    }

    @Override
    public CategoryModel delete(Long id) throws SQLException {
        CategoryModel oldModel = categoryDAO.findById(id);
        categoryDAO.delete(id);
        return oldModel;
    }

    @Override
    public List<CategoryModel> findAll(Pageble pageble) throws SQLException {
        return categoryDAO.findAll(pageble);
    }

    @Override
    public CategoryModel softDelete(Long id) throws SQLException {
        CategoryModel model = categoryDAO.findById(id);
        model.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        model.setIsDeleted(true);
        categoryDAO.update(model);
        return categoryDAO.findById(model.getId());
    }

    @Override
    public CategoryModel findById(Long id) throws SQLException {
        return categoryDAO.findById(id);
    }

    @Override
    public CategoryModel save(CategoryModel model) throws SQLException {
        Long productId = categoryDAO.save(model);
        return categoryDAO.findById(productId);
    }
}
