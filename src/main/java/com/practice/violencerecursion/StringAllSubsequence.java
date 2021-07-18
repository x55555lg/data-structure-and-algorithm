package com.practice.violencerecursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Xulg
 * Description: 字符串的所有子序列
 * Created in 2021-05-19 15:12
 */
class StringAllSubsequence {

    /*
     * 打印一个字符串的全部子序列，例如：
     *  字符串"abc"的所有子序列有：abc ab ac a bc b c ""
     *--------------------------------------------------
     * 思路：字符串"abc"，字符数组为char[] chars = {'a', 'b', 'c'};
     *       idx表示当前来到了字符数组chars的哪个位置
     *          idx = 0, chars[0] = a
     *          idx = 1, chars[1] = b
     *          idx = 2, chars[1] = c
     *       每一步都判断要不要当前的这个字符：
     *          不要当前字符
     *          要当前字符
     *                                        idx=0
     *                                     ↙       ↘
     *                                  ↙             ↘
     *                               选a                 不选a
     *                            ↙                         ↘
     *                         ↙                               ↘
     *                     idx=1                                  idx=1
     *                   ↙     ↘                              ↙     ↘
     *                选b       不选b                        选b        不选b
     *             ↙                ↘                    ↙                ↘
     *         idx=2                  idx=2              idx=2                idx=2
     *       ↙     ↘               ↙   ↘          ↙     ↘             ↙     ↘
     *     选c      不选c          选c     不选c     选c      不选c       选c      不选c
     *     ↓        ↓            ↓       ↓       ↓        ↓         ↓        ↓
     *    abc        ab            ac       a        bc        b          c         ""
     *
     */

    private static class BruteForce {

        public static List<String> getAllNotRepeatSubsequences(String str) {
            if (str == null) {
                return null;
            }
            Set<String> set = new HashSet<>();
            recurse(0, "", set, str.toCharArray());
            return new ArrayList<>(set);
        }

        private static void recurse(int curIdx, String path, Set<String> set, char[] chars) {
            if (curIdx == chars.length) {
                set.add(path);
                return;
            }
            // 用当前字符
            recurse(curIdx + 1, path + chars[curIdx], set, chars);
            // 不用当前字符
            recurse(curIdx + 1, path, set, chars);
        }

        public static List<String> getAllSubsequences(String str) {
            if (str == null) {
                return null;
            }
            List<String> list = new ArrayList<>();
            recurse(0, "", list, str.toCharArray());
            return list;
        }

        private static void recurse(int curIdx, String path, List<String> list, char[] chars) {
            if (curIdx == chars.length) {
                list.add(path);
                return;
            }
            // 用当前字符
            recurse(curIdx + 1, path + chars[curIdx], list, chars);
            // 不用当前字符
            recurse(curIdx + 1, path, list, chars);
        }
    }

    public static void main(String[] args) {
        System.out.println(BruteForce.getAllSubsequences("aacc"));
        System.out.println(BruteForce.getAllNotRepeatSubsequences("aacc"));
    }

}
