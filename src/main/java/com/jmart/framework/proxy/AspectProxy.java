package com.jmart.framework.proxy;

import java.lang.reflect.Method;

public class AspectProxy implements Proxy{

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> clazz = proxyChain.getTargetClass();
        Method method =proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParam();
        begin();
        try{
            if(intercept(clazz,method,params)){
                before(clazz,method,params);
                result = proxyChain.doChain();
                after(clazz,method,params);
            }else {
                result = proxyChain.doChain();
            }
        }catch (Exception ex){
            System.out.println("=========doProxy error ===========");
            error(clazz,method,params);
            throw  ex;
        }finally {
            end();
        }
        return result;
    }

    public  void begin(){}

    public  boolean intercept(Class<?> clazz ,Method method,Object[] params) throws  Throwable{
        return true;
    }

    public  void before(Class<?> clazz ,Method method,Object[] params) throws  Throwable{

    }
    public  void after(Class<?> clazz ,Method method,Object[] params) throws  Throwable{

    }
    public  void error(Class<?> clazz ,Method method,Object[] params) throws  Throwable{

    }
    public  void end(){}
}
