package com.lg.datastructure.linkedlist.mianshiti;

/**
 * 链表分区
 *
 * @author Xulg
 * Created in 2020-10-26 19:58
 */
class PartitionLinkedList {

    /*
     * 将单向链表按照某个值划分成左边小，中间相等，右边大的形式
     * 1.把链表放数组，在数组中进行partition操作(荷兰旗问题)，笔试用用
     * 2.分成小，中，大三部分，再把各个部分之间串起来，面试用用
     */

    /**
     * 链表转数组，用荷兰旗的方法实现
     */
    public static Node partition1(Node head, int value) {
        if (head == null) {
            return null;
        }

        // 链表的长度
        int length = 0;
        for (Node node = head; node != null; node = node.next) {
            length++;
        }

        // 创建一个数组，长度为链表的长度
        Node[] nodes = new Node[length];
        // 将链表存入数组
        int i = 0;
        for (Node node = head; node != null; node = node.next) {
            nodes[i++] = node;
        }

        // 做荷兰旗操作
        int lessThanIdx = -1;
        int moreThanIdx = nodes.length;
        for (int idx = 0; idx < moreThanIdx; ) {
            if (nodes[idx].value < value) {
                // 和小于区的下一个位置交换
                swap(nodes, idx, lessThanIdx + 1);
                // 小于区索引位置递增1
                lessThanIdx++;
                // 判断下一个去
                idx++;
            } else if (nodes[idx].value > value) {
                // 和大于区的前一个位置交换
                swap(nodes, idx, moreThanIdx - 1);
                moreThanIdx--;
                // 这里不能idx++
            } else {
                idx++;
            }
        }

        // 将数组中的节点串成链表
        for (int idx = 0; idx < nodes.length; idx++) {
            if (idx + 1 < nodes.length) {
                nodes[idx].next = nodes[idx + 1];
            } else {
                // 链表的最后一个节点了，next指针指向null，
                // 否则这里会形成循环链表
                nodes[idx].next = null;
            }
        }
        // 返回头节点
        return nodes[0];
    }

    private static void swap(Node[] array, int a, int b) {
        Node temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * 直接在原链表上实现
     * 创建小于区头节点lessThanHeader，尾节点lessThanTail
     * 创建等于区头节点equalsHeader，尾节点equalsTail
     * 创建大于区头节点moreThanHeader，尾节点moreThanTail
     * 1.将链表节点按值的大小分别插入到3个头节点中
     * 2.将小于，等于，大于区3个链表首尾连接
     * 3.返回小于区头节点
     */
    public static Node partition2(Node head, int value) {
        if (head == null) {
            return null;
        }
        // 小于区的头节点
        Node lessThanHeader = null;
        // 小于区的尾节点
        Node lessThanTail = null;
        // 等于区的头节点
        Node equalsHeader = null;
        // 等于区的尾节点
        Node equalsTail = null;
        // 大于区的头节点
        Node moreThanHeader = null;
        // 大于区的尾节点
        Node moreThanTail = null;

        // 遍历链表
        for (Node node = head; node != null; ) {
            if (node.value < value) {
                // 节点插入到小于区链表
                if (lessThanHeader == null) {
                    // 小于区链表头节点不存在，则新插入节点就是头节点
                    lessThanHeader = node;
                    // 尾节点指向新插入节点
                    lessThanTail = node;
                } else {
                    // 插到尾巴后面
                    lessThanTail.next = node;
                    // 尾节点指向新插入节点
                    lessThanTail = node;
                }
            } else if (node.value > value) {
                // 节点插入到大于区链表
                if (moreThanHeader == null) {
                    // 等于区链表头节点不存在，则新插入节点就是头节点
                    moreThanHeader = node;
                    moreThanTail = node;
                } else {
                    // 插到尾巴后面
                    moreThanTail.next = node;
                    // 尾节点指向新插入节点
                    moreThanTail = node;
                }
            } else {
                // 节点插入到等于区链表
                if (equalsHeader == null) {
                    // 大于区链表头节点不存在，则新插入节点就是头节点
                    equalsHeader = node;
                    equalsTail = node;
                } else {
                    // 插到尾巴后面
                    equalsTail.next = node;
                    // 尾节点指向新插入节点
                    equalsTail = node;
                }
            }
            // 被插入的节点需要断开和后面节点的联系
            Node oldNode = node.next;
            node.next = null;
            // 操作下一个节点去
            node = oldNode;
        }

        /* 将小于，等于，大于区3个链表首尾连接 */
        if (lessThanHeader == null) {
            // 小于区不存在啊，等于区直接挂上去
            lessThanHeader = equalsHeader;
            // 如果等于区尾巴存在，则将小于区的尾巴指向等于区尾巴
            lessThanTail = (equalsTail == null) ? lessThanTail : equalsTail;
        } else {
            // 小于区存在，将等于区挂到小于区的尾巴
            lessThanTail.next = equalsHeader;
            // 如果等于区尾巴存在，则将小于区的尾巴指向等于区尾巴
            lessThanTail = (equalsTail == null) ? lessThanTail : equalsTail;
        }
        if (lessThanHeader == null) {
            // 走到这里说明小于区，等于区都不存在啊
            lessThanHeader = moreThanHeader;
            // 如果大于区尾巴存在，则将小于区的尾巴指向等于区尾巴
            lessThanTail = (moreThanTail == null) ? lessThanTail : moreThanTail;
        } else {
            // 把大于区挂上去啊
            lessThanTail.next = moreThanHeader;
            // 如果大于区尾巴存在，则将小于区的尾巴指向等于区尾巴
            lessThanTail = (moreThanTail == null) ? lessThanTail : moreThanTail;
        }

        // 返回小于区头节点
        return lessThanHeader;
    }

    private static class Node {
        private final int value;
        private Node next;

        Node(int value) {
            this.value = value;
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

        if (false) {
            Node newHead = partition1(head, 3);
            for (Node node = newHead; node != null; node = node.next) {
                System.out.println(node.value);
            }
        } else {
            Node newHead = partition2(head, 3);
            for (Node node = newHead; node != null; node = node.next) {
                System.out.println(node.value);
            }
        }

    }
}
