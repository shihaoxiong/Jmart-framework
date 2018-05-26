package com.jmart.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Slf4j
public class PropertyUtil {

    public  static  Properties  loadProperties(String fileName){
        Properties properties  = new Properties();
        try {
            properties.load( new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName) ,"UTF-8"));
        } catch (IOException e) {
            log.error("jmart PropertiesUtil on load  exception ================= ",e);
        }
        return properties;
    }
    public  static  String getString(Properties properties,String key){
        if(StringUtils.isBlank(key)||properties ==null){
            return null;
        }
        return properties.getProperty(key);
    }
    public  static  String getString(Properties properties,String key,String defaultValue){
        if(StringUtils.isBlank(key)||properties ==null){
            return defaultValue;
        }
        return properties.getProperty(key);
    }
}

