/**
 * Create by sunyang on 2018/6/14 21:07
 * For me:One handred lines of code every day,make myself stronger.
 */
public class VolatileDemo {


    private static volatile VolatileDemo instance = null;

    public static VolatileDemo getInstance(){
        if (instance == null){
            instance = new VolatileDemo();
        }
        return instance;
    }

    public static void main(String[] args) {
        VolatileDemo.getInstance();
    }


}
