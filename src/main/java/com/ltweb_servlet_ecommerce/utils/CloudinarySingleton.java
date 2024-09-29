package com.ltweb_servlet_ecommerce.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinarySingleton {
    private static CloudinarySingleton instance;
    private Cloudinary cloudinary;

    private CloudinarySingleton() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "da5wewzih",
                "api_key", "594116454729532",
                "api_secret", "ZmY7c6OjD1xJImXIH2eYGRSayQs"
        ));
        cloudinary.config.secure = true;
    }

    public static CloudinarySingleton getInstance() {
        if (instance == null) {
            synchronized (CloudinarySingleton.class) {
                if (instance == null) {
                    instance = new CloudinarySingleton();
                }
            }
        }
        return instance;
    }

    public Cloudinary getCloudinary() {
        return cloudinary;
    }
}
