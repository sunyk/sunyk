package com.sunyk.spring.mvc;




//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
//        AnnotationConfigApplicationContext
//        AnnotationConfigWebApplicationContext
//        ContextLoaderListener list;
        Integer i = 1;
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(i);
        int i3 = 1;
        int a = 129;
        Integer a1=129;
        Integer a2=new Integer(125);
        System.out.println(a == a1);
        System.out.println( "Hello World!" );
    }
}
