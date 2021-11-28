package com.lg.algorithm.lru.a3;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-07-22 10:09
 * Description: LRU算法
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
class LRU {

    private static class LRUCache<K, V> {
        private final HashMap<K, Node<K, V>> cacheMap;
        private final DoubleLinkedList<K, V> queue;
        private final int capacity;

        private LRUCache(int capacity) {
            this.capacity = capacity;
            int initSize = capacity * 4 / 3 + 1;
            cacheMap = new HashMap<>(initSize);
            queue = new DoubleLinkedList<>();
        }

        public V put(K key, V value) {
            if (capacity <= 0) {
                return null;
            }
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                V oldValue = kvNode.value;
                // 更新
                kvNode.value = value;
                // 访问过了，移动到队列末尾
                queue.moveNodeToTail(kvNode);
                return oldValue;
            } else {
                // add the key value to the cache
                Node<K, V> kvNode = new Node<>(key, value);
                cacheMap.put(key, kvNode);
                queue.addToTail(kvNode);
                if (cacheMap.size() > capacity) {
                    Node<K, V> mostRecentlyUnusedValue = queue.removeHead();
                    if (mostRecentlyUnusedValue != null) {
                        cacheMap.remove(mostRecentlyUnusedValue.key);
                    }
                }
                return null;
            }
        }

        public V get(K key) {
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                // 访问过了，移动到队列末尾
                queue.moveNodeToTail(kvNode);
                return kvNode.value;
            }
            return null;
        }

        private static class DoubleLinkedList<K, V> {
            private Node<K, V> head, tail;

            public void addToTail(Node<K, V> kvNode) {
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
