package com.pattern.comparator;

/**
 * Create by sunyang on 2018/7/2 23:22
 * For me:One handred lines of code every day,make myself stronger.
 */
public class NumberComparator implements Comparator {
    @Override
    public int compareTo(Object obj1, Object obj2) {
        return obj1 == obj2 ? 0 : 1;
    }
}
