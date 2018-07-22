package concurrent;

/**
 * Create by sunyang on 2018/6/14 19:44
 * For me:One handred lines of code every day,make myself stronger.
 */
public class SyncDemo implements Runnable {

    static int x = 1;

    public void run() {
        for (int i = 0;i< 5;i++){
            System.out.println(Thread.currentThread().getName()+"-"+x++);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new Thread(new SyncDemo(), "t1").start();
        new Thread(new SyncDemo(), "t2").start();

    }

}
