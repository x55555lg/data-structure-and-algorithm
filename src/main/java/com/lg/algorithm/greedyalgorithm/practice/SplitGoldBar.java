package com.lg.algorithm.greedyalgorithm.practice;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 利用贪心算法求解的题目
 *
 * @author Xulg
 * Created in 2020-11-10 14:53
 */
class SplitGoldBar {

    /*
     * 一块金条切成两半，是需要花费和长度数值一样的铜板的。
     * 长度如果为20的金条，不管怎么切，都要花费20个铜板。一群人想整分整块金条，怎么分割最省铜板？
     * 例如：  给定数组[10, 20, 30]，代表一共3个人，整块金条长度为60，金条要分成10，20，30三部分。
     *       如果先把长度60的金条分成10和50，花费60；再把长度50的金条金条分成20和30，花费50；一
     *       共花费110铜板。
     *         但如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20，花费30；一共花
     *       费90铜板。
     * 金条的总长度等于数组中每个人需要的长度的总和。输入一个数组，返回分割的最小代价。
     *-------------------------------------------------------------------------------------------
     * 暴力解法：
     * 贪心解法：
     *      这个题实际上是哈夫曼编码问题，想把金条断成规定的多少段，选择一个怎样的顺序能让代价最低。
     *      一共是10,20,30这三个，10和20合成一个30，30和30合成一个60，共需要代价90。可以认为每一块是
     *      一个叶节点，怎么决定叶节点的合并顺序让整体的合并代价最低。合并代价怎么评估？两个叶节点合
     *      并之后产生的和就是它的合并代价。相当于这个题是求所有非叶节点的值加起来谁低。这个题整体就
     *      转化为：给了叶节点，选择一个什么合并顺序，能够导致非叶节点整体的求和最小。
     */

    /* 暴力解法 */

    /**
     * 有错误，还没找到为什么
     */
    public static int violence(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        List<Integer> allCosts = new ArrayList<>();
        recurse(array, 0, allCosts);
        int min = allCosts.get(0);
        for (Integer cost : allCosts) {
            min = Math.min(min, cost);
        }
        // TODO 2020/11/16 ...待更正
        return min;
    }

    private static void recurse(int[] array, int cost, List<Integer> allCosts) {
        if (array.length == 1) {
            allCosts.add(cost);
        } else {
            for (int i = 0; i < array.length; i++) {
                int[] remain = ArrayUtil.remove(array, i);
                int newCost = cost + array[i] + Arrays.stream(remain).sum();
                recurse(remain, newCost, allCosts);
            }
        }
    }

    /**
     * 错误的贪心策略
     */
    public static int errorGreedy(int[] array, int goldBarLength) {
        if (array == null || array.length == 0) {
            return 0;
        }
        assert Arrays.stream(array).sum() == goldBarLength;
        return process(array, goldBarLength, 0);
    }

    private static int process(int[] remain, int goldBarLength, int cost) {
        if (remain.length == 1) {
            return cost;
        }
        // 剩余中的最大值
        int maxlength = remain[0];
        int maxIdx = 0;
        for (int i = 0; i < remain.length; i++) {
            if (remain[i] > maxlength) {
                maxlength = remain[i];
                maxIdx = i;
            }
        }
        int[] newRemain = ArrayUtil.remove(remain, maxIdx);
        int newGoldBarLength = goldBarLength - maxlength;
        int newCost = cost + maxlength + newGoldBarLength;
        return process(newRemain, newGoldBarLength, newCost);
    }

    /* 贪心解法 */

    public static int greedy(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int value : array) {
            heap.add(value);
        }
        int result = 0;
        while (heap.size() > 1) {
            Integer val1 = heap.poll();
            Integer val2 = heap.poll();
            int sum = val1 + val2;
            heap.add(sum);
            result += sum;
        }
        return result;
    }

    /* ****************************************************************************************************************/

    /* 对数器 */

    // 暴力解法
    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        List<Integer> all = new ArrayList<>();
        return process(arr, 0, all);
    }

    public static int process(int[] arr, int pre, List<Integer> all) {
        if (arr.length == 1) {
            all.add(pre);
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j], all));
            }
        }
        return ans;
    }

    // 贪心解法
    public static int lessMoney2(int[] arr) {
        PriorityQueue<Integer> pQ = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pQ.add(arr[i]);
        }
        int sum = 0;
        int cur = 0;
        while (pQ.size() > 1) {
            cur = pQ.poll() + pQ.poll();
            sum += cur;
            pQ.add(cur);
        }
        return sum;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    /* ****************************************************************************************************************/

    public static void main2(String[] args) {
        int[] array = {4, 6, 10, 9, 1};
        int violence = violence(array);
        System.out.println(violence);
        int lessMoney1 = lessMoney1(array);
        System.out.println(lessMoney1);
        int lessMoney2 = lessMoney2(array);
        System.out.println(lessMoney2);
        int greedy = greedy(array);
        System.out.println(greedy);
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int r1 = greedy(arr);
            int r2 = violence(arr);
            if (r1 != r2) {
                System.out.println("Oops! data=" + JSON.toJSONString(arr) + ", r1=" + r1 + ", r2=" + r2);
            }
        }
        System.out.println("finish!");
    }

}
