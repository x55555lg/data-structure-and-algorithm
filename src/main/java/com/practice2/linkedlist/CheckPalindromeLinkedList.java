package com.practice2.linkedlist;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Xulg
 * @since 2021-10-20 15:35
 */
class CheckPalindromeLinkedList {

    /*
     * 给定一个单链表的头节点head，请判断该链表是否是回文结构？
     *  回文链表：①→②→③→②→①
     * 1.使用栈结构很简单，笔试用
     * 2.改原链表的方法需要注意边界，面试用
     *      找到中间节点，将后半段链表反转，然后前半段，后半段链表进行比较
     *      恢复原链表
     */

    /**
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     */
    private static class CheckPalindromeByStack {
        public static boolean isPalindrome(Node head) {
            if (head == null || head.next == null) {
                return false;
            }
            Stack<Node> stack = new Stack<>();
            for (Node node = head; node != null; node = node.next) {
                stack.add(node);
            }
            for (Node node = head; node != null; node = node.next) {
                if (node.val != stack.pop().val) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class Node {
        int val;
        Node next;

        Node(int value) {
            this.val = value;
        }
    }

    public static void main(String[] args) {
        {
            Node head = new Node(1);
            Node n2 = new Node(2);
            Node n3 = new Node(3);
            Node n4 = new Node(4);
            Node n5 = new Node(5);
            head.next = n2;
            n2.next = n3;
            n3.next = n4;
            n4.next = n5;
            n5.next = null;

            List<Integer> list = new ArrayList<>();
            for (Node node = head; node != null; node = node.next) {
                list.add(node.val);
            }
            System.out.println(StringUtils.join(list, ","));
            System.out.println(CheckPalindromeByStack.isPalindrome(head));
            list.clear();
            for (Node node = head; node != null; node = node.next) {
                list.add(node.val);
            }
            System.out.println(StringUtils.join(list, ","));
        }

        {
            Node head = new Node(1);
            Node n2 = new Node(2);
            Node n3 = new Node(2);
            Node n4 = new Node(1);
            head.next = n2;
            n2.next = n3;
            n3.next = n4;
            n4.next = null;

            List<Integer> list = new ArrayList<>();
            for (Node node = head; node != null; node = node.next) {
                list.add(node.val);
            }
            System.out.println(StringUtils.join(list, ","));
            System.out.println(CheckPalindromeByStack.isPalindrome(head));
            list.clear();
            for (Node node = head; node != null; node = node.next) {
                list.add(node.val);
            }
            System.out.println(StringUtils.join(list, ","));
        }
    }

}
