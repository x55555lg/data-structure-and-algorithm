package com.lg.datastructure.linkedlist;

/**
 * 简单实现的链表
 *
 * @author Xulg
 * Created in 2020-09-16 11:33
 */
class SimpleLinkedList<Item> {

    /**
     * 链表节点
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }
}
