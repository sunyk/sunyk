package com.concurrent.read.myquestion;

import sun.reflect.Reflection;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

/**
 * created on sunyang 2018/7/19 17:20
 * Are you different!"jia you" for me
 */
public class Unsafe {
    /*
    无锁的概念
    无锁的执行者-CAS
    CAS
    CPU指令对CAS的支持
    鲜为人知的指针 Unsafe类
    并发包中的原子操作类Atomic系列
    原子更新基本类型
    原子更新引用
    原子更新数组
    原子更新属性
    CAS的ABA问题及其解决方案
    再谈自旋锁
     */

    /**
     * 无锁的概念
     * 加锁是一种悲观策略，无锁是一种乐观策略。
     * 因为对于加锁的并发程序来说，他们总是认为每次访问共享资源时总会发生冲突，因此必须对每一次数据操作实施加锁策略。而无锁则总是假设对共享资源的访问没有冲突，线程可以不停
     * 执行，无需加锁，无需等待，一旦发现冲突，无锁策略则采用一种称为CAS的技术来保证线程执行的安全性。
     *
     * 无锁的执行者-CAS
     * 全称是compare and swap 即比较交换，其算法核心思想如下：
     * 执行函数：CAS(V,E,N)
     * V表示要更新的变量  E 表示预期值 N表示新值
     * 分V值等于E值，则V的值设为N值。
     *
     * CPU指令对CAS的支持
     * CAS是一条CPU的原子指令，不会造成所谓的数据不一致问题。
     *
     * 鲜为人知的指针：Unsafe类
     * Unsafe类存在于sun.misc包中，其内部方法操作可以像C的指针一样直接操作内存，单从名称看来就可以知道该类是非安全的，
     * 因为Java中CAS操作的执行依赖于Unsafe类的方法，注意Unsafe类中的所有方法都是native修饰的
     * 并发包中的原子操作类(Atomic系列) 1.5引入
     * 通过前面的分析我们已基本理解了无锁CAS的原理并对Java中的指针类Unsafe类有了比较全面的认识，下面进一步分析CAS在Java中的应用，即并发包中的原子操作类(Atomic系列)，
     * 从JDK 1.5开始提供了java.util.concurrent.atomic包，在该包中提供了许多基于CAS实现的原子操作类，用法方便，性能高效，主要分以下4种类型。
     * 原子更新基本类型
     * 原子更新基本类型主要包括3个类：
     * AtomicBoolean：原子更新布尔类型
     * AtomicInteger：原子更新整型
     * AtomicLong：原子更新长整型
     *
     * CAS的ABA问题及其解决方案
     * AtomicStampedReference类确实解决了ABA的问题，下面我们简单看看其内部实现原理
     * public class AtomicStampedReference<V> {
     *     //通过Pair内部类存储数据和时间戳
     *     private static class Pair<T> {
     *         final T reference;
     *         final int stamp;
     *         private Pair(T reference, int stamp) {
     *             this.reference = reference;
     *             this.stamp = stamp;
     *         }
     *         static <T> Pair<T> of(T reference, int stamp) {
     *             return new Pair<T>(reference, stamp);
     *         }
     *     }
     *     //存储数值和时间的内部类
     *     private volatile Pair<V> pair;
     *
     *     //构造器，创建时需传入初始值和时间初始值
     *     public AtomicStampedReference(V initialRef, int initialStamp) {
     *         pair = Pair.of(initialRef, initialStamp);
     *     }
     * }
     *
     * compareAndSet方法的实现：
     * public boolean compareAndSet(V   expectedReference,
     *                                  V   newReference,
     *                                  int expectedStamp,
     *                                  int newStamp) {
     *         Pair<V> current = pair;
     *         return
     *             expectedReference == current.reference &&
     *             expectedStamp == current.stamp &&
     *             ((newReference == current.reference &&
     *               newStamp == current.stamp) ||
     *              casPair(current, Pair.of(newReference, newStamp)));
     *     }
     *  清晰AtomicStampedReference的内部实现思想了，通过一个键值对Pair存储数据和时间戳，
     *  在更新时对数据和时间戳进行比较，只有两者都符合预期才会调用Unsafe的compareAndSwapObject方法执行数值和时间戳替换，也就避免了ABA的问题。
     *
     * 再谈自旋锁
     * 自旋锁是一种假设在不久将来，当前的线程可以获得锁，因此虚拟机会让当前想要获取锁的线程做几个空循环(这也是称为自旋的原因)，在经过若干次循环后，如果得到锁，就顺利进入临界区。
     * 如果还不能获得锁，那就会将线程在操作系统层面挂起，这种方式确实也是可以提升效率的。但问题是当线程越来越多竞争很激烈时，占用CPU的时间变长会导致性能急剧下降，
     * 因此Java虚拟机内部一般对于自旋锁有一定的次数限制，可能是50或者100次循环后就放弃，直接挂起线程，让出CPU资源。如下通过AtomicReference可实现简单的自旋锁。
     * 使用CAS原子操作作为底层实现，lock()方法将要更新的值设置为当前线程，并将预期值设置为null。unlock()函数将要更新的值设置为null，并预期值设置为当前线程。然后我们通过lock()和unlock来控制自旋锁的开启与关闭，注意这是一种非公平锁。
     * 事实上AtomicInteger(或者AtomicLong)原子类内部的CAS操作也是通过不断的自循环(while循环)实现，不过这种循环的结束条件是线程成功更新对于的值，但也是自旋锁的一种。
     */

