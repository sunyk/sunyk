package zookeeper2;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Create by sunyang on 2018/6/23 11:53
 * For me:One handred lines of code every day,make myself stronger.
 */

//实现分布式锁
public class DistrubutedLock implements Lock,Watcher {

    private ZooKeeper zk = null;
    private String ROOT_LOCK = "/locks";
    private String WAIT_LOCK;
    private String CURRENT_LOCK;

    private CountDownLatch countDownLatch;

    public DistrubutedLock(){
        try {
            zk = new ZooKeeper("192.168.11.153:2181",
                    4000,this);
            //判断根节点是否存在
            Stat stat = zk.exists(ROOT_LOCK,false);
            if (stat==null){
                //如果根节点不存在
                zk.create(ROOT_LOCK,"0".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock() {
        //创建临时有序节点
        try {
           CURRENT_LOCK = zk.create(ROOT_LOCK+"/","0".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

            List<String> childrens = zk.getChildren(ROOT_LOCK, false);
            SortedSet<String> sortedSet = new TreeSet<>();//定义一个集合进行排序
            for (String children: childrens) {
                sortedSet.add(ROOT_LOCK+"/"+children);
            }
            String firstNode = sortedSet.first();//获得当前所有节点中最小节点
            //获得当前这个节点是不是最小的，比如当前是seq2返回是seq1，如果是seq1返回是seq1
            SortedSet<String> lessThenMe = ((TreeSet<String>) sortedSet).headSet(CURRENT_LOCK);
            //通过当前的节点和子节点中最小的节点进行比较，如果相等，就表示获取锁成功
            if (CURRENT_LOCK.equals(firstNode)){
                return true;
            }
            //如果没有比自己更小的节点
            if (!lessThenMe.isEmpty()){
                WAIT_LOCK = lessThenMe.last();//获得当前节点更小的最后一个节点，设置为WAIT_LOCK
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void lock() {
        if (this.tryLock()){//如果获得锁成功
            System.out.println(Thread.currentThread().getName()+"->"+CURRENT_LOCK+"->获得锁成功");
            return;
        }
        //没有获得锁，继续等待获得锁
        waitForLock(WAIT_LOCK);
    }

    private boolean waitForLock(String prev){
        try {
            //监听当前节点的上一个节点
            Stat stat = zk.exists(prev, true);
            if (stat!=null){
                System.out.println(Thread.currentThread().getName()+"->等待锁"+ROOT_LOCK+"/"+prev+"释放");
                countDownLatch = new CountDownLatch(1);
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName()+"->获得锁成功");
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }



    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName()+"->释放当前锁"+CURRENT_LOCK);
        try {
            zk.delete(CURRENT_LOCK,-1);
            CURRENT_LOCK=null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (this.countDownLatch!=null){
            this.countDownLatch.countDown();
        }
    }
}
