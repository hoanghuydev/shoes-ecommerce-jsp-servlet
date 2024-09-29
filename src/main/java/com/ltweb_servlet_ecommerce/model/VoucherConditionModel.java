package com.ltweb_servlet_ecommerce.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VoucherConditionModel extends AbstractModel<VoucherConditionModel> {
    private Long voucherId;
    private String tableName;
    private String columnName;
    private String conditionValue;
}
