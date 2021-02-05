package com.lg.algorithm.kmp;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串匹配BF算法
 *
 * @author Xulg
 * Created in 2021-02-01 9:05
 */
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel", "DuplicatedCode"})
class BF {

    /*
     * BF算法的实现思路
     *      str = "abcdefg";
     *      match = "cdef";
     *      strChars    a   b   c   d   e   f   g
     *                  0   1   2   3   4   5   6
     *        strIdx   ↑
     *    matchChars    c   d   e   f
     *                  0   1   2   3
     *        matchIdx ↑
     *--------------------------------------------
     *      对于任意的位置strIdx，都有：
     *          str串从strIdx位置开始，match串每次都从0位置开始比较下去，如果
     *          （1）strChars[strIdx] == matchChars[matchIdx]
     *                  str串，match串都比较下一个位置去(即strIdx++，matchIdx++)
     *          （2）strChars[strIdx] != matchChars[matchIdx]
     *                  str串的比较位置需要回到最初的位置(回溯)的下一个位置，
     *                  match串重新从0位置开始比较
     *============================================
     *============================================
     *      strChars    a   b   c   d   e   f   g
     *                  0   1   2   3   4   5   6
     *        strIdx   ↑
     *    matchChars    c   d   e   f
     *                  0   1   2   3
     *        matchIdx ↑
     *          strChars[0] != matchChars[0]，strIdx回溯并移动到下一个位置(strIdx=1)，matchIdx重新从0开始
     *============================================
     *      strChars    a   b   c   d   e   f   g
     *                  0   1   2   3   4   5   6
     *        strIdx       ↑
     *    matchChars    c   d   e   f
     *                  0   1   2   3
     *        matchIdx ↑
     *          strChars[1] != matchChars[0]，strIdx回溯并移动到下一个位置(strIdx=2)，matchIdx重新从0开始
     *============================================
     *      strChars    a   b   c   d   e   f   g
     *                  0   1   2   3   4   5   6
     *        strIdx           ↑
     *    matchChars    c   d   e   f
     *                  0   1   2   3
     *        matchIdx ↑
     *          strChars[2] == matchChars[0]，strIdx++，matchIdx++
     *============================================
     *      strChars    a   b   c   d   e   f   g
     *                  0   1   2   3   4   5   6
     *        strIdx               ↑
     *    matchChars    c   d   e   f
     *                  0   1   2   3
     *        matchIdx      ↑
     *          strChars[3] == matchChars[1]，strIdx++，matchIdx++
     *============================================
     *      strChars    a   b   c   d   e   f   g
     *                  0   1   2   3   4   5   6
     *        strIdx                   ↑
     *    matchChars    c   d   e   f
     *                  0   1   2   3
     *        matchIdx         ↑
     *          strChars[4] == matchChars[2]，strIdx++，matchIdx++
     *============================================
     *      strChars    a   b   c   d   e   f   g
     *                  0   1   2   3   4   5   6
     *        strIdx                       ↑
     *    matchChars    c   d   e   f
     *                  0   1   2   3
     *        matchIdx             ↑
     *          strChars[5] == matchChars[3]，strIdx++，matchIdx++
     *============================================
     *      当matchIdx位置越界则表示str串中找到了match串，(strIdx-matchIdx)就是匹配结果位置
     *      若matchIdx位置没有越界，也就是strIdx位置已经越界，表示str串中没有match串，返回-1
     */

