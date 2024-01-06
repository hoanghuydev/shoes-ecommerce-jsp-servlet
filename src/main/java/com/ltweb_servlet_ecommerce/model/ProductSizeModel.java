package com.ltweb_servlet_ecommerce.model;

public class ProductSizeModel extends AbstractModel<ProductSizeModel>{
    private Long sizeId;
    private Long productId;


    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
