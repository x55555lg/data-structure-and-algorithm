package com.lg.algorithm.lru.a1;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * Description: LRU缓存实现
 * Created in 2021-05-21 14:37
 */
class LRU {

    private static class LRUCache<K, V> {
        private final int capacity;
        private final HashMap<K, Node<K, V>> keyNodeMap;
        private final DoubleLinkedList<K, V> doubleLinkedList;

        LRUCache(int capacity) {
            this.capacity = capacity;
            keyNodeMap = new HashMap<>(capacity * 4 / 3 + 1);
            doubleLinkedList = new DoubleLinkedList<>();
        }

        public V get(K key) {
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> kvNode = keyNodeMap.get(key);
                doubleLinkedList.moveNodeToTail(kvNode);
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
                doubleLinkedList.moveNodeToTail(kvNode);
            } else {
                Node<K, V> kvNode = new Node<>(key, value);
                keyNodeMap.put(key, kvNode);
                doubleLinkedList.addNodeToTail(kvNode);
                if (keyNodeMap.size() > capacity) {
                    Node<K, V> mostUnusedCache = doubleLinkedList.removeHead();
                    if (mostUnusedCache != null) {
                        keyNodeMap.remove(mostUnusedCache.key);
                    }
                }
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
                    node.prev = null;
                    node.next = null;
                }
                tail.next = node;
                node.prev = tail;
                tail = node;
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
