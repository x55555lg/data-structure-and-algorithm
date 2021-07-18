package com.practice.lru;

import java.util.HashMap;

/**
 * 符合LRU淘汰规则的cache实现
 *
 * @author Xulg
 * Created in 2021-05-08 22:20
 */
class LRUCache<K, V> {

    /**
     * 缓存数据的Map
     */
    private final HashMap<K, Node<K, V>> keyNodeMap;

    /**
     * 链表头部是最久未使用的数据
     * 链表尾部是刚刚操作过的数据
     */
    private final DoubleLinkedList<K, V> doubleLinkedList;

    /**
     * 缓存的容量
     */
    private final int capacity;

    LRUCache(int capacity) {
        this.capacity = capacity;
        int initSize = capacity * 4 / 3 + 1;
        keyNodeMap = new HashMap<>(initSize);
        doubleLinkedList = new DoubleLinkedList<>();
    }

    public V get(K key) {
        if (keyNodeMap.containsKey(key)) {
            Node<K, V> node = keyNodeMap.get(key);
            // 这个数据被访问了，移动到队列尾巴
            doubleLinkedList.moveNodeToTail(node);
            return node.value;
        }
        return null;
    }

    public void put(K key, V value) {
        if (capacity <= 0) {
            return;
        }
        if (keyNodeMap.containsKey(key)) {
            // key是已存在的，更新数据
            Node<K, V> node = keyNodeMap.get(key);
            node.value = value;
            // 这个数据被访问了，移动到队列尾巴
            doubleLinkedList.moveNodeToTail(node);
        } else {
            // cache the key value
            Node<K, V> newNode = new Node<>(key, value);
            keyNodeMap.put(key, newNode);

            // 新加入的数据，加入到队列尾巴
            doubleLinkedList.addNodeToTail(newNode);

            // 达到上限了，过期最近最少使用的数据，也就是移除链表头节点
            if (keyNodeMap.size() > capacity) {
                Node<K, V> mostUnusedCache = doubleLinkedList.removeHead();
                if (mostUnusedCache != null) {
                    keyNodeMap.remove(mostUnusedCache.key);
                }
            }
        }
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class DoubleLinkedList<K, V> {
        private Node<K, V> head;
        private Node<K, V> tail;

        public void addNodeToTail(Node<K, V> node) {
            if (node == null) {
                return;
            }
            if (head == null) {
                assert tail == null;
                // 双向链表中一个节点也没有
                head = node;
                tail = node;
            } else {
                assert tail != null;
                // 双向链表中之前有节点，tail（非null）
                Node<K, V> oldTail = this.tail;
                oldTail.next = node;
                node.prev = oldTail;
                tail = node;
            }
        }

        public void moveNodeToTail(Node<K, V> node) {
            if (node == tail) {
                // 节点已经是尾巴了
                return;
            }
            if (node == head) {
                /* 待移动的节点是头节点 */
                // A ↔ B ↔ C...
                head = node.next;
                head.prev = null;
                node.next = null;
            } else {
                /* 待移动的节点是中间部分的节点 */
                // ...A ↔ B ↔ C...
                Node<K, V> prev = node.prev;
                Node<K, V> next = node.next;
                prev.next = next;
                next.prev = prev;
                node.prev = null;
                node.next = null;
            }
            // 移动节点到尾巴
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
                // 链表只有一个节点
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

    public static void main(String[] args) {
        LRUCache<String, Integer> testCache = new LRUCache<>(3);
        testCache.put("A", 1);
        testCache.put("B", 2);
        testCache.put("C", 3);
        System.out.println(testCache.get("B"));
        System.out.println(testCache.get("A"));
        testCache.put("D", 4);
        System.out.println(testCache.get("D"));
        System.out.println(testCache.get("C"));
    }
}
