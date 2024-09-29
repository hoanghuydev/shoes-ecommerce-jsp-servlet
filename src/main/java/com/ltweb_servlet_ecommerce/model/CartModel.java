package com.ltweb_servlet_ecommerce.model;

import lombok.Builder;

public class CartModel extends AbstractModel<CartModel>{
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(Long orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    private  Long orderDetailsId;
}
