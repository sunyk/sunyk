package concurrentDemo.read.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by sunyang on 2018/7/15 16:12
 * For me:One handred lines of code every day,make myself stronger.
 */
public class No5SemaphoreDemo {
    //客户消费线程
    static class Consumer implements Runnable {

        private int num;
        private No5 runningMachine;

        public Consumer(int num, No5 runningMachine) {
            this.num = num;
            this.runningMachine = runningMachine;
        }

        @Override
        public void run() {
            //客户获取跑步机
            try {
                No5.RunningMachine machine = runningMachine.getWay();
                if (machine != null){
                    System.out.println("客户" + num + "在" + machine.toString() + "上跑步");
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("客户" + num +"释放" + machine.toString());
                    runningMachine.releaseRunningMachine(machine);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        No5 runningMachine = new No5();
        for (int i = 0; i < 100; i++) {
            pool.execute(new Consumer(i, runningMachine));
        }
        System.out.println("等待关闭执行之前。。。");
        pool.shutdown();
        System.out.println("等待关闭执行完成。。。");
    }
}
