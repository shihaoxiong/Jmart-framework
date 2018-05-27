package com.jmart.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyManager {

    public  static  <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList ){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,o,method,objects,methodProxy,proxyList).doChain();
            }
        });
    }
}
