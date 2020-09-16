package com.lg.datastructure.stack;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * 基于数组实现的简单的栈
 *
 * @author Xulg
 * Created in 2020-09-16 10:04
 */
class SimpleArrayStack<T> implements SimpleStack<T> {

    /*
     * 基于数组实现一个栈的数据结构
     */

    private T[] array;
    private int size;

    public SimpleArrayStack(int capacity) {
        if (capacity <= 0) {
            capacity = 16;
        }
        //noinspection unchecked
        this.array = (T[]) new Object[capacity];
    }

    @Override
    public void push(T item) {
        if (size >= array.length) {
            // 扩容2倍啊
            resize(2 * array.length);
        }
        // 数据存入数组中，size递增
        array[size++] = item;
    }

    @Override
    public T pop() {
        // 弹出最后进入的数据
        T t = array[--size];
        // 这个位置数据弹出了，需要置为null
        array[size] = null;

        // 使用空间小于总空间的1/4，就缩容1/2
        if (size > 0 && size <= array.length / 4) {
            resize(array.length / 2);
        }

        return t;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize(int maxSize) {
        Object[] ts = new Object[maxSize];
        //noinspection ManualArrayCopy
        for (int i = 0; i < size; i++) {
            ts[i] = array[i];
        }
        //noinspection unchecked
        this.array = (T[]) ts;
    }

    @Nonnull
    @Override
    public Iterator<T> iterator() {
        return new SimpleStackIterator();
    }

    /**
     * 迭代器
     */
    private class SimpleStackIterator implements Iterator<T> {
        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public T next() {
            return array[--size];
        }
    }

    public static void main(String[] args) {
        SimpleStack<String> stack = new SimpleArrayStack<>(3);
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        stack.push("e");
        stack.push("f");

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
