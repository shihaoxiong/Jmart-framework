package com.jmart.framework.bean;

import java.util.HashMap;
import java.util.Map;

public class View {

    private  String path ;

    private Map<String,Object> model ;

    public  View (String path){
        this.path = path ;
        this.model = new HashMap<String, Object>();
    }

    public View addModel(String key ,String value){
        model.put(key,value);
        return this;
    }

    public  String getPath(){
        return this.path;
    }
    public  Map<String,Object> getModel(){
        return this.model;
    }
}
