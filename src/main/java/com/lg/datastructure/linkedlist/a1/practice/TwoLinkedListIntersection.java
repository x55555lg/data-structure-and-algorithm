package com.lg.datastructure.linkedlist.a1.practice;

/**
 * 求两个链表的第一个交点，没有则返回null
 *
 * @author Xulg
 * Created in 2021-01-22 11:28
 */
class TwoLinkedListIntersection {

    /*
     * 给定两个可能有环也可能没环的单链表，头节点为header1和header2。
     * 请实现一个函数：
     *  如果两个链表相交，请返回相交的第一个节点；
     *  如果不想交，请返回null
     * 要求：
     *      如果两个链表的长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)
     */

    /**
     * 求两个链表的交点，链表都有可能是有环，也可能是无环
     *
     * @param header1 the linked list header
     * @param header2 the linked list header
     * @return the intersection of the linked list
     */
    public static Node getTwoLinkedListIntersection(Node header1, Node header2) {
        if (header1 == null || header2 == null) {
            return null;
        }
        // 判断2个链表是否有环
        Node loopNode1 = getLoopNode(header1);
        Node loopNode2 = getLoopNode(header2);

        if (loopNode1 == null && loopNode2 == null) {
            /*
             * 2个链表都是无环的，只有1种情况下会有交点，两个链表有公共部分
             * A链表
             * ○→○→○→○→●→○→○
             * 		           ↑
             * 		           ○
             * 		           ↑
             * 		           ○
             *                 B链表
             */
            return bothNotLoop(header1, header2);
        } else if (loopNode1 != null && loopNode2 != null) {
            /*
             * 两个链表都是循环链表，可能会有交点，可能没有交点，有如下4种情形
             *-----------------------------------
             * 情形1：链表1，链表2都有各自的入环点，而且有公共环，这种情况两个入环点都是交点
             * 链表1                  链表2
             *         loop1    loop2
             * ○→○→●←○←●←○←○←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             * loop1 != loop2
             * ●即是入环点又是交点
             *-----------------------------------
             * 情形2：链表1，链表2是同一个入环点，入环点就是交点
             *        链表2
             *         ○
             * 链表1   ↓loop1, loop2
             * ○→○→●←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             * loop1 == loop2
             * ●即是入环点又是交点
             *-----------------------------------
             * 情形3：链表1，链表2是同一个入环点，入环点不是交点
             *            链表2
             *             ○
             * 链表1       ↓  loop1, loop2
             * ○→○→○→◎→●←○←○
             * 	               ↓	   ↑
             * 	               ○→○→○
             * loop1 == loop2
             * ◎是交点
             *-----------------------------------
             * 情形4：链表1，链表2各自独立。没有交点
             * 链表1
             * ○→○→●←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             * 链表2
             * ○→○→●←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             */
            return bothLoop(header1, header2, loopNode1, loopNode2);
        } else {
            /*
             * 有1个链表是有环的，不会出现交点
             * 	           ○←○
             * 	           ↓  ↑
             * ○→○→○→○→○
             * A链表
             *
             * ○→○→○
             * B链表
             */
            return null;
        }
    }

    private static Node bothNotLoop(Node header1, Node header2) {
        /*
         * 2个链表都是无环的，只有1种情况下会有交点
         * A链表
         * ○→○→○→○→●→○→○
         * 		           ↑
         * 		           ○
         * 		           ↑
         * 		           ○
         *                 B链表
         */

        /* 如果没有空间复杂度的要求，可以使用Set集合来简单实现 */

        if (header1 == null || header2 == null) {
            return null;
        }

        // 计算链表1的长度
        int length1 = 0;
        for (Node node = header1; node != null; node = node.next) {
            length1++;
        }
        // 计算链表2的长度
        int length2 = 0;
        for (Node node = header2; node != null; node = node.next) {
            length2++;
        }
        /* 上面求两个链表的长度可以优化成求两个链表的长度差 */

        // 指定哪个是长链表，哪个是短链表
        Node longer = (length1 > length2) ? header1 : header2;
        Node shorter = (longer == header1) ? header2 : header1;

        // 长链表先走(length2-length1)步
        for (int diff = Math.abs(length2 - length1); diff > 0; diff--) {
            longer = longer.next;
        }

        // 现在长链表和短链表的长度一样了，开始遍历看哪个节点相等
        while (longer != null && shorter != null) {
            if (longer == shorter) {
                // 说明两个链表遍历到了同一个节点了啊，这里就是交点了啊
                return longer;
            }
            // 两个链表继续往下走
            longer = longer.next;
            shorter = shorter.next;
        }

        // 没有交点啊
        return null;
    }

