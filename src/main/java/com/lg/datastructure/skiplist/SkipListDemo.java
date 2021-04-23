package com.lg.datastructure.skiplist;

import java.util.ArrayList;

/**
 * @author Xulg
 * Created in 2021-04-06 9:24
 */
class SkipListDemo {

    /**
     * 跳表的节点结构
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    private static class Node<K, V> {
        private K key;
        private V val;
        private ArrayList<Node<K, V>> nextNodes;
    }

}