    /**
     * 字符串匹配-BF算法
     * 使用了回溯算法的思想
     * 时间复杂度：O(N*M) N为str串长度，M为match串长度
     */
    public static int indexOf1(String str, String match) {
        if (str == null || match == null || match.length() < 1 || str.length() < match.length()) {
            return -1;
        }

        char[] strChars = str.toCharArray();
        char[] matchChars = match.toCharArray();

        int strIdx = 0;
        int matchIdx = 0;
        // 首先都不能越界
        while (strIdx < strChars.length && matchIdx < matchChars.length) {
            // 记录str串开始匹配的位置，便于后续恢复
            // 用于回溯
            int oldStrIdx = strIdx;
            // str串从strIdx位置开始，match串从matchIdx位置开始 匹配操作
            while (strIdx < strChars.length && matchIdx < matchChars.length) {
                if (strChars[strIdx] == matchChars[matchIdx]) {
                    // 继续比较下一个位置去
                    strIdx++;
                    matchIdx++;
                } else {
                    // str串从之前的匹配位置的下一个位置开始匹配
                    // 进行回溯，回到原位置的下一个
                    strIdx = oldStrIdx + 1;
                    // match串重新开始匹配
                    matchIdx = 0;
                    break;
                }
            }
        }
        // match串越界了肯定匹配出结果了
        return (matchIdx == matchChars.length) ? strIdx - matchIdx : -1;
    }

    /**
     * 字符串匹配-BF算法
     * 使用了回溯算法的思想
     * 时间复杂度：O(N*M) N为str串长度，M为match串长度
     */
    public static int indexOf2(String str, String match) {
        if (str == null || match == null || match.length() < 1 || str.length() < match.length()) {
            return -1;
        }

        char[] strChars = str.toCharArray();
        char[] matchChars = match.toCharArray();

        int strIdx = 0;
        int matchIdx = 0;
        // 首先都不能越界
        while (strIdx < strChars.length && matchIdx < matchChars.length) {
            if (strChars[strIdx] == matchChars[matchIdx]) {
                // 相等则比较下一个位置去
                strIdx++;
                matchIdx++;
            } else {
                // str串需要回溯到原位置的下一个
                // (strIdx - matchIdx)就是str串最开始匹配的位置
                strIdx = strIdx - matchIdx + 1;
                // match串重新开始
                matchIdx = 0;
            }
        }
        // match串越界了肯定匹配出结果了
        return (matchIdx == matchChars.length) ? strIdx - matchIdx : -1;
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

    public static void main(String[] args) {
        System.out.println(indexOf1("abcdefg", "cdef"));
        System.out.println(indexOf1("abcdefg", "cded"));
        System.out.println(indexOf1("abcdefg", "abcd"));
        System.out.println(indexOf1("abcdefg", "abdc"));
        System.out.println(indexOf1("abcdefg", "efg"));
        System.out.println(indexOf1("abcdefg", "egf"));

        {
            Map<Integer, Integer> map = new HashMap<>(16);
            map.put(-1, 0);
            map.put(0, 0);
            map.put(1, 0);
            map.put(2, 0);
            int times = 1000000;
            String baseString = "ABCDEFG";
            for (int time = 0; time < times; ) {
                String str = RandomUtil.randomString(baseString, 5);
                String match = RandomUtil.randomString(baseString, 3);
                int r = StringUtils.indexOf(str, match);
                int r1 = indexOf1(str, match);
                int r2 = indexOf2(str, match);
                if (r != -1) {
                    time++;
                }
                map.put(r, map.get(r) + 1);
                if (!(r == r1 && r1 == r2)) {
                    System.out.println("Oops str=" + str + "     match=" + match);
                    break;
                }
            }
            System.out.println("finish!");
        }

        {
            Map<Integer, Integer> map = new HashMap<>(16);
            int possibilities = 5;
            int strSize = 20;
            int matchSize = 5;
            int testTimes = 5000000;
            System.out.println("test begin");
            for (int i = 0; i < testTimes; i++) {
                String str = getRandomString(possibilities, strSize);
                String match = getRandomString(possibilities, matchSize);
                int r = str.indexOf(match);
                int r1 = indexOf1(str, match);
                int r2 = indexOf2(str, match);
                if (map.containsKey(r)) {
                    map.put(r, map.get(r) + 1);
                } else {
                    map.put(r, 1);
                }
                if (!(r == r1 && r1 == r2)) {
                    System.out.println("Oops!");
                }
            }
            System.out.println("test finish");
        }
    }

}
