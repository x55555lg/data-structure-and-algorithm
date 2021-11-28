package com.practice2.linkedlist;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xulg
 * @since 2021-10-20 16:44
 * Description: 反转链表
 */
class ReverseLinkedList {

    private static class ReverseSingleLinkList {
        public static SingleNode reverse(SingleNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            SingleNode prev = null;
            SingleNode next;
            while (head != null) {
                next = head.next;
                head.next = prev;
                prev = head;
                head = next;
            }
            return prev;
        }
    }

    private static class ReverseDoubleLinkList {
        public static DoubleNode[] reverse(DoubleNode head, DoubleNode tail) {
            if (head == null && tail == null) {
                return new DoubleNode[]{null, null};
            }
            DoubleNode newTail = head;
            DoubleNode prev = null;
            DoubleNode next;
            while (head != null) {
                next = head.next;
                head.next = prev;
                head.prev = next;
                prev = head;
                head = next;
            }
            return new DoubleNode[]{prev, newTail};
        }
    }

    private static class SingleNode {
        int val;
        SingleNode next;

        SingleNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    private static class DoubleNode {
        int val;
        DoubleNode prev, next;

        DoubleNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }


    public static void main(String[] args) {
        {
            SingleNode head = new SingleNode(1);
            SingleNode n2 = new SingleNode(2);
            SingleNode n3 = new SingleNode(3);
            SingleNode n4 = new SingleNode(4);
            SingleNode n5 = new SingleNode(5);
            head.next = n2;
            n2.next = n3;
            n3.next = n4;
            n4.next = n5;
            n5.next = null;

            for (SingleNode node = head; node != null; node = node.next) {
                System.out.println(node.val);
            }

            SingleNode reverseHead = ReverseSingleLinkList.reverse(head);

            for (SingleNode node = reverseHead; node != null; node = node.next) {
                System.out.println(node.val);
            }
        }

        {
            DoubleNode head = new DoubleNode(1);
            DoubleNode n2 = new DoubleNode(2);
            DoubleNode n3 = new DoubleNode(3);
            DoubleNode n4 = new DoubleNode(4);
            DoubleNode tail = new DoubleNode(5);
            head.next = n2;
            n2.next = n3;
            n3.next = n4;
            n4.next = tail;
            tail.next = null;
            tail.prev = n4;
            n4.prev = n3;
            n3.prev = n2;
            n2.prev = head;
            head.prev = null;

            List<Integer> list = new ArrayList<>();
            for (DoubleNode node = head; node != null; node = node.next) {
                list.add(node.val);
            }
            System.out.println(StringUtils.join(list, ","));
            list.clear();
            for (DoubleNode node = tail; node != null; node = node.prev) {
                list.add(node.val);
            }
            System.out.println(StringUtils.join(list, ","));

            DoubleNode[] reverse = ReverseDoubleLinkList.reverse(head, tail);
            DoubleNode reverseHead = reverse[0];
            DoubleNode reverseTail = reverse[1];

            list.clear();
            for (DoubleNode node = reverseHead; node != null; node = node.next) {
                list.add(node.val);
            }
            System.out.println(StringUtils.join(list, ","));
            list.clear();
            for (DoubleNode node = reverseTail; node != null; node = node.prev) {
                list.add(node.val);
            }
            System.out.println(StringUtils.join(list, ","));
        }
    }

}
