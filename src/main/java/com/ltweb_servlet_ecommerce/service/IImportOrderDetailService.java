package com.ltweb_servlet_ecommerce.service;

import com.ltweb_servlet_ecommerce.model.ImportOrderDetailModel;
import com.ltweb_servlet_ecommerce.model.ImportOrderModel;
import com.ltweb_servlet_ecommerce.paging.Pageble;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface IImportOrderDetailService {
    double getTotalPriceByImportId(String importId);

    List<ImportOrderDetailModel> findByImportId(String importId);

    boolean importFileExcel(String importOrderId, Part importFile ,Collection<Part> parts) throws IOException;

    List<ImportOrderDetailModel> findByProductSizeId();
    List<ImportOrderDetailModel> findByProductSizeId(String productSizeId);
}
