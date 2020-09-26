package com.lg.datastructure.stack;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * 基于数组实现的栈
 *
 * @author Xulg
 * Created in 2020-09-16 10:04
 */
class ArrayStack<T> implements SimpleStack<T> {

    /*
     * 基于数组实现一个栈的数据结构
     */

    private final T[] array;
    private int cursor;
    private int size;

    public ArrayStack(int capacity) {
        if (capacity <= 0) {
            capacity = 16;
        }
        //noinspection unchecked
        this.array = (T[]) new Object[capacity];
    }

    @Override
    public void push(T item) {
        if (size >= array.length) {
            throw new IllegalStateException("栈满了，不给你放了");
        }
        array[cursor] = item;
        cursor++;
        size++;
    }

    @Override
    public T pop() {
        if (size <= 0) {
            throw new IllegalStateException("栈空了，没东西给你了");
        }
        T t = array[--cursor];
        array[cursor] = null;
        size--;
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

    @Nonnull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int idx = cursor;
            private int count = size;

            @Override
            public boolean hasNext() {
                return count > 0;
            }

            @Override
            public T next() {
                count--;
                return array[--idx];
            }
        };
    }

    public static void main(String[] args) {
        ArrayStack<String> stack = new ArrayStack<>(3);
        stack.push("a");
        stack.push("b");
        stack.push("c");

        for (String s : stack) {
            System.out.println(s);
        }

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        stack.push("a");
        stack.push("b");
        stack.push("c");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        stack.push("a");
        System.out.println(stack.pop());

        stack.push("b");
        stack.push("c");
        System.out.println(stack.pop());
    }
}
