package concurrentDemo.test;

import java.util.concurrent.CountDownLatch;

/**
 * created on sunyang 2018/7/13 16:03
 * Are you different!"jia you" for me
 */
public class Service implements Runnable{
    private CountDownLatch latch;

    public Service(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            execute();
        }finally {
            if (latch != null){
                latch.countDown();
            }
        }

    }

    public void execute() {
    }
}
