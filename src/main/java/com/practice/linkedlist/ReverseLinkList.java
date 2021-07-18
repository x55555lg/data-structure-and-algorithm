package com.practice.linkedlist;

import com.practice.linkedlist.ReverseLinkList.ReverseDoubleSingleLinkList.DoubleNode;
import com.practice.linkedlist.ReverseLinkList.ReverseSingleLinkList.SingleNode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 反转单向，双向链表
 *
 * @author Xulg
 * Created in 2021-05-11 9:59
 */
class ReverseLinkList {

    static class ReverseSingleLinkList {

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

        static class SingleNode {
            int value;
            SingleNode next;

            SingleNode(int value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return "SingleNode{" + "value=" + value + '}';
            }
        }
    }

    static class ReverseDoubleSingleLinkList {

        public static DoubleNode[] reverse(DoubleNode head, DoubleNode tail) {
            if (head == null && tail == null) {
                return new DoubleNode[]{null, null};
            }
            if (head == tail) {
                return new DoubleNode[]{head, tail};
            }

            // 反转后的链表尾巴
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

        static class DoubleNode {
            int value;
            DoubleNode prev, next;

            DoubleNode(int value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return "SingleNode{" + "value=" + value + '}';
            }
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
                System.out.println(node.value);
            }

            SingleNode reverseHead = ReverseSingleLinkList.reverse(head);

            for (SingleNode node = reverseHead; node != null; node = node.next) {
                System.out.println(node.value);
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
                list.add(node.value);
            }
            System.out.println(StringUtils.join(list, ","));
            list.clear();
            for (DoubleNode node = tail; node != null; node = node.prev) {
                list.add(node.value);
            }
            System.out.println(StringUtils.join(list, ","));

            DoubleNode[] reverse = ReverseDoubleSingleLinkList.reverse(head, tail);
            DoubleNode reverseHead = reverse[0];
            DoubleNode reverseTail = reverse[1];

            list.clear();
            for (DoubleNode node = reverseHead; node != null; node = node.next) {
                list.add(node.value);
            }
            System.out.println(StringUtils.join(list, ","));
            list.clear();
            for (DoubleNode node = reverseTail; node != null; node = node.prev) {
                list.add(node.value);
            }
            System.out.println(StringUtils.join(list, ","));
        }
    }

}
