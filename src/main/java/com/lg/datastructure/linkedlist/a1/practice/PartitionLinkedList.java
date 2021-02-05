package com.lg.datastructure.linkedlist.a1.practice;

/**
 * 链表分区
 *
 * @author Xulg
 * Created in 2021-01-23 20:58
 */
class PartitionLinkedList {

    /*
     * 将单向链表按照某个值划分成左边小，中间相等，右边大的形式
     * 1.把链表放数组，在数组中进行partition操作(荷兰旗问题)，笔试用用
     * 2.分成小，中，大三部分，再把各个部分之间串起来，面试用用
     */

    /**
     * 链表分区
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     */
    public static Node partition(Node header, int num) {
        if (header == null || header.next == null) {
            return header;
        }

        // 链表长度
        int length = 0;
        for (Node node = header; node != null; node = node.next) {
            length++;
        }

        // 链表复制到数组中
        Node[] helper = new Node[length];
        int i = 0;
        for (Node node = header; node != null; node = node.next) {
            helper[i++] = node;
        }

        // 分区操作
        doPartition(helper, num);

        // 分区后的数组拼接成链表
        for (int idx = 0; idx < helper.length; idx++) {
            if (idx < helper.length - 1) {
                // 当前节点指向下一个
                helper[idx].next = helper[idx + 1];
            } else {
                // 链表的最后一个节点了，next指针指向null，
                // 否则这里会形成循环链表
                helper[idx].next = null;
            }
        }

        // 返回新链表的头节点
        return helper[0];
    }

    /**
     * 荷兰旗解法
     */
    @SuppressWarnings("UnusedReturnValue")
    private static int[] doPartition(Node[] helper, int num) {
        // 等于区范围[lessThanZoneIdx+1, greaterThanZoneIdx-1]
        int lessThanZoneIdx = -1;
        int greaterThanZoneIdx = helper.length;
        for (int idx = 0; idx < greaterThanZoneIdx; ) {
            int val = helper[idx].value;
            if (val > num) {
                swap(helper, idx, --greaterThanZoneIdx);
            } else if (val < num) {
                swap(helper, idx++, ++lessThanZoneIdx);
            } else {
                idx++;
            }
        }
        if (lessThanZoneIdx + 1 > greaterThanZoneIdx - 1) {
            // 没有等于区
            return new int[]{-1, -1};
        } else {
            return new int[]{lessThanZoneIdx + 1, greaterThanZoneIdx - 1};
        }
    }

    private static void swap(Node[] helper, int i, int j) {
        Node temp = helper[i];
        helper[i] = helper[j];
        helper[j] = temp;
    }

    /* ****************************************************************************************************************/

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

}
