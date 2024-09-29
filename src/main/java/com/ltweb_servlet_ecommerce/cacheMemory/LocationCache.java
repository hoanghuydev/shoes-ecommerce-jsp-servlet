package com.ltweb_servlet_ecommerce.cacheMemory;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocationCache {
    private static LocationCache instance = null;
    private Map<String, String> locationCache = new ConcurrentHashMap<>();

    private LocationCache() {

    }

    public static LocationCache getInstance() {
        if (instance == null) {
            synchronized (LocationCache.class) {
                if (instance == null) {
                    instance = new LocationCache();
                }
            }
        }
        return instance;
    }

    public String getLocation(String ip) {
        return locationCache.get(ip);
    }

    public void putLocation(String ip, String location) {
        if (!StringUtils.isBlank(ip))
            locationCache.put(ip, location);
    }
}
