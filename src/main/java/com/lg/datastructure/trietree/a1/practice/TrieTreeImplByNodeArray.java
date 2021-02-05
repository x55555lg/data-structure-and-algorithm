package com.lg.datastructure.trietree.a1.practice;

/**
 * 字典树的简单实现
 *
 * @author Xulg
 * Created in 2021-01-20 9:02
 */
@SuppressWarnings({"ForLoopReplaceableByForEach", "DuplicatedCode"})
class TrieTreeImplByNodeArray implements TrieTree {

    /**
     * 根节点
     */
    private final Node root = new Node();

    /**
     * 将字符串加入到树中
     */
    @Override
    public void add(String words) {
        if (words == null) {
            return;
        }
        // 根节点的经过次数加1
        root.pass++;
        // 从左往右遍历字符
        Node node = root;
        char[] chars = words.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            assert c >= 'A' && c <= 'Z';
            // 由字符，对应成走向哪条路
            int pathIdx = c - 'A';
            // 路还没创建则创建
            if (node.nexts[pathIdx] == null) {
                node.nexts[pathIdx] = new Node();
            }
            // 这条路的经过次数加1
            node.nexts[pathIdx].pass++;
            // 判断下一个去
            node = node.nexts[pathIdx];
        }
        // 加完word的每个字符后来到最后一个节点，
        // 说明word字符串在这个节点上结束，结束次数加1
        node.end++;
    }

    /**
     * 将字符串从树中删除
     */
    @Override
    public void delete(String words) {
        if (words == null) {
            return;
        }
        if (!contains(words)) {
            // 没在树中，删除个毛
            return;
        }
        root.pass--;
        char[] chars = words.toCharArray();
        Node node = root;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            int pathIdx = c - 'A';
            // 路肯定存在
            if (--node.nexts[pathIdx].pass <= 0) {
                // 经过次数是0了，后面的路就没用了
                node.nexts[pathIdx] = null;
                return;
            }
            node = node.nexts[pathIdx];
        }
        // 结束次数减1
        node.end--;
    }

    /**
     * word这个单词之前加入过几次
     */
    @Override
    public int searchCount(String words) {
        if (words == null) {
            return 0;
        }
        // 树中没有任何的字符串记录
        if (root.pass == 0) {
            return 0;
        }
        Node node = root;
        char[] chars = words.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            int pathIdx = c - 'A';
            if (node.nexts[pathIdx] == null) {
                // 没路了，则肯定没有加入过
                return 0;
            } else {
                node = node.nexts[pathIdx];
            }
        }
        return node.end;
    }

    /**
     * 树中是否包含字符串
     */
    @Override
    public boolean contains(String words) {
        return searchCount(words) > 0;
    }

    /**
     * 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
     */
    @Override
    public int prefixNumber(String prefix) {
        if (!contains(prefix)) {
            return 0;
        }
        Node node = root;
        char[] chars = prefix.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            int pathIdx = c - 'A';
            if (node.nexts[pathIdx] == null) {
                return 0;
            } else {
                node = node.nexts[pathIdx];
            }
        }
        return node.pass;
    }

    /**
     * 获取树中一共有多少个字符串存进去了
     */
    @Override
    public int getTotalCount() {
        return root.pass;
    }

    private static class Node {
        // 表示节点被经过了多少次
        int pass;

        // 表示在该节点字符串结束了多少次数
        int end;

        // 节点后续节点，个数为大写字母数26
        Node[] nexts;

        public Node() {
            pass = 0;
            end = 0;
            // 代表26个字母
            // 0    A
            // 1    B
            // 2    C
            // ...  ...
            // 25   Z
            // nexts[i] == null   i方向的路不存在
            // nexts[i] != null   i方向的路存在
            nexts = new Node[26];
        }

    }

}
