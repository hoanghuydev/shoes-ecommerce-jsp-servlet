package com.ltweb_servlet_ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeModel extends AbstractModel<ProductSizeModel>{
    private Long sizeId;
    private Long productId;
    private Double price;


}
