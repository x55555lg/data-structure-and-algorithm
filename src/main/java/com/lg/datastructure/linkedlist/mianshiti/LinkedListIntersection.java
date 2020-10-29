package com.lg.datastructure.linkedlist.mianshiti;

import java.util.HashSet;

/**
 * 求两个单向链表(链表可能有环可能没有)的交点
 *
 * @author Xulg
 * Created in 2020-10-27 19:00
 */
@SuppressWarnings("DuplicatedCode")
class LinkedListIntersection {

    /*
     * 给定两个可能有环也可能没环的单链表，头节点为header1和header2。
     * 请实现一个函数：
     *  如果两个链表相交，请返回相交的第一个节点；
     *  如果不想交，请返回null
     * 要求：
     *      如果两个链表的长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)
     */

    /**
     * 获取两个(可能有环也可能没环)链表的交点
     */
    public static Node getIntersection(Node header1, Node header2) {
        if (header1 == null || header2 == null) {
            return null;
        }

        // 分别获取两个链表的入环节点
        Node loopNode1 = isLoopLinkedList(header1);
        Node loopNode2 = isLoopLinkedList(header2);

        // 判断两个链表的入环状况
        if (loopNode1 != null && loopNode2 != null) {
            /*
             * 两个链表都是循环链表，可能会有交点
             *-----------------------------------
             * 情形1：链表1，链表2都有各自的入环点
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
             */
            return getBothLoopIntersection(header1, loopNode1, header2, loopNode2);
        } else if (loopNode1 == null && loopNode2 == null) {
            /*
             * 两个链表都不是循环链表，可能会有交点
             * 只会有一种情形，两个链表会有公共部分
             *-----------------------------------
             * 链表1
             * ○→○→○→○→●→○→○
             *                 ↑
             * 链表2   ○→○→○
             *
             * ●就是交点
             *-----------------------------------
             */
            // 简单好实现但是使用额外空间
            //return getBothNotLoopIntersectionByHashSet(header1, header2);
            // 实现较复杂，时间复杂度O(N)，空间复杂度O(1)
            //return getBothNotLoopIntersectionByCalc1(header1, header2);
            // 实现较复杂，时间复杂度O(N)，空间复杂度O(1)，但是时间复杂度上量级更小点
            return getBothNotLoopIntersectionByCalc2(header1, header2);
        } else {
            /*
             * 其中一个链表是循环链表，绝对不可能出现交点
             *-----------------------------------
             * 链表1
             * ○→○→●→○→○
             * 	       ↑	   ↓
             * 	       ○←○←○
             * 链表2
             * ○→○→○→○→○
             *-----------------------------------
             */
            return null;
        }
    }

    /**
     * 求两个有环链表的第一个交点
     *
     * @param header1   链表1
     * @param loopNode1 链表1的入环节点
     * @param header2   链表2
     * @param loopNode2 链表2的入环节点
     * @return 两个有环链表的交点
     */
    private static Node getBothLoopIntersection(Node header1, Node loopNode1,
                                                Node header2, Node loopNode2) {
        if (header1 == null || header2 == null) {
            return null;
        }
        if (loopNode1 == loopNode2) {
            /*
             *情形1
             *        链表2
             *         ○
             * 链表1   ↓loop1, loop2
             * ○→○→●←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             *-------------------------
             *情形2
             *            链表2
             *             ○
             * 链表1       ↓  loop1, loop2
             * ○→○→○→◎→●←○←○
             * 	               ↓	   ↑
             * 	               ○→○→○
             *=========================
             * 解决思路：将公共部分环节点去掉，变成了求2个无环链表的交点
             *        链表2'
             *         ○
             * 链表1'  ↓
             * ○→○→●loop
             *-------------------------
             *            链表2'
             *             ○
             * 链表1'      ↓
             * ○→○→○→◎→●loop
             */
            // 求出链表1'和链表2'的长度差
            // 这里只会求每个链表从头节点到入环节点的长度
            Node currentNode1 = header1;
            Node currentNode2 = header2;
            int diffCount = 0;
            while (currentNode1.next != loopNode1) {
                diffCount++;
                currentNode1 = currentNode1.next;
            }
            while (currentNode2.next != loopNode2) {
                diffCount--;
                currentNode2 = currentNode2.next;
            }

            // 根据差值判断哪个链表长，哪个短
            Node longer = diffCount > 0 ? header1 : header2;
            Node shorter = longer == header1 ? header2 : header1;

            // 让长链表先走diffCount步
            for (int diff = Math.abs(diffCount); diff != 0; ) {
                longer = longer.next;
                diff--;
            }

            // 同时遍历链表，直到入环点
            while (longer != loopNode1 && shorter != loopNode2) {
                if (longer == shorter) {
                    // 交点是在入环点之前
                    return longer;
                } else {
                    // 继续走到下一个节点
                    longer = longer.next;
                    shorter = shorter.next;
                }
            }
            // 入环点就是交点
            return loopNode1;
        } else {
            /*
             * 链表1                  链表2
             *         loop1    loop2
             * ○→○→●←○←●←○←○←○←○
             * 	       ↓	   ↑
             * 	       ○→○→○
             *对链表1来说，loop2是交点
             *对链表2来说，loop1是交点
             */
            // Why?
            Node currentNode1 = loopNode1.next;
            while (currentNode1 != loopNode1) {
                if (currentNode1 == loopNode2) {
                    return loopNode1;
                }
                currentNode1 = currentNode1.next;
            }
            return null;
        }
    }

