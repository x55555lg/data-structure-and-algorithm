package com.practice.violencerecursion;

import cn.hutool.core.util.ArrayUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Xulg
 * Description: 打印一个字符串的全部排列
 * Created in 2021-05-19 15:50
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
     * Best
     */
    private static class BruteForce {
        public static List<String> getAllNotRepeatPermutation(String str) {
            if (str == null) {
                return null;
            }
            HashSet<String> list = new HashSet<>();
            recurse(0, list, str.toCharArray());
            return new ArrayList<>(list);
        }

        private static void recurse(int curIdx, Set<String> list, char[] chars) {
            if (curIdx == chars.length) {
                list.add(new String(chars));
                return;
            }
            for (int idx = curIdx; idx < chars.length; idx++) {
                swap(chars, curIdx, idx);
                recurse(curIdx + 1, list, chars);
                // 回溯
                swap(chars, curIdx, idx);
            }
        }

        private static void swap(char[] chars, int i, int j) {
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
    }

    private static class BruteForce2 {

        public static List<String> getAllNotRepeatPermutation(String str) {
            if (str == null) {
                return null;
            }
            HashSet<String> list = new HashSet<>();
            recurse("", list, str.toCharArray());
            return new ArrayList<>(list);
        }

        private static void recurse(String path, Set<String> list, char[] chars) {
            if (chars.length == 0) {
                list.add(path);
                return;
            }
            for (int idx = 0; idx < chars.length; idx++) {
                // remove the used char
                char[] newChars = ArrayUtil.remove(chars, idx);
                recurse(path + chars[idx], list, newChars);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(BruteForce.getAllNotRepeatPermutation("abc"));
        System.out.println(BruteForce2.getAllNotRepeatPermutation("abc"));
        System.out.println(BruteForce.getAllNotRepeatPermutation("aaa"));
        System.out.println(BruteForce2.getAllNotRepeatPermutation("aaa"));
    }

}
