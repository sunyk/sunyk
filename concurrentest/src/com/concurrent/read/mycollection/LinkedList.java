package com.concurrent.read.mycollection;

/**
 * created on sunyang 2018/7/25 13:52
 * Are you different!"jia you" for me
 */
public class LinkedList<E> {

    //获得第index个节点的值
    public E get(int index){
        return (E) node(index).item;
    }

    transient int size  = 0;
    transient Node<E> first;
    transient Node<E> last;
    transient int modCount;

    Node<E> node(int index) {
        //如果当index小于size/2时，从头开始招，当index大于等于size时，就从尾部开始找
        if (index < (size >> 1)){
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        }else{
            Node<E> x = last;
            for (int i = size-1; i > index ; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node( Node<E> next, Node<E> prev,E item) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    //设置第index元素的值
    public E set(int index, E element){
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal;
    }

    //在index个节点之前添加新的节点
    public void add(int index, E element){
        //首先检验index是适合的，再进行下面的操作
        if (index == size){
            linkedLast(element);
        }else{
            linkedBefore(element, node(index));
        }
    }

    private void linkedLast(E element) {
        final Node<E> l = last;
        final Node<E> newNode = null;//new Node<>(l, element,null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }

    private void linkedBefore(E element, Node<E> succ) {
        final Node<E> pred = succ.prev;
        final Node<E> newNode = null;//new Node<>(pred, element, succ);
        succ.prev = newNode;
        if(pred == null){
            first = newNode;
        }else{
            pred.next = newNode;
            size++;
            modCount++;
        }
    }


}
