package com.jmart.framework;

import com.google.common.collect.Maps;
import com.jmart.framework.bean.Data;
import com.jmart.framework.bean.Handler;
import com.jmart.framework.bean.Param;
import com.jmart.framework.bean.View;
import com.jmart.framework.helper.BeanHelper;
import com.jmart.framework.helper.ConfigHelper;
import com.jmart.framework.helper.ControllerHelper;
import com.jmart.framework.utils.CodecUtil;
import com.jmart.framework.utils.JsonMapper;
import com.jmart.framework.utils.ReflectionUtil;
import com.jmart.framework.utils.StreamUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

@WebServlet(urlPatterns = "/*" ,
       loadOnStartup = 0
)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
       // 初始化 helper
        HelperLoader.init();
        // 获取ServletContext 对象  用于注册Servlet
        ServletContext servletContext = config.getServletContext();
        // 注册jsp 的Servlet
       // ServletRegistration  jspServlet = servletContext.get
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getViewPath()+"*");
        // 注册静态资源处理的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAssertPath()+"*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath  =  req.getPathInfo();
        Handler handler  = ControllerHelper.getHandler(requestMethod,requestPath);
        if(handler!=null){
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // 创建参数
            Map<String,Object> paramMap = Maps.newHashMap();
            Enumeration<String> paramNames= req.getParameterNames();
            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            String body = CodecUtil.encodingURL(StreamUtil.getString(req.getInputStream()));
            if(StringUtils.isNotBlank(body)){
                String [] params = StringUtils.split(body,"&");
                if(ArrayUtils.isNotEmpty(params)){
                    for (String param:params){
                        String [] array = StringUtils.split(param,"=");
                        if(ArrayUtils.isNotEmpty(array)&&array.length==2){
                            String paramName = array[0];
                            String paramValue=array[1];
                            paramMap.put(paramName,paramValue);
                        }

                    }
                }
            }
            Param param =new Param(paramMap);
            // 调用Action
            Method actionMethod = handler.getActionMethod();
            actionMethod.setAccessible(true);
            Object result = ReflectionUtil.invoke(controllerBean,actionMethod,param);
            if(result instanceof View){
                View view = (View)result;
                String path =view.getPath();
                if(StringUtils.isNotBlank(path)){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath()+path);
                    }else{
                        Map<String,Object> model = view.getModel();
                        for (Map.Entry<String,Object>  entry : model.entrySet()){
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getViewPath()+path).forward(req,resp);
                    }
                }
            }else if(result instanceof Data){
                Data data = (Data) result;
                Object model = data.getModel();
                if(model!=null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter out = resp.getWriter();
                    out.write(JsonMapper.object2String(model));
                    out.flush();
                    out.close();
                }
            }
        }
    }
}
