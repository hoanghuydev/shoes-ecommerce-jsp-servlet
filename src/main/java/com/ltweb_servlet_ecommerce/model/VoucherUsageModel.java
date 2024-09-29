package com.ltweb_servlet_ecommerce.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VoucherUsageModel extends AbstractModel<VoucherUsageModel> {
    private Long voucherId;
    private Long orderId;
}