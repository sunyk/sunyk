package concurrentDemo.locks;

/**
 * created on sunyang 2018/6/25 15:16
 * Are you different!"jia you" for me
 * 1.5
 *
 */
public interface ReadWriteLock {

    Lock readLock();

    Lock writeLock();

}
