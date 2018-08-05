package com.concurrent.read.mycollection;


import javax.swing.tree.TreeNode;
import java.util.Map;

/**
 * created on sunyang 2018/7/25 13:52
 * Are you different!"jia you" for me
 */
public class HashMap<K,V> {

    static final Entry<?,?>[] EMPTY_TABLE = {};
    transient int hashSeed = 0;
    transient Entry<K,V>[] table = (Entry<K,V>[]) EMPTY_TABLE;
    transient int modCount;
    transient int size;
    int threshold;

    //HashMap存储数据的过程，put方法,引自JDK1.7
    public V put(K key, V value){
        //先判断如果键为null的话，调用putForNullKey(val)
        if (key == null) return null;
        //先计算hash值
        int hash = hash(key.hashCode());
        //hash 取余 表长度-1
        int i = indexFor(hash,table.length);
        for (Entry<K,V> e = table[i]; e != null; e = e.next){
            Object k;
            if(e.hash == hash && ((k = e.key) == key || key.equals(k))){
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;

            }
        }

        modCount++;
        addEntry(hash, key, value, i);
        return null;
    }

    //最终检验后的操作
    private void addEntry(int hash, K key, V value, int bucketIndex) {
        //扩容操作 2倍
        if ((size >= threshold) && (null != table[bucketIndex])){
            resize(2 * table.length);
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }
        //直接创建
        createEntry(hash,key,value,bucketIndex);

    }

    private void createEntry(int hash, K key, V value, int bucketIndex) {
        /*Entry<K,V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<>(hash,key,value, e);
        size++;*/
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    private void resize(int newCapacity) {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY){//最大阈值
            threshold = Integer.MAX_VALUE;
            return;
        }
        Entry[] newTable = new Entry[newCapacity];
//        transfer(newTable, initHashSeedAsNeeded(newCapacity));
        table = newTable;
        threshold = (int)Math.min(newCapacity * 0.75, MAXIMUM_CAPACITY + 1);
    }

    static class Entry<K,V> implements Map.Entry<K,V>{

        final K key;
        V value;
        Entry<K,V> next;
        int hash;

        Entry(K key, V value, Entry<K, V> next, int hash) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.hash = hash;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public void recordAccess(HashMap<K, V> m) {

        }
    }

