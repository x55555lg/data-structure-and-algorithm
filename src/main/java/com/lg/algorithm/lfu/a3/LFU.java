package com.lg.algorithm.lfu.a3;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-10-06 22:47
 * Description: LFU算法
 */
class LFU {

    private static class LFUCache<K, V> {
        private final int capacity;
        private final HashMap<K, Node<K, V>> cacheMap;
        private final HashMap<Integer, DoubleLinkedList<K, V>> frequentCountIndexMap;
        private int globalMinFrequentCount = Integer.MAX_VALUE;

        LFUCache(int capacity) {
            this.capacity = capacity;
            cacheMap = new HashMap<>(capacity * 4 / 3 + 1);
            frequentCountIndexMap = new HashMap<>(capacity * 4 / 3 + 1);
        }

        public V put(K key, V value) {
            if (key == null) {
                return value;
            }
            if (capacity <= 0) {
                return value;
            }
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                V oldVal = kvNode.value;
                kvNode.value = value;
                modifyFrequentCount(kvNode, false);
                return oldVal;
            } else {
                if (cacheMap.size() >= capacity) {
                    DoubleLinkedList<K, V> doubleLinkedList = frequentCountIndexMap.get(globalMinFrequentCount);
                    if (doubleLinkedList != null) {
                        Node<K, V> unusedCache = doubleLinkedList.removeHead();
                        if (unusedCache != null) {
                            cacheMap.remove(unusedCache.key);
                            if (doubleLinkedList.isEmpty()) {
                                frequentCountIndexMap.remove(unusedCache.frequentCount);
                            }
                        }
                    }
                }
                Node<K, V> kvNode = new Node<>(key, value);
                cacheMap.put(key, kvNode);
                modifyFrequentCount(kvNode, true);
                return null;
            }
        }

        public V get(K key) {
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                modifyFrequentCount(kvNode, false);
                return kvNode.value;
            }
            return null;
        }

        private void modifyFrequentCount(Node<K, V> kvNode, boolean isCreated) {
            if (isCreated) {
                kvNode.frequentCount++;
                globalMinFrequentCount = Math.min(globalMinFrequentCount, kvNode.frequentCount);
            } else {
                int originFrequentCount = kvNode.frequentCount;
                kvNode.frequentCount++;
                globalMinFrequentCount = Math.min(globalMinFrequentCount, kvNode.frequentCount);
                /// 从原来的频次队列移除
                if (frequentCountIndexMap.containsKey(originFrequentCount)) {
                    DoubleLinkedList<K, V> doubleLinkedList = frequentCountIndexMap.get(originFrequentCount);
                    doubleLinkedList.removeNode(kvNode);
                    if (doubleLinkedList.isEmpty()) {
                        frequentCountIndexMap.remove(originFrequentCount);
                        if (globalMinFrequentCount == originFrequentCount) {
                            // 全局最小频次的队列空了，需要重选全局最小频次
                            globalMinFrequentCount = originFrequentCount + 1;
                        }
                    }
                }
            }
            if (frequentCountIndexMap.containsKey(kvNode.frequentCount)) {
                frequentCountIndexMap.get(kvNode.frequentCount).addNodeToTail(kvNode);
            } else {
                DoubleLinkedList<K, V> doubleLinkedList = new DoubleLinkedList<>();
                doubleLinkedList.addNodeToTail(kvNode);
                frequentCountIndexMap.put(kvNode.frequentCount, doubleLinkedList);
            }
        }

        private static class Node<K, V> {
            private Node<K, V> prev, next;
            private final K key;
            private V value;
            private int frequentCount;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
                frequentCount = 0;
            }
        }

        private static class DoubleLinkedList<K, V> {
            private Node<K, V> head, tail;

            public void addNodeToTail(Node<K, V> kvNode) {
                if (kvNode == null) {
                    return;
                }
                if (head == null) {
                    assert tail == null;
                    head = kvNode;
                    tail = kvNode;
                } else {
                    tail.next = kvNode;
                    kvNode.prev = tail;
                    tail = kvNode;
                }
            }

            public Node<K, V> removeHead() {
                if (head == null) {
                    assert tail == null;
                    return null;
                }
                Node<K, V> oldHead = this.head;
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

            public void removeNode(Node<K, V> kvNode) {
                if (kvNode == null) {
                    return;
                }
                if (head == null) {
                    // should never happen
                    assert tail == null;
                    return;
                }
                if (head == tail) {
                    // assert kvNode == head && kvNode == tail;
                    head = null;
                    tail = null;
                } else if (kvNode == head) {
                    head = kvNode.next;
                    kvNode.next = null;
                    head.prev = null;
                } else if (kvNode == tail) {
                    tail = kvNode.prev;
                    kvNode.prev = null;
                    tail.next = null;
                } else {
                    kvNode.prev.next = kvNode.next;
                    kvNode.next.prev = kvNode.prev;
                    kvNode.prev = null;
                    kvNode.next = null;
                }
            }

            public boolean isEmpty() {
                return head == null;
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
