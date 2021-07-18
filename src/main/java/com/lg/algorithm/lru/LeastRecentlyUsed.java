package com.lg.algorithm.lru;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * LRU算法
 * 最近最少使用
 *
 * @author Xulg
 * Created in 2021-04-14 13:48
 */
class LeastRecentlyUsed {

    @SuppressWarnings("AlibabaClassNamingShouldBeCamel")
    private static class LRUCache<K, V> {

        private final int capacity;
        private final HashMap<K, Node<K, V>> keyNodeMap;

        /** head是最少访问的 */
        private final DoubleLinkedList<K, V> doubleLinkedList;

        private LRUCache(int capacity) {
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
                // update the value
                Node<K, V> kvNode = keyNodeMap.get(key);
                kvNode.value = value;
                doubleLinkedList.moveNodeToTail(kvNode);
            } else {
                // add the key value
                Node<K, V> kvNode = new Node<>(key, value);
                keyNodeMap.put(key, kvNode);
                doubleLinkedList.addNodeToTail(kvNode);
                if (keyNodeMap.size() > capacity) {
                    removeLeastRecentlyUsedCache();
                }
            }
        }

        private void removeLeastRecentlyUsedCache() {
            Node<K, V> leastRecentlyUsedCache = doubleLinkedList.removeHead();
            if (leastRecentlyUsedCache != null) {
                keyNodeMap.remove(leastRecentlyUsedCache.key);
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
                    assert tail == null;
                    head = node;
                    tail = node;
                } else {
                    assert tail != null;
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
                if (tail == node) {
                    return;
                }
                if (head == node) {
                    head = node.next;
                    node.next = null;
                    head.prev = null;
                } else {
                    // null <-> H <-> B <-> T <-> null
                    node.next.prev = node.prev;
                    node.prev.next = node.next;
                    node.prev = null;
                    node.next = null;
                }
                // move the node to tail
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
