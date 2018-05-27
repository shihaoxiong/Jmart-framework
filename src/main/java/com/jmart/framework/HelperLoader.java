package com.jmart.framework;

import com.jmart.framework.annotation.Controller;
import com.jmart.framework.helper.*;
import com.jmart.framework.utils.ClassUtil;

//集中初始化各个helper
public class HelperLoader {

    // 在这里统一加载类   因为各个Helper 都是通过 static 初始化各个部件  所以这里不需要创建实例对象
    public  static  void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> clazz : classList){
            ClassUtil.loadClass(clazz.getName());
        }
    }
}
