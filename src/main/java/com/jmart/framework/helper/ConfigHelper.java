package com.jmart.framework.helper;


import com.jmart.framework.constant.ConfigConstant;
import com.jmart.framework.utils.PropertyUtil;

import java.util.Properties;

public class ConfigHelper {

    private static Properties properties  = PropertyUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    public  static  String getJdbcUrl (){
        return  PropertyUtil.getString(properties,ConfigConstant.JDBC_URL);
    }

    public  static  String getJdbcDriver (){
        return  PropertyUtil.getString(properties,ConfigConstant.JDBC_DRIVERCLASS);
    }
    public  static  String getJdbcUser(){
        return  PropertyUtil.getString(properties,ConfigConstant.JDBC_USER);
    }
    public  static  String getJdbcPassword (){
        return  PropertyUtil.getString(properties,ConfigConstant.JDBC_PASSWORD);
    }
    public  static  String getViewPath (){
        return  PropertyUtil.getString(properties,ConfigConstant.APP_VIEW_PATH,"/WEB-INF/views/");
    }

    public  static  String getAssertPath(){
        return  PropertyUtil.getString(properties,ConfigConstant.APP_ASSERT_PATH,"/assert/");
    }
    public  static  String getBasePath(){
        System.out.println(ConfigConstant.CONFIG_FILE);
        System.out.println(ConfigConstant.BASE_PATH);
        return  PropertyUtil.getString(properties,ConfigConstant.BASE_PATH);
    }

}
