package com.lg.algorithm.lfu.a2;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-06-16 13:52
 * Description: LFU缓存
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class LFU {

    private static class LFUCache<K, V> {
        private final int capacity;
        private final HashMap<K, Node<K, V>> cacheMap;
        private final HashMap<Integer, DoubleLinkedList<K, V>> accessCountMap;
        private int globalMinAccessCount = Integer.MAX_VALUE;

        public LFUCache(int capacity) {
            this.capacity = capacity;
            cacheMap = new HashMap<>(capacity * 4 / 3 + 1);
            accessCountMap = new HashMap<>(capacity * 4 / 3 + 1);
        }

        public V get(K key) {
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                modifyAccessCount(kvNode, false);
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
                kvNode.value = value;
                modifyAccessCount(kvNode, false);
                return kvNode.value;
            } else {
                if (cacheMap.size() >= capacity) {
                    removeMinAccessCountCache();
                }
                Node<K, V> kvNode = new Node<>(key, value);
                cacheMap.put(key, kvNode);
                modifyAccessCount(kvNode, true);
                return null;
            }
        }

        private void modifyAccessCount(Node<K, V> kvNode, boolean isCreated) {
            if (isCreated) {
                globalMinAccessCount = Math.min(globalMinAccessCount, kvNode.accessCount);
            } else {
                int oldAccessCount = kvNode.accessCount;
                kvNode.accessCount++;
                globalMinAccessCount = Math.min(globalMinAccessCount, kvNode.accessCount);// 这步可以省略
                if (accessCountMap.containsKey(oldAccessCount)) {
                    // 从原来的计数队列移除
                    DoubleLinkedList<K, V> doubleLinkedList = accessCountMap.get(oldAccessCount);
                    doubleLinkedList.removeNode(kvNode);
                    if (doubleLinkedList.isEmpty()) {
                        accessCountMap.remove(oldAccessCount);
                        // 最小访问计数的队列空了
                        if (oldAccessCount == globalMinAccessCount) {
                            // 重新计算全局最小访问计数
                            globalMinAccessCount = kvNode.accessCount;
                        }
                    }
                }
            }
            if (accessCountMap.containsKey(kvNode.accessCount)) {
                accessCountMap.get(kvNode.accessCount).addNodeToTail(kvNode);
            } else {
                DoubleLinkedList<K, V> doubleLinkedList = new DoubleLinkedList<>();
                doubleLinkedList.addNodeToTail(kvNode);
                accessCountMap.put(kvNode.accessCount, doubleLinkedList);
            }
        }

        private void removeMinAccessCountCache() {
            DoubleLinkedList<K, V> doubleLinkedList = accessCountMap.get(globalMinAccessCount);
            if (doubleLinkedList != null) {
                Node<K, V> kvNode = doubleLinkedList.removeHead();
                if (kvNode != null) {
                    cacheMap.remove(kvNode.key);
                }
                if (doubleLinkedList.isEmpty()) {
                    accessCountMap.remove(globalMinAccessCount);
                }
            }
        }

        private static class Node<K, V> {
            K key;
            V value;
            int accessCount;
            Node<K, V> prev, next;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
                accessCount = 1;
            }
        }

        private static class DoubleLinkedList<K, V> {
            Node<K, V> head, tail;

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

            public void addNodeToTail(Node<K, V> node) {
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

            public void removeNode(Node<K, V> node) {
                if (node == null) {
                    return;
                }
                if (head == tail) {
                    assert head == node;
                    head = null;
                    tail = null;
                    return;
                }
                if (head == node) {
                    head = node.next;
                    head.prev = null;
                    node.next = null;
                    return;
                }
                if (tail == node) {
                    tail = node.prev;
                    tail.next = null;
                    node.prev = null;
                    return;
                }
                node.prev.next = node.next;
                node.next.prev = node.prev;
                node.prev = null;
                node.next = null;
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
