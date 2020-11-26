package com.lg.algorithm.violencerecursion.practice.base;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Xulg
 * Created in 2020-11-23 17:46
 */
@SuppressWarnings({"DuplicatedCode", "UnnecessaryLocalVariable"})
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

    /**
     * 获取字符串的所有的子序列，带重复的
     */
    public static ArrayList<String> getAllSubsequence(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] chars = str.toCharArray();
        ArrayList<String> allSubsequences = new ArrayList<>();
        recurse1(chars, 0, "", allSubsequences);
        return allSubsequences;
    }

    /**
     * chars固定不变
     * currentIdx此时来到的位置，要or不要
     * 如果currentIdx来到了chars中的终止位置，把沿途路径所形成的答案，放入allSubsequences中
     * 之前做出的选择，就是path
     */
    private static void recurse1(char[] chars, int currentIdx, String subSequence,
                                 ArrayList<String> allSubsequences) {
        if (chars.length == currentIdx) {
            // 每个字符都判断完了，可以退出了
            allSubsequences.add(subSequence);
        } else {
            // 不选择这个字符
            String noChoose = subSequence;
            recurse1(chars, currentIdx + 1, noChoose, allSubsequences);
            // 选择这个字符
            String yesChoose = subSequence + chars[currentIdx];
            recurse1(chars, currentIdx + 1, yesChoose, allSubsequences);
        }
    }

    /**
     * 获取字符串的所有的子序列，不重复的
     * 这里只能使用hash表去重？
     */
    public static ArrayList<String> getAllNonRepetitiveSubsequence(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] chars = str.toCharArray();
        HashSet<String> allSubsequences = new HashSet<>();
        recurse2(chars, 0, "", allSubsequences);
        return new ArrayList<>(allSubsequences);
    }

    private static void recurse2(char[] chars, int currentIdx, String subSequence, HashSet<String> allSubsequences) {
        if (currentIdx == chars.length) {
            // 每个字符都判断完了，可以退出了
            allSubsequences.add(subSequence);
        } else {
            // 不选择这个字符
            String noChoose = subSequence;
            recurse2(chars, currentIdx + 1, noChoose, allSubsequences);
            // 选择这个字符
            String yesChoose = subSequence + chars[currentIdx];
            recurse2(chars, currentIdx + 1, yesChoose, allSubsequences);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> allSubsequences = getAllSubsequence("aac");
        for (String subsequence : allSubsequences) {
            System.out.println(subsequence);
        }
        ArrayList<String> allNonRepetitiveSubsequence = getAllNonRepetitiveSubsequence("aac");
        for (String subsequence : allNonRepetitiveSubsequence) {
            System.out.println(subsequence);
        }
    }
}
