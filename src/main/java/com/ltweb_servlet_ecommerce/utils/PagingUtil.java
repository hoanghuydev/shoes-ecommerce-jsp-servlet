package com.ltweb_servlet_ecommerce.utils;

import com.ltweb_servlet_ecommerce.paging.PageRequest;
import com.ltweb_servlet_ecommerce.paging.Pageble;
import com.ltweb_servlet_ecommerce.sort.Sorter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PagingUtil {
    public static Pageble setUpPagingAndSort(HttpServletRequest req) {
        Integer page = req.getParameter("page")==null ? 1 : Integer.parseInt(req.getParameter("page").toString());
        Integer maxPageItem =req.getParameter("maxPageItem")==null ? 8 : Integer.parseInt(req.getParameter("maxPageItem").toString());
        String sortName = req.getParameter("sortName")==null ? "createAt" : req.getParameter("sortName");
        String sortBy = req.getParameter("sortBy")==null ? "desc" : req.getParameter("sortBy");
        return new PageRequest(page,maxPageItem,new Sorter(sortName, sortBy));
    }
}
