package com.ltweb_servlet_ecommerce.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoleModel extends AbstractModel<RoleModel>{
    private String code;
    private String value;

    public RoleModel(String value) {
        this.value = value;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
