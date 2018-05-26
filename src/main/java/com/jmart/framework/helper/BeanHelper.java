package com.jmart.framework.helper;

import com.jmart.framework.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BeanHelper {

    private  static  final Map<Class<?>,Object> BEAN_MAP  = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> classSet  = ClassHelper.getClassSet();
        for (Class<?>  clazz :classSet){
            Object bean = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz,bean);
        }
    }

    public  static  Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    public  static  Object getBean(Class<?> clazz){
        if(!BEAN_MAP.containsKey(clazz)){
            throw  new RuntimeException(" can  not get bean by"+clazz);
        }
        return BEAN_MAP.get(clazz);
    }
}
