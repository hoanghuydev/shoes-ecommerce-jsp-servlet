package com.ltweb_servlet_ecommerce.model;

public class UserAddressModel extends AbstractModel<UserAddressModel>{
    private Long addressId;
    private  Long userId;


    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
