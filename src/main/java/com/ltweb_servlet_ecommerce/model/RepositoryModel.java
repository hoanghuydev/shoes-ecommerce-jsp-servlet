package com.ltweb_servlet_ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryModel extends AbstractModel<RepositoryModel> {
    private String productName;
    private String size;
    private int quantity;
}
