package com.sunyk.spring.mvc.framework.servlet;

import com.sunyk.spring.mvc.framework.annotation.Controller;
import com.sunyk.spring.mvc.framework.annotation.RequestMapping;
import com.sunyk.spring.mvc.framework.annotation.RequestParam;
import com.sunyk.spring.mvc.framework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by sunyang on 2018/7/22 1:24
 * For me:One handred lines of code every day,make myself stronger.
 */
public class DispatcherServlet extends HttpServlet {
    private static final String LOCATION = "contextConfigLocation";

    private Map<String,Handlers> handlerMapping = new HashMap<String, Handlers>();

    private Map<Handlers, HandlerAdapters> adaptersMapping =new HashMap<Handlers, HandlerAdapters>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        }catch (Exception e){
            resp.getWriter().write("500");
        }
    }

    //TODO doDispatch
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Handlers handlers = getHandler(req);
        if (handlers == null){
            resp.getWriter().write("404,not found");
            return;
        }
        HandlerAdapters ha =  getHandlerAdapter(handlers);
        ha.handle(req,resp,handlers);



    }

    private  HandlerAdapters getHandlerAdapter(Handlers handlers){
        if (adaptersMapping.isEmpty()){
            return null;
        }
        return adaptersMapping.get(handlers);
    }

    private Handlers getHandler(HttpServletRequest req){
        if (handlerMapping.isEmpty()){
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "".replaceAll("/+","/"));
        return handlerMapping.get(url);
//        for (Map.Entry<String,Handlers> entry : handlerMapping.entrySet()){
//            if (url.equals(entry.getKey())){
//                return entry.getValue();
//            }
//        }
//        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {


        //初始化ioc容器
        ApplicationContext context = new ApplicationContext(config.getInitParameter(LOCATION));


        this.initMultipartResolver(context);
        this.initLocaleResolver(context);
        this.initThemeResolver(context);


        this.initHandlerMappings(context);
        this.initHandlerAdapters(context);
        this.initViewResolvers(context);


        this.initHandlerExceptionResolvers(context);
        this.initRequestToViewNameTranslator(context);


        this.initFlashMapManager(context);
    }

    private void initFlashMapManager(ApplicationContext context) {

    }

    private void initViewResolvers(ApplicationContext context) {

    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {
    }

    private void initHandlerAdapters(ApplicationContext context) {
        if (handlerMapping.isEmpty()) return;
        Map<String,Integer> paramMapping = new HashMap<String, Integer>();

        //只需要取出具体的某个方法
        //初始化是为了把所有参数解析出来
        //匹配自定义参数列表
        for (Map.Entry<String,Handlers> entry: handlerMapping.entrySet()){

            Class<?>[] parameterTypes = entry.getValue().method.getParameterTypes();//把这个方法上所有参数取到
            //有顺序，反射拿不到我们的参数名字
            //通过编号取拿
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class){
                    paramMapping.put(type.getName(),i);
                }
            }
            //这里匹配对应请求和response
            Annotation[][] pa = entry.getValue().method.getParameterAnnotations();
            for (int i = 0; i < pa.length; i++) {
                for (Annotation a : pa[i]){
                    if (a instanceof RequestParam){
                        String paramName = ((RequestParam) a).value();
                        if (!"".equals(paramName.trim())){
                            paramMapping.put(paramName,i);
                        }
                    }

                }
            }

            adaptersMapping.put(entry.getValue(),new HandlerAdapters(paramMapping));
        }

    }

    private void initHandlerMappings(ApplicationContext context) {

        Map<String,Object> ioc = context.getAll();
        if (ioc.isEmpty()){
            return;
        }
        //只要是由controller修饰类，里面方法全部找出来
        //而且这个方法上一个加上requestmapping注解，如果没有加这个注解，这个方法是不能被外界来访问的
        for (Map.Entry<String,Object> entry : ioc.entrySet()){
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(Controller.class)){
                continue;
            }
            String url = "";
            if (clazz.isAnnotationPresent(RequestMapping.class)){
               RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                 url = requestMapping.value();
            }
            //扫描controller下面所有的方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods){
                if (!method.isAnnotationPresent(RequestMapping.class)){
                    continue;
                }
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
//                String methodUrl = url+ requestMapping.value();
                String methodUrl = url+ requestMapping.value();
                handlerMapping.put(methodUrl, new Handlers(entry.getValue(), method));
            }

        }

        //requestmapping会配置一个URL，那么一个URL就会对于一个方法，并将这个关系保存到map中
    }

    private void initThemeResolver(ApplicationContext context) {

    }

    private void initLocaleResolver(ApplicationContext context) {

    }

    private void initMultipartResolver(ApplicationContext context) {

    }

    private class Handlers {
        protected Object controller;
        protected Method method;


        public Handlers(Object controller, Method method) {
            this.controller = controller;
            this.method = method;
        }
    }

    private class HandlerAdapters {
        private Map<String,Integer> paramMapping;

        public HandlerAdapters(Map<String, Integer> paramMapping) {
            this.paramMapping = paramMapping;
        }
        //主要目的是用反射调用URL对应的method
        public void handle(HttpServletRequest req, HttpServletResponse resp, Handlers handlers) throws InvocationTargetException, IllegalAccessException {
            Class<?>[] paramTypes = handlers.method.getParameterTypes();

            Object[] paramValues = new Object[paramTypes.length];

            Map<String,String[]> params = req.getParameterMap();

            for (Map.Entry<String,String[]> param : params.entrySet()){
              String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s",",");
               if (!this.paramMapping.containsKey(param.getKey())){
                   continue;
               }
               int index = this.paramMapping.get(param.getKey());
               paramValues[index] = castStringValue(value, paramTypes[index]);

            }
              //request和response要赋值
            String reqName = HttpServletRequest.class.getName();
            if (this.paramMapping.containsKey(reqName)){
                int reqIndex = this.paramMapping.get(reqName);
                paramValues[reqIndex] = req;
            }

            String respName = HttpServletResponse.class.getName();
            if (this.paramMapping.containsKey(respName)){
                int respIndex = this.paramMapping.get(respName);
                paramValues[respIndex] = resp;
            }
            handlers.method.invoke(handlers.controller, paramValues);
        }

        private Object castStringValue(String value,Class<?> clazz){

            if (clazz == String.class){
                return value;
            }else if (clazz == Integer.class){
                return Integer.valueOf(value);
            }else if (clazz == int.class){
                return Integer.valueOf(value).intValue();
            }else{
                return null;
            }
        }

    }
}
