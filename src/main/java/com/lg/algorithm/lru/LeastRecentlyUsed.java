package com.lg.algorithm.lru;

/**
 * LRU算法
 * 最近最少使用
 *
 * @author Xulg
 * Created in 2021-04-14 13:48
 */
@SuppressWarnings({"unused", "AlibabaClassNamingShouldBeCamel"})
class LeastRecentlyUsed {

    static class LRU {

        // 哈希表 + 双向链表

        private final int limit;

        LRU(int capacity) {
            limit = capacity;
        }

        public int get(int key) {
            return 0;
        }

        public void put(int key, int value) {
        }

        private static class Node {
            int key;
            int value;
            Node prev;
            Node next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

    }

}