    /**
     * 使用hashset集合找两个无环链表的交点
     * 时间复杂度O(N)
     * 需要额外的空间复杂度
     *
     * @param header1 the linked list
     * @param header2 the linked list
     * @return the intersection node if exist
     */
    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    private static Node getBothNotLoopIntersectionByHashSet(Node header1, Node header2) {
        // 使用HashSet实现
        HashSet<Node> nodes = new HashSet<>();
        // 遍历链表1所有节点并加入set集合
        for (Node node = header1; node != null; node = node.next) {
            nodes.add(node);
        }
        // 遍历链表2，如果遍历的时候发现节点在set集合中已经存在，说明这个点就是第一个交点
        for (Node node = header2; node != null; node = node.next) {
            if (nodes.contains(node)) {
                return node;
            }
        }
        return null;
    }

    /**
     * 求两个(都不是循环链表)链表的交点
     * 时间复杂度O(N)
     * 额外空间复杂度O(1)
     *
     * @param header1 the linked list
     * @param header2 the linked list
     * @return the intersection node if exist
     */
    private static Node getBothNotLoopIntersectionByCalc1(Node header1, Node header2) {
        if (header1 == null || header2 == null) {
            return null;
        }

        int length1 = 0;
        for (Node node = header1; node != null; node = node.next) {
            length1++;
        }
        int length2 = 0;
        for (Node node = header2; node != null; node = node.next) {
            length2++;
        }

        /*
         * 初始状况
         * 链表1
         * ①
         * ↓
         * ②
         * ↓
         * ③
         * ↓
         * ④
         * ↓
         * ⑤←②←①链表2
         * ↓
         * ⑥
         * ↓
         * ⑦
         * length1 = 7
         * length2 = 5
         */

        // 判断哪个链表长，那个短
        Node longer;
        Node shorter;
        if (length1 > length2) {
            longer = header1;
            shorter = header2;
        } else {
            longer = header2;
            shorter = header1;
        }

        // 长链表先走(length1-length2)步
        for (int count = Math.abs(length1 - length2); count != 0; count--) {
            longer = longer.next;
        }

        /*
         * 长链表处理之后
         * ①
         * ↓
         * ②
         * ↓
         * ③←longer
         * ↓
         * ④
         * ↓
         * ⑤←②←①
         * ↓      ↑shorter
         * ⑥
         * ↓
         * ⑦
         */

        // 这个时候，两个链表等长了，开始同时往下遍历
        for (int remainCount = Math.min(length1, length2); remainCount != 0; remainCount--) {
            if (longer == shorter) {
                // 说明两个链表遍历到了同一个节点了啊，这里就是交点了啊
                return longer;
            } else {
                // 两个链表继续往下走
                longer = longer.next;
                shorter = shorter.next;
            }
        }
        // 没有交点啊
        return null;
    }

