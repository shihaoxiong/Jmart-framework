package com.jmart.framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;

@Slf4j
public class CodecUtil {

    public  static   String encodingURL(String source){
        String target;
        try{
            target = URLEncoder.encode(source,"UTF-8");
        }catch (Exception ex){
            log.error("jmart  CodecUtil encodingURL ================================",ex);
            throw  new RuntimeException(ex);
        }
        return target;
    }
    public  static   String deEncodingURL(String source){
        String target;
        try{
            target = URLEncoder.encode(source,"UTF-8");
        }catch (Exception ex){
            log.error("jmart  CodecUtil encodingURL ================================",ex);
            throw  new RuntimeException(ex);
        }
      return target;
    }

}
