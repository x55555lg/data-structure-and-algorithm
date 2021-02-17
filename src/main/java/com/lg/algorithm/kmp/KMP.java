package com.lg.algorithm.kmp;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 字符串匹配KMP算法
 *
 * @author Xulg
 * Created in 2021-01-31 13:25
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
class KMP {

    /*
     * KMP算法实现思路
     *---------------------------------------------------------------------
     *str串和match串如下
     * str      ... a   a   b   a   a   c   a   a   b   a   a   c ...
     *              i
     *              |
     * match        a   a   b   a   a   c   a   a   b   a   a   t
     *              0   1   2   3   4   5   6   7   8   9   10  11
     *              |
     *---------------------------------------------------------------------
     *生成match串的next数组
     * next        -1   0   X   X   X   X   X   X   X   X   X   X
     *              0   1   2   3   4   5   6   7   8   9   10  11
     *              next[0]位置固定为-1
     *              next[1]位置固定为0
     *              next[2]位置比较特殊，不是0就是1
     * next[i]的含义：
     *              表示任意位置i，在match串的[0, i-1]范围上的最长前缀后缀相等的长度
     *              例如：next[11]位置，在match串[0, 10]范围上的最长前缀是aabaa，最长后缀是aabaa，
     *                    所有最长长度为5，即next[11] = 5。
     *                    (a   a   b   a   a)   c   [a   a   b   a   a]   t
     *                     0   1   2   3   4    5    6   7   8   9   10   11
     * 完整的next数组：
     * next        { -1   0   1   0   1   2   0   1   2   3   4   5 }
     *                0   1   2   3   4   5   6   7   8   9   10  11
     *如何高效的求出next数组？
     * 用前一个位置的值求当前位置，类似动态规划的思想
     *---------------------------------------------------------------------
     *字符串比较，str串从i位置开始，match串从0位置开始
     * str      ... a   a   b   a   a   c   a   a   b   a   a   c ...
     *              i                                           j
     *                                                          |       strIdx
     *
     * match        a   a   b   a   a   c   a   a   b   a   a   t
     *              0   1   2   3   4   5   6   7   8   9   10  11
     *                                                          |       matchIdx
     *                                                                  跳转
     *                                  |                               matchIdx
     * next        -1   0   1   0   1   2   0   1   2   3   4   5
     *              0   1   2   3   4   5   6   7   8   9   10  11
     * 比较到str[j]和match[11]位置的字符不相等，即 'c' != 't'
     * matchIdx指针可以跳转到next[11]=5位置，
     * 然后str串继续从j位置，match串从5位置开始继续比较，直到其中一个串越界为止。
     * 若match串越界了，说明比较出结果了，strIdx-matchIdx就是最终结果；
     * 其他情况都是未能匹配到，返回-1。
     */

    /**
     * 字符串匹配-KMP算法
     * 时间复杂度O(N)
     */
    public static int indexOf(String str, String match) {
        if (str == null || match == null || match.length() < 1 || str.length() < match.length()) {
            return -1;
        }

        // 如何高效的生成next数组
        int[] nextArray = getNextArray(match);

        char[] strChars = str.toCharArray();
        char[] matchChars = match.toCharArray();
        int strIdx = 0;
        int matchIdx = 0;
        // 都不能越界
        while (strIdx < strChars.length && matchIdx < matchChars.length) {
            if (strChars[strIdx] == matchChars[matchIdx]) {
                // 相等则比较下一个位置去
                strIdx++;
                matchIdx++;
            } else {
                // 字符不相等了，获取match串当前位置的前缀后缀长度
                // 这里的等价条件：matchIdx == 0
                if (nextArray[matchIdx] == -1) {
                    assert matchIdx == 0 : "why matchIdx != 0";
                    // 此时matchIdx为0，match串已经跳到0位置了
                    // str串只能从下一个位置开始比较了
                    strIdx++;
                } else {
                    // strIdx位置不变，matchIdx跳到前缀串的下一个位置
                    // 这里产生了加速
                    matchIdx = nextArray[matchIdx];
                }
            }
        }
        // match串越界了肯定匹配结束了
        return matchIdx == matchChars.length ? strIdx - matchIdx : -1;
    }

