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
        // TODO: 2021/1/22 太复杂了
        return isPalindrome2(head);
    }

    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node right = head.next;
        Node cur = head;
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }
        Stack<Node> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 空间复杂度O(1)
     */
    public boolean checkPalindrome3(Node head) {
        // TODO: 2021/1/22 太复杂了
        return isPalindrome3(head);
    }

    // need O(1) extra space
    public static boolean isPalindrome3(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node n1 = head;
        Node n2 = head;
        while (n2.next != null && n2.next.next != null) { // find mid node
            n1 = n1.next; // n1 -> mid
            n2 = n2.next.next; // n2 -> end
        }
        // n1 中点

        n2 = n1.next; // n2 -> right part first node
        n1.next = null; // mid.next -> null
        Node n3 = null;
        while (n2 != null) { // right part convert
            n3 = n2.next; // n3 -> save next node
            n2.next = n1; // next of right node convert
            n1 = n2; // n1 move
            n2 = n3; // n2 move
        }
        n3 = n1; // n3 -> save last node
        n2 = head;// n2 -> left first node
        boolean res = true;
        while (n1 != null && n2 != null) { // check palindrome
            if (n1.value != n2.value) {
                res = false;
                break;
            }
            n1 = n1.next; // left to mid
            n2 = n2.next; // right to mid
        }
        n1 = n3.next;
        n3.next = null;
        while (n1 != null) { // recover list
            n2 = n1.next;
            n1.next = n3;
            n3 = n1;
            n1 = n2;
        }
        return res;
    }

    private static class Node {
        private int value;
        private Node next;
    }
}
