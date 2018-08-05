package com.concurrent.locks;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import sun.misc.Unsafe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.LockSupport;

/**
 * created on sunyang 2018/6/26 15:38
 * Are you different!"jia you" for me
 * AQS类 @since 1.5
 */
public class AbstractQueuedSynchronizer extends AbstractOwnableSynchronizer implements Serializable {

    private static final long serialVersionUID = 7373984972572414691L;

    private transient volatile Node head;

    private transient volatile Node tail;

    private  volatile int state;



    public final int getState() {
        return state;
    }

    public final void setState(int newState) {
        this.state = newState;
    }


    protected AbstractQueuedSynchronizer() {
    }

    /**
     * 设置队列负责人，并检查继任者是否在等待
     * 在共享模式下，如果这样传播，如果传播为0或
     * 传播状态被设置。
     */
    private void setHeadAndPropagate(Node node, int propagate){
        Node h = head;
        setHead(node);

        /**
         * 试着发出下一个排队节点的信号：
         * 调用者表示传播，
         * 或者被先前的操作记录（作为h.waitstatus）
         * （注意：这使用了等待状态的符号检查，因为
         * 传播状态可能转换为信号。）
         * 和
         * 下一个节点在共享模式下等待，
         * 或者我们不知道，因为它看起来是空的
         * 这两项检查中的保守主义可能会导致
         * 不必要的唤醒，但只有当有多个时
         * 赛车获得/释放，所以现在大多数需要信号
         */

        if (propagate > 0 || h == null || h.waitStatus < 0) {
            Node s = node.next;
            if (s == null || s.isShared())
                doReleaseShared();
        }
    }



    /**
     * 共享模式的释放操作——信号的继承者和确保
     * 传播。（注意：对于独占模式，只释放数量
     * 如果它需要信号，就可以调用unpark继任者。）
     * @return
     */
    public void doReleaseShared(){
        /**
         * 确保发布的传播，即使有其他的
         * 正在进行的获得/版本。这是正常的
         * 如果需要的话，想要取消它的后继者
         * 信号。但是如果它不这样做，状态就会传播到
         * 确保在发布时，传播继续。
         * 另外，我们必须在添加新节点的情况下进行循环
         * 当我们这样做的时候。而且，与其他用途不同的是
         * unpark继承者，我们需要知道CAS是否重置状态
         * 如果这样重新检查，就会失败。
         */
        for (;;){
            Node h = head;
            if (h != null && h!= tail){
                int ws = h.waitStatus;
                if (ws == Node.SIGNAL){
                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                        continue;
                    unparkSuccessor(h);
                }else if (ws == 0 && !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                    continue;
            }
            if (h == head)
                break;
        }
    }

    public final boolean hasQueuedPredecessors(){
        Node t = tail;
        Node h = head;
        Node s;
        return h != t && ((s = h.next)==null || s.thread != Thread.currentThread());
    }

    public final void acquire(int arg){
        if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            Thread.currentThread().interrupt();
    }

    public final Collection<Thread> getWaitingThreads(ConditionObject condition){
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitingThreads();
    }

    public final int getWaitQueueLength(ConditionObject condition){
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitQueueLength();
    }

    public final boolean hasWaiters(ConditionObject condition){
        if (!owns(condition))
            throw new IllegalMonitorStateException("Not owner");
        return condition.hasWaiters();
    }

    public final boolean owns(ConditionObject condition){
        if (condition == null)
            throw new NullPointerException();
        return condition.isOwnedBy(this);
    }

    public final Collection<Thread> getQueuedThreads(){
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev){
            Thread t = p.thread;
            if (t != null)
                list.add(t);
        }
        return list;
    }

    public final int getQueueLength(){
        int n = 0;
        for (Node p = tail;p != null; p = p.prev){
            if (p.thread != null)
                ++n;
        }
        return n;
    }

    public final boolean isQueued(Thread thread){
        if (thread == null)
            throw new NullPointerException();
        for (Node p = tail; p != null; p = p.prev)
            if (p.thread == thread)
                return true;
        return false;
    }

    public final boolean hasQueuedThreads(){
        return head != tail;
    }

    protected boolean isHeldExclusively(){
        throw new UnsupportedOperationException();
    }

