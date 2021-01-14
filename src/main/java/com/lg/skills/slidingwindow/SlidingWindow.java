package com.lg.skills.slidingwindow;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 滑动窗口
 *
 * @author Xulg
 * Created in 2020-12-21 9:27
 */
class SlidingWindow {

    /*
     *滑动窗口是什么？
     *  滑动窗口是一种想象出来的数据结构：
     *      1.滑动窗口有左边界L，右边界R
     *      2.在数组、字符串、一个序列上，记为S，窗口就是S[L...R]这一部分
     *      3.L和R都只能往右边滑动
     *      4.L往右边滑动意味着一个样本出了窗口；R往右边滑动意味着一个样本进了窗口
     *              [ 1, 2, 3, 4, 5, 6 ]
     *                L        R
     *              L往右滑动，样本2出了了窗口：
     *                  [ 1, 2, 3, 4, 5, 6 ]
     *                       L     R
     *              R往右滑动，样本5进入了窗口：
     *                  [ 1, 2, 3, 4, 5, 6 ]
     *                    L           R
     *滑动窗口能做什么？
     *  滑动窗口、首尾指针等技巧，说白了是一种求解问题的流程设计
     *
     *滑动内最大值和最小值的更新结构
     *  窗口不管L还是R滑动之后，都会让窗口呈现新状况，
     *  如何能够更快的得到窗口当前状况下的最大值和最小值？
     *  最好平均下来复杂度能做到O(1)
     * 利用单调双端队列！
     */

    /* ****************************************************************************************************************/

    /*
     * 滑动窗口练习题1
     *  假设一个固定大小为W的窗口，依次划过arr数组，返回每一次滑出状态的最大值。
     *  (每个窗口的最大值)
     *  例如：int[] arr = {4, 3, 5, 4, 3, 3, 6, 7}; int w = 3
     *  返回：[5, 5, 5, 4, 6, 7]
     */

