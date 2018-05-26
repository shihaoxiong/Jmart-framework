package com.jmart.framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionUtil {

    public  static  Object newInstance(Class<?> clazz){
        Object instance;
        try{
            instance = clazz.newInstance();
        }catch (Exception e){
            log.error("jmart ReflectionUtil  newInstance exception  ====================== ",e);
            throw  new RuntimeException(e);
        }
        return instance;
    }

    public  static  Object invoke(Object object, Method method ,Object...args){
        Object result ;
        try {
             method.setAccessible(true);
             result= method.invoke(object,args);
        } catch (Exception e) {
            log.error("jmart ReflectionUtil invoke  exception  ====================== ",e);
            throw  new RuntimeException(e);
        }
        return result;
    }

    public  static  void setField (Object object, Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(object,value);
        } catch (IllegalAccessException e) {
            log.error("jmart ReflectionUtil setField  exception  ====================== ",e);
            throw  new RuntimeException(e);
        }
    }
}
