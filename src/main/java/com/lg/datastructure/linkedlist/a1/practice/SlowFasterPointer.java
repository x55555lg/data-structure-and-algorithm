package com.lg.datastructure.linkedlist.a1.practice;

import java.util.ArrayList;

/**
 * 快慢指针在链表的练习
 *
 * @author Xulg
 * Created in 2021-01-25 9:42
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
     *  returnPreUpMidNode()
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     */

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
     */
    public static Node returnMidOrUpMidNode(Node header) {
        if (header == null || header.next == null || header.next.next == null) {
            // 0个节点，1个节点，2个节点的情况下，上中点都是header节点
            return header;
        }

        /*
         * 慢指针S，步长为1；快指针F，步长为2
         * 当快指针走完的时候，慢指针所在位置就是上中点
         *
         *
         * 奇数长度的链表
         * ○    ○    ○    ○    ○    ○    ○
         *       S	   F
         * 		       S		   F
         * 			         S			        F
         *
         * 偶数长度的链表
         * ○    ○    ○    ○    ○    ○    ○    ○
         * 	     S	   F
         * 		       S		   F
         * 			         S			       F
         */

        // 走到这里链表起码都是3个节点以上(>=)的链表

        // 慢指针，步长为1
        Node slowPointer = header.next;
        // 快指针，步长为2
        Node fastPointer = header.next.next;

        // 快慢指针一起移动，直到快指针越界结束
        while (fastPointer.next != null && fastPointer.next.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        // 此时的慢指针所在位置就是上中点
        return slowPointer;
    }

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
     */
    public static Node returnMidOrDownMidNode(Node header) {
        if (header == null || header.next == null) {
            // 0个节点，1个节点，直接返回header节点即为下中点
            return header;
        }
        if (header.next.next == null || header.next.next.next == null) {
            // 2个节点，3个节点，返回header的next节点即为下中点
            return header.next;
        }

        /*
         * 慢指针S，步长为1；快指针F，步长为2
         * 当快指针走完的时候，慢指针所在位置就是下中点
         *
         *
         * 奇数长度的链表
         * ○    ○    ○    ○    ○    ○    ○
         *       SF
         *             S     F
         *                   S            F
         *
         * 偶数长度的链表
         * ○    ○    ○    ○    ○    ○    ○    ○
         *       SF
         *             S     F
         *                   S            F
         *                         S                  F
         */

        // 走到这里链表起码都是4个节点以上(>=)的链表

        // 慢指针，步长为1
        Node slowPointer = header.next;
        // 快指针，步长为2
        Node fastPointer = header.next;

        // 快慢指针一起移动，直到快指针越界结束
        while (fastPointer.next != null && fastPointer.next.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        // 此时的慢指针所在位置就是下中点
        return slowPointer;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
     */
    public static Node returnPreUpMidNode(Node header) {
        if (header == null || header.next == null || header.next.next == null) {
            // 0个节点，1个节点，2个节点情况下，无论是奇数还是偶数，中点或上中点前一个都是null
            return null;
        }
        if (header.next.next.next == null || header.next.next.next.next == null) {
            // 3个节点，4个节点情况下，中点前一个就是header节点
            return header;
        }

        /*
         * 慢指针S，步长为1；快指针F，步长为2
         * 当快指针走完的时候，慢指针所在位置就是中点或上中点前一个
         *
         *
         * 奇数长度的链表
         * ○    ○    ○    ○    ○    ○    ○
         * S           F
         *       S                 F
         *             S                       F
         *
         * 偶数长度的链表
         * ○    ○    ○    ○    ○    ○    ○    ○
         * S           F
         *       S                 F
         *             S                       F
         */

        // 走到这里链表起码都是5个节点以上(>=)的链表

        // 慢指针，步长为1
        Node slowPointer = header;
        // 快指针，步长为2
        Node fastPointer = header.next.next;

        // 快慢指针一起移动，直到快指针越界结束
        while (fastPointer.next != null && fastPointer.next.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        return slowPointer;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     */
    public static Node returnPostUpMidNode(Node header) {
        if (header == null || header.next == null) {
            // 0个节点，1个节点
            return null;
        }
        if (header.next.next == null || header.next.next.next == null) {
            // 2个节点，3个节点情况下，header就是中点或下中点的前一个
            return header;
        }

        /*
         * 慢指针S，步长为1；快指针F，步长为2
         * 当快指针走完的时候，慢指针所在位置就是中点或下中点前一个
         *
         *
         * 奇数长度的链表
         * ○    ○    ○    ○    ○    ○    ○
         * S     F
         *       S           F
         *             S                 F
         *
         * 偶数长度的链表
         * ○    ○    ○    ○    ○    ○    ○    ○
         * S     F
         *       S           F
         *             S                 F
         *                   S                       F
         */

        // 走到这里链表起码都是4个节点以上(>=)的链表

        // 慢指针，步长为1
        Node slowPointer = header;
        // 快指针，步长为2
        Node fastPointer = header.next;

        // 快慢指针一起移动，直到快指针越界结束
        while (fastPointer.next != null && fastPointer.next.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        // 此时的慢指针所在位置就是中点或下中点前一个
        return slowPointer;
    }

    /* ****************************************************************************************************************/

    /* 对数器方法 */

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
     */
    public static Node right1(Node head) {
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
     * 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
     */
    public static Node right2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size() / 2);
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
     */
    public static Node right3(Node head) {
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
     * 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     */
    public static Node right4(Node head) {
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

    /* ****************************************************************************************************************/

    private static class Node {
        int value;
        Node next;

        Node(int val) {
            this.value = val;
        }
    }

    /* ****************************************************************************************************************/

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
        ans2 = right1(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = returnMidOrDownMidNode(test);
        ans2 = right2(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = returnPreUpMidNode(test);
        ans2 = right3(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = returnPostUpMidNode(test);
        ans2 = right4(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");
    }
}
