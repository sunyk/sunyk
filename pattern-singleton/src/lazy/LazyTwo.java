package lazy;

/**
 * Create by sunyang on 2018/6/25 23:15
 * For me:One handred lines of code every day,make myself stronger.
 */
public class LazyTwo {

    public LazyTwo() {

    }

    private static LazyTwo lazy = null;

    public static synchronized LazyTwo getInstance(){
        if (lazy == null){
            lazy = new LazyTwo();
        }
        return lazy;
    }
}
