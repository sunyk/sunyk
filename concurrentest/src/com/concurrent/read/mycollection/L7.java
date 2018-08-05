package com.concurrent.read.mycollection;

/**
 * created on sunyang 2018/7/25 16:34
 * Are you different!"jia you" for me
 */
public class L7 {
    /**
     * HashMap 和 ConcurrentHashMap 的区别
     * Hashmap本质是数组加链表。根据key取得hash值，然后计算出数组下标，如果多个key对应到同一个下标，就用链表串起来，新插入的在前面。
     * ConcurrentHashMap：在hashMap的基础上，ConcurrentHashMap将数据分为多个segment(段)，默认16个（concurrency level），
     * 然后每次操作对一个segment(段)加锁，避免多线程锁的几率，提高并发效率。
     *
     * 什么是HashMap？
     * HashMap基于哈希表的 Map 接口的实现。此实现提供所有可选的映射操作，并允许使用 null 值和 null 键。
     * 值得注意的是HashMap不是线程安全的，如果想要线程安全的HashMap，可以通过Collections类的静态方法synchronizedMap获得线程安全的HashMap。
     * Map map = Collections.synchronizedMap(new HashMap());
     * 什么是HashMap的数据结构？
     * HashMap的底层主要是基于数组和链表来实现的，它之所以有相当快的查询速度主要是因为它是通过计算散列码来决定存储的位置，
     * 能够很快的计算出对象所存储的位置。HashMap中主要是通过key的hashCode来计算hash值的，只要hashCode相同，计算出来的hash值就一样。
     * 如果存储的对象对多了，就有可能不同的对象所算出来的hash值是相同的，这就出现了所谓的hash冲突。学过数据结构的同学都知道，解决hash冲突的方法有很多，
     * HashMap底层是通过链表来解决hash冲突的。
     *
     * HashMap底层就是一个数组结构，数组中存放的是一个Entry对象，如果产生的hash冲突，
     * 也就是说要存储的那个位置上面已经存储了对象了，这时候该位置存储的就是一个链表了。
     * HashMap其实就是一个Entry数组，Entry对象中包含了键和值，其中next也是一个Entry对象，它就是用来处理hash冲突的，形成一个链表。
     *
     */
}
