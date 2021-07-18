package com.lg.leetcode.topinterview.practice;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-03-09 9:01
 */
class LRUCache {

    /*
     * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
     *
     * Implement the LRUCache class:
     *
     * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
     * int get(int key) Return the value of the key if the key exists, otherwise return -1.
     * void put(int key, int value) Update the value of the key if the key exists. Otherwise,
     * add the key-value pair to the cache. If the number of keys exceeds the capacity from
     * this operation, evict the least recently used key.
     *
     * Follow up:
     * Could you do get and put in O(1) time complexity?
     *
     * Example 1:
     * Input
     * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
     * Output
     * [null, null, null, 1, null, -1, null, -1, 3, 4]
     *
     * Explanation
     * LRUCache lRUCache = new LRUCache(2);
     * lRUCache.put(1, 1); // cache is {1=1}
     * lRUCache.put(2, 2); // cache is {1=1, 2=2}
     * lRUCache.get(1);    // return 1
     * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
     * lRUCache.get(2);    // returns -1 (not found)
     * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
     * lRUCache.get(1);    // return -1 (not found)
     * lRUCache.get(3);    // return 3
     * lRUCache.get(4);    // return 4
     *
     * Constraints:
     *  1 <= capacity <= 3000
     *  0 <= key <= 3000
     *  0 <= value <= 104
     *  At most 3 * 104 calls will be made to get and put.
     */

    private static class LRU {

        private final HashMap<Integer, Node<Integer, Integer>> keyNodeMap;
        private final DoubleLinkedList<Integer, Integer> doubleLinkedList;
        private final int capacity;

        public LRU(int capacity) {
            this.capacity = capacity;
            keyNodeMap = new HashMap<>(capacity * 4 / 3 + 1);
            doubleLinkedList = new DoubleLinkedList<>();
        }

        public int get(int key) {
            if (keyNodeMap.containsKey(key)) {
                Node<Integer, Integer> node = keyNodeMap.get(key);
                doubleLinkedList.moveNodeToTail(node);
                return node.value == null ? -1 : node.value;
            }
            // not found
            return -1;
        }

        public void put(int key, int value) {
            if (keyNodeMap.containsKey(key)) {
                // update the value
                Node<Integer, Integer> node = keyNodeMap.get(key);
                node.value = value;
                doubleLinkedList.moveNodeToTail(node);
            } else {
                // add the key value
                Node<Integer, Integer> newNode = new Node<>(key, value);
                doubleLinkedList.addNodeToTail(newNode);
                keyNodeMap.put(key, newNode);

                // remove the most unused cache if necessary
                if (keyNodeMap.size() > capacity) {
                    Node<Integer, Integer> mostUnusedCache = doubleLinkedList.removeHead();
                    if (mostUnusedCache != null) {
                        keyNodeMap.remove(mostUnusedCache.key);
                    }
                }
            }
        }

        private static class Node<K, V> {
            K key;
            V value;
            Node<K, V> prev, next;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private static class DoubleLinkedList<K, V> {
            private Node<K, V> head, tail;

            public void moveNodeToTail(Node<K, V> node) {
                if (node == null) {
                    return;
                }
                if (node == tail) {
                    return;
                }
                if (node == head) {
                    head = node.next;
                    node.next = null;
                } else {
                    // ...A ↔ B ↔ C...
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    node.next = null;
                    node.prev = null;
                }
                tail.next = node;
                node.prev = tail;
                tail = node;
            }

            public void addNodeToTail(Node<K, V> node) {
                if (node == null) {
                    return;
                }
                if (head == null) {
                    // assert tail == null;
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    node.prev = tail;
                    tail = node;
                }
            }

            public Node<K, V> removeHead() {
                if (head == null) {
                    return null;
                }
                Node<K, V> oldHead = this.head;
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    // A ↔ B ↔ C...
                    head = oldHead.next;
                    head.prev = null;
                    oldHead.next = null;
                }
                return oldHead;
            }
        }
    }

}
