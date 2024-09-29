package com.ltweb_servlet_ecommerce.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductModel extends AbstractModel<ProductModel> {
    private String name;
    private String content;
    private String shortDescription;
    private String thumbnail;
    private Double price;
    private String modelUrl;
    private String slug;
    private Long categoryId;
    private Integer totalViewAndSearch;
    private Long sizeId;
    private String sizeName;
    private Integer quantity;
    private Integer available;
    private Double subTotal;
    private Long productSizeId;
}
