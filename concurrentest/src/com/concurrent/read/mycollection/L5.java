package com.concurrent.read.mycollection;

/**
 * created on sunyang 2018/7/25 15:14
 * Are you different!"jia you" for me
 */
public class L5 {
    /**
     * HashMap 和 Hashtable 的区别
     * Hashtable和HashMap它们的性能方面的比较类似 Vector和ArrayList，比如Hashtable的方法是同步的,而HashMap的不是。
     * 1.两者最主要的区别在于Hashtable是线程安全，而HashMap则非线程安全。Hashtable的实现方法里面都添加了synchronized关键字来确保线程同步，
     * 因此相对而言HashMap性能会高一些，我们平时使用时若无特殊需求建议使用HashMap，在多线程环境下若使用HashMap需要使用Collections.synchronizedMap()方法
     * 来获取一个线程安全的集合（Collections.synchronizedMap()实现原理是Collections定义了一个SynchronizedMap的内部类，这个类实现了Map接口，
     * 在调用方法时使用synchronized来保证线程同步,当然了实际上操作的还是我们传入的HashMap实例，简单的说就是Collections.synchronizedMap()方法
     * 帮我们在操作HashMap时自动添加了synchronized来实现线程同步，类似的其它Collections.synchronizedXX方法也是类似原理。
     * 2.HashMap可以使用null作为key，不过建议还是尽量避免这样使用。HashMap以null作为key时，总是存储在table数组的第一个节点上。
     * 而Hashtable则不允许null作为key。
     * 3.HashMap继承了AbstractMap，HashTable继承Dictionary抽象类，两者均实现Map接口。
     * 4.HashMap的初始容量为16，Hashtable初始容量为11，两者的填充因子默认都是0.75。
     * 5.HashMap扩容时是当前容量翻倍即:capacity*2，Hashtable扩容时是容量翻倍+1即:capacity*2+1。
     * 6.HashMap和Hashtable的底层实现都是数组+链表结构实现。
     * 7.两者计算hash的方法不同：
     * Hashtable计算hash是直接使用key的hashcode对table数组的长度直接进行取模：
     * int hash = key.hashCode();
     * int index = (hash & 0x7FFFFFFF) % tab.length;
     * HashMap计算hash对key的hashcode进行了二次hash，以获得更好的散列值，然后对table数组长度取摸：
     * static int hash(int h) {
     *      h ^= (h >>> 20) ^ (h >>> 12);
     *      return h ^ (h >>> 7) ^ (h >>> 4);
     *  }
     *
     * static int indexFor(int h, int length) {
     *      return h & (length-1);
     *  }
     *
     *  7.判断是否含有某个键
     * 在HashMap 中，null 可以作为键，这样的键只有一个；可以有一个或多个键所对
     * 应的值为null。当get()方法返回null 值时，既可以表示HashMap 中没有该键，也可
     * 以表示该键所对应的值为null。因此，在HashMap 中不能用get()方法来判断HashM
     * ap 中是否存在某个键，而应该用containsKey()方法来判断。Hashtable 的键值都不能
     * 为null，所以可以用get()方法来判断是否含有某个键。
     */
}
