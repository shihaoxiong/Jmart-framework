package com.jmart.framework.proxy;


import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyChain {

    private Class<?> targetClass;
    private  Object targetObject;
    private Method targetMethod;
    private Object[] methodParam;
    private MethodProxy methodProxy;

    private List<Proxy> proxyList = new ArrayList<Proxy>();

    private  int proxyIndex=0;

    public  ProxyChain(){}

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] methodParam, MethodProxy methodProxy, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodParam = methodParam;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;

    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParam() {
        return methodParam;
    }

    public  Object doChain() throws  Throwable{
        Object  methodResult ;
        if(proxyIndex<proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        }else {
            methodResult= methodProxy.invokeSuper(targetObject,methodParam);
        }
        return methodResult;
    }
}
