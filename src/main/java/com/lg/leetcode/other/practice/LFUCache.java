package com.lg.leetcode.other.practice;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-05-10 17:10
 */
class LFUCache {

    /*
     * Design and implement a data structure for a Least Frequently Used (LFU) cache.
     * Implement the LFUCache class:
     *  LFUCache(int capacity) Initializes the object with the capacity of the data structure.
     *  int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
     *  void put(int key, int value) Update the value of the key if present, or inserts the key if not already present.
     *  When the cache reaches its capacity, it should invalidate and remove the least frequently used key before
     *  inserting a new item. For this problem, when there is a tie (i.e., two or more keys with the same frequency),
     *  the least recently used key would be invalidated. To determine the least frequently used key, a use counter
     *  is maintained for each key in the cache. The key with the smallest use counter is the least frequently used key.
     *
     * When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation).
     * The use counter for a key in the cache is incremented either a get or put operation is called on it.
     *
     * Example 1:
     * Input
     * ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
     * Output
     * [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
     *
     * Explanation
     * // cnt(x) = the use counter for key x
     * // cache=[] will show the last used order for tiebreakers (leftmost element is  most recent)
     * LFUCache lfu = new LFUCache(2);
     * lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
     * lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
     * lfu.get(1);      // return 1
     *                  // cache=[1,2], cnt(2)=1, cnt(1)=2
     * lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
     *                  // cache=[3,1], cnt(3)=1, cnt(1)=2
     * lfu.get(2);      // return -1 (not found)
     * lfu.get(3);      // return 3
     *                  // cache=[3,1], cnt(3)=2, cnt(1)=2
     * lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
     *                  // cache=[4,3], cnt(4)=1, cnt(3)=2
     * lfu.get(1);      // return -1 (not found)
     * lfu.get(3);      // return 3
     *                  // cache=[3,4], cnt(4)=1, cnt(3)=3
     * lfu.get(4);      // return 4
     *                  // cache=[3,4], cnt(4)=2, cnt(3)=3
     *
     * Constraints:
     * 0 <= capacity, key, value <= 104
     * At most 105 calls will be made to get and put.
     *
     * Follow up: Could you do both operations in O(1) time complexity?
     */

    private static class MyLFUCache {

        private final HashMap<Integer, Node> keyNodeMap;
        private final HashMap<Integer, DoubleLinkedList> frequentMap;
        private final int capacity;
        private int minFrequent = Integer.MAX_VALUE;

        public MyLFUCache(int capacity) {
            this.capacity = capacity;
            keyNodeMap = new HashMap<>(capacity * 4 / 3 + 1);
            frequentMap = new HashMap<>(capacity * 4 / 3 + 1);
        }

        public int get(int key) {
            if (keyNodeMap.containsKey(key)) {
                Node node = keyNodeMap.get(key);
                modifyKeyFrequentCount(node, false);
                return node.value;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (capacity <= 0) {
                // no available capacity
                return;
            }
            if (keyNodeMap.containsKey(key)) {
                // update the value
                Node node = keyNodeMap.get(key);
                node.value = value;
                modifyKeyFrequentCount(node, false);
            } else {
                // 先执行淘汰策略
                if (keyNodeMap.size() >= capacity) {
                    removeLeastFrequentlyUsedCache();
                }
                // 加入数据
                Node node = new Node(key, value);
                keyNodeMap.put(key, node);
                modifyKeyFrequentCount(node, true);
            }
        }

        private void removeLeastFrequentlyUsedCache() {
            DoubleLinkedList linkedList = frequentMap.get(minFrequent);
            if (linkedList != null) {
                Node node = linkedList.removeHead();
                if (node != null) {
                    keyNodeMap.remove(node.key);
                }
            }
        }

        private void modifyKeyFrequentCount(Node node, boolean isNewCreated) {
            int originFrequent = node.frequent;
            // 计算最小访问频次
            minFrequent = Math.min(minFrequent, originFrequent);
            if (!isNewCreated) {
                // increase by one the key frequent if the node is not new created
                node.frequent++;
                // 从之前的频次队列中移除
                if (frequentMap.containsKey(originFrequent)) {
                    DoubleLinkedList linkedList = frequentMap.get(originFrequent);
                    linkedList.removeNode(node);
                    // 最小访问频次对应的队列空了，最小访问频次的值需要重新计算
                    if (originFrequent == minFrequent && linkedList.isEmpty()) {
                        // 全局最小值就是当前节点的最新访问频次计数值
                        minFrequent = node.frequent;
                    }
                }
            }
            if (frequentMap.containsKey(node.frequent)) {
                frequentMap.get(node.frequent).addNodeToTail(node);
            } else {
                DoubleLinkedList linkedList = new DoubleLinkedList();
                linkedList.addNodeToTail(node);
                frequentMap.put(node.frequent, linkedList);
            }
        }

        private static class DoubleLinkedList {
            Node head;
            Node tail;

            public void addNodeToTail(Node node) {
                if (node == null) {
                    return;
                }
                if (head == null) {
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    node.prev = tail;
                    tail = node;
                }
            }

            public Node removeHead() {
                if (head == null) {
                    return null;
                }
                Node oldHead = this.head;
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    head = oldHead.next;
                    oldHead.next = null;
                    head.prev = null;
                }
                return oldHead;
            }

            public void removeNode(Node node) {
                if (node == null) {
                    return;
                }
                if (head == null) {
                    return;
                }
                if (head == tail && head == node) {
                    // only one node
                    head = null;
                    tail = null;
                    return;
                }
                if (head == node) {
                    head = node.next;
                    node.next = null;
                    head.prev = null;
                    return;
                }
                if (tail == node) {
                    tail = node.prev;
                    node.prev = null;
                    tail.next = null;
                    return;
                }
                // ...A <-> N <-> C...
                node.prev.next = node.next;
                node.next.prev = node.prev;
                node.next = null;
                node.prev = null;
            }

            public boolean isEmpty() {
                return head == null;
            }
        }

        private static class Node {
            int key;
            int value;
            // 访问频率次数
            int frequent;
            Node prev, next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
                frequent = 1;
            }
        }

    }

    public static void main(String[] args) {
        MyLFUCache lfu = new MyLFUCache(2);
        lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
        lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
        Assert.isTrue(lfu.get(1) == 1);     // return 1

        // cache=[1,2], cnt(2)=1, cnt(1)=2
        lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.

        // cache=[3,1], cnt(3)=1, cnt(1)=2
        Assert.isTrue(lfu.get(2) == -1);      // return -1 (not found)
        Assert.isTrue(lfu.get(3) == 3);      // return 3

        // cache=[3,1], cnt(3)=2, cnt(1)=2
        lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.

        // cache=[4,3], cnt(4)=1, cnt(3)=2
        Assert.isTrue(lfu.get(1) == -1);     // return -1 (not found)
        Assert.isTrue(lfu.get(3) == 3);     // return 3

        // cache=[3,4], cnt(4)=1, cnt(3)=3
        Assert.isTrue(lfu.get(4) == 4);     // return 4
        // cache=[3,4], cnt(4)=2, cnt(3)=3
    }
}
