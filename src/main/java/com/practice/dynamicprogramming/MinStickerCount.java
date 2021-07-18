package com.practice.dynamicprogramming;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-05-14 14:01
 */
class MinStickerCount {

    /*
     * 给定一个字符串str，给定一个字符串类型的数组arr。
     *  arr里的每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来。
     *  返回需要至少多少张贴纸可以完成这个任务。
     * 例子：str = "babac"，arr = {"ba","c","abcd"}
     * 至少需要两张贴纸"ba"和"abcd"，因为使用这两张贴纸，把每一个字符单独剪开，含有2个a、2个b、1个c。是可以拼出str的。所以返回2。
     *------------------------------------------------------------------------------------------------------------------
     * 暴力递归的思路：
     *                贴纸stickers：           {"ba", "c", "abcd"}
     *                剩余字符串rest：         "babac"
     *      枚举每一种贴纸，剩余字符串扣减掉贴纸后，再去枚举每一种贴纸
     *          定义一个函数：int f(sticker, rest);
     *                          参数：sticker为贴纸，rest为剩余字符串，
     *                          功能：函数中执行rest-sticker操作
     *                          返回：最小贴纸数，-1表示不能拼凑
     *
     *                          f("ba", "babac")                                f("c", "babac")                                       f("abcd", "babac")
     *             f("ba", "bac") f("c", "bac") f("abcd", "bac")    f("ba", "baba") f("c", "baba") f("abcd", "baba")    f("ba", "ba") f("c", "ba") f("abcd", "ba")
     *                  ...            ...             ...                ...             ...              ...                ...          ...            ...
     *
     * 枚举一种情形：
     *                                                              f("ba","babac")
     *                          f("ba","bac")                        f("c","bac")                       f("abcd","bac")
     *               f("ba","c") f("c","c") f("abcd","c")  f("ba","ba") f("c","ba") f("abcd","ba")              1
     *                    -1          1            1             1           -1             1
     *
     *                                  f("c","c")
     *                      f("ba","")  f("c","")  f("abcd","")
     *                          0           0            0
     *              f("c","c") = min[f("ba",""), f("c",""), f("abcd","")] + 1
     *                         = min(0, 0, 0) + 1
     *                         = 1
     *
     *              f("ba","bac") = min[f("c","c"), f("abcd","c")] + 1
     *                            = min(1, 1) + 1
     *                            = 2
     *              f("c","bac") = min[f("ba","ba"), f("abcd","ba")] + 1
     *                           = min(1, 1) + 1
     *                           = 2
     *              f("abcd","bac") = 1;
     *              f("ba","babac") = min[f("ba","bac"), f("c","bac"), f("abcd","bac")] + 1
     *                              = min(2, 2, 1) + 1
     *                              = 1 + 1
     *                              = 2
     */

    /*
     * 给定一个字符串str，给定一个字符串类型的数组arr。
     *  arr里的每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来。
     *  返回需要至少多少张贴纸可以完成这个任务。
     * 例子：str = "babac"，arr = {"ba","c","abcd"}
     * 至少需要两张贴纸"ba"和"abcd"，因为使用这两张贴纸，把每一个字符单独剪开，含有2个a、2个b、1个c。是可以拼出str的。所以返回2。
     */

    private static class BruteForce {
        public static int minStickers(String str, String[] stickers) {
            if (str == null || stickers == null || stickers.length == 0) {
                return 0;
            }
            // 对贴纸词频统计
            List<int[]> stickersList = new ArrayList<>(stickers.length);
            for (String sticker : stickers) {
                int[] stickerChars = new int[26];
                for (char c : sticker.toCharArray()) {
                    stickerChars[c - 'a']++;
                }
                stickersList.add(stickerChars);
            }
            return recurse(str, stickersList);
        }

