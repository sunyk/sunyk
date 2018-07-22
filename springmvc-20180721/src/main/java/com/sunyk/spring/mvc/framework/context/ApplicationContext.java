package com.sunyk.spring.mvc.framework.context;

import com.sunyk.spring.mvc.framework.annotation.Autowired;
import com.sunyk.spring.mvc.framework.annotation.Controller;
import com.sunyk.spring.mvc.framework.annotation.Service;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by sunyang on 2018/7/22 1:40
 * For me:One handred lines of code every day,make myself stronger.
 */
public class ApplicationContext {
    //类似于内部的配置信息，我们在外面是看不到的
    //我们能够看到的只有IOC容器，getbean（）方法间接调用的
    private List<String> classCache = new ArrayList<String>();

    //ioc容器
    private Map<String,Object> instanceMapping = new ConcurrentHashMap<String, Object>();

    public ApplicationContext(String location) {
        /*先加载配置文件
        定位，加载，注册，初始化，注入*/
        InputStream is=null;//定位最终拿到一个inputstream
        try {
            //定位
            is = this.getClass().getClassLoader().getResourceAsStream(location);
            //载入
            Properties config = new Properties();
            config.load(is);
            //注册（beandefinition）,简化把所有class找出来存着
            String packageName = config.getProperty("scanPackage");
            doRegister(packageName);
            //初始化，只循环class了
            doCreateBean();
            //注入
            populate();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    //扫描class
    private void doRegister(String packageName){
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            //如果是一个文件夹，继续递归
            if(file.isDirectory()){
                doRegister(packageName + "." + file.getName());
            }else{
                classCache.add(packageName + "." + file.getName().replace(".class", "").trim());
            }
        }
    }

    private void doCreateBean() {
        if (this.classCache.size() == 0)
            return;
        try {
            for (String className: classCache){
                Class<?> clazz = Class.forName(className);
                //
                if (clazz.isAnnotationPresent(Controller.class)){
                    instanceMapping.put(lowerFirstChar(clazz.getSimpleName()),clazz.newInstance());
                }else if(clazz.isAnnotationPresent(Service.class)){
                    Service service = clazz.getAnnotation(Service.class);
                    String id = service.value();
                    if (!"".equals(id.trim())){
                        instanceMapping.put(id,clazz.newInstance());
                        continue;
                    }
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> i : interfaces){
                        instanceMapping.put(i.getName(),clazz.newInstance());
                    }
                }else{
                    continue;
                }


            }
        }catch (Exception e){

        }

    }

    private void populate() {
        if (instanceMapping.isEmpty()){
            return;
        }

        for (Map.Entry<String,Object> entry : instanceMapping.entrySet()){
            //把所有的属性全部取出来，包括私有属性
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields){
                if (!field.isAnnotationPresent(Autowired.class)){
                    continue;
                }

                Autowired autowired = field.getAnnotation(Autowired.class);
                String id = autowired.value().trim();
                //如果idw为空，也就是说自己么有设置，默认根据类型来注入
                if ("".equals(id)){
                    id = field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(),instanceMapping.get(id));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        }
    }



    public Object getBean(String name){
        return null;
    }

    public Map<String,Object> getAll(){
        return this.instanceMapping;
    }

    private String lowerFirstChar(String str){
        char[] chars= str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
