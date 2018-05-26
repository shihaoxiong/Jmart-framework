package com.jmart.framework.bean;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public class Request {

    private  String requestPath;
    private  String requestMethod ;

    public  Request(String requestMethod,String requestPath){
        this.requestMethod=requestMethod;
        this.requestPath=requestPath;
    }

    public  int hashCode (){
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public  boolean equals(Object object){
        return EqualsBuilder.reflectionEquals(this,object);
    }
}