        /**
         * @param restStr  剩余的字符串
         * @param stickers 贴纸列表
         * @return 最少贴纸数 -1表示不能拼接成功
         */
        private static int recurse(String restStr, List<int[]> stickers) {
            // base case
            if ("".equals(restStr)) {
                // 不需要贴纸了
                return 0;
            }
            // 剩余字符串词频统计
            int[] str = new int[26];
            for (char c : restStr.toCharArray()) {
                str[c - 'a']++;
            }
            int minCount = Integer.MAX_VALUE;
            for (int[] sticker : stickers) {
                if (!canUseSticker(str, sticker)) {
                    continue;
                }
                // 剩余字符串减去贴纸
                String newRestStr = subtractSticker(str, sticker);
                // 剩余字符串扣减完至少需要的贴纸数量
                int nextMinCount = recurse(newRestStr, stickers);
                if (nextMinCount != -1) {
                    // 本次最少贴纸数 = 本次用了1张 + 后续使用的最少贴纸数
                    int currentMinCount = 1 + nextMinCount;
                    minCount = Math.min(minCount, currentMinCount);
                }
            }
            return minCount == Integer.MAX_VALUE ? -1 : minCount;
        }

        private static boolean canUseSticker(int[] str, int[] sticker) {
            for (int i = 0; i < str.length; i++) {
                if (str[i] > 0 && sticker[i] > 0) {
                    return true;
                }
            }
            return false;
        }

