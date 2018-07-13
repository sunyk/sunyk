package com.pattern.comparator;

/**
 * Create by sunyang on 2018/7/2 23:19
 * For me:One handred lines of code every day,make myself stronger.
 */
public class MyList {

    public void sort(Comparator comparator){
        Object obj1= new Object();
        Object obj2 = obj1;
        comparator.compareTo(obj1,obj2);
        System.out.println("执行逻辑");
    }
}
