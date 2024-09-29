package com.ltweb_servlet_ecommerce.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderModel extends  AbstractModel<OrderModel>{
    private String status;
    private String note;
    private Double totalAmount;
    private String slug;
    private boolean isPaid;
//    Info render
    private List<ProductModel> listProduct;
    private AddressModel addressModel;
    private Long addressId;



    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean paid) {
        isPaid = paid;
    }
}
