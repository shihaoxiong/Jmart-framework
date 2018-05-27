package com.jmart.framework.proxy;


public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws  Throwable;
}
