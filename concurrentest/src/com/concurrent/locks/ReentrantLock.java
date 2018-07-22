package com.concurrent.locks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * created on sunyang 2018/6/25 16:05
 * Are you different!"jia you" for me
 */
public class ReentrantLock implements Lock,Serializable {

    private static final long serialVersionUID = 8093436443378496697L;

    private final Sync sync;

    //默认为非公平锁
    public ReentrantLock() {
        sync = new NonfairSync();
    }

    //true为公平锁，false为非公平锁
    public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterrupt() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {//非公平尝试获取
        return sync.nonfairTryAcquire(1);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return (Condition) sync.newCondition();
    }

    //------------------------------------12个TODO----------------------------------------------------------------------------------------------------------------------

    //TODO 1.获得持有锁总数
    public int getHoldCount(){
        return sync.getHoldCount();
    }

    //TODO 2.当前线程是否持有锁（类似监视锁）
    public boolean isHeldByCurrentThread(){
        return sync.isHeldExclusively();
    }

    //TODO 3.有锁吗,用于监视系统状态，不是为了同步控制
    public boolean isLocked(){
        return sync.isLocked();
    }

    //TODO 4.公平锁
    public final boolean isFair(){
        return sync instanceof FairSync;
    }

    //TODO 5.返回当前拥有锁的线程
    protected Thread getOwner(){
        return sync.getOwner();
    }

    //TODO 6.询问是否有线程正在等待获得这个锁
    public final boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }

    //TODO 7.查询指定的线程是否在等待获取的锁
    public final boolean hasQueuedThread(Thread thread){
        return sync.isQueued(thread);
    }

    //TODO 8.返回等待到的线程数量的估计获得这把锁
    public final int getQueueLength(){
        return sync.getQueueLength();
    }

    //TODO 9.获取队列的所有线程
    protected Collection<Thread> getQueuedThreads(){
        return sync.getQueuedThreads();
    }

    //TODO 10.询问是否有线程在给定条件下等待与这个锁相关联.
    public boolean hasWaiters(Condition condition){
        if (condition == null) throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject)){
            throw new IllegalArgumentException("不是自己拥有");
        }
        return sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject) condition);
    }

    //TODO 11.返回等待on的线程数量的估计给定与这个锁相关联的条件
    public int getWaitQueueLength(Condition condition){
        if (condition == null)
            throw new  NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("不是自己拥有");
        return sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject) condition);
    }

    //TODO 12.返回包含那些可能是线程的集合,等待与这个锁相关联的给定条件.
    protected Collection<Thread> getWaitingThreads(Condition condition){
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("不是自己拥有");
        return sync.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject) condition);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 静态内部类Sync
     * s使用AQS表示锁上持有数
     */
    abstract static class Sync extends AbstractQueuedSynchronizer{


        abstract void lock();

        final boolean nonfairTryAcquire(int acquires){
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0){
                /**
                 * compareAndSetState()原子性地将同步状态设置为给定的更新
                 */

                if (compareAndSetState(0, acquires)){
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }else if (current == getExclusiveOwnerThread()){
                int nextc = c + acquires;
                if (nextc < 0 )
                    throw new Error("maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0){
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final ConditionObject newCondition(){
            return new ConditionObject();
        }

        //from outer class (3个)
        final Thread getOwner(){
            return getState() == 0 ? null : getExclusiveOwnerThread();
        }

        final int getHoldCount(){
            return isHeldExclusively() ? getState() : 0;
        }

        final boolean isLocked(){
            return getState() != 0;
        }

        //流中重新构建这个锁实例
        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            //重置没有锁状态
            setState(0);
        }


    }

    /**
     * 非公平锁
     */
    static final class NonfairSync extends Sync{

        @Override
        final void lock() {
            if (compareAndSetState(0, 1))
                setExclusiveOwnerThread(Thread.currentThread());
            else
                acquire(1);
        }

        @Override
        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }
    }

    /**
     * 公平锁
     */
    static final class FairSync extends Sync{

        @Override
        void lock() {
            acquire(1);
        }

        @Override
        protected boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0){
                if (!hasQueuedPredecessors() && compareAndSetState(0, acquires)){
                    setExclusiveOwnerThread(current);
                    return true;
                }else if (current == getExclusiveOwnerThread()){
                    int nextc = c + acquires;
                    if (nextc < 0)
                        throw new Error("Maximum lock count exceeded");
                    setState(nextc);
                    return true;
                }
            }

            return false;
        }
    }






}
