package com.lg.algorithm.violencerecursion.practice.base;

import cn.hutool.core.util.ArrayUtil;

import java.util.ArrayList;

/**
 * @author Xulg
 * Created in 2020-11-23 17:51
 */
class StringAllPermutation {

    /*
     * 打印一个字符串的全部排列
     *------------------------------------------------------
     *不需要额外空间的实现思路：
     *  字符串"abc"，字符数组为char[] chars = {'a', 'b', 'c'};
     *              [a, b, c]
     *               0  1  2
     * 执行流程如下：
     *  0a→0表示0位置的字符a换到0位置
     *  1b→0表示1位置的字符b换到0位置
     *  已经选择过的位置是不能再选的
     *                                      [a, b, c]
     *                                     ↙   ↓    ↘
     *                                  ↙      ↓       ↘
     *                              0a→0     1b→0       2c→0
     *                            ↙            ↓            ↘
     *                         ↙               ↓               ↘
     *                    [a, b, c]          [b, a, c]          [c, b, a]           0位置选择完毕
     *                    ↓     ↓          ↓     ↓          ↓     ↓
     *                    ↓     ↓          ↓     ↓          ↓     ↓
     *                  1b→1  2c→1       1a→1   2c→1       1b→1  2a→1
     *                   ↓      ↓          ↓      ↓          ↓     ↓
     *                   ↓      ↓          ↓      ↓          ↓     ↓
     *                [a,b,c]  [a,c,b]    [b,a,c]  [b,c,a]    [c,b,a] [c,a,b]       1位置选择完毕
     *                  ↓       ↓         ↓      ↓          ↓      ↓
     *                  ↓       ↓         ↓      ↓          ↓      ↓
     *                2c→2    2b→2      2c→2   2a→2       2a→2   2b→2         2位置只剩1个字符了，选择完毕
     *                  ↓       ↓         ↓      ↓          ↓      ↓
     *                 abc      acb        bac     bca         cba      cab         最终结果
     */

    /**
     * 字符串全排列
     * String str = "abc";
     * 排列结果：
     * aaa
     * aab
     * aac
     * aba
     * abb
     * ...
     * bbb
     * ccc
     * 总共有(3^3)个
     */
    public static ArrayList<String> print1(String str) {
        if (str == null || str.length() == 0) {
            return new ArrayList<>(0);
        }
        char[] chars = str.toCharArray();
        ArrayList<String> allPaths = new ArrayList<>();
        recurse(chars, 0, "", allPaths);
        return allPaths;
    }

    private static void recurse(char[] chars, int currentIdx,
                                String path, ArrayList<String> allPaths) {
        // 递归的base case
        if (chars.length == currentIdx) {
            // 角标越界了，结束
            allPaths.add(path);
        } else {
            for (char c : chars) {
                recurse(chars, currentIdx + 1, path + c, allPaths);
            }
        }
    }

    /**
     * 字符串全排列
     * 这种方法耗费空间
     * String str = "abc";
     * 排列结果：
     * abc
     * acb
     * bac
     * bca
     * cab
     * cba
     * 总共有(3!)个
     */
    public static ArrayList<String> print2(String str) {
        if (str == null || str.length() == 0) {
            return new ArrayList<>(0);
        }
        char[] chars = str.toCharArray();
        ArrayList<String> allPaths = new ArrayList<>();
        recurse(chars, "", allPaths);
        return allPaths;
    }

    private static void recurse(char[] chars, String path,
                                ArrayList<String> allPaths) {
        // 递归的base case
        if (chars.length == 0) {
            allPaths.add(path);
        } else {
            for (int i = 0; i < chars.length; i++) {
                char[] newChars = ArrayUtil.remove(chars, i);
                recurse(newChars, path + chars[i], allPaths);
            }
        }
    }

    /**
     * 字符串全排列
     * 这种方法不需要额外空间
     */
    public static ArrayList<String> print3(String str) {
        if (str == null || str.length() == 0) {
            return new ArrayList<>(0);
        }
        char[] chars = str.toCharArray();
        ArrayList<String> allPaths = new ArrayList<>();
        recurse(chars, 0, allPaths);
        return allPaths;
    }

    /**
     * chars[0, currentIdx-1]已经做好决定的
     * chars[currentIdx...]都有机会来到currentIdx位置
     * currentIdx终止位置，chars[]当前的样子，就是一种path结果
     */
    private static void recurse(char[] chars, int currentIdx, ArrayList<String> allPaths) {
        // base case
        if (currentIdx == chars.length) {
            String path = new String(chars);
            allPaths.add(path);
        } else {
            for (int idx = currentIdx; idx < chars.length; idx++) {
                swap(chars, idx, currentIdx);
                recurse(chars, currentIdx + 1, allPaths);
                // 这里要恢复现场
                swap(chars, idx, currentIdx);
            }
        }
    }

    private static void swap(char[] chars, int a, int b) {
        char temp = chars[a];
        chars[a] = chars[b];
        chars[b] = temp;
    }

    public static void main(String[] args) {
        String str = "abc";
        ArrayList<String> allPaths = print1(str);
        for (String path : allPaths) {
            System.out.println(path);
        }
        allPaths = print2(str);
        for (String path : allPaths) {
            System.out.println(path);
        }
        allPaths = print3(str);
        for (String path : allPaths) {
            System.out.println(path);
        }
    }

}
