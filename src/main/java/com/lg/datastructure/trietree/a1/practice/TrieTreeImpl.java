package com.lg.datastructure.trietree.a1.practice;

import java.util.HashMap;

/**
 * 前缀树实现
 *
 * @author Xulg
 * Created in 2021-01-31 11:17
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
class TrieTreeImpl implements TrieTree {

    private final Node root = new Node();

    @Override
    public void add(String words) {
        if (words == null) {
            return;
        }
        // 根节点的经过次数加1
        root.pass++;
        Node node = root;
        char[] chars = words.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Character character = chars[i];
            Node path = node.nexts.get(character);
            if (path == null) {
                // 路径还不存在，进行创建
                path = new Node();
                node.nexts.put(character, path);
            }
            path.pass++;
            // 判断下一个字符(路径)去了
            node = path;
        }
        // 结束循环后，node来到了结尾字符的位置
        // 在此位置结尾的次数加1
        node.end++;
    }

    @Override
    public void delete(String words) {
        if (words == null) {
            return;
        }
        if (searchCount(words) <= 0) {
            return;
        }

        // 根节点的经过次数减1
        root.pass--;
        Node node = root;
        char[] chars = words.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Character character = chars[i];
            Node path = node.nexts.get(character);
            path.pass--;
            if (path.pass == 0) {
                // 这个路径上没有经过次数了，路径不再需要
                node.nexts.remove(character);
                return;
            }
            // 判断下一个字符(路径)去了
            node = path;
        }
        // 结束循环后，node来到了结尾字符的位置
        // 在此位置结尾的次数减1
        node.end--;
    }

    @Override
    public int searchCount(String words) {
        if (words == null) {
            // 不存在
            return 0;
        }
        Node node = root;
        char[] chars = words.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Character character = chars[i];
            Node path = node.nexts.get(character);
            if (path == null) {
                // 路都没有，肯定没这个字符串
                return 0;
            }
            // 判断下一个字符(路径)去了
            node = path;
        }
        // 结束循环后，node来到了结尾字符的位置
        // 返回在这个位置有多少字符结尾的
        return node.end;
    }

    @Override
    public int prefixNumber(String prefix) {
        if (prefix == null) {
            // 不存在
            return 0;
        }
        if (searchCount(prefix) <= 0) {
            return 0;
        }

        Node node = root;
        char[] chars = prefix.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Character character = chars[i];
            Node path = node.nexts.get(character);
            if (path == null) {
                // 路都没有，肯定没这个字符串
                return 0;
            }
            // 判断下一个字符(路径)去了
            node = path;
        }
        // 结束循环后，node来到了结尾字符的位置
        // 返回在这个位置有多少字符路过
        return node.pass;
    }

    @Override
    public int getTotalCount() {
        return root.pass;
    }

    private static class Node {
        int pass;
        int end;
        HashMap<Character, Node> nexts;

        Node() {
            pass = 0;
            end = 0;
            // 代表26个字母
            // 0    A
            // 1    B
            // 2    C
            // ...  ...
            // 25   Z
            // nexts.get(i) == null   i方向的路不存在
            // nexts.get(i) != null   i方向的路存在
            nexts = new HashMap<>();
        }
    }
}
