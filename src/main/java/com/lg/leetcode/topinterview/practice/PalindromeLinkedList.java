package com.lg.leetcode.topinterview.practice;

import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-02-24 15:09
 */
class PalindromeLinkedList {

    /*
     * Given a singly linked list, determine if it is a palindrome.
     *
     * Example 1:
     * Input: 1->2
     * Output: false
     *
     * Example 2:
     * Input: 1->2->2->1
     * Output: true
     *
     * Follow up:
     *  Could you do it in O(n) time and O(1) space?
     *
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */

    public static boolean isPalindrome(ListNode head) {
        return false;
    }

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(N)
     */
    private static boolean solution1(ListNode head) {
        if (head == null) {
            return true;
        }
        if (head.next == null) {
            return true;
        }
        Stack<ListNode> stack = new Stack<>();
        for (ListNode node = head; node != null; node = head.next) {
            stack.add(node);
        }
        for (ListNode node = head; node != null; node = head.next) {
            if (node.val != stack.pop().val) {
                return false;
            }
        }
        return true;
    }

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    private static boolean solution2(ListNode head) {
        if (head == null) {
            // 0节点链表
            return true;
        }
        if (head.next == null) {
            // 1节点链表
            return true;
        }

        // 计算链表长度
        int length = 0;
        for (ListNode node = head; node != null; node = node.next) {
            length++;
        }

        // 链表的中间位置的节点，偶数奇数长度要分别处理
        ListNode middle = head;
        int offset = length % 2 == 0 ? 0 : 1;
        int mid = (length - 1) >> 1;
        for (int i = 0; i < mid; i++) {
            middle = middle.next;
        }

        /*
         * 左链表[head, middle]
         * 右链表[middle.next, null]
         * 将链表分割成左右2部分，记录下切割点，方便后续恢复
         */

        // 左链表的头
        ListNode leftHead = head;
        // 左链表的尾巴(不包含)
        ListNode leftTail = middle.next;

        // 右链表的头
        ListNode rightHead = (offset == 0) ? middle.next : middle;
        // 反转右链表
        ListNode reversedRightHead = reverse(rightHead);

        // 左右链表逐一比较
        boolean isPalindrome = true;
        ListNode leftNode = leftHead, rightNode = reversedRightHead;
        while (leftNode != leftTail && rightNode != null) {
            if (leftNode.val != rightNode.val) {
                isPalindrome = false;
                break;
            }
            // next node
            leftNode = leftNode.next;
            rightNode = rightNode.next;
        }

        // recover the origin linked list
        reversedRightHead = reverse(reversedRightHead);
        middle.next = offset == 0 ? reversedRightHead : reversedRightHead.next;

        return isPalindrome;
    }

    private static boolean solution3(ListNode head) {

        // TODO: 2021/2/24 使用快慢指针查找链表中间节点

        return false;
    }

    private static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode next = null;
        ListNode node = head;
        while (node != null) {
            next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "ListNode{" + "val=" + val + '}';
        }
    }

    public static void main(String[] args) {
        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            ListNode head3 = new ListNode(3);
            ListNode head4 = new ListNode(4);
            head1.next = head2;
            head2.next = head3;
            head3.next = head4;
            head4.next = null;
            System.out.println(solution2(head1));
        }

        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            ListNode head3 = new ListNode(3);
            head1.next = head2;
            head2.next = head3;
            head3.next = null;
            System.out.println(solution2(head1));
        }

        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            head1.next = head2;
            head2.next = null;
            System.out.println(solution2(head1));
        }

        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(0);
            ListNode head3 = new ListNode(1);
            head1.next = head2;
            head2.next = head3;
            head3.next = null;
            System.out.println(solution2(head1));
        }
    }

}
