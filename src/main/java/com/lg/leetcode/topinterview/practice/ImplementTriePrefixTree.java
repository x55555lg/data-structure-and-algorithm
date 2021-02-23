package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-23 14:02
 */
class ImplementTriePrefixTree {

    /*
     * Implement a trie with insert, search, and startsWith methods.
     *
     * Example:
     * Trie trie = new Trie();
     * trie.insert("apple");
     * trie.search("apple");   // returns true
     * trie.search("app");     // returns false
     * trie.startsWith("app"); // returns true
     * trie.insert("app");
     * trie.search("app");     // returns true
     *
     * Note:
     * You may assume that all inputs are consist of lowercase letters a-z.
     * All inputs are guaranteed to be non-empty strings.
     *---------------------------------------------------------------------
     * 实现一个前缀树TrieTree
     */

    private static class Trie {

        private final Node root;

        Trie() {
            root = new Node();
        }

        public void insert(String word) {
            if (word == null || word.length() == 0) {
                return;
            }
            char[] chars = word.toCharArray();
            root.pass++;
            Node node = root;
            for (char c : chars) {
                int pathIdx = c - 'a';
                if (node.nexts[pathIdx] == null) {
                    // create the path
                    node.nexts[pathIdx] = new Node();
                }
                // add the pass count
                node.nexts[pathIdx].pass++;
                // check the next node
                node = node.nexts[pathIdx];
            }
            // add the end count
            node.end++;
        }

        public boolean search(String word) {
            if (word == null || word.length() == 0) {
                return false;
            }
            if (root.pass == 0) {
                return false;
            }
            char[] chars = word.toCharArray();
            Node node = root;
            for (char c : chars) {
                int pathIdx = c - 'a';
                if (node.nexts[pathIdx] == null) {
                    return false;
                }
                node = node.nexts[pathIdx];
            }
            return node.end > 0;
        }

        public boolean startsWith(String prefix) {
            if (prefix == null || prefix.length() == 0) {
                return false;
            }
            if (root.pass == 0) {
                return false;
            }
            char[] chars = prefix.toCharArray();
            Node node = root;
            for (char c : chars) {
                int pathIdx = c - 'a';
                if (node.nexts[pathIdx] == null) {
                    return false;
                }
                node = node.nexts[pathIdx];
            }
            return node.pass > 0;
        }

        private static class Node {
            int pass;
            int end;
            Node[] nexts;

            Node() {
                pass = 0;
                end = 0;
                // a-z 26条路径
                // 0-a 1-b 2-c...
                nexts = new Node[26];
            }
        }
    }

}
