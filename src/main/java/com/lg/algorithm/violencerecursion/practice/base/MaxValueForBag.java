package com.lg.algorithm.violencerecursion.practice.base;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Xulg
 * Created in 2020-11-25 18:23
 */
@SuppressWarnings("DuplicatedCode")
class MaxValueForBag {

    /*
     * 背包问题
     *  这里能不能重复装同一个物品？假设不行吧
     * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表i号物品的重量和价值。
     * 给定一个正数bag，表示一个载重bag的袋子，你装的物品不能超过这个重量。返回你能装下最多的价值
     * 是多少?
     */

    /* ****************************************************************************************************************/

    /**
     * 获取背包可负载的最大价值
     * 第一版本 实现了功能，复杂度太高了
     *
     * @param weights    物品重量列表
     * @param values     物品价值列表
     * @param bagMaxLoad 背包最大负载重量
     * @return 背包最大价值
     */
    public static int getMaxValue1(int[] weights, int[] values, int bagMaxLoad) {
        if (weights == null || values == null || bagMaxLoad <= 0) {
            return 0;
        }
        assert weights.length == values.length;
        // weight，value封装成一体
        Item[] items = new Item[weights.length];
        for (int i = 0; i < weights.length; i++) {
            items[i] = new Item(weights[i], values[i]);
        }
        ArrayList<Integer> allValues = new ArrayList<>();
        recurse(items, bagMaxLoad, 0, 0, 0, allValues);
        // 遍历出最大值
        return allValues.stream().max(Integer::compareTo).get();
    }

    /**
     * 暴力递归
     *
     * @param items      物品列表
     * @param bagMaxLoad 背包最大载荷重量
     * @param currentIdx 当前物品的位置
     * @param bagWeight  此时背包的质量
     * @param bagValue   此时背包的价值
     * @param allValues  保存所有背包价值情况的集合
     */
    private static void recurse(Item[] items, int bagMaxLoad, int currentIdx,
                                int bagWeight, int bagValue, ArrayList<Integer> allValues) {
        if (bagWeight > bagMaxLoad) {
            // 背包超重了，这个值要舍弃
            return;
        }
        if (currentIdx == items.length) {
            // 物品选完了
            allValues.add(bagValue);
            return;
        }
        // 选择不要这个物品
        recurse(items, bagMaxLoad, currentIdx + 1, bagWeight, bagValue, allValues);
        // 选择要这个物品
        recurse(items, bagMaxLoad, currentIdx + 1,
                bagWeight + items[currentIdx].weight,
                bagValue + items[currentIdx].value, allValues);
    }

    /* ****************************************************************************************************************/

    /**
     * 获取背包可负载的最大价值
     * 第二版本
     *
     * @param weights    物品重量列表
     * @param values     物品价值列表
     * @param bagMaxLoad 背包最大负载重量
     * @return 背包最大价值
     */
    public static int getMaxValue2(int[] weights, int[] values, int bagMaxLoad) {
        if (weights == null || values == null || bagMaxLoad <= 0) {
            return 0;
        }
        assert weights.length == values.length;
        Item[] items = new Item[weights.length];
        for (int i = 0; i < weights.length; i++) {
            items[i] = new Item(weights[i], values[i]);
        }
        return recurse(items, bagMaxLoad, 0, 0);
    }

    /**
     * 暴力递归
     *
     * @param items      物品列表
     * @param bagMaxLoad 背包最大载荷重量
     * @param currentIdx 当前物品的位置
     * @param bagWeight  此时背包的质量
     * @return 返回最大价值，返回-1表示没有价值
     */
    private static int recurse(Item[] items, int bagMaxLoad, int currentIdx, int bagWeight) {
        if (bagWeight > bagMaxLoad) {
            // 背包超重了，没有价值了
            return -1;
        }

        if (currentIdx == items.length) {
            // 物品选完了，这个时候能返回的物品价值就是0
            return 0;
        }

        /*
         * 这里的递归在不停的求子问题的解，通过子问题的解来解决当前的问题
         * 问子问题要：
         *  不选这个物品后的价值
         *  选了这个物品后的价值
         */

        // 选择不要这个物品，返回价值
        int noValue = recurse(items, bagMaxLoad, currentIdx + 1, bagWeight);

        // 选择要这个物品，返回价值
        int yesValue = recurse(items, bagMaxLoad, currentIdx + 1,
                bagWeight + items[currentIdx].weight);

        // 要这个物品的情况下，如果价值是有效的，那么加上当前这个物品的价值
        if (yesValue != -1) {
            yesValue = yesValue + items[currentIdx].value;
        }

        // 要这个物品和不要这个物品两种情况下哪个值大
        return Math.max(noValue, yesValue);
    }

    /* ****************************************************************************************************************/