    private static Node bothLoop(Node header1, Node header2, Node loopNode1, Node loopNode2) {
        if (header1 == null || header2 == null) {
            return null;
        }

        /*
         * 两个链表都是循环链表，可能会有交点，可能没有交点，有如下4种情形
         *-----------------------------------
         * 情形1：链表1，链表2都有各自的入环点，而且有公共环，这种情况两个入环点都是交点
         * 链表1                  链表2
         *         loop1    loop2
         * ○→○→●←○←●←○←○←○←○
         * 	       ↓	   ↑
         * 	       ○→○→○
         * loop1 != loop2
         * ●即是入环点又是交点
         *-----------------------------------
         * 情形2：链表1，链表2是同一个入环点，入环点就是交点
         *        链表2
         *         ○
         * 链表1   ↓loop1, loop2
         * ○→○→●←○←○
         * 	       ↓	   ↑
         * 	       ○→○→○
         * loop1 == loop2
         * ●即是入环点又是交点
         *-----------------------------------
         * 情形3：链表1，链表2是同一个入环点，入环点不是交点
         *            链表2
         *             ○
         * 链表1       ↓  loop1, loop2
         * ○→○→○→◎→●←○←○
         * 	               ↓	   ↑
         * 	               ○→○→○
         * loop1 == loop2
         * ◎是交点
         *-----------------------------------
         * 情形4：链表1，链表2各自独立。没有交点
         * 链表1
         * ○→○→●←○←○
         * 	       ↓	   ↑
         * 	       ○→○→○
         * 链表2
         * ○→○→●←○←○
         * 	       ↓	   ↑
         * 	       ○→○→○
         */

        if (loopNode1 == loopNode2) {
            /*
             * 情形2：链表1，链表2是同一个入环点，入环点就是交点
             *        链表2
             *         ○
             * 链表1   ↓loop1, loop2
             * ○→○→●←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             * loop1 == loop2
             * ●即是入环点又是交点
             *-----------------------------------
             * 情形3：链表1，链表2是同一个入环点，入环点不是交点
             *            链表2
             *             ○
             * 链表1       ↓  loop1, loop2
             * ○→○→○→◎→●←○←○
             * 	               ↓	   ↑
             * 	               ○→○→○
             * loop1 == loop2
             * ◎是交点
             *-----------------------------------
             */

            // 链表1[header, loopNode)，链表2[header, loopNode)求交点，做法和bothNotLoop()中类似

            // 求两链表差值
            Node curNode1 = header1;
            int diffCount = 0;
            while (curNode1.next != loopNode1) {
                curNode1 = curNode1.next;
                diffCount++;
            }
            Node curNode2 = header2;
            while (curNode2.next != loopNode2) {
                curNode2 = curNode2.next;
                diffCount--;
            }

            // 根据差值判断哪个是长链，哪个是短链
            Node longer = (diffCount > 0) ? header1 : header2;
            Node shorter = (longer == header1) ? header2 : header1;

            // 长链表走diffCount步，保证长短链表的起点一致
            for (int i = Math.abs(diffCount); i > 0; i--) {
                longer = longer.next;
            }
            while (longer != loopNode1 && shorter != loopNode2 && longer != shorter) {
                longer = longer.next;
                shorter = shorter.next;
            }
            return longer;
        } else {
            /*
             * 情形1：链表1，链表2都有各自的入环点，而且有公共环，这种情况两个入环点都是交点
             * 链表1                  链表2
             *         loop1    loop2
             * ○→○→●←○←●←○←○←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             * loop1 != loop2
             * ●即是入环点又是交点
             *-----------------------------------
             * 情形4：链表1，链表2各自独立。没有交点
             * 链表1
             * ○→○→●←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             * 链表2
             * ○→○→●←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             */

            // 从loopNode1出发开始遍历链表1，如果能遇到loopNode2，说明两个链表有公共环，那么loopNode1，loopNode2都是交点
            // 否则的话，两个链表就是独立的，没有交点。

            // 从loopNode1开始遍历环部分
            Node current = loopNode1;
            while (true) {
                if (current == loopNode2) {
                    // 遇到了另一个入环点，说明2个链表有公共的环，2个入环点都是交点
                    return loopNode1;
                } else {
                    // 判断下一个节点位置
                    current = current.next;
                }
                if (current == loopNode1) {
                    // 又回到了起点，没有遇到过另一个入环点，说明2个链表没有公共的环
                    return null;
                }
            }
        }
    }

