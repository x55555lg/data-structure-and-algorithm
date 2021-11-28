package com.practice2.lfu;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-10-30 13:14
 * Description: LFU算法实现
 */
class LFU {

    private static class LFUCache<K, V> {
        private final int capacity;
        private final HashMap<K, Node<K, V>> cacheMap;
        // 访问频次map
        private final HashMap<Integer, DoubleLinkedList<K, V>> frequentIndexMap;
        // 全局最小频次
        private int globalMinFrequent = Integer.MAX_VALUE;

        private LFUCache(int capacity) {
            this.capacity = capacity;
            cacheMap = new HashMap<>(capacity * 4 / 3 + 1);
            frequentIndexMap = new HashMap<>(capacity * 4 / 3 + 1);
        }

        public V get(K key) {
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                modifyFrequentCount(kvNode, false);
                return kvNode.value;
            }
            return null;
        }

        public V put(K key, V value) {
            if (capacity <= 0) {
                return null;
            }
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                V oldValue = kvNode.value;
                kvNode.value = value;
                modifyFrequentCount(kvNode, false);
                return oldValue;
            } else {
                if (cacheMap.size() >= capacity) {
                    DoubleLinkedList<K, V> doubleLinkedList = frequentIndexMap.get(globalMinFrequent);
                    if (doubleLinkedList != null) {
                        Node<K, V> unusedCache = doubleLinkedList.removeHead();
                        if (unusedCache != null) {
                            cacheMap.remove(unusedCache.key);
                            if (doubleLinkedList.isEmpty()) {
                                frequentIndexMap.remove(globalMinFrequent);
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

        private void modifyFrequentCount(Node<K, V> kvNode, boolean isCreated) {
            if (isCreated) {
                kvNode.frequent++;
                globalMinFrequent = Math.min(globalMinFrequent, kvNode.frequent);
            } else {
                int oldFrequent = kvNode.frequent;
                kvNode.frequent++;
                globalMinFrequent = Math.min(globalMinFrequent, kvNode.frequent);
                if (frequentIndexMap.containsKey(oldFrequent)) {
                    DoubleLinkedList<K, V> oldDll = frequentIndexMap.get(oldFrequent);
                    oldDll.removeNode(kvNode);
                    if (oldDll.isEmpty()) {
                        frequentIndexMap.remove(oldFrequent);
                        if (oldFrequent == globalMinFrequent) {
                            // 全局最小频次队列空了，需要重新计算最小频次
                            globalMinFrequent = oldFrequent + 1;
                        }
                    }
                }
            }
            if (frequentIndexMap.containsKey(kvNode.frequent)) {
                frequentIndexMap.get(kvNode.frequent).addNodeToTail(kvNode);
            } else {
                DoubleLinkedList<K, V> ddl = new DoubleLinkedList<>();
                ddl.addNodeToTail(kvNode);
                frequentIndexMap.put(kvNode.frequent, ddl);
            }
        }

        private static class Node<K, V> {
            private final K key;
            private V value;
            private int frequent;
            private Node<K, V> prev, next;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private static class DoubleLinkedList<K, V> {
            private Node<K, V> head, tail;

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

            public void removeNode(Node<K, V> kvNode) {
                if (kvNode == null) {
                    return;
                }
                if (head == null) {
                    assert tail == null;
                    return;
                }
                if (head == tail) {
                    head = null;
                    tail = null;
                } else if (head == kvNode) {
                    head = kvNode.next;
                    kvNode.next = null;
                    head.prev = null;
                } else if (tail == kvNode) {
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
