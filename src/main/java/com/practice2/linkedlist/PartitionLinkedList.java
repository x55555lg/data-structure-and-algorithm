package com.practice2.linkedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * @since 2021-10-24 21:39
 * Description: 链表分区
 */
class PartitionLinkedList {

    /*
     * 将单向链表按照某个值划分成左边小，中间相等，右边大的形式
     * 1.把链表放数组，在数组中进行partition操作(荷兰旗问题)，笔试用用
     * 2.分成小，中，大三部分，再把各个部分之间串起来，面试用用
     */

    private static class PartitionByArray {
        public static Node partition(Node head, int pivot) {
            if (head == null || head.next == null) {
                return head;
            }
            List<Node> list = new ArrayList<>();
            for (Node node = head; node != null; node = node.next) {
                list.add(node);
            }
            int less = -1, more = list.size() + 1;
            for (int idx = 0; idx < more; ) {
                if (list.get(idx).val > pivot) {
                    swap(list, idx, --more);
                } else if (list.get(idx).val < pivot) {
                    swap(list, idx++, ++less);
                } else {
                    idx++;
                }
            }
            Node tail = list.get(0);
            for (int idx = 1; idx < list.size(); idx++) {
                tail.next = list.get(idx);
                tail = tail.next;
            }
            // 避免循环链表
            list.get(list.size() - 1).next = null;
            return list.get(0);
        }

        private static void swap(List<Node> list, int i, int j) {
            Node temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }

    private static class PartitionBy {
        public static Node partition(Node head, int pivot) {
            if (head == null || head.next == null) {
                return head;
            }
            Node lessHead = null;
            Node lessTail = null;
            Node equalHead = null;
            Node equalTail = null;
            Node moreHead = null;
            Node moreTail = null;
            for (Node node = head; node != null; ) {
                if (node.val > pivot) {
                    if (moreHead == null) {
                        moreHead = node;
                        moreTail = node;
                    } else {
                        moreTail.next = node;
                        moreTail = node;
                    }
                } else if (node.val < pivot) {
                    if (lessHead == null) {
                        lessHead = node;
                        lessTail = node;
                    } else {
                        lessTail.next = node;
                        lessTail = node;
                    }
                } else {
                    if (equalHead == null) {
                        equalHead = node;
                        equalTail = node;
                    } else {
                        equalTail.next = node;
                        equalTail = node;
                    }
                }
                Node next = node.next;
                node.next = null;
                node = next;
            }
            // 拼接起来：小 -> 等 -> 大
            if (lessHead == null) {
                if (equalHead == null) {
                    if (moreHead == null) {
                        // 小于区不存在，等于区不存在，大于区不存在
                        return null;
                    } else {
                        // 小于区不存在，等于区不存在，大于区存在
                        lessHead = moreHead;
                        lessTail = moreTail;
                    }
                } else {
                    if (moreHead == null) {
                        // 小于区不存在，等于区存在，大于区不存在
                        lessHead = equalHead;
                        lessTail = equalTail;
                    } else {
                        // 小于区不存在，等于区存在，大于区存在
                        lessHead = equalHead;
                        lessTail = equalTail;
                        lessTail.next = moreHead;
                        lessTail = moreTail;
                    }
                }
            } else {
                if (equalHead == null) {
                    if (moreHead == null) {
                        // 小于区存在，等于区不存在，大于区不存在
                        return lessHead;
                    } else {
                        // 小于区存在，等于区不存在，大于区存在
                        lessTail.next = moreHead;
                        lessTail = moreTail;
                    }
                } else {
                    if (moreHead == null) {
                        // 小于区存在，等于区存在，大于区不存在
                        lessTail.next = equalHead;
                        lessTail = equalTail;
                    } else {
                        // 小于区存在，等于区存在，大于区存在
                        lessTail.next = equalHead;
                        lessTail = equalTail;
                        lessTail.next = moreHead;
                        lessTail = moreTail;
                    }
                }
            }
            return lessHead;
        }
    }

    private static class Node {
        int val;
        Node next;

        Node(int val) { this.val = val; }
    }
}
