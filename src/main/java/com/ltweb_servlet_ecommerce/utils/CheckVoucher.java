package com.ltweb_servlet_ecommerce.utils;

import com.ltweb_servlet_ecommerce.model.ProductModel;
import com.ltweb_servlet_ecommerce.model.UserModel;
import com.ltweb_servlet_ecommerce.model.VoucherConditionModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;

public class CheckVoucher {
    public static Boolean canApplyVoucher(List<ProductModel> products, List<VoucherConditionModel> voucherConditions, UserModel currentUser) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (VoucherConditionModel voucherCondition : voucherConditions) {
            String tableName = voucherCondition.getTableName();
            String columnName = voucherCondition.getColumnName();
            String conditionValue = voucherCondition.getConditionValue();
            switch (tableName) {
                case "product":
                    double totalPrice = 0;
                    for (ProductModel product : products) {
                        totalPrice += product.getSubTotal();
                        switch (columnName) {
                            case "name":
                                if (product.getName().contains(conditionValue))
                                    return false;
                                break;
                            case "id" :
                                if (product.getId() != Integer.parseInt(conditionValue))
                                    return false;
                                break;
                            default:
                                return false;
                        }
                    }
                    switch (columnName) {

                        case "price" :
                            if (totalPrice < Double.parseDouble(conditionValue))
                                return false;
                            break;
                        default:
                            return false;
                    }
                    break;
                case "user" :
                    if (currentUser != null) {
                        switch (columnName) {
                            case "fullName":
                                if (!currentUser.getFullName().contains(conditionValue))
                                    return true;
                                break;
                            case "id" :
                                if (currentUser.getId() != Integer.parseInt(conditionValue))
                                    return false;
                                break;
                            case "association":
                                if (!currentUser.getAssociation().contains(conditionValue))
                                    return false;
                                break;
                            case "createAt" :
                                if (!currentUser.getCreateAt().equals(new Timestamp(Long.parseLong(conditionValue))))
                                    return false;
                                break;
                            default:
                                return false;
                        }
                    } else return false;
                    break;
                case "category" :
                   for (ProductModel product : products) {
                       if (product.getCategoryId() != Integer.parseInt(conditionValue))
                           return false;
                   }
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
    public static Object createInstance(String className) {
        try {
            String packageName = "com.ltweb_servlet_ecommerce.model.";
            Class<?> clazz = Class.forName(packageName+className);
            Constructor<?> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getPropertyValue(Object instance, String columnName) {
        try {
            Class<?> clazz = instance.getClass();
            Field field = clazz.getDeclaredField(columnName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
