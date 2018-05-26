package com.jmart.framework.helper;

import com.jmart.framework.annotation.Action;
import com.jmart.framework.bean.Handler;
import com.jmart.framework.bean.Request;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 将请求路径与 controller 的method 形成映射
 */
public class ControllerHelper {

    private  final  static Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        //获取所有的 controller 的Class
        Set<Class<?>>  controllerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtils.isNotEmpty(controllerClassSet)){
            for (Class<?> controllerClass :controllerClassSet){
             Method[] methods =  controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)){
                    for (Method method:methods){
                        method.setAccessible(true);
                        if(method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping =  action.value();
                            // 验证映射规则  (GET: url )
                            if(mapping.matches("\\w+:/\\w*")){
                                String [] array = mapping.split(":");
                                if(ArrayUtils.isNotEmpty(array)&&array.length==2){
                                    String requestPath = array[1];
                                    String requestMethod =array[0];
                                    Request request =new Request(requestMethod,requestPath);
                                    Handler handler = new Handler(controllerClass,method);
                                    // 初始化 action_map
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public  static Handler getHandler(String requestMethod ,String  requestPath ){
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
