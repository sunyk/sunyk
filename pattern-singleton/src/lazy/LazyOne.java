package lazy;

/**
 * Create by sunyang on 2018/6/25 23:15
 * For me:One handred lines of code every day,make myself stronger.
 */
public class LazyOne {

    public LazyOne() {

    }

    private static LazyOne lazy = null;

    public static LazyOne getInstance(){
        if (lazy == null){
            lazy = new LazyOne();
        }
        return lazy;
    }
}
