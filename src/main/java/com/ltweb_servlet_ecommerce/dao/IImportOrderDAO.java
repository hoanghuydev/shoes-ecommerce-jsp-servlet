package com.ltweb_servlet_ecommerce.dao;

import com.ltweb_servlet_ecommerce.model.ImportOrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;

import java.util.List;

public interface IImportOrderDAO {
    List<ImportOrderModel> findAll(Pageble pageble) ;

    boolean softDelete(String id);

    String save(ImportOrderModel model) ;
    ImportOrderModel findById(String id);
}
