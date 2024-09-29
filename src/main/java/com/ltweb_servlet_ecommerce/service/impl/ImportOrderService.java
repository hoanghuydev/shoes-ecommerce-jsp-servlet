package com.ltweb_servlet_ecommerce.service.impl;

import com.ltweb_servlet_ecommerce.dao.IImportOrderDAO;
import com.ltweb_servlet_ecommerce.dao.IImportOrderDetailDAO;
import com.ltweb_servlet_ecommerce.dao.impl.ImportOrderDAO;
import com.ltweb_servlet_ecommerce.dao.impl.ImportOrderDetailDAO;
import com.ltweb_servlet_ecommerce.model.ImportOrderModel;
import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.service.IImportOrderService;
import com.ltweb_servlet_ecommerce.sort.Sorter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ImportOrderService implements IImportOrderService {
    @Inject
    private IImportOrderDAO importDAO;
    @Inject
    private IImportOrderDetailDAO importDetailDAO;

    @Override
    public List<ImportOrderModel> findAll(Pageble pageble) {
        // calculate total price for every import order

        if (importDAO == null || importDetailDAO == null) {
            importDAO = new ImportOrderDAO();
            importDetailDAO = new ImportOrderDetailDAO();
        }

        List<ImportOrderModel> all = importDAO.findAll(pageble);
        if (all == null) {
            all = new ArrayList<>();
        } else {
            all.forEach(importOrderModel -> {
                importOrderModel.setTotalPrice(importDetailDAO.getTotalPriceByImportId(importOrderModel.getId()));
            });
        }
        return all;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean result = true;
        for (String id : ids) {
            result = result && importDAO.softDelete(id) && importDetailDAO.softDeleteByImportId(id);
        }

        return result;
    }

    @Override
    public double getTotalImportPrice() {
        double totalImportPrice = 0;
        List<ImportOrderModel> listImportOrder = this.findAll(null);
        for (ImportOrderModel i : listImportOrder) {
            ImportOrderDetailService importOrderDetailService = new ImportOrderDetailService();
            double pricePerImport = importOrderDetailService.getTotalPriceByImportId(i.getId());
            totalImportPrice += pricePerImport;
        }
        return totalImportPrice;
    }


    @Override
    public ImportOrderModel save(ImportOrderModel model) {
        importDAO.save(model);
        return model;
    }

    @Override
    public boolean isExists(String importId) {
        ImportOrderModel model = importDAO.findById(importId);
        return model != null;
    }

}
