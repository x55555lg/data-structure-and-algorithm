package com.lg.datastructure.stack;

/**
 * 自己实现的简单栈
 *
 * @author Xulg
 * Created in 2020-09-16 12:34
 */
public interface SimpleStack<Item> extends Iterable<Item> {

    /**
     * 入栈
     *
     * @param item the item
     */
    void push(Item item);

    /**
     * 出栈
     *
     * @return the item
     */
    Item pop();

    /**
     * 栈是否空的
     *
     * @return is the stack empty
     */
    boolean isEmpty();

    /**
     * 栈的大小
     *
     * @return the size of the stack
     */
    int size();
}
