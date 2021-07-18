package com.practice.linkedlist;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-05-11 12:25
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
     * 时间：O(N)
     * 空间：O(N)
     */
    private static class CheckPalindromeByStack {
        public static boolean isPalindrome(Node head) {
            if (head == null || head.next == null) {
                return true;
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

    /**
     * 时间：O(N)
     * 空间：O(1)
     */
    private static class Best {
        public static boolean isPalindrome(Node head) {
            if (head == null || head.next == null) {
                return true;
            }

            /*
             * ①→②→③→②→①
             * a   b   c   d   e
             * length = 5
             *
             * ①→②→②→①
             * a   b   c   d
             * length = 4
             *
             * ①→②→②→①→②→①
             * a   b   c   d   e   f
             * length = 4
             */

            // 计算链表长度，找出中间位置的节点
            int length = 1;
            for (Node node = head.next; node != null; node = node.next) {
                length++;
            }

            // 偶数长度链表
            if (length % 2 == 0) {
                // 链表1
                Node h1 = head;
                Node t1 = head;
                for (int i = 1, mid = length / 2; i < mid; i++) {
                    t1 = t1.next;
                }

                // 链表2
                Node h2 = t1.next;
                t1.next = null;

                // 反转链表2
                Node reverseH2 = reverse(h2);

                // 逐一比较
                boolean isPalindrome = true;
                Node n1 = h1;
                Node n2 = reverseH2;
                while (n1 != t1.next && n2 != null) {
                    if (n1.val != n2.val) {
                        isPalindrome = false;
                        break;
                    }
                    n1 = n1.next;
                    n2 = n2.next;
                }

                // 恢复原链表
                h2 = reverse(reverseH2);
                t1.next = h2;

                // return the check result
                return isPalindrome;
            }
            // 奇数长度链表
            else {
                // 链表1头
                Node h1 = head;
                Node t1 = head;
                for (int i = 1, mid = length / 2; i < mid; i++) {
                    t1 = t1.next;
                }

                // 链表2
                Node h2 = t1.next.next;
                t1.next.next = null;

                // 反转链表2
                Node reverseH2 = reverse(h2);

                // 逐一比较
                boolean isPalindrome = true;
                Node n1 = h1;
                Node n2 = reverseH2;
                while (n1 != t1.next && n2 != null) {
                    if (n1.val != n2.val) {
                        isPalindrome = false;
                        break;
                    }
                    n1 = n1.next;
                    n2 = n2.next;
                }

                // 恢复原链表
                h2 = reverse(reverseH2);
                t1.next.next = h2;

                // return the check result
                return isPalindrome;
            }
        }

        private static Node reverse(Node head) {
            if (head == null || head.next == null) {
                return head;
            }
            Node temp = head;
            Node prev = null, next;
            while (temp != null) {
                next = temp.next;
                temp.next = prev;
                prev = temp;
                temp = next;
            }
            return prev;
        }
    }

    /**
     * 时间：O(N)，常数项小一点
     * 空间：O(1)
     */
    private static class Best2 {
        public static boolean isPalindrome(Node head) {
            if (head == null || head.next == null) {
                return true;
            }

            /*
             * ①→②→③→②→①
             *         S
             *                 F
             *
             * ①→②→③→③→②→①
             *         S
             *                 F
             */

            // 快慢指针一起从head出发移动，慢指针停着的位置就是链表的中间位置或者中上位置
            Node middle = head, faster = head;
            while (faster.next != null && faster.next.next != null) {
                middle = middle.next;
                faster = faster.next.next;
            }

            // 找到中间位置，将链表切分为2部分

            // 链表1部分
            Node head1 = head;

            // 链表2部分
            Node head2 = middle.next;
            middle.next = null;
            Node reverseHead2 = reverse(head2);

            // check
            boolean isPalindrome = true;
            Node n1 = head1, n2 = reverseHead2;
            while (n1 != null && n2 != null) {
                if (n1.val != n2.val) {
                    isPalindrome = false;
                    break;
                }
                n1 = n1.next;
                n2 = n2.next;
            }

            // recover the linked list
            middle.next = reverse(reverseHead2);

            // return the result
            return isPalindrome;
        }

        private static Node reverse(Node head) {
            if (head == null || head.next == null) {
                return head;
            }
            Node prev = null;
            Node next;
            Node temp = head;
            while (temp != null) {
                next = temp.next;
                temp.next = prev;
                prev = temp;
                temp = next;
            }
            return prev;
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
            System.out.println(Best2.isPalindrome(head));
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
            System.out.println(Best2.isPalindrome(head));
            list.clear();
            for (Node node = head; node != null; node = node.next) {
                list.add(node.val);
            }
            System.out.println(StringUtils.join(list, ","));
        }
    }

}