    final int hash(Object k) {
       int h =hashSeed;
       h ^= k.hashCode();
       h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    static int indexFor(int h, int length){
        return h & (length-1);
    }

    //HashMap的put方法，引自JDK1.8
//    public V put(K key, V value) {
    /*private V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K,V>[] tab;
        Node<K,V> p;
        int n,i;
        //先判断链表是否存在或大小是否是0，是的话就resize（）.length的长度
        if ((tab = table) == null || (n = tab.length) == 0){
            n = (tab = resize()).length;
        }
        if ((p = tab[i = (n - 1) & hash]) == null){
            tab[i] = newNode(hash, key, value, null);
        }else{
            Node<K,V> e; K k;
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))){//覆盖节点
                e = p;
            }else if (p instanceof TreeNode){//如果是tree结构，直接往树上放节点
                e = ((TreeNode<K,V>)p).putTreeVal(this,tab,hash,key,value);
            }else{//不是的话，就以链表的形式放入节点
                for (int binCount = 0;; ++binCount){
                    if ((e = p.next) == null){
//                        p.next =  newNode(hash,key,value,null);
                        p.next = new Node<>(hash,key,value,null);
                        if (binCount >= 8-1){
                            treeifyBin(tab, hash);
                        }
                        break;
                    }

                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))){
                        break;
                    }
                    p = e;
                }
            }

            if (e != null){
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null){
                    e.value = value;
                }
                afterNodeAccess(e);
                return oldValue;
            }

        }
        ++modCount;
        if (++size > threshold){
            resize();
        }
        afterNodeInsertion(evict);
        return null;
    }*/

    /*private void treeifyBin(Node<K,V>[] tab, int hash) {
        int n, index;
        Node<K,V> e;
        if (tab == null || (n = tab.length) < 64){//如果没有或小于64，重置
            resize();
        }else if ((e = tab[index = (n-1) & hash]) != null){
            TreeNode hd = null,t1 = null;
            do {
                TreeNode<K,V> p = replacementTreeNode(e,null);
                if (t1 == null){
                    hd = p;
                }else{
                    p.prev = t1;
                    t1.next = p;
                }
                t1 = p;
            }while ((e = e.next) != null);

            if ((tab[index] = hd) != null){
                hd.treeify(tab);
            }


        }
    }*/

    /*新建 和 扩容
    final Node<K,V>[] resize() {
        //链表
        Node<K,V>[] oldTab = table;
        //如果是null就赋值为0，否则就是当前链表长度
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        //老的阈值
        int oldThr = threshold;
        //新的容量 ， 新的阈值
        int newCap, newThr = 0;
        //如果老的容量大于0
        if (oldCap > 0) {
            //老的容量 >  最大的容量
            if (oldCap >= MAXIMUM_CAPACITY) {
                //赋值为Integer最大值
                threshold = Integer.MAX_VALUE;
                //返回老的链表
                return oldTab;
            }//如果2倍老的阈值为新的
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold 初始容量处于阈值
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults 零初始阈值
            newCap = DEFAULT_INITIAL_CAPACITY; //16
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY); //12
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        //开始扩容
        @SuppressWarnings({"rawtypes","unchecked"})
        //新建节点链表
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        //赋值新链表
        table = newTab;
        //如果老的链表不是null
        if (oldTab != null) {
            //开始循环老的容量
            for (int j = 0; j < oldCap; ++j) {
                //定义节点
                Node<K,V> e;
                //如果老的链表的节点不等于null
                if ((e = oldTab[j]) != null) {
                    //把当前老的链表的节点赋值null，清空
                    oldTab[j] = null;
                    //如果当前老的链表的节点下一个为null
                    if (e.next == null)
                        //新的链表位置计算出来 赋值当前节点，通过hash & 新的容量 8 -1
                        newTab[e.hash & (newCap - 1)] = e;
                        //如果当前节点是树节点
                    else if (e instanceof TreeNode)
                        //切割当前，新的链表，当前值，老的容量
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        //定义头节点，尾节点
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            //如果当前节点的hash & 老的容量 == 0
                            if ((e.hash & oldCap) == 0) {
                                //如果尾节点为null
                                if (loTail == null)
                                    //当前节点为头节点
                                    loHead = e;
                                else //尾节点的下一个节点
                                    loTail.next = e;
                                loTail = e;//尾节点设置
                            }
                            else {//新追加的节点
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {//如果尾部 不等于 null
                            loTail.next = null;//尾部节点的下一个 赋值null清空
                            newTab[j] = loHead;//新表的下标 赋值 头部节点
                        }
                        //如果尾部不等于 null
                        if (hiTail != null) {
                            将尾部节点下一个清空
                            hiTail.next = null;
                            //向表中添加头节点
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
     */



    /*
    final void treeify(Node<K,V>[] tab) {
            TreeNode<K,V> root = null;
            //构造红黑树开始
            for (TreeNode<K,V> x = this, next; x != null; x = next) {
                //获取下一个节点
                next = (TreeNode<K,V>)x.next;
                //初始化左节点和右节点
                x.left = x.right = null;
                //如果根节点为null
                if (root == null) {
                //赋值当前节点父节点为null
                    x.parent = null;
                    //设置当前颜色red为false，为黑
                    x.red = false;
                    //把当前的节点设置为根节点
                    root = x;
                }
                else {//如果存在根节点
                    K k = x.key;//拿到当前key
                    int h = x.hash;//拿到当前hash
                    Class<?> kc = null;
                    //循环根节点
                    for (TreeNode<K,V> p = root;;) {
                        //定义目录，当前的hash
                        int dir, ph;
                        //赋值当前key
                        K pk = p.key;
                        //当前的hash 大于 父的hash，则目录为-1
                        if ((ph = p.hash) > h)
                            dir = -1;
                        else if (ph < h) //如果当前的hash 小于 父的hash，则目录为1
                            dir = 1;
                        else if ((kc == null &&
                                  (kc = comparableClassFor(k)) == null) ||
                                 (dir = compareComparables(kc, k, pk)) == 0)
                            dir = tieBreakOrder(k, pk);
                            //新的树节点，赋值当前的树节点
                        TreeNode<K,V> xp = p;
                        //如果当前节点为左节点或右节点为null
                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
                        //
                            x.parent = xp;
                            //如果目录小于等0为左节点，如果目录大于等于1为右节点
                            if (dir <= 0)
                                xp.left = x;
                            else
                                xp.right = x;
                            root = balanceInsertion(root, x);
                            break;
                        }
                    }
                }
            }
            moveRootToFront(tab, root);
        }
     */

    /*平衡插入
    static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,TreeNode<K,V> x) {
            //默认红树
            x.red = true;
            //循环树的节点
            for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
                //当前节点赋值传进来的节点的父节点为null的话，就是黑色
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                else if (!xp.red || (xpp = xp.parent) == null)
                    return root;
                if (xp == (xppl = xpp.left)) {
                    if ((xppr = xpp.right) != null && xppr.red) {
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.right) {
                            root = rotateLeft(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                }
                else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }
     */


    private class Node<K, V> implements Map.Entry<K,V> {
        Node<K,V> next;
        final K key;
        V value;
       final int hash;

        public Node( int hash, K k, V v,Node<K, V> next) {
            this.next = next;
            this.key = k;
            this.value = v;
            this.hash = hash;
        }


        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }
}
