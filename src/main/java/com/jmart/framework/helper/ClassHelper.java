package com.jmart.framework.helper;

import com.google.common.collect.Sets;
import com.jmart.framework.annotation.Controller;
import com.jmart.framework.annotation.Service;
import com.jmart.framework.constant.ConfigConstant;
import com.jmart.framework.utils.ClassUtil;
import java.util.Set;

/*
    获取CLassSet
 */
public class ClassHelper {

    private  static  final Set<Class<?>> CLASS_SET ;

    static {
        String packageName = ConfigHelper.getBasePath();
        System.out.println(packageName);
        CLASS_SET = ClassUtil.getClassSet(packageName);

    }
    public  static  Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }
    public  static  Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> serviceClassSet  = Sets.newHashSet();
        for (Class<?> clazz :CLASS_SET){
            if(clazz.isAnnotationPresent(Service.class)){
                serviceClassSet.add(clazz);
            }
        }
        return serviceClassSet;
    }
    public  static  Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> controllerClassSet  = Sets.newHashSet();
        for (Class<?> clazz :CLASS_SET){
            if(clazz.isAnnotationPresent(Controller.class)){
                controllerClassSet.add(clazz);
            }
        }
        return controllerClassSet;
    }
    public  static  Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanCLassSet  = Sets.newHashSet();
        beanCLassSet.addAll(getControllerClassSet());
        beanCLassSet.addAll(getControllerClassSet());
        return beanCLassSet;
    }
}
