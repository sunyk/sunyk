package com.pattern.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Create by sunyang on 2018/6/30 19:55
 * For me:One handred lines of code every day,make myself stronger.
 */
public class MyListTest {


    public static void main(String[] args) {
        List<Long> numbers = new ArrayList<>();
        Collections.sort(numbers, new Comparator<Long>() {
            @Override
            //返回值是固定的
            //-1,0,1
            public int compare(Long o1, Long o2) {
                return 0;
            }
        });
    }
}
