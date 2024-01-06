package com.ltweb_servlet_ecommerce.paging;

import com.ltweb_servlet_ecommerce.sort.Sorter;

public interface Pageble {
    Integer getPage();
    Integer getOffset();
    Integer getLimit();
    Sorter getSorter();
}
