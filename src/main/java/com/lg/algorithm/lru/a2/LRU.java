package com.lg.algorithm.lru.a2;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-06-16 13:52
 * Description: LRU缓存
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class LRU {

    private static class LRUCache<K, V> {
        private final int capacity;
        private final HashMap<K, Node<K, V>> cacheMap;
        private final DoubleLinkedList<K, V> queue;

        private LRUCache(int capacity) {
            this.capacity = capacity;
            cacheMap = new HashMap<>(capacity * 4 / 3 + 1);
            queue = new DoubleLinkedList<>();
        }

        public V put(K key, V value) {
            if (capacity <= 0) {
                return null;
            }
            if (cacheMap.containsKey(key)) {
                // update the value
                Node<K, V> kvNode = cacheMap.get(key);
                kvNode.value = value;
                // move the visited node to tail
                queue.moveNodeToTail(kvNode);
                // return old value
                return kvNode.value;
            } else {
                Node<K, V> kvNode = new Node<>(key, value);
                cacheMap.put(key, kvNode);
                queue.addToTail(kvNode);
                if (cacheMap.size() > capacity) {
                    // remove the most unused cache
                    removeLeastRecentlyUsedCache();
                }
                return null;
            }
        }

        public V get(K key) {
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                queue.moveNodeToTail(kvNode);
                return kvNode.value;
            }
            return null;
        }

        private void removeLeastRecentlyUsedCache() {
            Node<K, V> leastRecentlyUsedCache = queue.removeHead();
            if (leastRecentlyUsedCache != null) {
                cacheMap.remove(leastRecentlyUsedCache.key);
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
            Node<K, V> head, tail;

            public void addToTail(Node<K, V> node) {
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
                    head = head.next;
                    oldHead.next = null;
                    head.prev = null;
                }
                return oldHead;
            }

            public void moveNodeToTail(Node<K, V> node) {
                if (node == null) {
                    return;
                }
                assert head != null && tail != null;
                if (node == tail) {
                    return;
                }
                if (node == head) {
                    head = node.next;
                    node.next = null;
                    head.prev = null;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    node.next = null;
                    node.prev = null;
                }
                tail.next = node;
                node.prev = tail;
                tail = node;
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
