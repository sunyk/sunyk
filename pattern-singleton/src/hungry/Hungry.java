package hungry;

/**
 * Create by sunyang on 2018/6/25 23:08
 * For me:One handred lines of code every day,make myself stronger.
 */
//饿汉式单例
    //优点：没有加任何的锁，执行效率比较高
    //在用户体验上来说，比懒汉式更好

    //缺点：类加载的时候，不管你用还是不要，我都占着空间
    //浪费了内存
    //绝对是线程安全的，在线程还没出现以前就实例化了，不可能出现线程安全性问题
public class Hungry {

    public Hungry() {
    }

    private static final Hungry hugry = new Hungry();

    public static Hungry getHugry() {
        return hugry;
    }
}
