package com.practice.lfu;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-05-10 20:35
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
class LFUCache<K, V> {

    private final int capacity;
    private final HashMap<K, Node<K, V>> keyNodeMap;
    private final HashMap<Integer, DoubleLinkedList<K, V>> frequentCountNodeIndexMap;
    private int minFrequentCount = Integer.MAX_VALUE;

    LFUCache(int capacity) {
        this.capacity = capacity;
        keyNodeMap = new HashMap<>(capacity * 4 / 3 + 1);
        frequentCountNodeIndexMap = new HashMap<>(capacity * 4 / 3 + 1);
    }

    public V get(K key) {
        if (keyNodeMap.containsKey(key)) {
            Node<K, V> kvNode = keyNodeMap.get(key);
            modifyKeyFrequentCount(kvNode, false);
            return kvNode.value;
        }
        return null;
    }

    public void put(K key, V value) {
        if (keyNodeMap.containsKey(key)) {
            // update the value
            Node<K, V> kvNode = keyNodeMap.get(key);
            kvNode.value = value;
            modifyKeyFrequentCount(kvNode, false);
        } else {
            // 容量满了就执行淘汰
            if (keyNodeMap.size() >= capacity) {
                removeLeastFrequentUsedCache();
            }

            // add the key value
            Node<K, V> kvNode = new Node<>(key, value);
            keyNodeMap.put(key, kvNode);
            modifyKeyFrequentCount(kvNode, true);
        }
    }

    private void removeLeastFrequentUsedCache() {
        // 将访问次数最小，最早插入的数据淘汰掉
        DoubleLinkedList<K, V> minFrequentCountQueue = frequentCountNodeIndexMap.get(minFrequentCount);
        if (minFrequentCountQueue != null) {
            Node<K, V> kvNode = minFrequentCountQueue.removeHead();
            if (kvNode != null) {
                keyNodeMap.remove(kvNode.key);
            }
        }
    }

    private void modifyKeyFrequentCount(Node<K, V> node, boolean isNewCreated) {
        minFrequentCount = Math.min(minFrequentCount, node.frequentCount);
        if (!isNewCreated) {
            /*该节点是更新访问频次*/
            int originFrequentCount = node.frequentCount;
            // key的访问次数加1
            node.frequentCount++;
            // 从原来的频次队列中移除
            if (frequentCountNodeIndexMap.containsKey(originFrequentCount)) {
                DoubleLinkedList<K, V> linkedList = frequentCountNodeIndexMap.get(originFrequentCount);
                linkedList.removeNode(node);
                // 这个频次队列就是最小频次的，最小频次队列已经空了，需要重新计算最小频次
                if (minFrequentCount == originFrequentCount && linkedList.isEmpty()) {
                    minFrequentCount = node.frequentCount;
                }
            }
        }
        // 将节点加入到对应访问频次的队列
        if (frequentCountNodeIndexMap.containsKey(node.frequentCount)) {
            frequentCountNodeIndexMap.get(node.frequentCount).addNodeToTail(node);
        } else {
            DoubleLinkedList<K, V> queue = new DoubleLinkedList<>();
            queue.addNodeToTail(node);
            frequentCountNodeIndexMap.put(node.frequentCount, queue);
        }
    }

    /* ************************************************************************************************************** */

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

        public void removeNode(Node<K, V> node) {
            if (node == null) {
                return;
            }
            if (head == null) {
                return;
            }
            if (head == tail && head == node) {
                head = null;
                tail = null;
                return;
            }
            if (head == node) {
                head = node.next;
                node.next = null;
                head.prev = null;
                return;
            }
            if (tail == node) {
                tail = node.prev;
                node.prev = null;
                tail.next = null;
                return;
            }
            // ...H <-> N <-> T...
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    private static class Node<K, V> {
        K key;
        V value;
        int frequentCount;
        Node<K, V> prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            frequentCount = 1;
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

        System.out.println(Integer.MAX_VALUE + 1);
    }
}
