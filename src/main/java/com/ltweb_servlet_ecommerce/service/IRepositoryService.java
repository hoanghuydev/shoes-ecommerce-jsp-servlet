package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.RepositoryModel;

import java.sql.SQLException;
import java.util.List;

public interface IRepositoryService {
    List<RepositoryModel> findAll() throws SQLException;
}
