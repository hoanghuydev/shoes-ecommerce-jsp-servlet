package com.ltweb_servlet_ecommerce.utils;

import java.util.LinkedHashMap;

public class ObjectComparator {
    public static <T> LinkedHashMap<String, String>[] compareObjects(T oldObj, T newObj) {
        LinkedHashMap<String, String> map1 = new LinkedHashMap<>();
        LinkedHashMap<String, String> map2 = new LinkedHashMap<>();

        // Lấy ra tất cả các trường (fields) của đối tượng
        java.lang.reflect.Field[] fields = oldObj.getClass().getDeclaredFields();

        for (java.lang.reflect.Field field : fields) {
            // Sử dụng reflection để lấy giá trị của trường từ cả hai đối tượng
            field.setAccessible(true);
            Object oldValue;
            Object newValue;
            try {
                oldValue = field.get(oldObj);
                newValue = field.get(newObj);
            } catch (IllegalAccessException e) {
                // Xử lý ngoại lệ nếu không thể truy cập giá trị của trường
                e.printStackTrace();
                continue;
            }

            // So sánh giá trị của trường trong old và new
            oldValue = (oldValue != null) ? oldValue : "null";
            newValue = (newValue != null) ? newValue : "null";
            if (!isEqual(oldValue, newValue)) {
                map1.put(field.getName(), oldValue.toString());
                map2.put(field.getName(), newValue.toString());
            }
        }
        // Tạo mảng chứa map1 và map2
        @SuppressWarnings("unchecked")
        LinkedHashMap<String, String>[] result = new LinkedHashMap[]{map1, map2};
        return result;
    }

    // Method để kiểm tra xem hai giá trị có bằng nhau không
    private static boolean isEqual(Object obj1, Object obj2) {
        return (obj1 == null && obj2 == null) || (obj1 != null && obj1.equals(obj2));
    }
}
