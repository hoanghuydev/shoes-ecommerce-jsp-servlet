package com.ltweb_servlet_ecommerce.utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class UTF8ControlUtil extends Control {
    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException, IOException {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStreamReader reader = null;
        if (resourceName != null) {
            InputStream stream = loader.getResourceAsStream(resourceName);
            if (stream != null) {
                reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                bundle = new PropertyResourceBundle(reader);
            }
        }
        if (reader != null) {
            reader.close();
        }
        return bundle;
    }
}