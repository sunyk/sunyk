package com.concurrent.read.mycollection;

import java.util.Map;

/**
 * created on sunyang 2018/7/25 13:53
 * Are you different!"jia you" for me
 */
public class ConcurrentHashMap {

    private class Node<K,V> implements Map.Entry<K,V>{

        final int hash;
        final K k;
        volatile V v;
        volatile Node<K,V> next;

        public Node(int hash, K k, V v, Node<K, V> next) {
            this.hash = hash;
            this.k = k;
            this.v = v;
            this.next = next;
        }



        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public V setValue(V value) {
            V oldValue = v;
            oldValue = value;
            return oldValue;
        }
    }
}
