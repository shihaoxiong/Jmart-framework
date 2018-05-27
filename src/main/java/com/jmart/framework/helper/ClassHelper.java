package com.jmart.framework.helper;

import com.google.common.collect.Sets;
import com.jmart.framework.annotation.Controller;
import com.jmart.framework.annotation.Service;
import com.jmart.framework.constant.ConfigConstant;
import com.jmart.framework.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.Set;

/*
    获取CLassSet
 */
public class ClassHelper {

    private  static  final Set<Class<?>> CLASS_SET ;

    static {
        String packageName = ConfigHelper.getBasePath();
        CLASS_SET = ClassUtil.getClassSet(packageName);

    }

    /*
     获取 指定父类的 子类Set
     */
    public  static  Set<Class<?>> getClassSetBySuperClass(Class<?> superClass){
        Set<Class<?>> classSet = Sets.newHashSet();
        for (Class<?> clazz:CLASS_SET){
            if(superClass.isAssignableFrom(clazz)&&!superClass.equals(clazz)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /*
      获取 带有指定注解的CLassSet
     */
    public  static  Set<Class<?>>  getClassSetByAnnotation(Class<? extends Annotation> annotationCLass){
        Set<Class<?>> classSet = Sets.newHashSet();
        for (Class<?> clazz:CLASS_SET){
            if(clazz.isAnnotationPresent(annotationCLass)){
                classSet.add(clazz);
            }
        }
        return classSet;
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
