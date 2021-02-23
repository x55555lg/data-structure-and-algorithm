package com.lg.algorithm.dynamicprogramming.a1;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @author Xulg
 * Created in 2021-02-17 20:14
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
class ComposeCurrencyValue {

    /*
     * 给定数组arr，arr中所有的值都为正数且不重复
     * 每个值代表一种面值的货币，每种面值的货币可以使用任意张
     * 再给定一个整数aim，代表要找的钱数
     * 求组成aim的方法数
     */

    /**
     * 暴力解法
     * 给定数组arr，arr中所有的值都为正数且不重复，每个值代表一种面值的货币，每种面值的货币可以使用任意张。
     * 再给定一个整数aim，代表要找的钱数。
     * 求组成aim的方法数
     *
     * @param arr the currency array
     * @param aim the aim
     * @return the combination count
     */
    public static int combineByBruteForce(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return recurse(arr, 0, aim);
    }

    /**
     * @param currencyArray 货币面值数组
     * @param currencyIdx   当前面值货币的index位置
     * @param remainderAim  剩余目标值
     * @return 组合数
     */
    private static int recurse(int[] currencyArray, int currencyIdx, int remainderAim) {
        if (currencyIdx == currencyArray.length) {
            // 货币选完了，是否达到目标值
            return remainderAim == 0 ? 1 : 0;
        }
        /* 还有货币可以选 */
        if (remainderAim == 0) {
            // 达到目标值了
            return 1;
        }
        if (remainderAim < 0) {
            // 过头了
            return 0;
        }

        // 使用这个面值货币，有几种组合方法
        int r1 = recurse(currencyArray, currencyIdx, remainderAim - currencyArray[currencyIdx]);
        // 使用下一个面值货币，有几种组合方法
        int r2 = recurse(currencyArray, currencyIdx + 1, remainderAim);

        // the total count
        return r1 + r2;
    }

    /**
     * 动态规划
     * 给定数组arr，arr中所有的值都为正数且不重复，每个值代表一种面值的货币，每种面值的货币可以使用任意张。
     * 再给定一个整数aim，代表要找的钱数。
     * 求组成aim的方法数
     *
     * @param arr the currency array
     * @param aim the aim
     * @return the combination count
     */
    public static int combineByDP(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        // row表示currencyIdx，即当前是哪个货币
        // column表示remainderAim，即距离目标值剩余多少
        int[][] table = new int[arr.length + 1][aim + 1];

        // base case 达到了目标值
        for (int currencyIdx = 0; currencyIdx <= arr.length; currencyIdx++) {
            table[currencyIdx][0] = 1;
        }

        // 填表
        for (int currencyIdx = arr.length - 1; currencyIdx >= 0; currencyIdx--) {
            for (int remainderAim = 1; remainderAim <= aim; remainderAim++) {
                // 使用这个面值货币，有几种组合方法
                // 剩余目标值不能为负数啊(也不能越界啊)
                int r1 = (remainderAim - arr[currencyIdx] < 0) ? 0
                        : table[currencyIdx][remainderAim - arr[currencyIdx]];
                // 使用下一个面值货币，有几种组合方法
                int r2 = table[currencyIdx + 1][remainderAim];
                // total count
                table[currencyIdx][remainderAim] = r1 + r2;
            }
        }
        return table[0][aim];
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

            int count1 = ways1(array, aim);
            int count2 = combineByBruteForce(array, aim);
            int count3 = combineByDP(array, aim);
            if (!(count1 == count2 && count2 == count3)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finished!");

        {
            int[] array = {1, 2, 3, 4, 5, 6};
            int aim = 143;
            int count1 = combineByBruteForce(array, aim);
            System.out.println(count1);
            int count2 = combineByDP(array, aim);
            System.out.println(count2);
            int count3 = ways1(array, aim);
            System.out.println(count3);
        }
    }

}
