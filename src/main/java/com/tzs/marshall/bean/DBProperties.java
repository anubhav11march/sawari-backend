package com.tzs.marshall.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class DBProperties {

    public static Properties properties;


    private final static Logger log = LoggerFactory.getLogger(DBProperties.class);

    public DBProperties(Properties dbProperties) {
        log.info("Persisting Marshall Properties..." + dbProperties);
        properties = new Properties();
        dbProperties.keySet().forEach(k -> properties.setProperty(k.toString().toUpperCase(), dbProperties.getProperty(k.toString())));
        log.info("Persist Successfully. {}", properties.toString());
    }

    public static List<String> getAESHProperty(String property) {
        return (List<String>) properties.get(property.toUpperCase());
    }

    public static List<String> splitString(String str) {
        if (str.contains(","))
            return Arrays.stream(str.split(",")).collect(Collectors.toList());
        else
            return List.of(str);
    }
}