    /**
     * 获取链表的入环点，没有则返回null
     *
     * @param header the linked list header
     * @return the loop node
     */
    private static Node getLoopNode(Node header) {
        if (header == null || header.next == null || header.next.next == null) {
            // 0个节点，1个节点，2个节点的链表是不会形成环的
            return null;
        }
        // 使用快慢指针
        Node slow = header.next;
        Node fast = header.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                // 无环链表
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = header;
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    private static class Node {
        int value;
        Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        {
            // 1->2->3->4->5->6->7->null
            Node head1 = new Node(1);
            head1.next = new Node(2);
            head1.next.next = new Node(3);
            head1.next.next.next = new Node(4);
            head1.next.next.next.next = new Node(5);
            head1.next.next.next.next.next = new Node(6);
            head1.next.next.next.next.next.next = new Node(7);

            // 0->9->8->6->7->null
            Node head2 = new Node(0);
            head2.next = new Node(9);
            head2.next.next = new Node(8);
            head2.next.next.next = head1.next.next.next.next.next; // 8->6
            System.out.println(getTwoLinkedListIntersection(head1, head2).value);

            // 1->2->3->4->5->6->7->4...
            head1 = new Node(1);
            head1.next = new Node(2);
            head1.next.next = new Node(3);
            head1.next.next.next = new Node(4);
            head1.next.next.next.next = new Node(5);
            head1.next.next.next.next.next = new Node(6);
            head1.next.next.next.next.next.next = new Node(7);
            head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

            // 0->9->8->2...
            head2 = new Node(0);
            head2.next = new Node(9);
            head2.next.next = new Node(8);
            head2.next.next.next = head1.next; // 8->2
            System.out.println(getTwoLinkedListIntersection(head1, head2).value);

            // 0->9->8->6->4->5->6..
            head2 = new Node(0);
            head2.next = new Node(9);
            head2.next.next = new Node(8);
            head2.next.next.next = head1.next.next.next.next.next; // 8->6
            System.out.println(getTwoLinkedListIntersection(head1, head2).value);
        }

        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        // 8->6
        head2.next.next.next = head1.next.next.next.next.next;
        /*
         * 无环
         *                     〇(0)
         *                     ↓
         *                     ⑨
         *                     ↓
         *                     ⑧
         *                     ↓
         * ①→②→③→④→⑤→⑥→⑦→null
         */
        System.out.println(getTwoLinkedListIntersection(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        // 7->4
        head1.next.next.next.next.next.next = head1.next.next.next;

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        // 8->2
        head2.next.next.next = head1.next;
        /*
         *都有环，入环节点相等
         *     〇(0)
         *     ↓
         *     ⑨
         *     ↓
         *     ⑧      ⑦←⑥
         *     ↓      ↓  ↑
         * ①→②→③→④→⑤
         *             loop
         */
        System.out.println(getTwoLinkedListIntersection(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        // 8->6
        head2.next.next.next = head1.next.next.next.next.next;
        /*
         *都有环，入环节点不等
         *                 loop2
         *                 ⑥←⑧←⑨←〇(0)
         *               ↙↑
         * ①→②→③→④→⑤
         *             loop1
         */
        System.out.println(getTwoLinkedListIntersection(head1, head2).value);

        {
            /*  0   1  2    7  6   11  10   9   8
             * ○→○→●←○←●←○←○←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             *         3   4   5
             */
            Node node0 = new Node(0);
            Node node1 = new Node(1);
            Node node2 = new Node(2);
            Node node3 = new Node(3);
            Node node4 = new Node(4);
            Node node5 = new Node(5);
            Node node6 = new Node(6);
            Node node7 = new Node(7);
            Node node8 = new Node(8);
            Node node9 = new Node(9);
            Node node10 = new Node(10);
            Node node11 = new Node(11);

            Node header1 = node0;
            node0.next = node1;
            node1.next = node2;
            node2.next = node3;
            node3.next = node4;
            node4.next = node5;
            node5.next = node6;
            node6.next = node7;
            node7.next = node2;

            Node header2 = node8;
            node8.next = node9;
            node9.next = node10;
            node10.next = node11;
            node11.next = node6;

            System.out.println(getTwoLinkedListIntersection(header1, header2).value);
        }
    }

}
