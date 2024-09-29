package com.ltweb_servlet_ecommerce.utils;

public class IPAddressHolder {
    private static final ThreadLocal<String> ipAddressThreadLocal = new ThreadLocal<>();

    public static void setIPAddress(String ipAddress) {
        ipAddressThreadLocal.set(ipAddress);
    }

    public static String getIPAddress() {
        return ipAddressThreadLocal.get();
    }

    public static void removeIPAddress() {
        ipAddressThreadLocal.remove();
    }
}
