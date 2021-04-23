package com.lg.leetcode.top100likedquestions.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-03-11 16:47
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
class WordBreak {

    /*
     *  Given a string s and a dictionary of strings wordDict, return true if s can be segmented into
     * a space-separated sequence of one or more dictionary words.
     *  Note that the same word in the dictionary may be reused multiple times in the segmentation.
     *
     * Example 1:
     * Input: s = "leetcode", wordDict = ["leet","code"]
     * Output: true
     * Explanation: Return true because "leetcode" can be segmented as "leet code".
     *
     * Example 2:
     * Input: s = "applepenapple", wordDict = ["apple","pen"]
     * Output: true
     * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
     * Note that you are allowed to reuse a dictionary word.
     *
     * Example 3:
     * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
     * Output: false
     *
     * Constraints:
     *  1 <= s.length <= 300
     *  1 <= wordDict.length <= 1000
     *  1 <= wordDict[i].length <= 20
     *  s and wordDict[i] consist of only lowercase English letters.
     *  All the strings of wordDict are unique.
     */

    public boolean wordBreak(String s, List<String> wordDict) {
        return BruteForce1.wordBreak(s, wordDict);
    }

    /**
     * 超级暴力解法1
     */
    private static class BruteForce1 {

        public static boolean wordBreak(String str, List<String> wordDict) {
            int[] strChars = new int[26];
            for (char c : str.toCharArray()) {
                strChars[c - 'a']++;
            }
            List<int[]> dictionary = new ArrayList<>(wordDict.size());
            for (String word : wordDict) {
                int[] wordChars = new int[26];
                for (char c : word.toCharArray()) {
                    wordChars[c - 'a']++;
                }
                dictionary.add(wordChars);
            }
            return recurse(strChars, dictionary);
        }

        private static boolean recurse(int[] restStrChars, List<int[]> dictionary) {
            if (isEmpty(restStrChars)) {
                return true;
            }
            boolean wordBreak = false;
            for (int[] wordChars : dictionary) {
                if (!canSubAll(restStrChars, wordChars)) {
                    continue;
                }
                int[] rest = sub(restStrChars, wordChars);
                wordBreak = wordBreak || recurse(rest, dictionary);
            }
            return wordBreak;
        }

        private static boolean canSubAll(int[] str, int[] word) {
            for (int i = 0; i < str.length; i++) {
                //if (str[i] > 0 && word[i] > 0 && str[i] >= word[i]) {
                if (str[i] < word[i]) {
                    return false;
                }
            }
            return true;
        }

        private static int[] sub(int[] str, int[] word) {
            // 字符相减
            int[] newStr = new int[26];
            for (int i = 0; i < newStr.length; i++) {
                if (str[i] > 0) {
                    newStr[i] = str[i] - Math.min(str[i], word[i]);
                }
            }
            return newStr;
        }

