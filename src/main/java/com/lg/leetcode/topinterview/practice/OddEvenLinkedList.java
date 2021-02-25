package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-24 17:43
 */
class OddEvenLinkedList {

    /*
     *  Given a singly linked list, group all odd nodes together followed by the even nodes.
     * Please note here we are talking about the node number and not the value in the nodes.
     *  You should try to do it in place. The program should run in O(1) space complexity and
     * O(nodes) time complexity.
     *
     * Example 1:
     * Input: 1->2->3->4->5->NULL
     * Output: 1->3->5->2->4->NULL
     *
     * Example 2:
     * Input: 2->1->3->5->6->4->7->NULL
     * Output: 2->3->6->7->1->5->4->NULL
     *
     * Constraints:
     *  The relative order inside both the even and odd groups should remain as it was in the input.
     *  The first node is considered odd, the second node even and so on ...
     *  The length of the linked list is between [0, 10^4].
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

    /**
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    public static ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            // 0节点，1节点，2节点
            return head;
        }
        // 奇数链表
        ListNode oddHead = head;
        ListNode oddTail = oddHead;
        head = head.next;
        oddHead.next = null;

        // 偶数链表
        ListNode evenHead = head;
        ListNode evenTail = evenHead;
        head = head.next;
        evenHead.next = null;

        // 节点序号，从3开始
        int orderNum = 3;
        ListNode node = head;
        while (node != null) {
            ListNode next = node.next;
            if (orderNum % 2 == 0) {
                // 挂到偶数链表下
                evenTail.next = node;
                // 尾巴后移
                evenTail = node;
            } else {
                // 挂到奇数链表下
                oddTail.next = node;
                // 尾巴后移
                oddTail = node;
            }
            node.next = null;
            // 下一个节点
            node = next;
            orderNum++;
        }

        // 偶数链表挂到奇数链表上
        oddTail.next = evenHead;

        // 返回奇数链表的头
        return oddHead;
    }

    /**
     * 写法更简单的实现
     * 时间复杂度O(N)
     * 空间复杂度O(1)
     */
    private static ListNode solution(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            // 0节点，1节点，2节点
            return head;
        }
        // 奇数链表
        ListNode odd = head;
        ListNode oddHead = odd;
        // 偶数链表
        ListNode even = head.next;
        // 偶数链表的头
        ListNode evenHead = even;

        head = head.next.next;
        while (head != null) {
            // 第一个为奇数节点
            odd.next = head;
            odd = odd.next;
            // 第二个为偶数节点，可能为空
            if (head.next != null) {
                even.next = head.next;
                even = even.next;
            }
            if (head.next == null) {
                break;
            } else {
                head = head.next.next;
            }
        }
        // 拼接奇数偶数链表
        odd.next = evenHead;
        // 放在循环链表
        even.next = null;
        return oddHead;
    }

    private static ListNode solution2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode odd = head;
        ListNode eHead = head.next;
        ListNode even = eHead;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = eHead;
        return head;
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
            ListNode head5 = new ListNode(5);
            head1.next = head2;
            head2.next = head3;
            head3.next = head4;
            head4.next = head5;
            head5.next = null;
            ListNode oddEvenList = oddEvenList(head1);
            System.out.println(oddEvenList);
        }

        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            ListNode head3 = new ListNode(3);
            head1.next = head2;
            head2.next = head3;
            head3.next = null;
            ListNode oddEvenList = oddEvenList(head1);
            System.out.println(oddEvenList);
        }

        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            ListNode head3 = new ListNode(3);
            ListNode head4 = new ListNode(4);
            ListNode head5 = new ListNode(5);
            head1.next = head2;
            head2.next = head3;
            head3.next = head4;
            head4.next = head5;
            head5.next = null;
            ListNode solution = solution(head1);
            System.out.println(solution);
        }
        {
            ListNode head1 = new ListNode(1);
            ListNode head2 = new ListNode(2);
            ListNode head3 = new ListNode(3);
            head1.next = head2;
            head2.next = head3;
            head3.next = null;
            ListNode oddEvenList = solution(head1);
            System.out.println(oddEvenList);
        }
    }
}
