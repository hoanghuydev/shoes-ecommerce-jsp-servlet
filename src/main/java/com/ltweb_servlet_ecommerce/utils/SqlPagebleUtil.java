package com.ltweb_servlet_ecommerce.utils;

import com.ltweb_servlet_ecommerce.paging.Pageble;
import org.apache.commons.lang3.StringUtils;

public class SqlPagebleUtil {
    public static void addSQlPageble(StringBuilder stringSQLBuilder, Pageble pageble) {
        if (pageble!=null) {
            if (pageble.getSorter() != null && StringUtils.isNotBlank(pageble.getSorter().getSortName()) && StringUtils.isNotBlank(pageble.getSorter().getSortBy())) {
                stringSQLBuilder.append(" ORDER BY "+pageble.getSorter().getSortName()+" "+pageble.getSorter().getSortBy()+"");
            }
            if (pageble.getOffset() != null && pageble.getLimit() != null) {
                stringSQLBuilder.append(" LIMIT "+pageble.getOffset()+", "+pageble.getLimit()+"");
            }
        }
    }
}