    //TODO 获取队列
    /**
     * @@code
     * @param node
     * @param arg
     * @return true如果在等待时被中断
     */
    final boolean acquireQueued(final Node node, int arg){
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;){
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)){
                    setHead(node);
                    p.next = null; //help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    interrupted = true;
            }

        }finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    //TODO 是否在同步队列上
    /**
     * 返回true如果一个节点，总是一个初始放置的节点
     * 一个条件队列，现在正在等待在sync队列上重新获得。
     * @param node
     * @return
     */
    final boolean isOnSyncQueue(Node node){
        if (node.waitStatus == Node.CONDITION || node.prev == null)
            return false;
        if (node.next != null)
            return true;
        return findNodeFromTail(node);
    }

    /**
     * 如果节点从尾部向后搜索，则返回true。
     * 只有在isOnSyncQueue需要时才调用。
     * @param node
     * @return
     */
    private boolean findNodeFromTail(Node node){
        Node t = tail;
        for (;;){
            if (t == node)
                return true;
            if (t == null)
                return false;
            t = t.prev;
        }

    }


    //TODO 使用当前状态值调用release;返回保存的状态
    /**
     * 使用当前状态值调用release;返回保存的状态。
     * 取消节点，并在失败时抛出异常。
     * @param node
     * @return
     */
    final int fullyRelease(Node node){
        boolean failed = true;
        try {
            int savedState = getState();
            if (release(savedState)){
                failed =false;
                return savedState;
            }else{
                throw new IllegalMonitorStateException();
            }
        }finally {
            if (failed)
                node.waitStatus = Node.CANCELLED;
        }
    }

    //TODO 如果需要的话，传输节点在被取消后同步队列
    /**
     * 如果在节点发出信号之前被取消，则返回true
     * @param node
     * @return
     */
    final boolean transferAfterCancelledWait(Node node){
        if (compareAndSetWaitStatus(node, Node.CONDITION, 0)){
            enq(node);
            return true;
        }
        /**
         * 如果我们输给了一个信号（），那么我们就不能继续下去了
         * 直到它完成enq（）。取消在
         * 不完全转移既罕见又短暂，所以
         * 旋转。
         */
        while (!isOnSyncQueue(node))
            Thread.yield();
        return false;
    }

    /**
     * 将一个节点从一个条件队列转移到sync队列上。
     * 如果成功，返回true。
     * @param node
     * @return true（else节点是取消之前的信号)。
     */
    final boolean transferForSignal(Node node){
        if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
            return false;
        /**
         * 连接到队列，并尝试设置前辈的等待状态
         * 指示线程（可能）等待。如果取消或
         * 尝试设置等待状态失败，唤醒重新同步（在其中
         * 如果等待状态可以是短暂的，并且无害的错误）。
         */
        Node p = enq(node);
        int ws = p.waitStatus;
        if (ws > 0 || !compareAndSetWaitStatus(p,ws,Node.SIGNAL))
            LockSupport.unpark(node.thread);
        return true;
    }

    //TODO 释放独占mode
    public final boolean release(int arg){
        if (tryRelease(arg)){
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }

    //尝试释放
    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }


    //TODO 视图以独占模式获取，如果中断，给给定的时间超时，则会失败
    public final boolean tryAcquireNanos(int arg, long nanosTimeout) throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquire(arg) || doAcquireNanos(arg, nanosTimeout);

    }

    static final long spinForTimeoutThreshold = 1000L;

    //获得独家计时模式。
    private boolean doAcquireNanos(int arg, long nanosTimeout) throws InterruptedException {
        long lastTime = System.nanoTime();
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (;;){
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)){
                    setHead(node);
                    p.next = null;//help GC
                    failed = false;
                    return true;
                }

                if (nanosTimeout < 0 )
                    return false;
                if (shouldParkAfterFailedAcquire(p,node) &&
                        nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                long now = System.nanoTime();
                nanosTimeout -= now - lastTime;
                lastTime = now;
                if (Thread.interrupted())
                    throw new InterruptedException();

            }
        }finally {
            if (failed)
                cancelAcquire(node);
        }

    }


    //TODO 获取中断
    public final void acquireInterruptibly(int arg) throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (!tryAcquire(arg))
            doAcquireInterruptibly(arg);
    }

    //TODO 获得专有可中断模式
    private void doAcquireInterruptibly(int arg) throws InterruptedException {
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (;;){
                final Node p = node.predecessor();//获得上一个节点
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; //help GC
                    failed = false;
                    return;
                }

                if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                throw new InterruptedException();
            }
        }finally {
            if (failed)
                cancelAcquire(node);
        }

    }

    //取消正在进行的获取
    private void cancelAcquire(Node node) {
        if (node == null)
            return;
        node.thread = null;

        //跳过取消前任
        Node pred = node.prev;
        while (pred.waitStatus > 0)
            node.prev = pred = pred.prev;

        Node preNext = pred.next;

        node.waitStatus = Node.CANCELLED;

        //如果我们是尾巴，那就把自己移走。
        if (node == tail && compareAndSetTail(node, pred)){
            compareAndSetNext(pred, preNext, null);
        }else{
            //如果继任者需要信号，试着设置pred的下一个链接
            //所以它会得到一个。否则唤醒它来传播。
            int ws;
            if (pred != head &&
                    ((ws = pred.waitStatus) == Node.SIGNAL || (ws < 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                    pred.thread != null){
                Node next = node.next;
                if (next != null && next.waitStatus <= 0)
                    compareAndSetNext(pred, preNext, next);
            }else{
                unparkSuccessor(node);
            }
        }
        node.next = node; //help GC

    }

    //唤醒节点
    private void unparkSuccessor(Node node) {
        int ws = node.waitStatus;
        if (ws < 0)
            compareAndSetWaitStatus(node,ws, 0);

        Node s = node.next;
        if (s == null || s.waitStatus > 0){
            s = null;
            for (Node t = tail; t != null && t !=node; t = t.prev){
                if (t.waitStatus <= 0){
                    s = t;
                }
            }
        }
        if (s != null)
            LockSupport.unpark(s.thread);

    }


    //方便的停车方法，然后检查是否被中断
    //返回@code true 表示中断
    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    //检查和更新未获得的节点的状态。
    //如果线程应该阻塞，返回true。
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL){//发出信号，它可以安全停车
            return true;
        }
        if (ws > 0){//前任被取消.
            do {
                node.prev = pred = pred.prev;
            }while (pred.waitStatus > 0);
                pred.next = node;

        }else{
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }


    //TODO 将队列头设为节点，从而取消排队
    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    //TODO 新加独占等待 enq(node)
    private Node addWaiter(Node exclusive) {
        Node node = new Node(Thread.currentThread(), exclusive);
        Node pred = tail;
        if (pred != null){
            node.prev = pred;
            if (compareAndSetTail(pred, node)){
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }

    //TODO enq()
    /**
     * 将节点插入到队列中，必要时初始化。见上图。
     * @param节点插入节点
     * * @return节点的前任
     * @param node
     */
    private Node enq(Node node) {
        for (;;){
            Node t = tail;
            if (t == null){
                if (compareAndSetHead(new Node()))
                    tail = head;
            }else{
                node.prev = t;
                if (compareAndSetTail(t,node)){
                    t.next = node;
                    return t;
                }
            }
        }
    }

    /**
     * AQS内部维护了一个以Node为节点实现的链表的队列
     *
     *           +--------+    prev  +------+        +------+
     *      head |        |   <-----|       | <------|      |   tail
     *           +--------+         +-------+        +------+
     *
     */

    //TODO 静态内部类Node-----
    static final class Node{
        //共享
        static final Node SHARED = new Node();
        //独有
        static final Node EXCLUSIVE = null;
        //取消
        static final int CANCELLED = 1;
        //信号
        static final int SIGNAL = -1;
        //条件
        static final int CONDITION = -2;
        //传播
        static final int PROPAGATE = -3;

        //等待状态,上一个，下一个,在这个节点上排队的线程,下一个等待
        volatile int waitStatus;
        volatile Node prev;
        volatile Node next;
        volatile Thread thread;
        Node nextWaiter;

        public Node() {
        }

        public Node(Thread thread, Node nextWaiter) {
            this.thread = thread;
            this.nextWaiter = nextWaiter;
        }

        public Node( Thread thread, int waitStatus) {
            this.waitStatus = waitStatus;
            this.thread = thread;
        }

        //TODO Node静态内部类：是否共享
        final boolean isShared(){
            return nextWaiter == SHARED;
        }

        //TODO Node静态内部类：返回之前的节点，或空指针
        final Node predecessor(){
            Node p = prev;
            if (p == null){
                throw new NullPointerException();
            }else{
                return p;
            }
        }

    }

    //TODO 尝试以独占模式获取
    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    //TODO 静态内部类ConditionObject-----
    public class ConditionObject implements Condition,Serializable {

        private static final long serialVersionUID = 2569938640590784418L;
        //第一个条件队列的节点
        private transient Node firstWaiter;//条件队列的头节点
        //最后一个条件队列的节点
        private transient Node lastWaiter;//条件队列的尾节点

        public ConditionObject() {
        }

        /**
         *  添加一个新节点到条件队列
         *             可以看到，在修改队列节点结构时候并没有使用CAS，这是因为通常使用condition的前提必须是在独占模式的lock下
         * @return
         */
        //添加一个条件等待队列
        private Node addConditionWaiter(){
            Node t = lastWaiter;
            //如果最后一个条件的节点被取消，清空
            if (t != null && t.waitStatus != Node.CONDITION){
                unlinkCancelledWaiters();
                t = lastWaiter;
            }
            Node node =  new Node(Thread.currentThread(), Node.CONDITION);
            if (t == null){
                firstWaiter = node;
            }else{
                t.nextWaiter = node;
            }
            lastWaiter = node;
            return node;
        }

        /**
         * 持有锁的情况下，从condition等待队列中分离已被取消的节点。
         *             该方法只有在条件队列中发生节点取消或者添加一个新的节点的时候发现尾节点已被取消时调用。
         *             该方法需要避免垃圾滞留（没有signal时候），所以即使它需要完整遍历，但也只有在在由于没有signal
         *             而导致的超时或者取消时才起作用。
         */
        private void unlinkCancelledWaiters(){
            Node t = firstWaiter;
            Node trail = null;
            while (t != null){
                Node next = t.nextWaiter;
                if (t.waitStatus != Node.CONDITION){
                    t.nextWaiter = null;
                    if (trail == null){
                        firstWaiter = next;
                    }else{
                        trail.nextWaiter = next;
                    }
                    if (next == null)
                        lastWaiter = trail;

                }else{
                    trail = t;
                }
                t = next;
            }
        }

        //重新中断参数
        private static final int REINTERRUPT = 1;
        //
        private static final int THROW_IE = -1;

        private int checkInterruptWhileWaiting(Node node){
            return Thread.interrupted() ? (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) : 0;
        }

        @Override
        public void await() throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            // 添加一个新节点到等待队列
            Node node = addConditionWaiter();
            //释放当前所有state。因为其他线程需要lock,后面还会恢复
            int saveState = fullyRelease(node);
            int interruptMode = 0;
            //判断当前节点是否已被释放（从等待队列转移到同步队列）
            while (!isOnSyncQueue(node)){
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            //调用acquireQueued使节点node从AQS同步队列出队
            if (acquireQueued(node, saveState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            //  如果存在后继节点，则检查且清理取消节点。
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }

        /**
         * 根据mode来决定是否抛出InterruptedException异常或者，或者不执行任何动作。
         * 抛出中断，重新中断当前线程，或者
         *         什么都不做，取决于模式。
         * @param interruptMode
         */
        private void reportInterruptAfterWait(int interruptMode) throws InterruptedException {
            if (interruptMode == THROW_IE) {
                throw new InterruptedException();
            }else if (interruptMode == REINTERRUPT){
                Thread.currentThread().interrupt();
            }
        }

        /**
         * 实现不可中断状态等待
         * 保存由@link getstate返回的锁状态。
         * 调用@link释放
         * 将状态保存为参数，抛出
         * 非法的监控，如果它失败了。
         * 直到有信号为止。
         * 通过调用专业版的
         * @link以保存的状态作为参数。
         */
        /**
         * 不抛出线程中断异常的的条件等待的实现.
         *             保存getState方法返回的状态值，且调用release释放getState方法返回的状态值。
         *             为什么要释放所有的getState的状态值？
         *             试下Condition的await方法功能是类似于Object的await方法，Object的await方法
         *             在调用时候需要进行该Object的同步（Synchronized）。所以Condition的await在
         *             被调用时候也需要拥有独占锁（Lock.lock）。
         *             如果 线程1 await后不释放AQS的state，那么 线程2 在signal时候无法获取锁。
         *
         *            如果release失败，则抛出异常 IllegalMonitorStateException
         *
         *             接着会调用LockSupport.park(this);阻塞当前线程，直到该线程被唤醒
         */
        @Override
        public final void awaitUnInterrupted() {
            Node node = addConditionWaiter();
            int saveState = fullyRelease(node);
            boolean interrupted = false;
            //线程被唤醒后需要判断当前节点是否在同步队列（因为调用signal唤醒，会把头节点从等待队列转移到AQS的同步队列 ）
            while (!isOnSyncQueue(node)){
                LockSupport.park(this);
                if (Thread.interrupted())
                    interrupted = true;
            }
            //这里注意acquireQueued带有一个形参savedState。至于为什么要把这个savedState传入，
            //想想之前释放掉的savedState就明白了
            if (acquireQueued(node,saveState) || interrupted)
                Thread.currentThread().interrupt();
        }

        /**
         *实现定时条件等待。
         * 如果当前的线程被中断，则抛出中断。
         * 保存由@link getstate返回的锁状态。
         * 调用@link释放
         * 将状态保存为参数，抛出
         * 非法的监控，如果它失败了。
         * 阻塞，直到发出信号，中断，或超时。
         * 通过调用专业版的
         * @link以保存的状态作为参数。
         * 如果在第4步被阻塞时被中断，则抛出中断。
         * @param nanosTimeOut
         * @return
         * @throws InterruptedException
         */
        //指定一个相对时间，如果在相对时间内被唤醒且检查是否满足不再阻塞线程条件，否知阻塞直至到达过期时间，
        //释放当前线程
        @Override
        public final long awaitNanos(long nanosTimeOut) throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node =  addConditionWaiter();
            int savedState = fullyRelease(node);
            long lastTime = System.nanoTime();
            int interruptMode = 0;
            while (!isOnSyncQueue(node)){
                if (nanosTimeOut <= 0L){
                    transferAfterCancelledWait(node);
                    break;
                }
                //这里和上面普通。只挂起线程nanosTimeout纳秒
                LockSupport.parkNanos(this, nanosTimeOut);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                long now = System.nanoTime();
                //注意这里运算符德优先级关系
                nanosTimeOut -= now - lastTime;
                lastTime = now;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return nanosTimeOut - (System.nanoTime() - lastTime);
        }

        /**
         *实现定时条件等待。
         * 如果当前的线程被中断，则抛出中断。
         * 保存由@link getstate返回的锁状态。
         * 调用@link释放
         * 将状态保存为参数，抛出
         * 非法的监控，如果它失败了。
         * 阻塞，直到发出信号，中断，或超时。
         * 通过调用专业版的
         * @link以保存的状态作为参数。
         * 如果在第4步被阻塞时被中断，则抛出中断。
         * 如果在第4步被阻塞时超时，返回false，else true。
         * @param time
         * @param unit
         * @return
         */
        @Override
        public boolean await(long time, TimeUnit unit) throws InterruptedException {
            if (unit == null)
                throw new NullPointerException();
            long nanosTimeOut = unit.toNanos(time);
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            long lastTime = System.nanoTime();
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)){
                if (nanosTimeOut <= 0L){
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeOut >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this,nanosTimeOut);
                if ((interruptMode = checkInterruptWhileWaiting(node)) !=0)
                    break;
                long now = System.nanoTime();
                nanosTimeOut -= now - lastTime;
                lastTime = now;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        /**
         * 实现绝对定时条件等待。
         * 如果当前的线程被中断，则抛出中断。
         * 保存由@link getstate返回的锁状态。
         * 调用@link释放
         * 将状态保存为参数，抛出
         * 非法的监控，如果它失败了。
         * 阻塞，直到发出信号，中断，或超时。
         * 通过调用专业版的
         * @link以保存的状态作为参数。
         * 如果在第4步被阻塞时被中断，则抛出中断。
         * 如果在第4步被阻塞时超时，返回false，else true。
         * @param dealDate
         * @return
         */
        //指定一个绝对时间，如果在绝对时间之前被唤醒，则线程检查是否满足完成阻塞，是则推出阻塞。
        //否则继续阻塞直至到达绝对时间，然后才中断阻塞
        @Override
        public boolean awaitUntil(Date dealDate) throws InterruptedException {
            if (dealDate == null)
                throw new NullPointerException();
            long abstime = dealDate.getTime();
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)){
                if (System.currentTimeMillis() > abstime){
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                LockSupport.parkUntil(this, abstime);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        //------------------------------公共方法--------------------------------------------------------------------------------------------

        /**
         * 移动最长等待的线程，如果存在的话，从等待队列等待队列的等待队列拥有锁。
         */
        /**
         * 在独占锁模式下，删除当前Condition中的等待队列的头节点.
         */
        @Override
        public final void singal() {
            if (!isHeldExclusively()){
                throw new IllegalMonitorStateException();
            }
            Node first = firstWaiter;
            if (first != null)
            /**
             * 删除当前Condition中的等待队列的头节点，且转移头节点到AQS的同步等待队列中
             *                 注意：仅仅只是删除头节点,并没有唤醒任何节点！
             *                 那么疑问来了，为什么signal不唤醒节点却能达到Object的signal一样的效果呢？(头)
             *                 单纯的从这一步解释的通，因为signal代表着唤醒线程。AQS利用signal必须得持有独占锁，
             *                 在unlock时候实际上就是唤醒await节点。而这里的signal仅仅只是移除等待队列的头部
             */
                doSignal(first);
        }

        /**
         *  删除condition等待队列中的节点first,且把节点first转移到condition所属的AQS等待队列中，
         *             直到成功。
         * @param first
         */
        //删除和传输节点，直到未被取消的节点
        private void doSignal(Node first){
            do {
                if ((firstWaiter = first.nextWaiter) == null)
                    lastWaiter = null;
                first.nextWaiter = null;
            } while (!transferForSignal(first) && (first = firstWaiter) != null);
        }

        //将所有线程从等待队列中移动到这个条件拥有锁的等待队列。

        /**
         * 在当前线程拥有独占锁的情况下，删除当前condition 中等待队列的所有线程
         */
        @Override
        public void singalAll() {
            //判断是否拥有独占锁
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignalAll(first);
        }

        /**
         * 转移且删除所有的节点
         * @param first
         */
        //删除并传输所有节点。
        private void doSignalAll(Node first){
            lastWaiter = firstWaiter = null;
            do {
                Node next = first.nextWaiter;
                first.nextWaiter = null;
                transferForSignal(first);
                first = next;
            }while (first != null);
        }

        //返回等待线程的集合
        //获取当前等待队列里节点里的处于有效等待唤醒状态的线程的集合
        protected final Collection<Thread> getWaitingThreads(){
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            ArrayList<Thread> list = new ArrayList<Thread>();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter){
                if (w.waitStatus == Node.CONDITION){
                    Thread t = w.thread;
                    if (t != null)
                        list.add(t);
                }
            }
            return list;
        }

        //返回等待的线程数量的估计
        //获取当前等待队列里节点数的估计值
        protected final int getWaitQueueLength(){
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int n = 0;
            for (Node w = firstWaiter;w!=null;w = w.nextWaiter){
                if (w.waitStatus == Node.CONDITION)
                    ++n;
            }
            return n;
        }

        //询问是否有线程在等待这个条件。
        //查询当前等待队列是否存在 有效等待(waitStatus 值为CONDITION)的线程
        protected final boolean hasWaiters(){
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter){
                if (w.waitStatus == Node.CONDITION)
                    return true;
            }
            return false;
        }

        //如果这个条件是由给定的条件创建的同步对象
        //判断sync是否和和当前的调用的this是同一个。追踪下AQS的owns(ConditionObject condition)就明白了
        final boolean isOwnedBy(AbstractQueuedSynchronizer sync){
            return sync == AbstractQueuedSynchronizer.this;
        }

        //

    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //TODO CAS展现
    /**
     * CASable
     */
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
        }catch (Exception e){
            throw new Error(e);
        }
    }

    private final boolean compareAndSetHead(Node update){
        return unsafe.compareAndSwapObject(this,headOffset, null, update);
    }

    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset,expect, update);
    }

    private static final boolean compareAndSetWaitStatus(Node node, int expect, int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,expect,update);
    }

    private static final boolean compareAndSetNext(Node node, Node expect, Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }

    protected final boolean compareAndSetState(int expect, int update){
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

}
