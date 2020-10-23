package com.lg.datastructure.trietree;

/**
 * 实现一个字典树1.0
 * 只能处理[a,z]的小写字符组成的字符串
 */
class TrieTree1 {

    private static class Node {
        private static final int MAX_NEXT_NODES_LENGTH = 26;

        /**
         * 经过该节点的次数
         */
        private int pass;

        /**
         * 以该节点结尾的次数
         */
        private int end;

        /**
         * 后续节点，个数为小写字母数26
         * nextNodes[0] a
         * nextNodes[1] b
         * nextNodes[2] c
         * ...
         * nextNodes[25] z
         */
        private final Node[] nextNodes;

        public Node() {
            pass = 0;
            end = 0;
            nextNodes = new Node[MAX_NEXT_NODES_LENGTH];
        }
    }

    /**
     * 字典树的根节点
     */
    private final Node root;

    public TrieTree1() {
        root = new Node();
    }

    // 将word加入树中
    public void insert(String word) {
        if (word == null) {
            return;
        }
        Node node = root;
        // 根节点的经过次数加1
        node.pass++;
        // 从左往右遍历字符
        for (char c : word.toCharArray()) {
            // 由字符，对应成走向哪条路
            int path = c - 'a';
            // 路还没创建
            if (node.nextNodes[path] == null) {
                node.nextNodes[path] = new Node();
            }
            // 获取这条路的引用
            node = node.nextNodes[path];
            // 这条路的经过次数加1
            node.pass++;
        }
        // 加完word的每个字符后来到最后一个节点，
        // 说明word字符串在这个节点上结束，结束次数加1
        node.end++;
    }

    // 将word从树中删除
    public void delete(String word) {
        if (word == null) {
            return;
        }
        if (search(word) <= 0) {
            // word不存在树中，删个毛线
            return;
        }
        Node node = root;
        // 根节点的经过次数减1
        node.pass--;
        for (char c : word.toCharArray()) {
            // 由字符，对应成走向哪条路
            int path = c - 'a';
            // 获取路径并将经过次数减1
            node.nextNodes[path].pass--;
            if (node.nextNodes[path].pass == 0) {
                // 经过次数是0了，后面的路就没用了
                node.nextNodes[path] = null;
                return;
            }
            node = node.nextNodes[path];
        }
        // 结束次数减1
        node.end--;
    }

    // word这个单词之前加入过几次
    public int search(String word) {
        if (word == null) {
            return 0;
        }
        Node node = root;
        for (char c : word.toCharArray()) {
            int path = c - 'a';
            if (node.nextNodes[path] != null) {
                // 路存在，看下一个节点
                node = node.nextNodes[path];
            } else {
                // 路不存在，说明这个word不存在啊
                return 0;
            }
        }
        return node.end;
    }

    // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
    public int prefixNumber(String pre) {
        if (pre == null) {
            return 0;
        }
        Node node = root;
        for (char c : pre.toCharArray()) {
            int path = c - 'a';
            if (node.nextNodes[path] != null) {
                node = node.nextNodes[path];
            } else {
                return 0;
            }
        }
        return node.pass;
    }
}