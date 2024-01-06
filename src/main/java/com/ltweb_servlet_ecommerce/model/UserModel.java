package com.ltweb_servlet_ecommerce.model;

import java.util.Date;

public class UserModel extends AbstractModel<UserModel> {
    private  String userName;
    private String  email;
    private  String password;
    private  String fullName;
    private String birthDay;
    private Boolean admin;
    private String association = "none";

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", admin=" + admin +
                ", association='" + association + '\'' +
                '}';
    }
}
