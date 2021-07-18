package com.lg.algorithm.lfu;

import cn.hutool.core.lang.Assert;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-05-10 9:40
 */
class LeastFrequentlyUsedCache {

    private static class LFUCache<K, V> {
        private final int capacity;
        private final HashMap<K, Node<K, V>> keyNodeMap;

        /**
         * key为访问计数，value为有这么多访问次数的节点队列，按照插入的先后顺序，前面的是最早插入的
         * 1    node1 <-> node2
         * 2    node3
         */
        private final HashMap<Integer, DoubleLinkedList<K, V>> frequentCountMap;

        /** 当前访问次数最小值 */
        private int minFrequentCount = Integer.MAX_VALUE;

        LFUCache(int capacity) {
            this.capacity = capacity;
            keyNodeMap = new HashMap<>(capacity * 4 / 3 + 1);
            frequentCountMap = new HashMap<>(capacity * 4 / 3 + 1);
        }

        public V get(K key) {
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> node = keyNodeMap.get(key);
                // update the key access count
                modifyAccessCountInfo(node, false);
                return node.value;
            }
            return null;
        }

        public void put(K key, V value) {
            if (capacity <= 0) {
                return;
            }
            if (keyNodeMap.containsKey(key)) {
                // update value
                Node<K, V> node = keyNodeMap.get(key);
                node.value = value;
                // update the key access count
                modifyAccessCountInfo(node, false);
            } else {
                if (keyNodeMap.size() >= capacity) {
                    // remove the least frequently used cache
                    removeLeastFrequentlyUsedCache();
                }
                // cache the key value
                Node<K, V> node = new Node<>(key, value);
                keyNodeMap.put(key, node);
                // update the key access count
                modifyAccessCountInfo(node, true);
            }
        }

        private void modifyAccessCountInfo(Node<K, V> node, boolean isNewCreated) {
            int oldAccessCount = node.frequentCount;
            // 记录最小访问次数
            minFrequentCount = Math.min(node.frequentCount, minFrequentCount);
            if (!isNewCreated) {
                // key的访问次数加1
                node.frequentCount++;
                // 非新建的节点，那么节点一定在某个访问次数对应的队列中，找到并移除
                if (frequentCountMap.containsKey(oldAccessCount)) {
                    DoubleLinkedList<K, V> linkedList = frequentCountMap.get(oldAccessCount);
                    linkedList.removeNode(node);
                    // 全局最小访问频次对应的队列空了，需要重新计算全局最小值
                    if (oldAccessCount == minFrequentCount && linkedList.isEmpty()) {
                        // 全局最小值就是当前节点的最新访问频次计数值
                        minFrequentCount = node.frequentCount;
                    }
                }
            }
            // 将节点记录到对应访问次数的队列
            if (frequentCountMap.containsKey(node.frequentCount)) {
                frequentCountMap.get(node.frequentCount).addToTail(node);
            } else {
                DoubleLinkedList<K, V> nodes = new DoubleLinkedList<>();
                nodes.addToTail(node);
                frequentCountMap.put(node.frequentCount, nodes);
            }
        }

        private void removeLeastFrequentlyUsedCache() {
            // 获取访问频次最低的队列
            DoubleLinkedList<K, V> nodes = frequentCountMap.get(minFrequentCount);
            // 最早加入的移除掉
            Node<K, V> node = nodes.removeHead();
            if (node != null) {
                keyNodeMap.remove(node.key);
            }
        }

    }

    private static class DoubleLinkedList<K, V> {
        private Node<K, V> head, tail;

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

        public void removeNode(Node<K, V> node) {
            if (node == null) {
                return;
            }
            if (head == tail && head == node) {
                // only one node
                head = null;
                tail = null;
                return;
            }
            if (node == head) {
                head = node.next;
                node.next = null;
                head.prev = null;
                return;
            }
            if (node == tail) {
                tail = node.prev;
                node.prev = null;
                tail.next = null;
                return;
            }
            // ...A <-> B <-> C...
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
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

        public boolean isEmpty() {
            return head == null;
        }
    }

    private static class Node<K, V> {
        K key;
        V value;
        // 访问计数
        int frequentCount;
        Node<K, V> prev;
        Node<K, V> next;

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
