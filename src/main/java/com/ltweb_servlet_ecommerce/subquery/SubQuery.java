package com.ltweb_servlet_ecommerce.subquery;

import java.util.List;

public class SubQuery {
    private String columnName;
    private String type;
    private List<Object> datasQuery;

    public SubQuery(String columnName, String type, List<Object> datasQuery) {
        this.columnName = columnName;
        this.type = type;
        this.datasQuery = datasQuery;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public List<Object> getDatasQuery() {
        return datasQuery;
    }

    public void setDatasQuery(List<Object> datasQuery) {
        this.datasQuery = datasQuery;
    }
}
