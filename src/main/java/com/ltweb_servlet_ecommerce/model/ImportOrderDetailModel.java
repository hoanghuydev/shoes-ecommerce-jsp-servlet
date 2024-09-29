package com.ltweb_servlet_ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportOrderDetailModel extends AbstractModel<ImportOrderDetailModel>{
    private String importOrderId;
    private long productSizeId;
    private int quantityImport;
    private double priceImport;

    private long productId;
    private String productName;
    private String size;
    private String categoryName;
    private String categoryCode;
}
