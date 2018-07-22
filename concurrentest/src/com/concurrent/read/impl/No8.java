package com.concurrent.read.impl;

/**
 * created on sunyang 2018/7/10 16:20
 * Are you different!"jia you" for me
 */

/**
 * ThreadLocal实现原理
 */
public class No8 {
    /**
     * 1.ThreadLocal是什么
     * 他是一个本地变量副本，在并发模式下是绝对安全的变量，也是线程封闭的一种标准用法除局部变量外，即使你将他定义为static，他也是线程安全的。
     * ThreadLocal的数据结构是K-V结构
     * 2.TheadLocal能做什么
     * 在很多并发编程中，ThreadLocal起着重要的作用，他不加锁，非常轻松的将线程封闭做的天衣无缝，又不会像局部变量那样每次需要从新分配空间，很多空间由于是线程安全，
     * 所以，可以反复利用线程私有的缓冲区。
     * 怎么使用：
     * 定义为public static 类型直接new出来一个ThreadLocal对象，要向里面放数据就使用set（Object），要获取数据就用get（）操作，remove就是删除元素，其余是非public
     * 方法，不推荐使用
     * 3.ThreadLocal解决了什么
     * 对于多线程资源共享的问题，同步机制采用了以时间换空间的方式
     * ThreadLocal采用是以空间换时间的方式。
     *
     *
     */

    /*public T get(){
        Thread t = Thread.currentThread();
        ThreadLocalMap map =  getMap(t);
        if(map != null){
            ThreadLocalMap.Entry e = map.getEntry(this);
            if(e != null)
                return (T)e.value;
        }
        return setInitialValue();
    }*/

    /*public void set(T value){
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if(map != null){
            map.put(this, value);
        }else{
            createMap(t, value);
            //void createMap(Thread t, T firestValue){
            //  t.threadLocals = new ThreadLocalMap(this, firestValue);
            //}
            }
        }
    }*/

    /*public void remove(){
        ThreadLocalMap map = getMap(Thread.currentThread());
        if(map != null){
            map.remove(this);
        }
    }*/


    public final static ThreadLocal<String> THREAD_NAME_LOCAL = new ThreadLocal<>();
    public final static ThreadLocal<String> THREAD_VALUE_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            final String name = "线程-【"+ i + "】";
            final String value = String.valueOf(i);

            new Thread(){
                @Override
                public void run() {
                    try {
                        THREAD_NAME_LOCAL.set(name);
                        THREAD_VALUE_LOCAL.set(value);
                        callA();
                    }finally {
                        THREAD_NAME_LOCAL.remove();
                        THREAD_VALUE_LOCAL.remove();
                    }
                }
            }.start();
        }
    }

    public static void callA(){
        callB();
    }

    private static void callB() {
        new No8().callC();
    }

    private void callC() {
        callD();
    }

    private void callD() {
        System.out.println(THREAD_NAME_LOCAL.get() + "/t=/t" + THREAD_VALUE_LOCAL.get());
    }
}
