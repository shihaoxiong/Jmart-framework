package com.jmart.framework.bean;

import lombok.Getter;
import java.lang.reflect.Method;

// controller
@Getter
public class Handler {

    private  Class<?> controllerClass;
    private Method  actionMethod ;

    public  Handler(Class<?> controllerClass ,Method actionMethod){
        this.controllerClass= controllerClass;
        this.actionMethod=actionMethod;
    }

}
