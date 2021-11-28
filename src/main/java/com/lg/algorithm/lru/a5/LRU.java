package com.lg.algorithm.lru.a5;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-10-06 22:23
 * Description: LRU算法
 */
class LRU {

    private static class LRUCache<K, V> {
        private final int capacity;
        private final HashMap<K, Node<K, V>> cacheMap;
        private final DoubleLinkedList<K, V> doubleLinkedList;

        LRUCache(int capacity) {
            this.capacity = capacity;
            cacheMap = new HashMap<>(capacity * 4 / 3 + 1);
            doubleLinkedList = new DoubleLinkedList<>();
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
                doubleLinkedList.moveNodeToTail(kvNode);
                return oldVal;
            } else {
                Node<K, V> kvNode = new Node<>(key, value);
                cacheMap.put(key, kvNode);
                doubleLinkedList.addNodeToTail(kvNode);

                if (cacheMap.size() > capacity) {
                    Node<K, V> unusedCache = doubleLinkedList.removeHead();
                    if (unusedCache != null) {
                        cacheMap.remove(unusedCache.key);
                    }
                }
                return null;
            }
        }

        public V get(K key) {
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                doubleLinkedList.moveNodeToTail(kvNode);
                return kvNode.value;
            }
            return null;
        }

        private static class Node<K, V> {
            private Node<K, V> prev, next;
            private final K key;
            private V value;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
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

            public void moveNodeToTail(Node<K, V> kvNode) {
                if (kvNode == null) {
                    return;
                }
                if (head == null) {
                    // should never happen
                    assert tail == null;
                    return;
                }
                if (kvNode == tail) {
                    return;
                }
                if (kvNode == head) {
                    head = kvNode.next;
                    kvNode.next = null;
                    head.prev = null;
                } else {
                    kvNode.prev.next = kvNode.next;
                    kvNode.next.prev = kvNode.prev;
                    kvNode.prev = null;
                    kvNode.next = null;
                }
                tail.next = kvNode;
                kvNode.prev = tail;
                tail = kvNode;
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
        }
    }

    public static void main(String[] args) {
        LRUCache<String, Integer> testCache = new LRUCache<>(3);
        testCache.put("A", 1);
        testCache.put("B", 2);
        testCache.put("C", 3);
        Assert.isTrue(testCache.get("B") == 2);
        Assert.isTrue(testCache.get("A") == 1);
        testCache.put("D", 4);
        Assert.isTrue(testCache.get("D") == 4);
        Assert.isTrue(testCache.get("C") == null);
    }
}
