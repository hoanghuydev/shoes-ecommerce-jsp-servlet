package com.ltweb_servlet_ecommerce.model;

public class InventoryModel extends AbstractModel<InventoryModel> {
    private Integer amount;
    private Long productSizeId;


    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
