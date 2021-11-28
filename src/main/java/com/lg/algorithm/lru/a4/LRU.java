package com.lg.algorithm.lru.a4;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-08-31 10:02
 * Description: lru算法实现
 */
public class LRU {

    private static class LRUCache<K, V> {
        /** 缓存大小 */
        private final int capacity;
        /** 存放数据 */
        private final HashMap<K, Node<K, V>> cache;
        /** 队列头部存的是最老的数据 */
        private final DoubleLinkedList<K, V> queue;

        private LRUCache(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>(capacity * 4 / 3 + 1);
            queue = new DoubleLinkedList<>();
        }

        public void put(K key, V value) {
            if (key == null || capacity <= 0) {
                return;
            }
            if (cache.containsKey(key)) {
                // 更新数据
                Node<K, V> oldNode = cache.get(key);
                oldNode.value = value;

                // 将节点移到队列尾巴，表示刚访问过
                queue.moveNodeToTail(oldNode);
            } else {
                // 加入缓存
                Node<K, V> node = new Node<>(key, value);
                cache.put(key, node);
                // 将节点加到队列尾巴，表示刚访问过
                queue.addNodeToTail(node);

                // 缓存满了，移除最早未访问的
                if (cache.size() > capacity) {
                    Node<K, V> mostUnused = queue.removeHead();
                    if (mostUnused != null) {
                        cache.remove(mostUnused.key);
                    }
                }
            }
        }

        public V get(K key) {
            if (cache.containsKey(key)) {
                Node<K, V> kvNode = cache.get(key);
                // 将节点移到队列尾巴，表示刚访问过
                queue.moveNodeToTail(kvNode);
                return kvNode.value;
            }
            return null;
        }

        private static class DoubleLinkedList<K, V> {
            private Node<K, V> head, tail;

            public void moveNodeToTail(Node<K, V> kvNode) {
                if (head == null || tail == null || kvNode == null) {
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
                tail = tail.next;
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
                    tail = tail.next;
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