        private static String subtractSticker(int[] str, int[] sticker) {
            // 剩余字符串中减去贴纸
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length; i++) {
                if (str[i] > 0) {
                    int remain = str[i] <= sticker[i] ? 0 : str[i] - sticker[i];
                    for (int count = 0; count < remain; count++) {
                        sb.append((char) (i + 'a'));
                    }
                }
            }
            return sb.toString();
        }
    }

    private static class MemorySearch {
        public static int minStickers(String str, String[] stickers) {
            if (str == null || stickers == null || stickers.length == 0) {
                return 0;
            }
            // 对贴纸词频统计
            List<int[]> stickersList = new ArrayList<>(stickers.length);
            for (String sticker : stickers) {
                int[] stickerChars = new int[26];
                for (char c : sticker.toCharArray()) {
                    stickerChars[c - 'a']++;
                }
                stickersList.add(stickerChars);
            }
            HashMap<String, Integer> table = new HashMap<>();
            table.put("", 0);
            return recurse(str, stickersList, table);
        }

        /**
         * @param restStr  剩余的字符串
         * @param stickers 贴纸列表
         * @return 最少贴纸数 -1表示不能拼接成功
         */
        private static int recurse(String restStr, List<int[]> stickers, HashMap<String, Integer> table) {
            if (table.containsKey(restStr)) {
                return table.get(restStr);
            }
            // base case
            if ("".equals(restStr)) {
                // 不需要贴纸了
                return 0;
            }
            // 剩余字符串词频统计
            int[] str = new int[26];
            char[] chars = restStr.toCharArray();
            for (char c : chars) {
                str[c - 'a']++;
            }
            int minCount = Integer.MAX_VALUE;
            for (int[] sticker : stickers) {
                // if (!canUseSticker(str, sticker)) {
                //     continue;
                // }
                if (sticker[chars[0] - 'a'] == 0) {
                    continue;
                }
                // 剩余字符串减去贴纸
                String newRestStr = subtractSticker(str, sticker);
                // 剩余字符串扣减完至少需要的贴纸数量
                int nextMinCount = recurse(newRestStr, stickers, table);
                if (nextMinCount != -1) {
                    // 本次最少贴纸数 = 本次用了1张 + 后续使用的最少贴纸数
                    int currentMinCount = 1 + nextMinCount;
                    minCount = Math.min(minCount, currentMinCount);
                }
            }
            minCount = minCount == Integer.MAX_VALUE ? -1 : minCount;
            table.put(restStr, minCount);
            return minCount;
        }

        private static boolean canUseSticker(int[] str, int[] sticker) {
            for (int i = 0; i < str.length; i++) {
                if (str[i] > 0 && sticker[i] > 0) {
                    return true;
                }
            }
            return false;
        }

        private static String subtractSticker(int[] str, int[] sticker) {
            // 剩余字符串中减去贴纸
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length; i++) {
                if (str[i] > 0) {
                    int remain = str[i] <= sticker[i] ? 0 : str[i] - sticker[i];
                    for (int count = 0; count < remain; count++) {
                        sb.append((char) (i + 'a'));
                    }
                }
            }
            return sb.toString();
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        String[] arr = {"aaaa", "bbaa", "ccddd"};
        String str = "abcccccdddddbbbaaaaa";
        System.out.println(Compare.minStickers(arr, str));
        System.out.println(BruteForce.minStickers(str, arr));
        System.out.println(MemorySearch.minStickers(str, arr));

        // 执行次数
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            // 随机生成1~10种贴纸
            String[] stickers = new String[RandomUtil.randomInt(1, 11)];
            for (int i = 0; i < stickers.length; i++) {
                // 贴纸长度为1~10
                stickers[i] = RandomStringUtils.randomAlphabetic(1, 11).toLowerCase();
            }
            // 随机生成一个目标字符串
            String restTarget = RandomStringUtils.randomAlphabetic(1, 8).toLowerCase();
            // 对数器方法
            int r1 = Compare.minStickers(stickers, restTarget);
            // 暴力尝试方法
            int r2 = BruteForce.minStickers(restTarget, stickers);
            // 动态规划方法
            int r3 = MemorySearch.minStickers(restTarget, stickers);
            if (!(r1 == r2 && r2 == r3)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finished!");
    }

    /* ****************************************************************************************************************/

    /*对数器方法*/

    private static class Compare {
        public static int minStickers(String[] stickers, String target) {
            int n = stickers.length;
            // stickers -> [26] [26] [26]
            int[][] map = new int[n][26];
            for (int i = 0; i < n; i++) {
                char[] str = stickers[i].toCharArray();
                for (char c : str) {
                    map[i][c - 'a']++;
                }
            }
            HashMap<String, Integer> dp = new HashMap<>();
            dp.put("", 0);
            return process1(dp, map, target);
        }

        // dp 傻缓存，如果t已经算过了，直接返回dp中的值
        // t 剩余的目标
        // 0..N每一个字符串所含字符的词频统计
        // 返回值是-1，map 中的贴纸  怎么都无法rest
        private static int process1(HashMap<String, Integer> dp, int[][] map, String rest) {
            if (dp.containsKey(rest)) {
                return dp.get(rest);
            }
            // 以下就是正式的递归调用过程

            // ans -> 搞定rest，使用的最少的贴纸数量
            int ans = Integer.MAX_VALUE;
            // N种贴纸
            int n = map.length;
            // tmap 去替代 rest
            int[] tmap = new int[26];
            char[] target = rest.toCharArray();
            for (char c : target) {
                tmap[c - 'a']++;
            }
            for (int i = 0; i < n; i++) {
                // 枚举当前第一张贴纸是谁？
                if (map[i][target[0] - 'a'] == 0) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                // i 贴纸， j 枚举a~z字符
                for (int j = 0; j < 26; j++) {
                    // j这个字符是target需要的
                    if (tmap[j] > 0) {
                        for (int k = 0; k < Math.max(0, tmap[j] - map[i][j]); k++) {
                            sb.append((char) ('a' + j));
                        }
                    }
                }
                // sb ->  i
                String s = sb.toString();
                int tmp = process1(dp, map, s);
                if (tmp != -1) {
                    ans = Math.min(ans, 1 + tmp);
                }
            }
            // ans 系统最大  rest
            dp.put(rest, ans == Integer.MAX_VALUE ? -1 : ans);
            return dp.get(rest);
        }

    }

    /* ****************************************************************************************************************/

}
