package com.lg.algorithm.dynamicprogramming.a2;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @author Xulg
 * @since 2021-07-21 14:23
 * Description: 凑硬币
 */
class ComposeCurrencyValue {

    /*
     * 给定数组arr，arr中所有的值都为正数且不重复
     * 每个值代表一种面值的货币，每种面值的货币可以使用任意张
     * 再给定一个整数aim，代表要找的钱数
     * 求组成aim的方法数
     */

    private static class BruteForce1 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            return recurse(0, aim, arr);
        }

        private static int recurse(int curIdx, int restAim, int[] arr) {
            if (curIdx == arr.length) {
                // 货币用完了，看有没有凑到目标值
                return restAim == 0 ? 1 : 0;
            }
            if (restAim == 0) {
                // 凑成目标值了
                return 1;
            }
            if (restAim < 0) {
                // 凑不成功
                return 0;
            }
            // 用当前硬币
            int yes = recurse(curIdx, restAim - arr[curIdx], arr);
            // 不用当前硬币
            int no = recurse(curIdx + 1, restAim, arr);
            return yes + no;
        }
    }

    private static class DP1 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            // base case
            for (int curIdx = 0; curIdx <= arr.length; curIdx++) {
                table[curIdx][0] = 1;
            }
            // base case
            for (int restAim = 1; restAim <= aim; restAim++) {
                table[arr.length][restAim] = 0;
            }
            for (int restAim = 1; restAim <= aim; restAim++) {
                for (int curIdx = arr.length - 1; curIdx >= 0; curIdx--) {
                    // 用当前硬币
                    int yes = restAim - arr[curIdx] >= 0 ? table[curIdx][restAim - arr[curIdx]] : 0;
                    // 不用当前硬币
                    int no = table[curIdx + 1][restAim];
                    table[curIdx][restAim] = yes + no;
                }
            }
            return table[0][aim];
        }
    }

    private static class BruteForce2 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            return recurse(0, aim, arr);
        }

        private static int recurse(int curIdx, int restAim, int[] arr) {
            if (curIdx == arr.length) {
                // 货币用完了，看有没有凑到目标值
                return restAim == 0 ? 1 : 0;
            }
            if (restAim < 0) {
                return 0;
            }
            if (restAim == 0) {
                return 1;
            }
            int count = 0;
            for (int zhang = 0; zhang * arr[curIdx] <= restAim; zhang++) {
                // 当前货币用0张，后续有多少种换法
                // 当前货币用1张，后续有多少种换法
                // ...
                // 当前货币用x张，后续有多少种换法
                count += recurse(curIdx + 1, restAim - (zhang * arr[curIdx]), arr);
            }
            return count;
        }
    }

    private static class DP2 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            // base case
            for (int curIdx = 0; curIdx <= arr.length; curIdx++) {
                table[curIdx][0] = 1;
            }
            // base case
            for (int restAim = 1; restAim <= aim; restAim++) {
                table[arr.length][restAim] = 0;
            }
            for (int restAim = 1; restAim <= aim; restAim++) {
                for (int curIdx = arr.length - 1; curIdx >= 0; curIdx--) {
                    int count = 0;
                    for (int zhang = 0; zhang * arr[curIdx] <= restAim; zhang++) {
                        // 当前货币用0张，后续有多少种换法
                        // 当前货币用1张，后续有多少种换法
                        // 当前货币用x张，后续有多少种换法
                        count += table[curIdx + 1][restAim - zhang * arr[curIdx]];
                    }
                    table[curIdx][restAim] = count;
                }
            }
            return table[0][aim];
        }
    }

    /* ****************************************************************************************************************/

    /* 对数器 */

    private static class Compare {
        public static int ways1(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            return process1(arr, 0, aim);
        }

        private static int process1(int[] arr, int index, int rest) {
            if (index == arr.length) {
                return rest == 0 ? 1 : 0;
            }
            int ways = 0;
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                ways += process1(arr, index + 1, rest - (zhang * arr[index]));
            }
            return ways;
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        if (true) {
            int times = 100000;
            for (int time = 0; time < times; time++) {
                int aim = RandomUtil.randomInt(0, 100);
                int[] array = new int[10];
                for (int i = 0; i < array.length; i++) {
                    int val = RandomUtil.randomInt(1, 31);
                    while (ArrayUtil.contains(array, val)) {
                        val = RandomUtil.randomInt(1, 101);
                    }
                    array[i] = val;
                }
                int count1 = Compare.ways1(array, aim);
                int count2 = BruteForce1.compose(array, aim);
                int count3 = BruteForce2.compose(array, aim);
                int count4 = DP1.compose(array, aim);
                int count5 = DP2.compose(array, aim);
                if (!(count1 == count2 && count2 == count3 && count3 == count4 && count4 == count5)) {
                    System.out.println("Oops");
                }
            }
            System.out.println("finished!");
        }

        int[] array = {1};
        System.out.println(Compare.ways1(array.clone(), 0));
        System.out.println(BruteForce1.compose(array.clone(), 0));
        System.out.println(BruteForce2.compose(array.clone(), 0));
        System.out.println(DP1.compose(array.clone(), 0));
        System.out.println(DP2.compose(array.clone(), 0));
    }

}
