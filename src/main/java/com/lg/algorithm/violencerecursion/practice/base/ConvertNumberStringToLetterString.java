package com.lg.algorithm.violencerecursion.practice.base;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Xulg
 * Created in 2020-11-25 15:26
 */
public class ConvertNumberStringToLetterString {

    /*
     *Facebook面试原题
     * 规定1和A对应、2和B对应、3和C对应...11和K对应，那么一个数字字符串比如"111"就可以转化为:
     *      "AAA"、"KA"、"AK"
     * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果？
     *------------------------------------------------------------------------------------------------------------------
     *暴力递归解法
     * 例如：字符串 str = "12315"，字符数组 chars = [1,2,3,1,5]
     *         [ 1, 2, 3, 1, 5 ]
     *   idx     0  1  2  3  4
     *          ↑
     *    1.每个idx位置的数字chars[idx]都有2种选择：
     *          (1).chars[idx]数字单独变成字母
     *          (2).chars[idx],chars[idx+1]2个位置的数字组合成1个2位数，再变成字母
     *    2.数字的首位需要区分：
     *          chars[idx] = '0'        0是没有对应的字母的
     *          chars[idx] = '1'        可以是1位数；可以是2位数
     *          chars[idx] = '2'        可以是1位数；可以是2位数，如果是2位数，那么第二位数只能是[0,6]之间的
     *          chars[idx] = '3'~'9'    只能是1位数
     *==================================================================================================================
     * 以currentIdx = 0为例子
     *                                          chars[0]=1
     *                                             1(0)
     *                                 1位[0]                  2位[0,1]
     *                                  1->A                     12->L
     *
     *                     1位[1]              2位[1,2]
     *                     2->B=AB             23->W=AW
     *
     *           1位[2]              2位[2,3]
     *          3->C=ABC              31没有对应字母
     *                                这个分支不能组
     *                                成字母串了
     *     1位[3]      2位[3,4]
     *    1->A=ABCA    15->O=ABCO
     *    太长了不写了......
     *
     *动态规划解法
     * // TODO 2020/11/25 待续
     *
     */

    public static int getConvertCount(String numberStr) {
        // TODO 2020/11/25
        if (numberStr == null || numberStr.length() == 0) {
            return 0;
        }
        char[] chars = numberStr.toCharArray();
        return recurse(chars, 0);
    }

    @SuppressWarnings("AlibabaUndefineMagicConstant")
    private static int recurse(char[] chars, int currentIdx) {
        if (currentIdx == chars.length) {
            // 表示这种组合是ok的，返回1
            return 1;
        }
        if (chars[currentIdx] == '0') {
            // 以0开头的话，没有可以组合出来的字母啊
            return 0;
        }
        // 首位是1或者2的话可以选择是1位数还是2位数
        if (chars[currentIdx] == '1') {
            // 选择是1位数
            int count = recurse(chars, currentIdx + 1);
            // 选择是2位数，前提是不越界啊
            if (currentIdx + 1 < chars.length) {
                // 这里由于下一位已经选择过了，所有currentIdx是加2
                count = count + recurse(chars, currentIdx + 2);
            }
            return count;
        } else if (chars[currentIdx] == '2') {
            // 选择是1位数
            int count = recurse(chars, currentIdx + 1);
            // 选择是2位数，前提是不越界啊，而且只能是20,21,22,23,24,25,26
            // 所以第二位的数字只能是0,1,2,3,4,5,6
            if (currentIdx + 1 < chars.length
                    && chars[currentIdx + 1] >= '0'
                    && chars[currentIdx + 1] <= '6') {
                // 这里由于下一位已经选择过了，所有currentIdx是加2
                count = count + recurse(chars, currentIdx + 2);
            }
            return count;
        } else {
            // 首位是3,4,5,6,7,8,9的话只能是1位数
            return recurse(chars, currentIdx + 1);
        }
    }

    /* ****************************************************************************************************************/

    /* 对数器方法 */

    public static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    /**
     * str[0...i-1]已经转化完了，固定了
     * i之前的位置，如何转化已经做过决定了, 不用再关心
     * i... 有多少种转化的结果
     */
    private static int process(char[] str, int i) {
        // base case
        if (i == str.length) {
            return 1;
        }
        if (str[i] == '0') {
            return 0;
        }
        if (str[i] == '1') {
            int res = process(str, i + 1);
            if (i + 1 < str.length) {
                res += process(str, i + 2);
            }
            return res;
        }
        if (str[i] == '2') {
            int res = process(str, i + 1);
            if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
                // (i和i+1)作为单独的部分，后续有多少种方法
                res += process(str, i + 2);
            }
            return res;
        }
        return process(str, i + 1);
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        System.out.println(Math.pow(8, 10));
        int times = 100000000;
        for (int time = 0; time < times; time++) {
            // 随机生成1~8位长的数字字符串
            String numeric = RandomStringUtils.randomNumeric(1, 9);
            int count1 = getConvertCount(numeric);
            int count2 = number(numeric);
            if (count1 != count2) {
                System.out.println("Oops!======numeric为" + numeric);
            }
        }
        System.out.println("finish!");
    }

}
