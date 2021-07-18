package com.practice.trietree;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-05-11 22:45
 */
class TrieTree2 {

    private final Node root = new Node();

    public void add(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        char[] chars = word.toCharArray();
        Node node = root;
        node.pass++;
        for (char c : chars) {
            Node path = node.nextPaths.get(c);
            if (path == null) {
                // 路径不存在则创建
                path = new Node();
                node.nextPaths.put(c, path);
            }
            path.pass++;
            // 下一个路口
            node = path;
        }
        node.end++;
    }

    public void delete(String word) {
        if (search(word) <= 0) {
            return;
        }
        char[] chars = word.toCharArray();
        Node node = root;
        node.pass--;
        for (char c : chars) {
            Node path = node.nextPaths.get(c);
            path.pass--;
            if (path.pass == 0) {
                node.nextPaths.remove(c);
                return;
            }
            // 下一个路口
            node = path;
        }
        node.end--;
    }

    public int search(String word) {
        if (word == null || word.length() == 0) {
            return 0;
        }
        char[] chars = word.toCharArray();
        Node node = root;
        for (char c : chars) {
            Node path = node.nextPaths.get(c);
            if (path == null) {
                return 0;
            }
            node = path;
        }
        return node.end;
    }

    public int prefix(String word) {
        if (word == null || word.length() == 0) {
            return 0;
        }
        char[] chars = word.toCharArray();
        Node node = root;
        for (char c : chars) {
            Node path = node.nextPaths.get(c);
            if (path == null) {
                return 0;
            }
            node = path;
        }
        return node.pass;
    }

    private static class Node {

        /**
         * 路径该位置的字符串有几个
         */
        int pass;

        /**
         * 在该位置结束的字符串有几个
         */
        int end;

        /**
         * 后续的路
         */
        HashMap<Character, Node> nextPaths = new HashMap<>();
    }

    /* ****************************************************************************************************************/

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
                TrieTree2 trie1 = new TrieTree2();
                BruteForce right = new BruteForce();
                for (String s : arr) {
                    trie1.add(s);
                    right.insert(s);
                }
                for (int j = 0; j < arr.length; j++) {
                    double decide = Math.random();
                    if (decide < 0.5) {
                        trie1.delete(arr[j]);
                        right.delete(arr[j]);
                    } else if (decide < 0.75) {
                        int ans1 = trie1.search(arr[j]);
                        int ans3 = right.search(arr[j]);
                        if (ans1 != ans3) {
                            System.out.println("Oops!1111111");
                        }
                    } else {
                        int ans1 = trie1.prefix(arr[j]);
                        int ans3 = right.prefixNumber(arr[j]);
                        if (ans1 != ans3) {
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
