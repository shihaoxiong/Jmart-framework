package com.jmart.framework.bean;

import com.jmart.framework.utils.CastUtil;
import java.util.Map;

/*
  controller 的参数类
 */
public class Param {

    private Map<String ,Object> paramMap ;

    public  Param(Map<String ,Object> map){
        this.paramMap= map;
    }
    public  Map<String ,Object> getMap(){
        return paramMap;
    }

    public  long getLong(String name ){
        return CastUtil.castLong(paramMap.get(name));
    }
}
