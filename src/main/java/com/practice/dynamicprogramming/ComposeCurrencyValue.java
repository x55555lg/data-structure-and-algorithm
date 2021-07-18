package com.practice.dynamicprogramming;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @author Xulg
 * Created in 2021-05-13 11:36
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

        private static int recurse(int curCoinIdx, int restAim, int[] coinArr) {
            // base case
            if (curCoinIdx == coinArr.length) {
                // 硬币用完了，看凑成功没
                return restAim == 0 ? 1 : 0;
            }
            // base case
            if (restAim == 0) {
                // 凑成功了
                return 1;
            }
            if (restAim < 0) {
                // 凑过头了
                return 0;
            }
            // 使用当前硬币凑
            int yes = recurse(curCoinIdx, restAim - coinArr[curCoinIdx], coinArr);
            // 使用下一个硬币凑
            int no = recurse(curCoinIdx + 1, restAim, coinArr);
            return yes + no;
        }
    }

    private static class MemorySearch1 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            for (int i = 0; i <= arr.length; i++) {
                for (int j = 0; j <= aim; j++) {
                    table[i][j] = -1;
                }
            }
            return recurse(0, aim, arr, table);
        }

        private static int recurse(int curCoinIdx, int restAim, int[] coinArr, int[][] table) {
            if (restAim < 0) {
                // 凑过头了
                return 0;
            }
            if (table[curCoinIdx][restAim] != -1) {
                // go cache
                return table[curCoinIdx][restAim];
            }
            // base case
            if (curCoinIdx == coinArr.length) {
                // 硬币用完了，看凑成功没
                int r = restAim == 0 ? 1 : 0;
                table[curCoinIdx][restAim] = r;
                return r;
            }
            // base case
            if (restAim == 0) {
                // 凑成功了
                table[curCoinIdx][restAim] = 1;
                return 1;
            }
            // 使用当前硬币凑
            int yes = recurse(curCoinIdx, restAim - coinArr[curCoinIdx], coinArr, table);
            // 使用下一个硬币凑
            int no = recurse(curCoinIdx + 1, restAim, coinArr, table);
            int r = yes + no;
            table[curCoinIdx][restAim] = r;
            return r;
        }
    }

    private static class DP1 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            // base case
            for (int i = 0; i <= arr.length; i++) {
                table[i][0] = 1;
            }
            for (int curCoinIdx = arr.length - 1; curCoinIdx >= 0; curCoinIdx--) {
                for (int restAim = 1; restAim <= aim; restAim++) {
                    // 使用当前硬币凑
                    int yes = 0;
                    if (restAim - arr[curCoinIdx] >= 0) {
                        yes = table[curCoinIdx][restAim - arr[curCoinIdx]];
                    }
                    // 使用下一个硬币凑
                    int no = table[curCoinIdx + 1][restAim];
                    table[curCoinIdx][restAim] = yes + no;
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

        private static int recurse(int curIdx, int restAim, int[] coinArr) {
            if (restAim < 0) {
                return 0;
            }
            if (curIdx == coinArr.length) {
                // 硬币用完了
                return restAim == 0 ? 1 : 0;
            }
            if (restAim == 0) {
                return 1;
            }
            int ways = 0;
            int curCoinValue = coinArr[curIdx];
            // 当前货币使用0~N个的各种情况
            for (int zhang = 0; zhang * curCoinValue <= restAim; zhang++) {
                // 使用下一个货币
                ways = ways + recurse(curIdx + 1, restAim - zhang * curCoinValue, coinArr);
            }
            return ways;
        }
    }

    private static class MemorySearch2 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            for (int i = 0; i <= arr.length; i++) {
                for (int j = 0; j <= aim; j++) {
                    table[i][j] = -1;
                }
            }
            return recurse(0, aim, arr, table);
        }

        private static int recurse(int curIdx, int restAim, int[] coinArr, int[][] table) {
            if (restAim < 0) {
                return 0;
            }
            if (table[curIdx][restAim] != -1) {
                // go cache
                return table[curIdx][restAim];
            }
            if (curIdx == coinArr.length) {
                // 硬币用完了
                int r = restAim == 0 ? 1 : 0;
                table[curIdx][restAim] = r;
                return r;
            }
            if (restAim == 0) {
                table[curIdx][restAim] = 1;
                return 1;
            }
            int ways = 0;
            int curCoinValue = coinArr[curIdx];
            // 当前货币使用0~N个的各种情况
            for (int zhang = 0; zhang * curCoinValue <= restAim; zhang++) {
                // 使用下一个货币
                ways = ways + recurse(curIdx + 1, restAim - zhang * curCoinValue, coinArr, table);
            }
            table[curIdx][restAim] = ways;
            return ways;
        }
    }

    private static class DP2 {
        public static int compose(int[] arr, int aim) {
            if (arr == null || arr.length == 0 || aim < 0) {
                return 0;
            }
            int[][] table = new int[arr.length + 1][aim + 1];
            // base case
            for (int i = 0; i <= arr.length; i++) {
                table[i][0] = 1;
            }
            for (int curCoinIdx = arr.length - 1; curCoinIdx >= 0; curCoinIdx--) {
                for (int restAim = 1; restAim <= aim; restAim++) {
                    int ways = 0;
                    int curCoinValue = arr[curCoinIdx];
                    // 当前货币使用0~N个的各种情况
                    for (int zhang = 0; zhang * curCoinValue <= restAim; zhang++) {
                        // 使用下一个货币
                        ways = ways + table[curCoinIdx + 1][restAim - zhang * curCoinValue];
                    }
                    table[curCoinIdx][restAim] = ways;
                }
            }
            return table[0][aim];
        }
    }

    /* ****************************************************************************************************************/

    /* 对数器 */

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

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 100000;
        for (int time = 0; time < times; time++) {
            int aim = RandomUtil.randomInt(1, 100);
            int[] array = new int[10];
            for (int i = 0; i < array.length; i++) {
                int val = RandomUtil.randomInt(1, 31);
                while (ArrayUtil.contains(array, val)) {
                    val = RandomUtil.randomInt(1, 101);
                }
                array[i] = val;
            }
            int r0 = ways1(array, aim);
            int r1 = BruteForce1.compose(array, aim);
            int r2 = BruteForce2.compose(array, aim);
            int r3 = MemorySearch1.compose(array, aim);
            int r4 = MemorySearch2.compose(array, aim);
            int r5 = DP1.compose(array, aim);
            int r6 = DP2.compose(array, aim);
            if (!(r0 == r1 && r1 == r2 && r2 == r3 && r3 == r4 && r4 == r5 && r5 == r6)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finished!");
    }
}