    public static void maintest(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        //通过field得到该field对应的具体对象，传入null是因为该field为static的
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) field.get(null);
        System.out.println(unsafe);

        //通过allocateInstance直接创建对象
        User user = (User) unsafe.allocateInstance(User.class);

        Class userClass = user.getClass();
       Field name = userClass.getDeclaredField("name");
       Field age = userClass.getDeclaredField("age");
       Field id = userClass.getDeclaredField("id");
       //获取实例变量name和age在对象内存中的偏移量并设置值
       unsafe.putInt(user, unsafe.objectFieldOffset(age), 18);
       unsafe.putObject(user, unsafe.objectFieldOffset(name), "sunyk");
       //这里返回USER.CLASS
        Object staticBase = unsafe.staticFieldBase(id);
        System.out.println("staticBase  " + staticBase);
        //获取静态变量id的偏移量staticoffset
        long staticOffset = unsafe.staticFieldOffset(userClass.getDeclaredField("id"));
        //104
        System.out.println(staticOffset);
        //设置前的ID:USER_ID
        System.out.println("设置前的ID:"+unsafe.getObject(staticBase,staticOffset));

        //设置值
        unsafe.putObject(staticBase, staticOffset, "sssssssss");
        System.out.println("设置前的ID："+unsafe.getObject(staticBase,staticOffset));
        System.out.println("输出USER:" + user.toString());
        long data = 1000;
        byte size = 1;
        //调用allocateMemory分配内存，并获取内存地址memoryAddress
        long memoryAddress = unsafe.allocateMemory(size);
        //直接写入内存数据
        unsafe.putAddress(memoryAddress, data);
        //获取指定内存地址的数据
        long addrData = unsafe.getAddress(memoryAddress);
        System.out.println("addrData:" + addrData);
        /**
         * sun.misc.Unsafe@74a14482
         * staticBase  class com.concurrent.read.myquestion.Unsafe$User
         * 104
         * 设置前的ID:USER_ID
         * 设置前的ID：sssssssss
         * 输出USER:User{name='sunyk', age=18, String_id='sssssssss'}
         * addrData:1000
         */

    }

    public static AtomicReference<User> atomicUserRef = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("sunyk", 18);
        atomicUserRef.set(user);
        System.out.println(atomicUserRef.get().toString());
        User updateUser = new User("sunykyk", 25);
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get().toString());


    }

    /*public static sun.misc.Unsafe getUnsafe(){
        Class clazz = Reflection.getCallerClass(2);
        if (clazz.getClassLoader() != null)
            throw new SecurityException("Unsafe");
        return theUnsafe;
    }*/


    static class  User{
        public User(){
            System.out.println("user 构造方法被调用");
        }
        private String name;
        private int age;
        private static String id = "USER_ID";

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", String_id='" + id + '\'' +
                    '}';
        }
    }


}
