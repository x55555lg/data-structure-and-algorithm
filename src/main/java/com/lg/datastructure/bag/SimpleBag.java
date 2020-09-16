package com.lg.datastructure.bag;

/**
 * 简单包结构实现
 * 包和栈一样是先进后出的
 *
 * @author Xulg
 * Created in 2020-09-16 16:37
 */
public interface SimpleBag<Item> extends Iterable<Item> {

    /**
     * 加入包中
     *
     * @param item the item
     */
    void add(Item item);

    /**
     * 包是否空的
     *
     * @return is the stack empty
     */
    boolean isEmpty();

    /**
     * 包的大小
     *
     * @return the size of the stack
     */
    int size();
}