    /**
     * 获取match串每个位置的最长前缀后缀的长度
     */
    private static int[] getNextArray(String match) {
        assert match != null : "what the hell?";
        if (match.length() == 1) {
            return new int[]{-1};
        }
        if (match.length() == 2) {
            return new int[]{-1, 0};
        }

        char[] chars = match.toCharArray();
        int[] next = new int[chars.length];
        // 0位置规定为-1
        next[0] = -1;
        // 1位置规定为0
        next[1] = 0;

        // 从2位置开始算
        int idx = 2;
        // cn代表，cn位置的字符，是当前和i-1位置比较的字符
        int cn = 0;
        while (idx < chars.length) {
            if (chars[idx - 1] == chars[cn]) {
                // 等价写法
                // next[idx] = next[idx - 1] + 1;
                next[idx] = cn + 1;
                // 判断下一个位置去
                idx++;
                // 下一个位置的cn
                cn = next[idx - 1];
            } else {
                if (cn > 0) {
                    // jump
                    cn = next[cn];
                } else {
                    // cn == -1 || cn ==0
                    next[idx] = 0;
                    idx++;
                }
            }
        }

        return next;
    }

    /**
     * 暴力求取next数组
     */
    private static int[] getNextArrayByViolence(String match) {
        // TODO: 2021/2/4 错误的写法
        if (match.length() == 1) {
            return new int[]{-1};
        }
        if (match.length() == 2) {
            return new int[]{-1, 0};
        }
        char[] chars = match.toCharArray();
        int[] result = new int[chars.length];
        result[0] = -1;
        result[1] = 0;
        for (int idx = 2; idx < chars.length; idx++) {
            int maxLength = 0;
            int start = 0;
            int end = idx - 1;
            int left = start;
            int right = end;
            while (left <= end) {
                boolean isAllMatch = true;
                for (int i = start, j = right; i <= left && j <= end; i++, j--) {
                    if (chars[i] != chars[j]) {
                        isAllMatch = false;
                        break;
                    }
                }
                if (isAllMatch) {
                    maxLength = Math.max(maxLength, left - start + 1);
                }
                left++;
                right--;
            }
            result[idx] = maxLength;
        }
        return result;
    }

    /* ****************************************************************************************************************/

    /* 对数器 */

    static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    /* ****************************************************************************************************************/

    public static void main2(String[] args) {
        int[] next1 = getNextArrayByViolence("abcabck");
        System.out.println(Arrays.toString(next1));
        int[] next2 = getNextArrayByViolence("abcabc2222k");
        System.out.println(Arrays.toString(next2));
        if (true) {
            int times = 1000000;
            String baseString = "ABCDEFG";
            for (int time = 0; time < times; ) {
                String str = RandomUtil.randomString(baseString, 5);
                String match = RandomUtil.randomString(baseString, 3);
                int r = StringUtils.indexOf(str, match);
                int r1 = indexOf(str, match);
                if (r != -1) {
                    time++;
                }
                if (!(r == r1)) {
                    System.out.println("Oops str=" + str + "     match=" + match);
                    break;
                }
            }
            System.out.println("finish!");
        }
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            int r1 = str.indexOf(match);
            int r2 = indexOf(str, match);
            if (map.containsKey(r1)) {
                map.put(r1, map.get(r1) + 1);
            } else {
                map.put(r1, 1);
            }
            if (r1 != r2) {
                System.out.println("Oops!");
            }
            {
                int[] nextArray = getNextArray(match);
                if (nextArray.length >= 3) {
                    set.add(nextArray[2]);
                } else {
                    set.add(Integer.MAX_VALUE);
                }
            }
        }
        System.out.println("test finish");

        {
            int[] nextArray = getNextArray("aabaacaabaat");
            System.out.println(Arrays.toString(nextArray));
        }
    }

}
