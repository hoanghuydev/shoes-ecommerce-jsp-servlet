package com.ltweb_servlet_ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOutStock {
    private ProductModel product;
    private boolean isOutOfStock;
    private String note;

    public boolean getIsOutOfStock() {
        return isOutOfStock;
    }
}
