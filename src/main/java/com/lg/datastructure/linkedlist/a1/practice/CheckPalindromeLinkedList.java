package com.lg.datastructure.linkedlist.a1.practice;

import java.util.Stack;

/**
 * 验证是否是回文链表
 *
 * @author Xulg
 * Created in 2021-01-23 20:48
 */
class CheckPalindromeLinkedList {

    /*
     * 给定一个单链表的头节点head，请判断该链表是否是回文结构？
     *  回文链表：①→②→③→②→①
     * 1.使用栈结构很简单，笔试用
     * 2.改原链表的方法需要注意边界，面试用
     */

    /**
     * 判断是否是回文链表
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     */
    public static boolean isPalindromeByStack(Node header) {
        if (header == null) {
            // 0个节点不是回文链表
            return false;
        }
        if (header.next == null) {
            // 1个节点是回文链表
            return true;
        }
        // 链表存入栈中，这样从栈中弹出时就是逆序的
        Stack<Node> stack = new Stack<>();
        for (Node node = header; node != null; node = node.next) {
            stack.push(node);
        }
        // 同时遍历栈和链表
        for (Node node = header; node != null; node = node.next) {
            if (stack.pop().value != node.value) {
                return false;
            }
        }
        return true;
    }

    private static class Node {
        int value;
        Node next;

        Node(int val) {
            value = val;
        }
    }
}
