package com.ltweb_servlet_ecommerce.model;

import java.sql.Timestamp;
import java.util.List;

public class AbstractModel<T> {
    private Long id;
    private Timestamp createAt;
    private Timestamp updateAt;

    private boolean isDeleted = false;

    private String actionExcute;



    public String getActionExcute() {
        return actionExcute;
    }

    public void setActionExcute(String actionExcute) {
        this.actionExcute = actionExcute;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

}
