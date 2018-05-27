package com.jmart.framework.helper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jmart.framework.annotation.Aspect;
import com.jmart.framework.proxy.AspectProxy;
import com.jmart.framework.proxy.Proxy;
import com.jmart.framework.proxy.ProxyManager;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class AopHelper {

    static {
        try{
            Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>,List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>,List<Proxy>>  targetEntry:targetMap.entrySet()){
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass,proxyList);
                BeanHelper.setBean(targetClass,proxy);
            }
        }catch (Exception ex){
            log.error("Jmart AopHelper error ===============================",ex);
        }
    }

    private  static Set<Class<?>>  createTargetClassSet(Aspect aspect) throws  Exception{
        Set<Class<?>>   targetClassSet  = Sets.newHashSet();
        Class< ? extends Annotation> annotation  = aspect.value();
        if(annotation!=null&&!annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    private  static Map<Class<?>,Set<Class<?>>> createProxyMap() throws  Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap  = Maps.newHashMap();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuperClass(AspectProxy.class);
        for (Class<?> proxyClazz :proxyClassSet){
            if (proxyClazz.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClazz.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClazz,targetClassSet);
            }
        }
        return proxyMap;
    }

    private  static  Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws  Exception{
        Map<Class<?>,List<Proxy>> targetMap = Maps.newHashMap();
        for (Map.Entry<Class<?>,Set<Class<?>>> entry :proxyMap.entrySet()){
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();
            for (Class<?> targetClazz :targetClassSet){
                Proxy proxy  = (Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClazz)){
                    targetMap.get(targetClazz).add(proxy);
                }else{
                    List<Proxy> proxyList = Lists.newArrayList();
                    proxyList.add(proxy);
                    targetMap.put(targetClazz,proxyList);
                }
            }
        }
        return targetMap;
    }

}
