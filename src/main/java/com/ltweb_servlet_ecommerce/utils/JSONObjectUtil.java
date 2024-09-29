package com.ltweb_servlet_ecommerce.utils;

import org.json.JSONObject;

import java.util.List;

public class JSONObjectUtil {
    /**
     * Removes specified keys from the given JSONObject.
     *
     * @param jsonObject The JSONObject from which keys will be removed.
     * @param keysToRemove A list of keys to be removed.
     */
    public static void removeKeys(JSONObject jsonObject, List<String> keysToRemove) {
        for (String key : keysToRemove) {
            jsonObject.remove(key);
        }
    }
}
