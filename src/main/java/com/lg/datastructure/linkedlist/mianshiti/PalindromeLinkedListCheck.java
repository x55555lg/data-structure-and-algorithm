package com.lg.datastructure.linkedlist.mianshiti;

import java.util.Stack;

/**
 * 回文链表
 *
 * @author Xulg
 * Created in 2020-10-26 19:27
 */
class PalindromeLinkedListCheck {

    /*
     * 给定一个单链表的头节点head，请判断该链表是否是回文结构？
     *  回文链表：①→②→③→②→①
     * 1.使用栈结构很简单，笔试用
     * 2.改原链表的方法需要注意边界，面试用
     */

    /**
     * 空间复杂度O(N)
     */
    public boolean checkPalindromeByStack1(Node head) {
        if (head == null) {
            return false;
        }
        Stack<Node> stack = new Stack<>();

        // 将链表节点加入栈中
        // 这样操作的话栈中存放的顺序
        // 和实际链表的顺序刚好是相反的
        for (Node n = head; n != null; n = n.next) {
            stack.push(n);
        }

        // 遍历栈和链表
        for (Node n = head; n != null; n = n.next) {
            if (n.value != stack.pop().value) {
                return false;
            }
        }
        return true;
    }

    /**
     * 空间复杂度O(N/2)
     */
    public boolean checkPalindromeByStack2(Node head) {
        return false;
    }

    /**
     * 空间复杂度O(1)
     */
    public boolean checkPalindrome3(Node head) {
        return false;
    }

    private static class Node {
        private int value;
        private Node next;
    }
}
