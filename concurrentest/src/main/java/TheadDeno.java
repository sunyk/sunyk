/**
 * Create by sunyang on 2018/6/14 20:21
 * For me:One handred lines of code every day,make myself stronger.
 */
public class TheadDeno extends Thread{


    private static int count = 0;

    public static void incr(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*
         *线程和内存的交互过程
        *getstatic
        * iadd
        * putstatic
        *
         */
        count ++;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            //incr();
            Thread.sleep(2000);

//            Thread thread = new Thread(TheadDeno()->incr()).start();
            System.out.println(count);
        }
    }


}
