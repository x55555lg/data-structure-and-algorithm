package com.lg.datastructure.linkedlist.mianshiti;

import java.util.ArrayList;

/**
 * 快慢指针
 *
 * @author Xulg
 * Created in 2020-10-26 19:20
 */
class SlowFasterPointer {

    /*
     * 上中点：如果链表长度是偶数，中点位置有2个，返回前一个
     * 下中点：如果链表长度是偶数，中点位置有2个，返回后一个
     *
     * 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
     *  returnMidOrUpMidNode()
     * 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
     *  returnMidOrDownMidNode()
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     */

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
     */
    public static Node returnMidOrUpMidNode(Node header) {
        if (header == null || header.next == null || header.next.next == null) {
            // 头节点不存在，链表只有1个节点，链表只有2个节点
            return header;
        }
        /* 链表有3个点或以上 */
        // 慢指针，步长为1
        Node slow = header.next;
        // 快指针，步长为2
        Node fast = header.next.next;
        // 只要快指针可以继续往下走，慢指针走1步，快指针走2步
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 快指针走完之后，慢指针所在位置就是中点或上中点
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
     */
    public static Node returnMidOrDownMidNode(Node header) {
        if (header == null || header.next == null) {
            // 没节点，一个节点，直接返回头节点
            return header;
        }
        if (header.next.next == null) {
            // 2个节点直接返回最后一个节点
            return header.next;
        }
        /* 链表有3个点或以上 */
        // 慢指针，步长为1
        Node slow = header.next;
        // 快指针，步长为2
        Node fast = header.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 快指针走完之后，慢指针所在位置就是中点或下中点
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
     */
    public static Node returnBeforeMidOrBeforeUpMidNode(Node header) {
        if (header == null) {
            // 没节点，直接返回头节点
            return header;
        }
        if (header.next == null || header.next.next == null) {
            // 1个节点，中点前一个为null
            // 2个节点，上中点前一个为null
            return null;
        }
        /* 链表有3个点或以上 */
        if (true) {
            // 慢指针，步长为1
            Node slow = header;
            // 快指针，步长为2
            Node fast = header;
            // 慢指针的前面节点
            Node beforeSlow = null;
            while (fast.next != null && fast.next.next != null) {
                beforeSlow = slow;
                slow = slow.next;
                fast = fast.next.next;
            }
            return beforeSlow;
        } else {
            // 视频中写法======
            // 慢指针，步长为1
            Node slow = header;
            // 快指针，步长为2
            Node fast = header.next.next;
            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            // 快指针走完之后，慢指针所在位置就是中点前一个或上中点前一个
            return slow;
        }
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     */
    public static Node returnBeforeMidOrBeforeDownMidNode(Node header) {
        if (header == null) {
            // 没节点，直接返回头节点
            return header;
        }
        if (header.next == null) {
            // 1个节点，中点前一个为null
            return null;
        }
        if (header.next.next == null) {
            // 2个节点，下中点前一个就是header
            return header;
        }
        /* 链表有3个点或以上 */
        // 慢指针，步长为1
        Node slow = header;
        // 快指针，步长为2
        Node fast = header.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 快指针走完之后，慢指针所在位置就是中点前一个或下中点前一个
        return slow;
    }

    /**
     * 对数器
     */
    private static Node returnMidOrUpMidNodeComparator(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 1) / 2);
    }

    /**
     * 对数器
     */
    private static Node returnMidOrDownMidNodeComparator(Node header) {
        if (header == null) {
            return null;
        }
        Node cur = header;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size() / 2);
    }

    /**
     * 对数器
     */
    private static Node returnBeforeMidOrBeforeUpMidNodeComparator(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 3) / 2);
    }

    /**
     * 对数器
     */
    private static Node returnBeforeMidOrBeforeDownMidNodeComparator(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 2) / 2);
    }

    /* ************************************************************************************************************** */

    private static class Node {
        private final int value;
        private Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Node test = null;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
        test.next.next.next.next.next.next.next.next = new Node(8);

        Node ans1 = null;
        Node ans2 = null;

        ans1 = returnMidOrUpMidNode(test);
        ans2 = returnMidOrUpMidNodeComparator(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = returnMidOrDownMidNode(test);
        ans2 = returnMidOrDownMidNodeComparator(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = returnBeforeMidOrBeforeUpMidNode(test);
        ans2 = returnBeforeMidOrBeforeUpMidNodeComparator(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = returnBeforeMidOrBeforeDownMidNode(test);
        ans2 = returnBeforeMidOrBeforeDownMidNodeComparator(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

    }
}
