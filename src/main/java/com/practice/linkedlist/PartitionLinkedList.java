package com.practice.linkedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-05-11 15:03
 */
class PartitionLinkedList {

    /*
     * 将单向链表按照某个值划分成左边小，中间相等，右边大的形式
     * 1.把链表放数组，在数组中进行partition操作(荷兰旗问题)，笔试用用
     * 2.分成小，中，大三部分，再把各个部分之间串起来，面试用用
     */

    private static class PartitionByArray {
        public static Node partition(Node head, int pivot) {
            if (head == null) {
                return head;
            }
            List<Node> list = new ArrayList<>();
            for (Node node = head; node != null; node = node.next) {
                list.add(node);
            }
            int less = -1, more = list.size();
            for (int idx = 0; idx < more; ) {
                Node node = list.get(idx);
                if (node.val > pivot) {
                    swap(list, idx, --more);
                } else if (node.val < pivot) {
                    swap(list, idx++, ++less);
                } else {
                    idx++;
                }
            }
            Node newHead = list.get(0);
            Node tail = newHead;
            for (int idx = 1; idx < list.size(); idx++) {
                tail.next = list.get(idx);
                tail = tail.next;
            }
            // 防止链表循环
            tail.next = null;
            return newHead;
        }

        private static void swap(List<Node> list, int i, int j) {
            Node temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
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
        Node head = new Node(3);
        Node node1 = new Node(6);
        Node node2 = new Node(0);
        Node node3 = new Node(2);
        Node node4 = new Node(3);
        Node node5 = new Node(1);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        if (true) {
            Node newHead = PartitionByArray.partition(head, 3);
            for (Node node = newHead; node != null; node = node.next) {
                System.out.println(node.val);
            }
        } else {
//            Node newHead = partition2(head, 3);
//            for (Node node = newHead; node != null; node = node.next) {
//                System.out.println(node.val);
//            }
        }

    }
}
