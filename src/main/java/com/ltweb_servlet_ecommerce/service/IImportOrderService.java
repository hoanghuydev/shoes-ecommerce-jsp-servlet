package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.ImportOrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;

import java.util.List;

public interface IImportOrderService {
    List<ImportOrderModel> findAll(Pageble pageble);

    boolean delete(String[] ids);

    double getTotalImportPrice();

    ImportOrderModel save(ImportOrderModel model);

    boolean isExists(String importId);
}
