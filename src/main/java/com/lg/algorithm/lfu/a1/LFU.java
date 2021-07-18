package com.lg.algorithm.lfu.a1;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * Description: LFU缓存实现
 * Created in 2021-05-21 17:35
 */
class LFU {

    private static class LFUCache<K, V> {

        private final int capacity;
        private final HashMap<K, Node<K, V>> keyNodeMap;
        private final HashMap<Integer, DoubleLinkedList<K, V>> frequentCountMap;
        private int minFrequentCount = Integer.MAX_VALUE;

        private LFUCache(int capacity) {
            this.capacity = capacity;
            keyNodeMap = new HashMap<>(capacity * 4 / 3 + 1);
            frequentCountMap = new HashMap<>(capacity * 4 / 3 + 1);
        }

        public V get(K key) {
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> kvNode = keyNodeMap.get(key);
                modifyFrequentCount(kvNode, false);
                return kvNode.value;
            }
            return null;
        }

        public void put(K key, V value) {
            if (capacity <= 0) {
                return;
            }
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> kvNode = keyNodeMap.get(key);
                kvNode.value = value;
                modifyFrequentCount(kvNode, false);
            } else {
                if (keyNodeMap.size() >= capacity) {
                    removeMinFrequentCountCache();
                }
                Node<K, V> kvNode = new Node<>(key, value);
                keyNodeMap.put(key, kvNode);
                modifyFrequentCount(kvNode, true);
            }
        }

        private void removeMinFrequentCountCache() {
            DoubleLinkedList<K, V> linkedList = frequentCountMap.get(minFrequentCount);
            if (linkedList != null) {
                Node<K, V> kvNode = linkedList.removeHead();
                if (kvNode != null) {
                    keyNodeMap.remove(kvNode.key);
                    if (linkedList.isEmpty()) {
                        frequentCountMap.remove(minFrequentCount);
                    }
                }
            }
        }

        private void modifyFrequentCount(Node<K, V> kvNode, boolean isCreate) {
            if (isCreate) {
                // get the min frequent count
                minFrequentCount = Math.min(minFrequentCount, kvNode.frequentCount);
            } else {
                int oldFrequentCount = kvNode.frequentCount;

                // increase the frequent count
                kvNode.frequentCount++;

                // get the min frequent count
                minFrequentCount = Math.min(minFrequentCount, kvNode.frequentCount);

                // remove from the old frequent count linked list
                if (frequentCountMap.containsKey(oldFrequentCount)) {
                    DoubleLinkedList<K, V> oldLinkedList = frequentCountMap.get(oldFrequentCount);
                    oldLinkedList.removeNode(kvNode);
                    if (oldLinkedList.isEmpty()) {
                        frequentCountMap.remove(oldFrequentCount);
                        // recalculate the min frequent count if the min frequent count linked list is empty
                        if (oldFrequentCount == minFrequentCount) {
                            minFrequentCount = kvNode.frequentCount;
                        }
                    }
                }
            }
            if (frequentCountMap.containsKey(kvNode.frequentCount)) {
                frequentCountMap.get(kvNode.frequentCount).addNodeToTail(kvNode);
            } else {
                DoubleLinkedList<K, V> linkedList = new DoubleLinkedList<>();
                linkedList.addNodeToTail(kvNode);
                frequentCountMap.put(kvNode.frequentCount, linkedList);
            }
        }

        private static class DoubleLinkedList<K, V> {
            Node<K, V> head, tail;

            public void addNodeToTail(Node<K, V> node) {
                if (node == null) {
                    return;
                }
                if (head == null) {
                    assert tail == null;
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    node.prev = tail;
                    tail = node;
                }
            }

            public void removeNode(Node<K, V> node) {
                if (node == null) {
                    return;
                }
                if (head == tail) {
                    head = null;
                    tail = null;
                } else if (head == node) {
                    head = node.next;
                    node.next = null;
                    head.prev = null;
                } else if (tail == node) {
                    tail = node.prev;
                    node.prev = null;
                    tail.next = null;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    node.prev = null;
                    node.next = null;
                }
            }

            public Node<K, V> removeHead() {
                if (head == null) {
                    assert tail == null;
                    return null;
                }
                Node<K, V> oldHead = head;
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

            public boolean isEmpty() {
                return head == null;
            }
        }

        private static class Node<K, V> {
            K key;
            V value;
            int frequentCount;
            Node<K, V> prev, next;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
                frequentCount = 1;
            }
        }
    }

    public static void main(String[] args) {
        LFUCache<Integer, Integer> lfu = new LFUCache<>(2);
        // cache=[1,_], cnt(1)=1
        lfu.put(1, 1);
        // cache=[2,1], cnt(2)=1, cnt(1)=1
        lfu.put(2, 2);
        // return 1
        Assert.isTrue(lfu.get(1) == 1);

        // cache=[1,2], cnt(2)=1, cnt(1)=2
        // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
        lfu.put(3, 3);

        // cache=[3,1], cnt(3)=1, cnt(1)=2
        // return -1 (not found)
        Assert.isTrue(lfu.get(2) == null);
        // return 3
        Assert.isTrue(lfu.get(3) == 3);

        // cache=[3,1], cnt(3)=2, cnt(1)=2
        // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
        lfu.put(4, 4);

        // cache=[4,3], cnt(4)=1, cnt(3)=2
        // return -1 (not found)
        Assert.isTrue(lfu.get(1) == null);
        // return 3
        Assert.isTrue(lfu.get(3) == 3);

        // cache=[3,4], cnt(4)=1, cnt(3)=3
        // return 4
        Assert.isTrue(lfu.get(4) == 4);
        // cache=[3,4], cnt(4)=2, cnt(3)=3
    }
}
