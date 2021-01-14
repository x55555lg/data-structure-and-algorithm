package com.lg.skills.monotonicstack.practice;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-01-12 19:18
 */
class Demo1 {

    /*
     * 给定一个只包含正数的数组arr，arr中任何一个子数组sub，
     * 一定都可以算出(sub累加和) * (sub中的最小值)是什么，
     * 那么所有子数组中，这个值最大是多少？
     */

    /* ****************************************************************************************************************/

    /**
     * 时间复杂度
     * O(n^3)
     */
    public static int getMaxByViolence(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] * arr[0];
        }

        ArrayList<Integer> list = new ArrayList<>();
        // 枚举所有的sub子数组
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                //if (i == 0 && j == arr.length - 1) {
                // subArr[0, n-1]这种子数组就是arr，排除掉
                //    continue;
                //}
                // subArr[i, j]
                //System.out.println("subArr[" + i + ", " + j + "]");
                int min = arr[i];
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                    sum += arr[k];
                }
                list.add(sum * min);
            }
        }

        int max = Integer.MIN_VALUE;
        for (int val : list) {
            max = Math.max(max, val);
        }
        return max;
    }

    /**
     * 时间复杂度
     * O(n)
     */
    public static int getMaxByBestSolution(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // sum(0, i)的和
        // sumArr[0]表示arr[0, 0]的和
        // sumArr[1]表示arr[0, 1]的和
        // 数组arr[L, R]任意范围的和 = sumArr[R] - sumArr[L-1];
        int[] sumArr = new int[arr.length];
        // [0, 0]的和就是arr[0]
        sumArr[0] = arr[0];
        for (int i = 1; i < sumArr.length; i++) {
            // arr[0, i]的和 = arr[0, i-1]的和(即sumArr[i-1]) + arr[i];
            sumArr[i] = sumArr[i - 1] + arr[i];
        }

        // 求数组每个位置arr[i]的左边最近最小和右边最近最小
        int[][] leftRightLess = getLeftRightLess(arr);
        assert leftRightLess != null;

        int result = Integer.MIN_VALUE;
        for (int idx = 0; idx < arr.length; idx++) {
            // 求取idx位置的值为最小的范围最大的子数组
            int left = (leftRightLess[idx][0] == -1) ? 0 : leftRightLess[idx][0] + 1;
            int right = (leftRightLess[idx][1] == -1) ? arr.length - 1 : leftRightLess[idx][1] - 1;
            // 如果子数组是整个arr则不要
            //if (left == 0 && right == arr.length - 1) {
            //    continue;
            //}
            // 求arr[left,right]范围的累加和
            int subSum = (left == 0) ? sumArr[right] : sumArr[right] - sumArr[left - 1];
            int val = subSum * arr[idx];
            result = Math.max(val, result);
        }

        return result;
    }

    @SuppressWarnings("DuplicatedCode")
    private static int[][] getLeftRightLess(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        // result[i][0]表示i位置的左边最近的小于arr[i]的位置
        // result[i][1]表示i位置的右边边最近的小于arr[i]的位置
        int[][] result = new int[arr.length][2];

        // 栈底到栈顶 从小到大 相同则用list并排存
        // ArrayList<Integer> -> 放的是位置，同样值的东西，位置压在一起
        // 代表值    底  ->  顶   小  -> 大
        Stack<ArrayList<Integer>> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            // i位置想要入栈，保证栈如下特性：底 -> 顶， 小 -> 大
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                ArrayList<Integer> list = stack.pop();
                for (int idx : list) {
                    // idx位置的左边最近最小 (取位于下面位置的列表中，最晚加入的那个)
                    // idx位置的左边最近最小 (取位于下面位置的列表中，最晚加入的那个)
                    result[idx][0] = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                    // idx位置的右边最近最小 (谁让我弹出来，谁就是我的右边最近小于)
                    result[idx][1] = i;
                }
            }

            // 栈顶位置位置的值 <= arr[i]
            if (stack.isEmpty() || arr[stack.peek().get(0)] < arr[i]) {
                // 栈顶位置的值小于arr[i]，将arr[i]压入栈
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            } else {
                // arr[i]和栈顶位置的值相等，加入list列表中
                stack.peek().add(i);
            }
        }

        // 栈中剩余的位置都没有右边最近小于
        while (!stack.isEmpty()) {
            ArrayList<Integer> list = stack.pop();
            for (int idx : list) {
                // idx位置的左边最近最小 (取位于下面位置的列表中，最晚加入的那个)
                result[idx][0] = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                // 没有右边最近小于
                result[idx][1] = -1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int[] arr = new int[RandomUtil.randomInt(1, 11)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(1, 21);
            }
            int r1 = getMaxByViolence(arr);
            int r2 = getMaxByBestSolution(arr);
            if (!(r1 == r2)) {
                System.out.println("Oops======arr " + JSON.toJSONString(arr)
                        + " =====r1 " + r1 + " ======r2 " + r2);
            }
        }
        System.out.println("finish!");

        System.out.println(getMaxByViolence(new int[]{12, 6}));
        System.out.println(getMaxByBestSolution(new int[]{12, 6}));

        System.out.println(getMaxByViolence(new int[]{10, 5, 7}));
        System.out.println(getMaxByBestSolution(new int[]{10, 5, 7}));

        System.out.println(getMaxByViolence(new int[]{2}));
        System.out.println(getMaxByBestSolution(new int[]{2}));

        System.out.println(getMaxByViolence(new int[]{9, 8, 11, 7}));
        System.out.println(getMaxByBestSolution(new int[]{9, 8, 11, 7}));
    }

}
