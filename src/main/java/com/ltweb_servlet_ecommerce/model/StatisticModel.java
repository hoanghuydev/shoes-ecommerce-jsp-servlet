package com.ltweb_servlet_ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticModel extends AbstractModel<RepositoryModel>{
    private int stt;
    private Long userId;
    private String userFullName;
    private Long orderId;
    private double orderPrice;
    private Timestamp orderTime;
}
