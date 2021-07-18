package com.practice.trietree;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-05-11 21:40
 */
class TrieTree {

    private final Node root = new Node();

    public void add(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        char[] chars = word.toCharArray();
        Node node = root;
        node.pass++;
        for (char c : chars) {
            // 计算路径
            int pathIdx = c - 'a';
            if (node.nexts[pathIdx] == null) {
                // 路径不存在则创建
                node.nexts[pathIdx] = new Node();
            }
            Node path = node.nexts[pathIdx];
            // 路过次数+1
            path.pass++;
            // 到下一个路口
            node = path;
        }
        // 在这里结束
        node.end++;
    }

    public void delete(String word) {
        if (!contains(word)) {
            return;
        }
        Node node = root;
        node.pass--;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            int pathIdx = c - 'a';
            Node path = node.nexts[pathIdx];
            path.pass--;
            if (path.pass == 0) {
                // 没有经过的字符串了，这个路不需要了
                node.nexts[pathIdx] = null;
                return;
            }
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
            // 计算路径
            int pathIdx = c - 'a';
            if (node.nexts[pathIdx] == null) {
                // 路径不存在，肯定不包含
                return 0;
            }
            node = node.nexts[pathIdx];
        }
        return node.end;
    }

    public boolean contains(String word) {
        return search(word) > 0;
    }

    public int prefix(String word) {
        if (word == null || word.length() == 0) {
            return 0;
        }
        char[] chars = word.toCharArray();
        Node node = root;
        for (char c : chars) {
            // 计算路径
            int pathIdx = c - 'a';
            if (node.nexts[pathIdx] == null) {
                // 路径不存在，肯定不包含
                return 0;
            }
            node = node.nexts[pathIdx];
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
         * nexts[0] 表示字符a
         * nexts[1] 表示字符b
         * ...
         * nexts[25] 表示字符z
         */
        Node[] nexts = new Node[26];
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
                TrieTree trie1 = new TrieTree();
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

    private static String[] generateRandomStringArray2(int arrLen, int strLen) {
        String[] ans = new String[arrLen];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

}
