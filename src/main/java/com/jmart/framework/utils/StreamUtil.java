package com.jmart.framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class StreamUtil {

    public  static  String getString(InputStream is){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }catch (Exception e){
            log.error("jmart  StreamUtil getString ================================",e);
            throw  new RuntimeException(e);
        }
        return sb.toString();
    }


}
