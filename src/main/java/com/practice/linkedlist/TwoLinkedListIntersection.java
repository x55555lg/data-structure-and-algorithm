package com.practice.linkedlist;

/**
 * @author Xulg
 * Created in 2021-05-11 15:01
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

    private static class Best {
        public static Node getIntersection(Node head1, Node head2) {
            if (head1 == null || head2 == null) {
                return null;
            }
            // 两个链表是否有环
            Node circleNode1 = getCircleNode(head1);
            Node circleNode2 = getCircleNode(head2);
            if (circleNode1 == null && circleNode2 == null) {
                /*
                 * 两个链表都没有环
                 * 两个链表一定有公共部分才会出现交点
                 * A链表
                 * ○→○→○→○→●→○→○
                 * 		           ↑
                 * 		           ○
                 * 		           ↑
                 * 		           ○
                 *                 B链表
                 */
                return bothNotCircle(head1, head2);
            } else if (circleNode1 != null && circleNode2 != null) {
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
                return bothCircle(head1, head2, circleNode1, circleNode2);
            } else {
                /*
                 *一个链表都有环，一个没环，不会有交点
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

        private static Node getCircleNode(Node head) {
            if (head == null || head.next == null || head.next.next == null) {
                return null;
            }
            Node slow = head.next;
            Node fast = head.next.next;
            while (slow != fast) {
                if (fast.next == null || fast.next.next == null) {
                    return null;
                }
                slow = slow.next;
                fast = fast.next.next;
            }
            fast = head;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }
            return slow;
        }

        private static Node bothNotCircle(Node head1, Node head2) {
            /*
             * 两个链表一定有公共部分才会出现交点
             * A链表
             * ○→○→○→○→●→○→○
             * 		           ↑
             * 		           ○
             * 		           ↑
             * 		           ○
             *                 B链表
             */
            int length1 = 1;
            for (Node node = head1.next; node != null; node = node.next) {
                length1++;
            }
            int length2 = 1;
            for (Node node = head2.next; node != null; node = node.next) {
                length2++;
            }

            // 计算长短链表是哪个
            Node longer = length1 > length2 ? head1 : head2;
            Node shorter = longer == head1 ? head2 : head1;
            // 长链表先走length1 - length2步
            int diff = Math.abs(length1 - length2);
            for (int i = 0; i < diff; i++) {
                longer = longer.next;
            }

            // 长短链表一起移动，相遇点就是交点
            while (longer != shorter) {
                longer = longer.next;
                shorter = shorter.next;
            }
            return longer;
        }

        private static Node bothCircle(Node head1, Node head2, Node circleNode1, Node circleNode2) {
            // 两个链表都是循环链表，可能会有交点，可能没有交点，有如下4种情形
            if (circleNode1 == circleNode2) {
                /*
                 *两个链表有相等的入环点
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
                // 计算2个链表的差值
                int diff = 0;
                for (Node node = head1; node != circleNode1; node = node.next) {
                    diff++;
                }
                for (Node node = head2; node != circleNode2; node = node.next) {
                    diff--;
                }
                // 长短链表
                Node longer = diff > 0 ? head1 : head2;
                Node shorter = longer == head1 ? head2 : head1;
                // 长链表先移动
                for (int i = 0; i < Math.abs(diff); i++) {
                    longer = longer.next;
                }
                // 长短链表一起移动
                while (longer != shorter) {
                    longer = longer.next;
                    shorter = shorter.next;
                }
                return longer;
            } else {
                /*
                 *两个链表有不同的入环点
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
                Node node = circleNode1.next;
                while (node != circleNode1) {
                    if (node == circleNode2) {
                        // 两个链表有公共的环
                        return circleNode1;
                    }
                    node = node.next;
                }
                // 两个链表没有公共的环，没有交点
                return null;
            }
        }
    }

    private static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
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
            System.out.println(Best.getIntersection(head1, head2).val);

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
            System.out.println(Best.getIntersection(head1, head2).val);

            // 0->9->8->6->4->5->6..
            head2 = new Node(0);
            head2.next = new Node(9);
            head2.next.next = new Node(8);
            head2.next.next.next = head1.next.next.next.next.next; // 8->6
            System.out.println(Best.getIntersection(head1, head2).val);
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
        System.out.println(Best.getIntersection(head1, head2).val);

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
        System.out.println(Best.getIntersection(head1, head2).val);

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
        System.out.println(Best.getIntersection(head1, head2).val);

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

            System.out.println(Best.getIntersection(header1, header2).val);
        }
    }
}
