package com.ltweb_servlet_ecommerce.model;

public class UserOrderModel extends AbstractModel<UserOrderModel> {
    private Long userId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    private Long orderId;
}
