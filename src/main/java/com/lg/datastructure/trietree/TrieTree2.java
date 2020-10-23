package com.lg.datastructure.trietree;

import java.util.HashMap;

/**
 * 实现一个字典树2.0
 * 可以是任意多个字符
 */
class TrieTree2 {
    private static class Node {
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
         * key：  字符的ASCII码值
         * value：后续节点
         */
        private final HashMap<Integer, Node> nextPaths;

        public Node() {
            pass = 0;
            end = 0;
            nextPaths = new HashMap<>();
        }
    }

    /**
     * 字典树的根节点
     */
    private final Node root;

    public TrieTree2() {
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
        for (char c : word.toCharArray()) {
            // 计算路径，也就是字符的ASCII码值
            int pathIdx = (int) c;
            // 没有路就创建
            node.nextPaths.putIfAbsent(pathIdx, new Node());
            // 获取这条路
            Node pathNode = node.nextPaths.get(pathIdx);
            // 经过次数加1
            pathNode.pass++;
            // 跳到下一个节点
            node = pathNode;
        }
        // 结尾次数加1
        node.end++;
    }

    // 将word从树中删除
    public void delete(String word) {
        if (word == null) {
            return;
        }
        if (search(word) == 0) {
            // 没加过，删个锤子
            return;
        }
        Node node = root;
        // 根节点的经过次数减1
        node.pass--;
        for (char c : word.toCharArray()) {
            int pathIdx = (int) c;
            Node pathNode = node.nextPaths.get(pathIdx);
            pathNode.pass--;
            if (pathNode.pass == 0) {
                node.nextPaths.remove(pathIdx);
                return;
            }
            node = pathNode;
        }
        node.end--;
    }

    // word这个单词之前加入过几次
    public int search(String word) {
        if (word == null) {
            return 0;
        }
        Node node = root;
        for (char c : word.toCharArray()) {
            int pathIdx = (int) c;
            if (!node.nextPaths.containsKey(pathIdx)) {
                // 没有路啊，肯定没有
                return 0;
            }
            node = node.nextPaths.get(pathIdx);
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
            int pathIdx = (int) c;
            if (!node.nextPaths.containsKey(pathIdx)) {
                // 没有路啊，肯定没有
                return 0;
            }
            node = node.nextPaths.get(pathIdx);
        }
        return node.pass;
    }
}
