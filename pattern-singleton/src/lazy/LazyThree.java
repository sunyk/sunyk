package lazy;

/**
 * Create by sunyang on 2018/6/25 23:15
 * For me:One handred lines of code every day,make myself stronger.
 */
//特点：在外部类被调用的时候内部类才会被加载
    //内部类一定是要在方法调用之前初始化
    //巧妙避免的线程安全的问题

    //这种形式兼顾饿汉式的内存浪费，也兼顾了synchronized性能问题
    //史上最牛B的单例模式
public class LazyThree {

    private static boolean initialized = false;

    private LazyThree() {
        synchronized (LazyThree.class){
            if (initialized == false){
                initialized = !initialized;
            }else{
                throw new RuntimeException("单例已被侵犯");
            }
        }

    }


    //每一个关键字都不是多余的
    //static 是为了使单例的空间共享
    //final 保证这个方法不会被重写，重载
    public static final LazyThree getInstance(){
        //在返回结果以前，一定会先加载内部类
        return LazyHolder.LAZY;
    }

    //默认不加载
    private static class LazyHolder{
        private static final LazyThree LAZY = new LazyThree();
    }




}
