package com.pattern.prototype.deep;

/**
 * Create by sunyang on 2018/7/3 0:30
 * For me:One handred lines of code every day,make myself stronger.
 */
public class TestPrototype {

    public static void main(String[] args) throws CloneNotSupportedException {
//        大圣本尊生日是：1530550123783
//        六耳猕猴的生日是:1530550123803
//        大圣本尊和六耳猕猴是否为同一个对象：false
//        大圣本尊持有金箍棒和六耳猕猴持有金箍棒是否为同一个对象false
        TheGreatestSage sage = new TheGreatestSage();
        sage.change();

        /**
         * 大圣本尊生日是：1530549061797
         * 六耳猕猴的生日是:1530549061797
         * 大圣本尊和六耳猕猴是否为同一个对象：false
         * 大圣本尊持有金箍棒和六耳猕猴持有金箍棒是否为同一个对象true
         *
         * 同用一根金箍棒，和西游记剧情不一致，是由于浅克隆导致
         */

    }
}
