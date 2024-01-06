package com.ltweb_servlet_ecommerce.model;

public class AdressModel extends AbstractModel<AdressModel>{
    private String fullName;
    private Integer phoneNumber;
    private String  province;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getHamlet() {
        return hamlet;
    }

    public void setHamlet(String hamlet) {
        this.hamlet = hamlet;
    }

    private String district;
    private String commune;
    private String hamlet;
}
