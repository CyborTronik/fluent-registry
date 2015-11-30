package com.github.cybortronik.registry.repository.sql2o;

/**
 * Created by stanislav on 11/30/15.
 */
public class InjectionUtils {


    public static String filterInjectionValue(String text) {
        //Do not allow to split commands
        //Very basic injector checker
        return text.replaceAll(";", "").replaceAll("DELIMITER", "").replaceAll("//", "");
    }

}
