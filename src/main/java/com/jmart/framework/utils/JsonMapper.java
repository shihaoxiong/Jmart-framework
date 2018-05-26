package com.jmart.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

@Slf4j
public class JsonMapper {

    private  static  final ObjectMapper  objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    public  static  <T>  String object2String(T target){
        if(target==null){
            return null;
        }
        try {
            return target instanceof String ? (String) target : objectMapper.writeValueAsString(target);
        }catch (Exception ex) {
            log.error("jmart  ObjectMapper object2String ================================",ex);
            return null;
        }
    }

    public  static   <T> T  string2Object(String target, TypeReference typeReference){
        if(target==null || typeReference==null){
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? target : objectMapper.readValue(target, typeReference));
        }catch (Exception ex){
            log.error("jmart  ObjectMapper object2String ================================",ex);
            return null;
        }
    }

}
