package com.ltweb_servlet_ecommerce.model;

import com.ltweb_servlet_ecommerce.constant.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;


public class UserModel extends AbstractModel<UserModel> {
    private  String userName;
    private String  email;
    private  String password;
    private  String fullName;
    private String birthDay;
    private Timestamp lastLogged;
    private String association;
//    Info render
    private Boolean admin;
    private  Boolean moderator;
    private Boolean user;
    private  Long roleId;
    private RoleModel role;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Timestamp getLastLogged() {
        return lastLogged;
    }

    public void setLastLogged(Timestamp lastLogged) {
        this.lastLogged = lastLogged;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Boolean getAdmin() {
        return (this.role != null && this.role.getValue().equalsIgnoreCase(SystemConstant.ADMIN_ROLE));
    }
    public Boolean getModerator() {
        return (this.role != null && this.role.getValue().equalsIgnoreCase(SystemConstant.MODERATOR_ROLE));
    }
    public Boolean getUser() {
        return (this.role != null && this.role.getValue().equalsIgnoreCase(SystemConstant.USER_MODEL));
    }

}
