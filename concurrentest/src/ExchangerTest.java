import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created on sunyang 2018/7/9 16:11
 * Are you different!"jia you" for me
 */
public class ExchangerTest {

    private static final Exchanger<String> exchanger =  new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                String A = "bank detil list A";
                try {
                    String result = exchanger.exchange(A);
                    System.out.println(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String B = "bank detil list B";
                    String result = exchanger.exchange(B);
                    System.out.println("A和B的数据是否一致" + result.equals(B) + ",result录入的是：" + result + "，当前B录入的是：" +B);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPool.shutdown();
    }
}
