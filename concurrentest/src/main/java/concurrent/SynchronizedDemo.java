package concurrent;

/**
 * Create by sunyang on 2018/6/14 16:06
 * For me:One handred lines of code every day,make myself stronger.
 */
public class SynchronizedDemo {

    private Object lock;

    public SynchronizedDemo(Object lock) {
        this.lock = lock;
    }

    //加锁、实例锁
    public synchronized void demo(){}

    public  void demo1(){

    }

    //全局锁
    public void demo3(){
        synchronized (SynchronizedDemo.class){

        }
    }

    public static void main(String[] args) {

//        SynchronizedDemo sd = new SynchronizedDemo();

        Object lock = new Object();

    }
}
