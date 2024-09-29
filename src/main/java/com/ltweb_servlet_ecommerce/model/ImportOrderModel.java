package com.ltweb_servlet_ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportOrderModel {
    private String id;
    private String supplier;
    private double totalPrice;
    private Timestamp createAt;
    private boolean isDeleted = false;
}
