/**
 * Create by sunyang on 2018/6/14 19:55
 * For me:One handred lines of code every day,make myself stronger.
 */
public class SyncDemo implements Runnable {

    private static int x = 1;
    Object lock;

    public SyncDemo(Object lock) {
        this.lock = lock;
    }

    public void run() {
        synchronized (lock){
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()+"-" + x++);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }
    }

    public static void main(String[] args) throws InterruptedException {

//        Thread t1 = new Thread(new SyncDemo(),"T1");
//        Thread t2 = new Thread(new SyncDemo(),"T2");
//        t1.start();
//        t1.join();
//        t2.start();


        Object lock = new Object();
        new Thread(new SyncDemo(lock), "T1").start();

        new Thread(new SyncDemo(lock), "T2").start();

    }


}
