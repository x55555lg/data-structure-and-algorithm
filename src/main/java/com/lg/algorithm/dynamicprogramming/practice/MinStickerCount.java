package com.lg.algorithm.dynamicprogramming.practice;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2020-12-07 17:17
 */
@SuppressWarnings("DuplicatedCode")
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

    /* ****************************************************************************************************************/

    /**
     * 暴力尝试
     * 组成目标字符串最少需要几张贴纸
     *
     * @param stickers 贴纸数组
     * @param target   目标字符串
     * @return 最小贴纸数
     */
    public static int minStickerCountByRecurse(String[] stickers, String target) {
        if (target == null || stickers == null || stickers.length == 0) {
            return 0;
        }
        // 贴纸表，行代表一种贴纸，列代表字符[a, z]
        int[][] stickerMap = new int[stickers.length][26];
        for (int row = 0; row < stickers.length; row++) {
            String sticker = stickers[row];
            // 统计词频
            for (char c : sticker.toCharArray()) {
                // 计算索引位置，0为'a' 25为'z'
                int column = c - 'a';
                stickerMap[row][column]++;
            }
        }
        return recurse(stickerMap, target);
    }

    /**
     * 递归计算拼出restTarget需要的最少的贴纸数
     *
     * @param stickerMap 贴纸表
     * @param restTarget 剩余的字符串
     * @return 拼出restTarget需要的最少的贴纸数，返回-1表示不能使用贴纸拼出剩余字符串
     */
    private static int recurse(int[][] stickerMap, String restTarget) {
        // base case
        if ("".equals(restTarget)) {
            // 没有剩余字符了，那么需要的还贴纸就是0张了
            return 0;
        }

        // 将剩余字符串转成字符表[0为'a'...25为'z']，array[0]=3表示'a'字符有3个
        int[] restStr = new int[26];
        char[] chars = restTarget.toCharArray();
        for (char c : chars) {
            // 计算每个字符在数组中的位置，进行字符词频统计
            restStr[c - 'a']++;
        }

        // 拼凑剩余字符串restTarget所需要的最小贴纸数，初始值为MAX值
        int minStickerCount = Integer.MAX_VALUE;

        // 遍历每一种贴纸
        for (int[] sticker : stickerMap) {
            // 当前遍历到的这个贴纸可不可以用？即这贴纸中是否至少包含一种剩余字符串中的字符
            // 如果这个贴纸用不了，直接pass
            // 这里可以优化成值判断首字符
            if (canNotUseTheSticker(sticker, restStr)) {
                continue;
            }

            // 从剩余字符串中扣减掉贴纸，拿到剩余的字符串
            String nextRestTarget = deductSticker(sticker, restStr);

            // 继续使用贴纸去拼剩余的字符串，获取最少需要多少张贴纸
            int nextMinStickerCount = recurse(stickerMap, nextRestTarget);

            // 返回-1表示用了当前的贴纸，剩余字符串无法拼凑，这是一种无效的拼凑选择
            if (nextMinStickerCount != -1) {
                // 用了当前的贴纸后，后面可以拼凑出剩余字符串，
                // 那么，当前的最小贴纸数量 = 后续的最小贴纸数 + 1
                // 因为用了当前的贴纸，所有需要加1
                int currentMinStickerCount = nextMinStickerCount + 1;

                // 上一次结果和这次的比较，取最小的
                minStickerCount = Math.min(minStickerCount, currentMinStickerCount);
            }
        }

        // 如果说最小贴纸数量还是MAX值，表示没法拼凑返回-1
        return (minStickerCount == Integer.MAX_VALUE) ? -1 : minStickerCount;
    }

    /**
     * 从剩余字符串中减去贴纸
     *
     * @param sticker 贴纸
     * @param restStr 剩余字符串
     * @return 扣减后的字符串
     */
    private static String deductSticker(int[] sticker, int[] restStr) {
        // 剩余字符减去贴纸中的字符
        StringBuilder sb = new StringBuilder();
        // 遍历[a, z]26个字符
        for (int i = 0; i < 26; i++) {
            /*
             * 剩余字符串中有这个字符，当前贴纸也有这种字符，那么进行相减操作
             * 例如：
             *      i = 0             表示字符'a'
             *      restStr[0] = 2    表示剩余字符串中有2个'a'
             *      sticker[0] = 3    表示当前的贴纸中有3个'a'
             * 相减之后(不可能减成负数的)：
             *      restStr[0] = 0    表示剩余字符串中还剩余1个'a'
             */
            if (restStr[i] > 0) {
                // 贴纸中如果没有字符i也没关系，这里扣减不会出问题
                // 有可能剩余字符串中i字符比贴纸中的i字符少，不能减成负数啊
                int remainderCharCount = (restStr[i] <= sticker[i]) ? 0 : restStr[i] - sticker[i];
                // 减完之后这个字符还有剩余，就存起来，剩余几个就存入几个
                for (int c = 0; c < remainderCharCount; c++) {
                    sb.append((char) (i + 'a'));
                }
            }
        }
        // 返回扣减贴纸后剩余的字符串
        return sb.toString();
    }

    /**
     * 当前遍历到的这个贴纸可不可以用？
     * 这贴纸中是否至少包含一种剩余字符串中的字符
     *
     * @param sticker 贴纸
     * @param restStr 剩余字符串
     * @return 是否可以用这个贴纸
     */
    private static boolean canNotUseTheSticker(int[] sticker, int[] restStr) {
        /*
         * 当前遍历到的这个贴纸可不可以用？
         *  这贴纸中是否至少包含一种剩余字符串中的字符
         */
        boolean canNotUse = true;
        for (int i = 0; i < restStr.length; i++) {
            if (restStr[i] > 0 && sticker[i] > 0) {
                canNotUse = false;
                break;
            }
        }
        return canNotUse;
    }

    /* ****************************************************************************************************************/

    /**
     * 动态规划-记忆化搜索
     * 组成目标字符串最少需要几张贴纸
     *
     * @param stickers 贴纸数组
     * @param target   目标字符串
     * @return 最小贴纸数
     */
    public static int minStickerCountByDp(String[] stickers, String target) {
        if (target == null || stickers == null || stickers.length == 0) {
            return 0;
        }
        // 贴纸表，行代表一种贴纸，列代表字符[a, z]
        int[][] stickerMap = new int[stickers.length][26];
        for (int row = 0; row < stickers.length; row++) {
            String sticker = stickers[row];
            // 统计词频
            for (char c : sticker.toCharArray()) {
                // 计算索引位置，0为'a' 25为'z'
                int column = c - 'a';
                stickerMap[row][column]++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        return recurse(stickerMap, target, dp);
    }

    /**
     * 递归计算拼出restTarget需要的最少的贴纸数
     *
     * @param stickerMap 贴纸表
     * @param restTarget 剩余的字符串
     * @param dp         缓存表
     * @return 拼出restTarget需要的最少的贴纸数，返回-1表示不能使用贴纸拼出剩余字符串
     */
    private static int recurse(int[][] stickerMap, String restTarget, HashMap<String, Integer> dp) {
        // 优先从缓存中拿
        if (dp.containsKey(restTarget)) {
            return dp.get(restTarget);
        }

        // base case
        if ("".equals(restTarget)) {
            // 没有剩余字符了，那么需要的还贴纸就是0张了
            dp.put(restTarget, 0);
            return 0;
        }

        // 将剩余字符串转成字符表[0为'a'...25为'z']，array[0]=3表示'a'字符有3个
        int[] restStr = new int[26];
        char[] chars = restTarget.toCharArray();
        for (char c : chars) {
            // 计算每个字符在数组中的位置，进行字符词频统计
            restStr[c - 'a']++;
        }

        // 拼凑剩余字符串restTarget所需要的最小贴纸数，初始值为MAX值
        int minStickerCount = Integer.MAX_VALUE;

        // 遍历每一种贴纸
        for (int[] sticker : stickerMap) {
            // 当前遍历到的这个贴纸可不可以用？即这贴纸中是否至少包含一种剩余字符串中的字符
            // 如果这个贴纸用不了，直接pass
            // 这里可以优化成值判断首字符
            if (canNotUseTheSticker(sticker, restStr)) {
                continue;
            }

            // 从剩余字符串中扣减掉贴纸，拿到剩余的字符串
            String nextRestTarget = deductSticker(sticker, restStr);

            // 继续使用贴纸去拼剩余的字符串，获取最少需要多少张贴纸
            int nextMinStickerCount = recurse(stickerMap, nextRestTarget, dp);

            // 返回-1表示用了当前的贴纸，剩余字符串无法拼凑，这是一种无效的拼凑选择
            if (nextMinStickerCount != -1) {
                // 用了当前的贴纸后，后面可以拼凑出剩余字符串，
                // 那么，当前的最小贴纸数量 = 后续的最小贴纸数 + 1
                // 因为用了当前的贴纸，所有需要加1
                int currentMinStickerCount = nextMinStickerCount + 1;

                // 上一次结果和这次的比较，取最小的
                minStickerCount = Math.min(minStickerCount, currentMinStickerCount);
            }
        }

        // 如果说最小贴纸数量还是MAX值，表示没法拼凑返回-1
        int ans = (minStickerCount == Integer.MAX_VALUE) ? -1 : minStickerCount;
        dp.put(restTarget, ans);
        return ans;
    }

    /* ****************************************************************************************************************/

    /*对数器方法*/

    public static int minStickers1(String[] stickers, String target) {
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
    public static int process1(
            HashMap<String, Integer> dp,
            int[][] map,
            String rest) {
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

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        String[] arr = {"aaaa", "bbaa", "ccddd"};
        String str = "abcccccdddddbbbaaaaa";
        System.out.println(minStickers1(arr, str));
        System.out.println(minStickerCountByRecurse(arr, str));
        System.out.println(minStickerCountByDp(arr, str));

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
            int r1 = minStickers1(stickers, restTarget);
            // 暴力尝试方法
            int r2 = minStickerCountByRecurse(stickers, restTarget);
            // 动态规划方法
            int r3 = minStickerCountByDp(stickers, restTarget);

            if (!(r1 == r2 && r2 == r3)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finished!");
    }

}
