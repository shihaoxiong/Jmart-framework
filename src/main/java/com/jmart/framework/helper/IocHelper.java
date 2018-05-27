package com.jmart.framework.helper;

import com.jmart.framework.annotation.Inject;
import com.jmart.framework.utils.ReflectionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class IocHelper {

    static {
        // 获取所有bean 与bean实例之间的映射
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if(MapUtils.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>,Object>  beanEntry : beanMap.entrySet() ){
                Class<?> beanClass = beanEntry.getKey();
                Object instance = beanEntry.getValue();
                Field[] fields = beanClass.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(fields)){
                    for (Field field:fields){
                        field.setAccessible(true);
                        if(field.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass = field.getType();
                            Object fieldBean =  beanMap.get(beanFieldClass);
                            if(fieldBean!=null){
                                ReflectionUtil.setField(instance,field,fieldBean);
                            }
                        }
                    }
                }
            }
        }
    }
}
