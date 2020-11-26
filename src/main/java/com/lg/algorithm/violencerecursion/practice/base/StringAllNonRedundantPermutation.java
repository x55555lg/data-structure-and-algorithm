package com.lg.algorithm.violencerecursion.practice.base;

import java.util.ArrayList;

/**
 * @author Xulg
 * Created in 2020-11-23 17:51
 */
@SuppressWarnings("DuplicatedCode")
class StringAllNonRedundantPermutation {

    /*
     * 打印一个字符串的全部排列，不能重复
     *------------------------------------------------------
     *不需要额外空间的实现思路：
     *  字符串"abc"，字符数组为char[] chars = {'a', 'b', 'c'};
     *              [a, b, c]
     *               0  1  2
     * 执行流程如下：
     *  0a→0表示0位置的字符a换到0位置
     *  1b→0表示1位置的字符b换到0位置
     *  已经选择过的位置是不能再选的
     *                                      [a, a, c]
     *                                     ↙   ↓    ↘
     *                                  ↙      ↓       ↘
     *                              0a→0     1a→0       2c→0
     *                            ↙           跳过           ↘
     *                         ↙              a前面             ↘
     *                    [a, a, c]            访问           [c, a, a]           0位置选择完毕
     *                    ↓     ↓            过了           ↓     ↓
     *                    ↓     ↓                           ↓     ↓
     *                  1a→1  2c→1                        1a→1  2a→1
     *                   ↓      ↓                           ↓    跳过，a前面访问过了
     *                   ↓      ↓                           ↓
     *                [a,a,c]  [a,c,a]                      [c,a,a]               1位置选择完毕
     *                  ↓       ↓                           ↓
     *                  ↓       ↓                           ↓
     *                2c→2    2a→2                        2a→2                 2位置只剩1个字符了，选择完毕
     *                  ↓       ↓                           ↓
     *                 aac      aca                          caa
     */

    public static ArrayList<String> getAllNonRedundantPermutation(String str) {
        if (str == null || str.length() == 0) {
            return new ArrayList<>(0);
        }
        char[] chars = str.toCharArray();
        ArrayList<String> allPaths = new ArrayList<>();
        recurse(chars, 0, allPaths);
        return allPaths;
    }

    private static void recurse(char[] chars, int currentIdx, ArrayList<String> allPaths) {
        if (currentIdx == chars.length) {
            String path = new String(chars);
            allPaths.add(path);
        } else {
            // 字符有没有访问过的记录表
            // idx：  表示字符的ASCII码值。例如，idx=0表示字符'a'、idx=1表示字符'b'、idx=25表示字符'z'
            // value：表示角标idx对应的这个字符有没有访问过
            boolean[] visited = new boolean[26];
            for (int idx = currentIdx; idx < chars.length; idx++) {
                // 计算这个字符在访问记录表中的位置
                int visitedIdx = chars[idx] - 'a';
                // 这个字符没有访问过才进入递归
                if (!visited[visitedIdx]) {
                    // 标记这个字符访问过了
                    visited[visitedIdx] = true;
                    swap(chars, idx, currentIdx);
                    recurse(chars, currentIdx + 1, allPaths);
                    // 恢复现场
                    swap(chars, idx, currentIdx);
                }
            }
        }
    }

    private static void swap(char[] chars, int a, int b) {
        char temp = chars[a];
        chars[a] = chars[b];
        chars[b] = temp;
    }

    public static void main(String[] args) {
        ArrayList<String> allPaths = getAllNonRedundantPermutation("abc");
        for (String path : allPaths) {
            System.out.println(path);
        }
        allPaths = getAllNonRedundantPermutation("aac");
        for (String path : allPaths) {
            System.out.println(path);
        }
        allPaths = getAllNonRedundantPermutation("aaa");
        for (String path : allPaths) {
            System.out.println(path);
        }
    }
}
