package com.concurrent.read.mycollection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;


/**
 * created on sunyang 2018/7/24 17:45
 * Are you different!"jia you" for me
 */
public class L3 {
    /**
     * Arraylist 与 LinkedList 区别
     * 1. ArrayList是实现了基于动态数组的数据结构，而LinkedList是基于链表的数据结构；
     * 2. 对于随机访问get和set，ArrayList要优于LinkedList，因为LinkedList要移动指针；
     * 3. 对于添加和删除操作add和remove，一般大家都会说LinkedList要比ArrayList快，因为ArrayList要移动数据。
     */

    /**
     * 从源码可以看出，ArrayList想要get(int index)元素时，直接返回index位置上的元素，而LinkedList需要通过for循环进行查找，
     * 虽然LinkedList已经在查找方法上做了优化，
     * 比如index < size / 2，则从左边开始查找，反之从右边开始查找，但是还是比ArrayList要慢。这点是毋庸置疑的。
     */
    /**
     * ArrayList想要在指定位置插入或删除元素时，主要耗时的是System.arraycopy动作，会移动index后面所有的元素；
     * LinkedList主耗时的是要先通过for循环找到index，然后直接插入或删除。这就导致了两者并非一定谁快谁慢，下面通过一个测试程序来测试一下两者插入的速度
     * 主要有两个因素决定他们的效率，插入的数据量和插入的位置。
     */
    /**
     * 结论：
     *  所以当插入的数据量很小时，两者区别不太大，当插入的数据量大时，大约在容量的1/10之前，LinkedList会优于ArrayList，在其后就劣与ArrayList，
     *  且越靠近后面越差。所以个人觉得，一般首选用ArrayList，由于LinkedList可以实现栈、队列以及双端队列等数据结构，所以当特定需要时候，使用LinkedList，
     *  当然咯，数据量小的时候，两者差不多，视具体情况去选择使用；当数据量大的时候，如果只需要在靠前的部分插入或删除数据，那也可以选用LinkedList，
     *  反之选择ArrayList反而效率更高。
     */

    static List<Integer> array=new ArrayList<Integer>();
    static List<Integer> linked=new LinkedList<Integer>();

    public static void main(String[] args) {

        //首先分别给两者插入10000条数据
        for(int i=0;i<10000;i++){
            array.add(i);
            linked.add(i);
        }
        //获得两者随机访问的时间
        System.out.println("array time:"+getTime(array));
        System.out.println("linked time:"+getTime(linked));
        //获得两者插入数据的时间
        System.out.println("array insert time:"+insertTime(array));
        System.out.println("linked insert time:"+insertTime(linked));

    }
    public static long getTime(List<Integer> list){
        long time=System.currentTimeMillis();
        for(int i = 0; i < 10000; i++){
            int index = Collections.binarySearch(list, list.get(i));
            if(index != i){
                System.out.println("ERROR!");
            }
        }
        return System.currentTimeMillis()-time;
    }

    //插入数据
    public static long insertTime(List<Integer> list){
        /*
         * 插入的数据量和插入的位置是决定两者性能的主要方面，
         * 我们可以通过修改这两个数据，来测试两者的性能
         */
        long num = 10000; //表示要插入的数据量
        int index = 1000; //表示从哪个位置插入
        long time=System.currentTimeMillis();
        for(int i = 1; i < num; i++){
            list.add(index, i);
        }
        return System.currentTimeMillis()-time;

    }



}
