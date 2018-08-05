package com.concurrent.read.mycollection;

import java.util.Arrays;

/**
 * created on sunyang 2018/7/25 13:52
 * Are you different!"jia you" for me
 */
public class ArrayLsit<E> {

    transient Object[] elementData;
    protected transient int modCount = 0;
    private int size;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    /**
     * arrayList.get()   set()  add()
     */
    public E get(int index) {
        //rangeCheck(index);//环绕检验
        return (E) elementData[index];
    }

    public E set(int index, E element) {
//        rangeCheck(index);

        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    //新增方法，开始封装方法--------------------------------------START---------------------------------------------
    public void add(int index,E element){
        //先环绕验证最大index和最小index
        ensureCapacityInternal(size + 1); //自增 modCount
        System.arraycopy(elementData, index, elementData, index + 1, size -index);
        elementData[index] = element;
        size++;

    }

    private void ensureCapacityInternal(int minCapacity) {
        modCount ++;
        //如果最小容量 - 元素长度大于0
        if(minCapacity - elementData.length > 0){
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        //获取旧值
        int oldCapacity = elementData.length;
        //扩容创建新
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        //如果新的容量 - 最小容量  《 0
        if (newCapacity - minCapacity < 0){
            newCapacity = minCapacity;
        }
        //如果新的容量 - 最大的数组大小 > 0
        if (newCapacity - MAX_ARRAY_SIZE > 0){
            newCapacity = hugeCapacity(minCapacity);
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    //新增方法，开始封装方法----------------------------------------END---------------------------------------------
}