    /**
     * 获取背包可负载的最大价值
     * 第三版本 经典写法
     *
     * @param weights    物品重量列表
     * @param values     物品价值列表
     * @param bagMaxLoad 背包最大负载重量
     * @return 背包最大价值
     */
    public static int getMaxValue3(int[] weights, int[] values, int bagMaxLoad) {
        if (weights == null || values == null || bagMaxLoad <= 0) {
            return 0;
        }
        assert weights.length == values.length;
        Item[] items = new Item[weights.length];
        for (int i = 0; i < weights.length; i++) {
            items[i] = new Item(weights[i], values[i]);
        }
        return recurse(items, 0, bagMaxLoad);
    }

    /**
     * 暴力递归
     *
     * @param items            物品列表
     * @param currentIdx       当前物品的位置
     * @param remainderBagLoad 背包剩余可装载的重量
     * @return 返回最大价值，返回-1表示没有价值
     */
    private static int recurse(Item[] items, int currentIdx, int remainderBagLoad) {
        if (remainderBagLoad < 0) {
            // 背包剩余容量为负数了，说明超重了，这种装载方式的价值是无效的
            return -1;
        }

        if (remainderBagLoad == 0) {
            // 背包空间刚好用完了，这个时候能返回的物品价值就是0
            return 0;
        }

        if (currentIdx == items.length) {
            // 物品选完了，这个时候能返回的物品价值就是0
            return 0;
        }

        /*
         * 这里的递归在不停的求子问题的解，通过子问题的解来解决当前的问题
         * 问子问题要：
         *  不选这个物品后的价值
         *  选了这个物品后的价值
         */

        // 选择不要这个物品，返回价值
        int noValue = recurse(items, currentIdx + 1, remainderBagLoad);

        // 要这个物品的情况下，如果价值是有效的，那么加上当前这个物品的价值
        int yesValue = recurse(items, currentIdx + 1,
                remainderBagLoad - items[currentIdx].weight);
        if (yesValue != -1) {
            yesValue = yesValue + items[currentIdx].value;
        }

        return Math.max(noValue, yesValue);
    }

    /* ****************************************************************************************************************/

    @SuppressWarnings("UnusedReturnValue")
    private static Record recurseProcess(Item[] items, int currentIdx, int remainderBagLoad) {
        if (remainderBagLoad < 0) {
            // 背包空间没了
            return new Record(-1, 0);
        }
        if (remainderBagLoad == 0) {
            // 背包空间刚好用完了，这个时候能返回的物品价值就是0
            return new Record(0, 0);
        }

        if (currentIdx == items.length) {
            // 物品选完了，这个时候能返回的物品价值就是0
            return new Record(0, 0);
        }

        recurseProcess(items, currentIdx + 1, remainderBagLoad);
        return null;
    }

    private static class Record {
        // 选择物品后的价值
        private int yesSelectedVal;
        // 不选择物品后的价值
        private int noSelectedVal;

        Record(int yesSelectedVal, int noSelectedVal) {
            this.yesSelectedVal = yesSelectedVal;
            this.noSelectedVal = noSelectedVal;
        }
    }

    /* ****************************************************************************************************************/

    private static class Item {
        // 货物的重量
        final int weight;
        // 货物的价值
        final int value;

        Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/

    /* 对数器 */

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public static int dpWay(int[] w, int[] v, int bag) {
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 1; rest <= bag; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest >= w[index]) {
                    dp[index][rest] = Math.max(dp[index][rest], v[index] + dp[index + 1][rest - w[index]]);
                }
            }
        }
        return dp[0][bag];
    }

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int times = 1000000;
        int maxLength = 10;
        for (int time = 0; time < times; time++) {
            int bagMaxLoad = RandomUtil.randomInt(1, 200);
            int length = RandomUtil.randomInt(1, maxLength + 1);
            int[] weights = new int[length];
            int[] values = new int[length];
            for (int i = 0; i < length; i++) {
                int weight = RandomUtil.randomInt(1, 101);
                int value = RandomUtil.randomInt(1, 101);
                weights[i] = weight;
                values[i] = value;
            }
            int max1 = dpWay(weights, values, bagMaxLoad);
            int max2 = getMaxValue1(weights, values, bagMaxLoad);
            int max3 = getMaxValue2(weights, values, bagMaxLoad);
            int max4 = getMaxValue3(weights, values, bagMaxLoad);
            if (!(max1 == max2 && max2 == max3 && max3 == max4)) {
                System.out.println("Oops!=======weights=" + Arrays.toString(weights)
                        + "----values=" + Arrays.toString(values) + "----bagMaxLoad=" + bagMaxLoad);
            }
        }
        System.out.println("finish!!!");
        int[] w = new int[2];
        int[] v = new int[2];
        w[0] = 2;
        w[1] = 3;
        v[0] = 4;
        v[1] = 1;
        int bag = 3;
        int maxValue = dpWay(w, v, bag);
        System.out.println(maxValue);
        maxValue = getMaxValue1(w, v, bag);
        System.out.println(maxValue);
        maxValue = getMaxValue2(w, v, bag);
        System.out.println(maxValue);
        maxValue = getMaxValue3(w, v, bag);
        System.out.println(maxValue);
    }

}