    /**
     * 求两个(都不是循环链表)链表的交点
     * 视频中的写法，时间复杂度O(N)，但是更优
     *
     * @param header1 the linked list
     * @param header2 the linked list
     * @return the intersection node if exist
     */
    private static Node getBothNotLoopIntersectionByCalc2(Node header1, Node header2) {
        if (header1 == null || header2 == null) {
            return null;
        }
        // 计算两个链表长度差值
        Node currentNode1 = header1;
        Node currentNode2 = header2;
        // 两个链表长度的差值，可能为正，可能为负
        int diffCount = 0;
        while (currentNode1.next != null) {
            currentNode1 = currentNode1.next;
            diffCount++;
        }
        while (currentNode2.next != null) {
            currentNode2 = currentNode2.next;
            diffCount--;
        }

        // 链表1和链表2的尾节点如果不是同一个节点，那么两个链表一定是没有交点的
        // 因为如果两个无环链表相交，一定有公共部分节点
        if (currentNode1 != currentNode2) {
            return null;
        }

        // 根据两链表的差值的正负判断哪个链表长哪个链表短
        Node longer = (diffCount > 0) ? header1 : header2;
        Node shorter = (longer == header1) ? header2 : header1;

        // 长链表先走diffCount步，保证长链表和短链表的起点一致
        for (int diff = Math.abs(diffCount); diff != 0; diff--) {
            // 不可能为null
            assert longer != null;
            longer = longer.next;
        }

        // 这个时候，两个链表等长了，开始同时往下遍历
        while (longer != null && shorter != null) {
            if (longer == shorter) {
                // 说明两个链表遍历到了同一个节点了啊，这里就是交点了啊
                return longer;
            } else {
                // 两个链表继续往下走
                longer = longer.next;
                shorter = shorter.next;
            }
        }
        // 没有交点啊
        return null;
    }

    /**
     * 使用快慢指针判断是否是循环链表
     * 如果是循环链表，则返回第一个入环节点
     * 否则返回null
     */
    @SuppressWarnings({"DuplicatedCode"})
    private static Node isLoopLinkedList(Node header) {
        if (header == null || header.next == null || header.next.next == null) {
            // 链表为null，链表只有一个节点，链表只有两个节点，都不可能是循环链表
            return null;
        }

        // 慢指针，步长为1
        Node slow = header.next;
        // 快指针，步长为2
        Node faster = header.next.next;

        /*
         *		               ○←○
         * 		               ↓  ↑
         * ○→○→○→○→○→○→○
         *      s  f
         * 循环之后
         * ●为快慢指针的相遇点
         *		               ●←○
         * 		               ↓  ↑
         * ○→○→○→○→○→○→○
         */

        // 只要有环，快慢指针一定会相遇的
        while (slow != faster) {
            if (faster.next == null || faster.next.next == null) {
                // 快指针已经走完了，肯定没有环
                return null;
            }
            slow = slow.next;
            faster = faster.next.next;
        }

        /*
         *                      s
         *		               ●←○
         * 		               ↓  ↑
         * ○→○→○→○→○→○→○
         * f
         * 1.快指针回到header起点，慢指针还在相遇点
         * 2.快慢指针一起移动，步长为1
         * 3.若快慢指针相遇了，那么相遇点就是入环点
         *		               ●←○
         * 		               ↓  ↑
         * ○→○→○→○→○→★→○
         *                     sf
         * ★就是入环节点
         */

        // 保持慢指针的位置不动，快指针从header重新开始
        faster = header;
        // 快慢指针一起移动，步长为1
        // 如果快指针和慢指针相遇了，那么相遇点就是入环点
        while (faster != slow) {
            faster = faster.next;
            slow = slow.next;
        }
        // 返回相遇点
        return slow;
    }

    /* ************************************************************************************************************** */

    private static class Node {
        int value;
        Node next;

        Node(int val) {
            value = val;
        }
    }

    public static void main(String[] args) {
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
        System.out.println(getIntersection(head1, head2).value);

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
        System.out.println(getIntersection(head1, head2).value);

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
        System.out.println(getIntersection(head1, head2).value);

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

            System.out.println(getIntersection(header1, header2).value);
        }
    }
}
