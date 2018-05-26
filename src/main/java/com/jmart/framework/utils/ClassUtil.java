package com.jmart.framework.utils;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * Created by jay
 *  alias as  package scanner
 *  主要用于获取 指定包下的class
 */
@Slf4j
public  final class ClassUtil {
    /*
        从当前线程中获取ClassLoader
     */
    public  static   ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }
    /*
      通过权限类名加载类实例
     */

    public static  void loadClass(String name ){
        Class<?> clazz;
        try {
            clazz =  Class.forName(name);
        } catch (ClassNotFoundException e) {
            log.error("jmart ClassUtil loadClass  with name  exception =======================",e);
            throw  new RuntimeException(e);
        }
    }

    public static  Class<?> loadClass(String name ,boolean  isInitialized){
        Class<?> clazz ;
        try {
            clazz = Class.forName(name,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("jmart ClassUtil loadClass  exception =======================",e);
            throw  new RuntimeException(e);
        }
        return clazz;
    }
    /**
     *    获取指定包下的 Class
     * @param packageName 需要加载类的包名
     * @return
     */
    public  static  Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet  = Sets.newHashSet();
        try {
            // 将包名 转化为 url
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url  =  urls.nextElement();
                if (url!=null){
                    // 获取协议名称   jar 文件需要特殊处理
                    String protocol  = url.getProtocol();
                    if(protocol.equals("file")){
                        //  获取path 时 空格会转为%20 这里进行相应的处理
                        String packagePath  = url.getPath().replaceAll("%20"," ");
                        // 循环加载 包下的 class
                        addClass(classSet,packagePath,packageName);
                    }else if(protocol.equals("jar")){
                        // 获取jarURLConnection
                        JarURLConnection jarURLConnection =(JarURLConnection) url.openConnection();
                        if(jarURLConnection!=null){
                            //  从JarURLConnection 得到 jarFile
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if(jarFile!=null){
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()){
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    // 处理 jarEntry
                                    String jarEntryName = jarEntry.getName();
                                    if(jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                                        doAddClass(classSet,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("jmart ClassUtil getClassSet exception =======================",e);
            throw  new RuntimeException(e);
        }
        return classSet;
    }
    private  static  void addClass(Set<Class<?>> classSet,String packagePath,String packageName){
        //  获取packagePath下的所有的 class文件和目录
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile()&&file.getName().endsWith(".class")||file.isDirectory());
            }
        });
        for (File file :files){
            String fileName = file.getName();
            if(file.isFile()){
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtils.isNotBlank(packageName)){
                    className = packageName+"."+className;
                }
                doAddClass( classSet,className);
            }else {
                // 目录调用循环处理
                String subPackagePath = fileName;
                if(StringUtils.isNotBlank(packagePath)){
                    // 组装 子路径
                    subPackagePath = packagePath+"/"+fileName;
                }
                String subPackageName  = fileName;
                if(StringUtils.isNotBlank(packageName)){
                    // 组装 子包名
                    subPackageName = packageName+"."+subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }
    private  static  void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> clazz = loadClass(className,false);
        System.out.println(className);
        classSet.add(clazz);
    }
}
