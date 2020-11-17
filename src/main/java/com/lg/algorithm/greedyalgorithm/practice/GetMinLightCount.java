package com.lg.algorithm.greedyalgorithm.practice;

/**
 * 利用贪心算法求解的题目
 *
 * @author Xulg
 * Created in 2020-11-10 14:13
 */
class GetMinLightCount {

    /*
     * 给定一个字符串str，只由'X'和'.'两种字符组成。
     *  'X'字符表示墙，不能放灯，也不需要点灯
     *  '.'字符表示居民点，可以放灯，需要点亮
     * 如果灯放在i位置，可以让i-1，i，i+1三个位置被点亮。
     * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯？
     *  例如：
     *      String str = "X..XX....X.X..X";
     *      X   .   .   X   X   .   .   .   .   X   .   X   .   .   X
     *         ●              ●           ●      ●      ●
     *      最少需要5个灯，才能照亮所有的居民点('.')
     */

    /* 暴力解法 */

    public static int violence() {
        // TODO 2020/11/12 ......
        return 0;
    }

    /* 贪心解法 */

    public static int greedy(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int lightCount = 0;
        char[] chars = str.toCharArray();
        for (int idx = 0; idx < chars.length; ) {
            // 墙('X')不需要灯，直接判断下一个字符去了
            if (chars[idx] == 'X') {
                idx++;
            }
            // 居民点('.')需要判断这个点要不要放灯
            else {
                // 看当前位置的下1个，下2个是什么情况
                if (idx + 1 < chars.length) {
                    // 居民点('.')后面就是墙('X')，需要点灯
                    if (chars[idx + 1] == 'X') {
                        lightCount++;
                        // 跳过后面这个墙了，直接判断后面的去
                        idx = idx + 2;
                    }
                    // 居民点后面还是居民点
                    else {
                        if (idx + 2 < chars.length) {
                            // 当前位置后面是居民点('.')，墙('X')，这个位置需要点灯
                            if (chars[idx + 2] == 'X') {
                                lightCount++;
                                // 跳过后面居民点和墙，直接判断后面的去
                                idx = idx + 3;
                            }
                            // 当前位置后面是两个居民点('.')，(idx+1)位置需要点灯
                            else {
                                lightCount++;
                                // 跳过后面两个居民点，直接判断后面的去
                                idx = idx + 3;
                            }
                        } else {
                            // 当前位置的后面只有一个居民点，需要点灯
                            lightCount++;
                            // 跳过后面这个居民点了
                            idx = idx + 2;
                        }
                    }
                } else {
                    // 没有下一个位置了，当前居民点是末尾了，需要一个灯
                    lightCount++;
                    break;
                }
            }
        }
        return lightCount;
    }

    /* ****************************************************************************************************************/

    /* 对数器 */

    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static int minLight(String road) {
        char[] str = road.toCharArray();
        int index = 0;
        int light = 0;
        while (index < str.length) {
            if (str[index] == 'X') {
                index++;
            } else { // i -> .
                light++;
                if (index + 1 == str.length) {
                    break;
                } else {
                    if (str[index + 1] == 'X') {
                        index = index + 2;
                    } else {
                        index = index + 3;
                    }
                }
            }
        }
        return light;
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = greedy(test);
            int ans2 = minLight(test);
            if (ans1 != ans2) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }
}
