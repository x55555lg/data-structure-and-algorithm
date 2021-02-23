package com.lg.leetcode.topinterview.practice;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-02-23 14:57
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class CopyListWithRandomPointer {

    /*
     * A linked list of length n is given such that each node contains an additional random pointer,
     * which could point to any node in the list, or null.
     *
     * Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes,
     * where each new node has its value set to the value of its corresponding original node. Both the
     * next and random pointer of the new nodes should point to new nodes in the copied list such that
     * the pointers in the original list and copied list represent the same list state. None of the pointers
     * in the new list should point to nodes in the original list.
     *
     * For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the
     * corresponding two nodes x and y in the copied list, x.random --> y.
     *
     * Return the head of the copied linked list.
     *
     * The linked list is represented in the input/output as a list of n nodes. Each node is represented as
     * a pair of [val, random_index] where:
     *      val: an integer representing Node.val
     *      random_index: the index of the node (range from 0 to n-1)
     *                    that the random pointer points to, or null if it does not point to any node.
     * Your code will only be given the head of the original linked list.
     *
     * Definition for a Node.
     * class Node {
     *     int val;
     *     Node next;
     *     Node random;
     *
     *     public Node(int val) {
     *         this.val = val;
     *         this.next = null;
     *         this.random = null;
     *     }
     * }
     */

    public static Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        // key为原节点 value为克隆节点
        HashMap<Node, Node> cloneNodeMap = new HashMap<>(16);
        for (Node node = head; node != null; node = node.next) {
            Node cloneNode = new Node(node.val);
            cloneNodeMap.put(node, cloneNode);
        }

        // copy operation
        for (Node node = head; node != null; node = node.next) {
            Node cloneNode = cloneNodeMap.get(node);
            cloneNode.next = cloneNodeMap.get(node.next);
            cloneNode.random = cloneNodeMap.get(node.random);
        }
        return cloneNodeMap.get(head);
    }

    private static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

}
