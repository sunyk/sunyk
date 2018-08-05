package concurrentDemo.locks;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * created on sunyang 2018/6/25 15:21
 * Are you different!"jia you" for me
 */
public interface Condition {
    //阻塞
    void await() throws InterruptedException;
    //阻塞不间断
    void awaitUnInterrupted();
    //阻塞nanos
    long awaitNanos(long nanosTimeOut) throws InterruptedException;
    //阻塞多长时间
    boolean await(long time, TimeUnit unit) throws InterruptedException;
    //阻塞一直到某个时间点
    boolean awaitUntil(Date dealDate) throws InterruptedException;
    //信号
    void singal();
    //ALL信号
    void singalAll();

}
