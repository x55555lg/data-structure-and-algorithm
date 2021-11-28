package com.practice2.trietree;

import java.util.HashMap;

/**
 * @author Xulg
 * @since 2021-10-19 15:03
 * Description:
 */
class TrieTree {

    private static class Tree1 {
        private final Node root;

        Tree1() {
            root = new Node();
        }

        public void add(String str) {
            if (str == null || str.length() == 0) {
                return;
            }
            root.pass++;
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                int pathIdx = c - 'a';
                if (node.nexts[pathIdx] == null) {
                    node.nexts[pathIdx] = new Node();
                }
                node.nexts[pathIdx].pass++;
                node = node.nexts[pathIdx];
            }
            node.end++;
        }

        public void delete(String str) {
            if (str == null || str.length() == 0) {
                return;
            }
            root.pass--;
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                int pathIdx = c - 'a';
                Node nextPath = node.nexts[pathIdx];
                assert nextPath != null;
                nextPath.pass--;
                if (nextPath.pass == 0) {
                    node.nexts[pathIdx] = null;
                    return;
                }
                node = nextPath;
            }
            node.end--;
        }

        public boolean contains(String str) {
            if (str == null || str.length() == 0) {
                return false;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                int pathIdx = c - 'a';
                if (node.nexts[pathIdx] == null) {
                    return false;
                }
                node = node.nexts[pathIdx];
            }
            return true;
        }

        public int searchCount(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                int pathIdx = c - 'a';
                if (node.nexts[pathIdx] == null) {
                    return 0;
                }
                node = node.nexts[pathIdx];
            }
            return node.end;
        }

        public int prefixCount(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                int pathIdx = c - 'a';
                if (node.nexts[pathIdx] == null) {
                    return 0;
                }
                node = node.nexts[pathIdx];
            }
            return node.pass;
        }

        private static class Node {
            private int pass;
            private int end;
            /**
             * next[0]表示 'a'
             * next[b]表示 'b'
             * ...
             * next[25]表示 'z'
             */
            private Node[] nexts;

            Node() {
                nexts = new Node[26];
            }
        }
    }

    private static class Tree2 {
        private final Node root;

        Tree2() {
            root = new Node();
        }

        public void add(String str) {
            if (str == null || str.length() == 0) {
                return;
            }
            root.pass++;
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                if (node.nexts.get(c) == null) {
                    node.nexts.put(c, new Node());
                }
                node.nexts.get(c).pass++;
                node = node.nexts.get(c);
            }
            node.end++;
        }

        public void delete(String str) {
            if (str == null || str.length() == 0) {
                return;
            }
            root.pass--;
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                Node nextPath = node.nexts.get(c);
                assert nextPath != null;
                nextPath.pass--;
                if (nextPath.pass == 0) {
                    node.nexts.remove(c);
                    return;
                }
                node = nextPath;
            }
            node.end--;
        }

        public boolean contains(String str) {
            if (str == null || str.length() == 0) {
                return false;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                if (node.nexts.get(c) == null) {
                    return false;
                }
                node = node.nexts.get(c);
            }
            return true;
        }

        public int searchCount(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                if (node.nexts.get(c) == null) {
                    return 0;
                }
                node = node.nexts.get(c);
            }
            return node.end;
        }

        public int prefixCount(String str) {
            if (str == null || str.length() == 0) {
                return 0;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            for (char c : chars) {
                if (node.nexts.get(c) == null) {
                    return 0;
                }
                node = node.nexts.get(c);
            }
            return node.pass;
        }

        private static class Node {
            private int pass;
            private int end;
            /**
             * next[0]表示 'a'
             * next[b]表示 'b'
             * ...
             * next[25]表示 'z'
             */
            private HashMap<Character, Node> nexts;

            Node() {
                nexts = new HashMap<>();
            }
        }
    }

    private static class BruteForce {

        private final HashMap<String, Integer> box;

        public BruteForce() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            return box.getOrDefault(word, 0);
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int n = 0; n < 10; n++) {
            for (int i = 0; i < testTimes; i++) {
                String[] arr = generateRandomStringArray(arrLen, strLen);
                Tree1 trie1 = new Tree1();
                Tree2 trie2 = new Tree2();
                BruteForce right = new BruteForce();
                for (String s : arr) {
                    trie1.add(s);
                    trie2.add(s);
                    right.insert(s);
                }
                for (int j = 0; j < arr.length; j++) {
                    double decide = Math.random();
                    if (decide < 0.5) {
                        trie1.delete(arr[j]);
                        trie2.delete(arr[j]);
                        right.delete(arr[j]);
                    } else if (decide < 0.75) {
                        int ans1 = trie1.searchCount(arr[j]);
                        int ans2 = trie2.searchCount(arr[j]);
                        int ans3 = right.search(arr[j]);
                        if (ans1 != ans3 || ans2 != ans3) {
                            System.out.println("Oops!1111111");
                        }
                    } else {
                        int ans1 = trie1.prefixCount(arr[j]);
                        int ans2 = trie2.prefixCount(arr[j]);
                        int ans3 = right.prefixNumber(arr[j]);
                        if (ans1 != ans3 || ans2 != ans3) {
                            System.out.println("Oops!2222222");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    // for test
    private static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    private static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

}
