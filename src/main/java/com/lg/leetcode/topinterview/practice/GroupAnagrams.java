package com.lg.leetcode.topinterview.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-03-04 15:36
 */
class GroupAnagrams {

    /*
     * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
     * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
     * typically using all the original letters exactly once.
     *
     * Example 1:
     * Input: strs = ["eat","tea","tan","ate","nat","bat"]
     * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
     *
     * Example 2:
     * Input: strs = [""]
     * Output: [[""]]
     *
     * Example 3:
     * Input: strs = ["a"]
     * Output: [["a"]]
     *
     * Constraints:
     *  1 <= strs.length <= 104
     *  0 <= strs[i].length <= 100
     *  strs[i] consists of lower-case English letters.
     */

    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>(0);
        }
        HashMap<String, List<String>> indexMap = new HashMap<>();
        for (String str : strs) {
            // 对字符串的字符进行字典序排列
            char[] chars = str.toCharArray();

            // 排序
            if (false) {
                int[] helper = new int[26];
                for (int i = 0; i < chars.length; i++) {
                    helper[chars[i] - 'a']++;
                }
                for (int i = 0, idx = 0; i < helper.length; i++) {
                    if (helper[i] > 0) {
                        for (int c = 0; c < helper[i]; c++) {
                            chars[idx++] = (char) (i + 'a');
                        }
                    }
                }
            }
            Arrays.sort(chars);

            String newStr = new String(chars);
            if (indexMap.containsKey(newStr)) {
                indexMap.get(newStr).add(str);
            } else {
                indexMap.put(newStr, new ArrayList<>(Collections.singletonList(str)));
            }
        }
        return new ArrayList<>(indexMap.values());
    }

    public static void main(String[] args) {
        List<List<String>> lists = groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
        for (List<String> list : lists) {
            System.out.println(list);
        }

        lists = groupAnagrams(new String[]{""});
        for (List<String> list : lists) {
            System.out.println(list);
        }

        lists = groupAnagrams(new String[]{"a"});
        for (List<String> list : lists) {
            System.out.println(list);
        }
    }
}