    public static void main2(String[] args) {
        if (true) {
            int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
            int w = 3;
            int[] r = slidingByViolence(arr, w);
            System.out.println(Arrays.toString(r));

            r = slidingByBestSolution(arr, w);
            System.out.println(Arrays.toString(r));
        }

        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(1, 10);
            int[] arr = new int[length];
            for (int i = 0; i < length; i++) {
                arr[i] = RandomUtil.randomInt(0, 10);
            }
            int w = RandomUtil.randomInt(0, length + 1);

            int[] r1 = slidingByViolence(arr, w);
            int[] r2 = slidingByBestSolution(arr, w);
            if (!(Arrays.equals(r1, r2))) {
                System.out.println("oops");
            }
        }
        System.out.println("finished!");
    }

    // 暴力解法

    public static int[] slidingByViolence(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w <= 0 || w > arr.length) {
            return null;
        }

        // arr.length - w + 1表示会产生多少个窗口
        int[] result = new int[arr.length - w + 1];
        int idx = 0;

        // 滑动窗口左边界位置
        int left = 0;
        // 滑动窗口右边界位置
        int right = left + w - 1;

        while (left < arr.length && right < arr.length) {
            // 遍历窗口内的数据
            int max = arr[left];
            for (int i = left; i <= right; i++) {
                max = Math.max(max, arr[i]);
            }
            // 记录这个窗口内的最大值
            result[idx++] = max;

            // 窗口整体往右边移动
            left++;
            right = left + w - 1;
        }

        return result;
    }

    // 最优解法

    @SuppressWarnings("ConstantConditions")
    public static int[] slidingByBestSolution(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w <= 0 || w > arr.length) {
            return null;
        }

        // arr.length - w + 1表示会产生多少个窗口
        int[] result = new int[arr.length - w + 1];
        int idx = 0;

        // 存放的是索引位置
        LinkedList<Integer> queue = new LinkedList<>();

        // right为滑动窗口的右边界位置
        for (int right = 0; right < arr.length; right++) {
            // 弹出队列中小于等于arr[right]的值，保证队列中的值是从大到小的顺序
            while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[right]) {
                queue.pollLast();
            }
            // 从队列尾部加入值
            queue.addLast(right);

            // 滑动窗口的左边界位置
            int left = right - w + 1;

            if (false) {
                // 视频中这么写的
                if (queue.peekFirst() == right - w) {
                    queue.pollFirst();
                }
            }

            // 必须形成w长度的窗口
            if (left >= 0) {
                // 判断队列头部元素有没有越过窗口的左边界
                if (queue.peekFirst() < left) {
                    // 超过了窗口的左边界，舍弃
                    queue.pollFirst();
                }
                // 队列的头部就是窗口的最大值位置
                result[idx++] = arr[queue.peekFirst()];
            }
        }
        return result;
    }

    /* ****************************************************************************************************************/

    /*
     * 滑动窗口练习题2
     *  给定一个整型数组arr，和一个整数num。某个arr的子数组sub，如果想达标，必须满足：
     *      sub中最大值 - sub中最小值 <= num
     *  返回arr中达标子数组的数量
     *=============================================================================
     * 暴力解法：
     *  枚举所有的子数组，然后判断每一个子数组的最大值最小值的差是否满足小于等于num
     *-----------------------------------------------------------------------------
     * 最优解法：
     *          arr { ... a    b    c    d    e    f    g ... }
     *                 L2 L    L1                  R1   R R2
     *  假设：数组arr在[L, R]范围上，满足条件：max(arr[L, R]) - min(arr[L, R]) <= num
     *  则有：在[L, R]范围上的任意子数组sub[L1, R1]都满足条件：max(arr[L1, R1]) - min(arr[L1, R1]) <= num
     *          因为：max(arr[L, R]) - min(arr[L, R]) <= num
     *                max(arr[L1, R1]) <= max(arr[L, R])    min(arr[L1, R1]) >= min(arr[L, R])
     *          推出：max(arr[L1, R1]) - min(arr[L1, R1]) <= num
     *  假设：数组arr在[L, R]范围上，不满足条件
     *  则有：窗口继续外扩(L左移，R右移)形成的任意子数组sub[L2, R2]也不满足条件
     *
     * L从0位置开始形成窗口，R从0位置不停右扩，一直扩到不满足条件为止
     * arr:{ a    b    c    d    e    f    g }
     *       0    1    2    3    4    5    6
     *       L              R
     *      R右扩到3位置时不满足条件了，那么在[L, R-1]上的以L开头的子数组都满足条件，计算出有多少个子数组
     *          count = (R - 1) - L + 1
     *                =  R - L
     *      L位置右移
     * L从1位置开始形成窗口，R从3位置不停右扩，一直扩到不满足条件为止
     * arr:{ a    b    c    d    e    f    g }
     *       0    1    2    3    4    5    6
     *            L                   R
     *      ......
     */

    // 暴力解法

    public static int countByViolence(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 0) {
            // 数组中的最大值减最小值不可能出现负数情况，所有如果num为负数直接返回0
            return 0;
        }
        int count = 0;
        // 枚举arr的所有子数组sub
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                // sub[i..j]数组的最大，最小值
                //System.out.println("subArr[" + i + ", " + j + "]");
                int min = arr[i];
                int max = arr[i];
                for (int k = i; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                    max = Math.max(max, arr[k]);
                }
                if (max - min <= num) {
                    count++;
                }
            }
        }
        return count;
    }

    // 最优解法

    public static int countByBestSolution(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 0) {
            // 数组中的最大值减最小值不可能出现负数情况，所以如果num为负数直接返回0
            return 0;
        }

        LinkedList<Integer> maxQueue = new LinkedList<>();
        LinkedList<Integer> minQueue = new LinkedList<>();

        int count = 0;
        int left = 0;
        int right = 0;

        // left是开头位置，尝试每一个开头
        while (left < arr.length) {
            // 如果此时窗口的开头是left,下面的while工作:right向右扩到违规为止

            // 找到第一个不满足条件的右边界位置
            while (right < arr.length) {
                // 窗口内最小值
                while (!maxQueue.isEmpty() && arr[maxQueue.peekLast()] <= arr[right]) {
                    maxQueue.pollLast();
                }
                maxQueue.addLast(right);
                // 窗口内最大值
                while (!minQueue.isEmpty() && arr[minQueue.peekLast()] >= arr[right]) {
                    minQueue.pollLast();
                }
                minQueue.addLast(right);
                if (arr[maxQueue.getFirst()] - arr[minQueue.getFirst()] > num) {
                    break;
                }
                right++;
            }

            // 此时的right位置是不满足条件的
            // 在[left, right-1]范围上以left开始的子数组都符合
            // count += (right - 1) - left + 1;
            count += right - left;

            // 窗口左边界有没有失效
            if (maxQueue.peekFirst() == left) {
                maxQueue.pollFirst();
            }
            if (minQueue.peekFirst() == left) {
                minQueue.pollFirst();
            }

            left++;
        }

        return count;
    }

    public static void main(String[] args) {
        int times = 1000000;
        for (int time = 0; time < times; time++) {
            int length = RandomUtil.randomInt(1, 10);
            int[] arr = new int[length];
            for (int i = 0; i < length; i++) {
                arr[i] = RandomUtil.randomInt(0, 10);
            }
            int num = ArrayUtil.max(arr) - ArrayUtil.min(arr) + RandomUtil.randomInt(-5, 5);
            int r1 = countByViolence(arr, num);
            int r2 = countByBestSolution(arr, num);
            if (!(r1 == r2)) {
                System.out.println("oops" + "num = " + num);
            }
        }
        System.out.println("finished!");
        int count = countByBestSolution(new int[]{3, 7}, -1);
        System.out.println(count);

        main2(args);
    }

}
