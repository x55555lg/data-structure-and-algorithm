package com.practice2.lru;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-10-30 13:14
 * Description: LRU算法实现
 */
class LRU {


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
                return value;
            }
            if (cacheMap.containsKey(key)) {
                Node<K, V> kvNode = cacheMap.get(key);
                V oldValue = kvNode.value;
                kvNode.value = value;
                queue.moveNodeToTail(kvNode);
                return oldValue;
            } else {
                Node<K, V> kvNode = new Node<>(key, value);
                cacheMap.put(key, kvNode);
                queue.addNodeToTail(kvNode);
                if (cacheMap.size() > capacity) {
                    Node<K, V> leastRecentlyUsedCache = queue.removeHead();
                    if (leastRecentlyUsedCache != null) {
                        cacheMap.remove(leastRecentlyUsedCache.key);
                    }

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

        private static class Node<K, V> {
            private final K key;
            private V value;
            private Node<K, V> prev, next;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private static class DoubleLinkedList<K, V> {
            private Node<K, V> head, tail;

            // 将节点挪到队列末尾
            public void moveNodeToTail(Node<K, V> kvNode) {
                if (kvNode == null) {
                    return;
                }
                if (tail == kvNode) {
                    return;
                }
                if (head == kvNode) {
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

            // 添加节点到队列末尾
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