        private static boolean isEmpty(int[] str) {
            if (str == null || str.length == 0) {
                return true;
            }
            for (int i = 0; i < str.length; i++) {
                if (str[i] > 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 超级暴力解法2
     */
    private static class BruteForce2 {
        public static boolean wordBreak(String str, List<String> wordDict) {
            return recurse(str, wordDict);
        }

        private static boolean recurse(String restStr, List<String> words) {
            if (restStr.isEmpty()) {
                return true;
            }
            boolean wordBreak = false;
            for (String word : words) {
                if (!restStr.startsWith(word)) {
                    continue;
                }
                String newRestStr = restStr.replaceFirst(word, "");
                wordBreak = wordBreak || recurse(newRestStr, words);
            }
            return wordBreak;
        }
    }

    private static class MemorySearch2 {
        public static boolean wordBreak(String str, List<String> wordDict) {
            HashMap<String, Boolean> table = new HashMap<>(16);
            table.put("", true);
            return recurse(str, wordDict, table);
        }

        private static boolean recurse(String restStr, List<String> words, HashMap<String, Boolean> table) {
            if (table.containsKey(restStr)) {
                return table.get(restStr);
            }
            if (restStr.isEmpty()) {
                return true;
            }
            boolean wordBreak = false;
            for (String word : words) {
                if (!restStr.startsWith(word)) {
                    continue;
                }
                String newRestStr = restStr.replaceFirst(word, "");
                wordBreak = wordBreak || recurse(newRestStr, words, table);
            }
            table.put(restStr, wordBreak);
            return wordBreak;
        }
    }

    /**
     * 优化后的暴力解法
     */
    private static class BruteForce3 {
        public static boolean wordBreak(String str, List<String> wordDict) {
            return recurse(0, str, wordDict);
        }

        // 字符串str[startIdx...]范围的字符能否用词典匹配上
        private static boolean recurse(int startIdx, String str, List<String> words) {
            if (startIdx >= str.length()) {
                return true;
            }
            boolean wordBreak = false;
            for (String word : words) {
                if (!str.startsWith(word, startIdx)) {
                    continue;
                }
                int newStartIdx = startIdx + word.length();
                wordBreak = wordBreak || recurse(newStartIdx, str, words);
            }
            return wordBreak;
        }
    }

    private static class MemorySearch3 {
        public static boolean wordBreak(String str, List<String> wordDict) {
            HashMap<Integer, Boolean> table = new HashMap<>(16);
            table.put(str.length(), true);
            return recurse(0, str, wordDict, table);
        }

        // 字符串str[startIdx...]范围的字符能否用词典匹配上
        private static boolean recurse(int startIdx, String str, List<String> words, HashMap<Integer, Boolean> table) {
            if (table.containsKey(startIdx)) {
                return table.get(startIdx);
            }
            if (startIdx >= str.length()) {
                return true;
            }
            boolean wordBreak = false;
            for (String word : words) {
                if (!str.startsWith(word, startIdx)) {
                    continue;
                }
                // 减掉word长度后，从新的位置开始匹配
                int newStartIdx = startIdx + word.length();
                wordBreak = wordBreak || recurse(newStartIdx, str, words, table);
            }
            table.put(startIdx, wordBreak);
            return wordBreak;
        }
    }

    private static class DP3 {
        public static boolean wordBreak(String str, List<String> wordDict) {
            boolean[] table = new boolean[str.length() + 1];
            // base case
            table[str.length()] = true;
            // 填表
            for (int startIdx = str.length() - 1; startIdx >= 0; startIdx--) {
                boolean wordBreak = false;
                for (String word : wordDict) {
                    if (!str.startsWith(word, startIdx)) {
                        continue;
                    }
                    // 减掉word长度后，从新的位置开始匹配
                    int newStartIdx = startIdx + word.length();
                    if (newStartIdx > str.length()) {
                        wordBreak = true;
                    } else {
                        wordBreak = wordBreak || table[newStartIdx];
                    }
                }
                table[startIdx] = wordBreak;
            }
            return table[0];
        }
    }

    public static void main(String[] args) {
        {
            String str = "applepenapple";
            String word = "apple";

            int[] strChars = new int[26];
            for (char c : str.toCharArray()) {
                strChars[c - 'a']++;
            }
            int[] wordChars = new int[26];
            for (char c : word.toCharArray()) {
                wordChars[c - 'a']++;
            }

            int[] subtracte = BruteForce1.sub(strChars, wordChars);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < subtracte.length; i++) {
                if (subtracte[i] > 0) {
                    char c = (char) (i + 'a');
                    for (int j = 0; j < subtracte[i]; j++) {
                        sb.append(c);
                    }
                }
            }
            System.out.println(sb.toString());
        }

        {
            System.out.println(BruteForce2.wordBreak("leetcode", new ArrayList<>(Arrays.asList("leet", "code"))));
            System.out.println(BruteForce2.wordBreak("applepenapple", new ArrayList<>(Arrays.asList("apple", "pen"))));
            System.out.println(BruteForce2.wordBreak("catsandog", new ArrayList<>(Arrays.asList("cats", "dog", "sand", "and", "cat"))));
            System.out.println(BruteForce2.wordBreak("ccbb", new ArrayList<>(Arrays.asList("bc", "cb"))));
            System.out.println(BruteForce2.wordBreak("cars", new ArrayList<>(Arrays.asList("car", "ca", "rs"))));

            System.out.println(MemorySearch2.wordBreak("leetcode", new ArrayList<>(Arrays.asList("leet", "code"))));
            System.out.println(MemorySearch2.wordBreak("applepenapple", new ArrayList<>(Arrays.asList("apple", "pen"))));
            System.out.println(MemorySearch2.wordBreak("catsandog", new ArrayList<>(Arrays.asList("cats", "dog", "sand", "and", "cat"))));
            System.out.println(MemorySearch2.wordBreak("ccbb", new ArrayList<>(Arrays.asList("bc", "cb"))));
            System.out.println(MemorySearch2.wordBreak("cars", new ArrayList<>(Arrays.asList("car", "ca", "rs"))));

            System.out.println(BruteForce3.wordBreak("leetcode", new ArrayList<>(Arrays.asList("leet", "code"))));
            System.out.println(BruteForce3.wordBreak("applepenapple", new ArrayList<>(Arrays.asList("apple", "pen"))));
            System.out.println(BruteForce3.wordBreak("catsandog", new ArrayList<>(Arrays.asList("cats", "dog", "sand", "and", "cat"))));
            System.out.println(BruteForce3.wordBreak("ccbb", new ArrayList<>(Arrays.asList("bc", "cb"))));
            System.out.println(BruteForce3.wordBreak("cars", new ArrayList<>(Arrays.asList("car", "ca", "rs"))));

            System.out.println(MemorySearch3.wordBreak("leetcode", new ArrayList<>(Arrays.asList("leet", "code"))));
            System.out.println(MemorySearch3.wordBreak("applepenapple", new ArrayList<>(Arrays.asList("apple", "pen"))));
            System.out.println(MemorySearch3.wordBreak("catsandog", new ArrayList<>(Arrays.asList("cats", "dog", "sand", "and", "cat"))));
            System.out.println(MemorySearch3.wordBreak("ccbb", new ArrayList<>(Arrays.asList("bc", "cb"))));
            System.out.println(MemorySearch3.wordBreak("cars", new ArrayList<>(Arrays.asList("car", "ca", "rs"))));

            System.out.println(DP3.wordBreak("leetcode", new ArrayList<>(Arrays.asList("leet", "code"))));
            System.out.println(DP3.wordBreak("applepenapple", new ArrayList<>(Arrays.asList("apple", "pen"))));
            System.out.println(DP3.wordBreak("catsandog", new ArrayList<>(Arrays.asList("cats", "dog", "sand", "and", "cat"))));
            System.out.println(DP3.wordBreak("ccbb", new ArrayList<>(Arrays.asList("bc", "cb"))));
            System.out.println(DP3.wordBreak("cars", new ArrayList<>(Arrays.asList("car", "ca", "rs"))));
        }
    }
}
