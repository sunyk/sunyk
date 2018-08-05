package com.concurrent.read.myquestion;

import javax.xml.soap.Node;

/**
 * created on sunyang 2018/7/16 14:59
 * Are you different!"jia you" for me
 */
public class Aqs {
    /**
     * AQS是为实现依赖于先进先出 (FIFO) 等待队列 的阻塞锁和相关同步器（信号量、事件，等等）提供一个框架。
     * 它使用了一个原子的int value status来作为同步器的状态值(如：独占锁。1代表已占有，0代表未占有)，
     * 通过该类提供的原子修改status方法（getState, setState and compareAndSetState），我们可以把它作为同步器的基础框架类来实现各种同步器。
     * AQS还定义了一个实现了Condition接口的ConditionObject内部类。Condition 将 Object 监视器方法（wait、notify 和 notifyAll）分解成截然不同的对象，
     * 以便通过将这些对象与任意 Lock 实现组合使用，为每个对象提供多个等待 set （wait-set）。
     * 其中，Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的使用。
     * 简单来说，就是Condition提供类似于Object的wait、notify的功能signal和await，都是可以使一个正在执行的线程挂起（推迟执行），
     * 直到被其他线程唤醒。但是Condition更加强大，如支持多个条件谓词、保证线程唤醒的顺序和在挂起时不需要拥有锁。
     */

    /**
     * 总结：
     * 5.区别 Shard(共享模式) 和非Shard(独占模式)
     *
     * 虽然AQS的共享模式和独占模式写的有点复杂。但是要知道无非就两种情况：
     * 独占模式：即state值在0和1之前来回切换，保证同一时间只能有一个线程是处于活动的，其他线程都被阻塞， 参考：ReentranLock..
     * 共享模式：state值在整数区间内，如果state值<0则阻塞，否则则不阻塞。可以参考：Semphore、CountDownLautch..
     */

    //AQS的独占锁实现
    /**
     *
     * // 重写AQS独占锁获取锁方法：如果当前AQS的state状态为0，则获取锁成功
     *      public boolean tryAcquire(int acquires) {
     *        assert acquires == 1; // Otherwise unused
     *        //CAS （Compare and Set）调用CPU指令原子更新AQS的state值
     *        if (compareAndSetState(0, 1)) {
     *          setExclusiveOwnerThread(Thread.currentThread());
     *          //返回获取成功
     *          return true;
     *        }
     *        return false;
     *      }
     *
     *      // 重写AQS独占锁释放方法
     *      protected boolean tryRelease(int releases) {
     *        assert releases == 1; // Otherwise unused
     *        if (getState() == 0) throw new IllegalMonitorStateException();
     *        setExclusiveOwnerThread(null);
     *       //可以看到这里并没有使用CAS原子更新state的值，不够严谨
     *        setState(0);
     *        return true;
     *      }
     */
    //AQS的共享锁实现
    /**
     *
     * /重写AQS的共享锁获取方法,只要返回的值大于0则可以获取锁，否则AQS会调用unsafe的park挂起线程，后面我们会分析这块源码
     *      protected int tryAcquireShared(int ignore) {
     *        return isSignalled() ? 1 : -1;
     *      }
     *
     * //重写AQS的共享锁释放方法，这里仅仅只是设置AQS的state值为1，和参数ignore没有任何关系。设置完毕后，AQS会调用unsafe的unpark唤醒线程，则之前被挂起的线程会重新执行tryAcquireShared
     *     //方法。而此时的state大于0，则可以获取锁
     *     protected boolean tryReleaseShared(int ignore) {
     *        setState(1);
     *        return true;
     *      }
     */

    /**
     * 自旋入队列
     * private Node enq(final Node node){
     *         for (;;){
     *             Node t = tail;
     *             if (t == null){
     *                 if (compareAndSetHead(new Node()))
     *                     tail = head;
     *             }else{
     *                 node.prev = t;
     *                 if (compareAndSetTail(t, node)){
     *                     t.next = node;
     *                     return t;
     *                 }
     *             }
     *         }
     *     }
     */

    /**
     * 共享模式获取的核心公共方法
     *  private void doAcquireShared(int arg){
     *         //添加当前线程为一个共享模式的节点
     *         final Node node = addWaiter(Node.SHARED);
     *         boolean failed = true;
     *         try {
     *             boolean intterupted = false;
     *             for (;;){
     *                 final Node p = node.predecessor();
     *                 if (p  == head){
     *                     int r = tryAcquireShared(arg);
     *                     if (r > 0){
     *                         setHeadAndPropagate(node,r);
     *                         p.next = null;
     *                         if (intterupted)
     *                             selfInterrupt();
     *                         failed = false;
     *                         return;
     *                     }
     *                 }
     *                 //判断当前节点是否应该被阻塞，是则阻塞等待其他线程release
     *                 if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()){
     *                     intterupted = true;
     *                 }
     *             }
     *         }finally {
     *             if (failed)
     *                 cancelAcquire(node);
     *         }
     *     }
     */




}
