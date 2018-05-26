package com.jmart.framework.bean;

import java.util.Date;

public class Data {

    private  Object model;

    public Data(Object object){
        this.model=object;
    }
    public  Object getModel(){
        return this.model;
    }
}
