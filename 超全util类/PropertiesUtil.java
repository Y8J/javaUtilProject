package com.zjht.youoil.util;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesUtil {

    private static final String           FILE_NAME = "conf";

    private static PropertyResourceBundle prb;


    public static final String getString(String propertyName) {
        try {
            return prb.getString(propertyName);
        } catch (Exception e) {
            return "";
        }
    }

    public static final String getString() {
        return prb.getString("app/url");
    }

    public static final String getAppDomain() {
        return getString("app_domain");
    }


    static {
        prb = (PropertyResourceBundle) ResourceBundle.getBundle(FILE_NAME);
    }

}
