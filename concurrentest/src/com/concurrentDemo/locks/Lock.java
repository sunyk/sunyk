package concurrentDemo.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * created on sunyang 2018/6/25 15:07
 * Are you different!"jia you" for me
 * 1.5
 */
public interface Lock {

    void lock();

    void lockInterrupt() throws InterruptedException;

    boolean tryLock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void unlock();

    Condition newCondition();




}
