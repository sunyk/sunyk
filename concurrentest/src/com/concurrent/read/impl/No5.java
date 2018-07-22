package concurrent.read.impl;

import java.util.concurrent.Semaphore;

/**
 * created on sunyang 2018/7/5 17:34
 * Are you different!"jia you" for me
 */
public class No5 {
    /**
     * 是什么？
     * Semaphore是计数信号量。Semaphore管理一系列许可证。每个acquire方法阻塞，直到有一个许可证可以获得然后拿走一个许可证；
     * 每个release方法增加一个许可证，这可能会释放一个阻塞的acquire方法。然而，其实并没有实际的许可证这个对象，Semaphore只是维持了一个可获得许可证的数量。
     * 做什么？
     * Semaphore经常用于限制获取某种资源的线程数量。
     */

    /**
     * 跑步机
     */
    static class RunningMachine{
        private int num;

        public RunningMachine(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "RunningMachine{" +
                    "num=" + num +
                    '}';
        }
    }

    private RunningMachine[] machines = {new RunningMachine(1),new RunningMachine(2),new RunningMachine(3),new RunningMachine(4),new RunningMachine(5)};

    private volatile boolean[] usedMan = new boolean[5];
    private Semaphore semaphore = new Semaphore(5,true);

    /**
     * 获取空闲跑步机
     */
    public RunningMachine getWay() throws InterruptedException {
        semaphore.acquire(1);
        return getNextWay();
    }

    /**
     * 运动完后释放跑步机
     * @return
     */
    public void releaseRunningMachine(RunningMachine machine){
        if (makeAsUsed(machine))
        semaphore.release(1);
    }

    /**
     * 是否可用
     * @param machine
     * @return
     */
    private boolean makeAsUsed(RunningMachine machine) {
        for (int i = 0; i < usedMan.length; i++) {
            if (machines[i] == machine){
                if (usedMan[i]){
                    usedMan[i] = false;
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 找到一个空闲的跑步机
     * @return
     */
    private RunningMachine getNextWay() {
        for (int i = 0; i < usedMan.length; i++) {
            if (!usedMan[i]){
                usedMan[i] = true;
                return machines[i];
            }
        }
        return null;
    }


}
