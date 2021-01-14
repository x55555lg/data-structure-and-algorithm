package com.lg.skills.monotonicstack;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 单调栈
 *
 * @author Xulg
 * Created in 2021-01-12 14:29
 */
class MonotonicStack {

    /*
     * 单调栈是什么？
     * 一种特别设计的栈结构，为了解决如下的问题：
     *  给定一个可能含有重复值的数组arr，i位置的数一定存在如下两个信息
     *  1）arr[i]的左侧离i最近并且小于(或者大于)arr[i]的数在哪？
     *  2）arr[i]的右侧离i最近并且小于(或者大于)arr[i]的数在哪？
     *  如果想得到arr中所有位置的两个信息，怎么能让得到信息的过程尽量快。
     *
     * 那么到底怎么设计呢？
     *=========================================================================================
     * 暴力解法
     *  在i位置往左边遍历[0, i-1]找出最近最小的位置；在i位置往右边遍历[i+1, n)找出最近最小的位置
     *=========================================================================================
     * 单调栈解法
     *  数组arr：                          { 4, 7, 1, 3, 14, 12, 1, 14 }
     *  创建栈Stack<List<Integer>> stack： {                           }
     *                                     栈顶                        栈底
     *                                     大            ←            小
     *                                     栈底到栈顶，从小到大排列，如果相等则并列
     *  结果集int[][] result：             {}
     *-------------------------------------------------------------------------------
     *  for循环遍历数组arr：               arr[i]
     *      { 4    7    1    3    14    12    1    14 }
     *        ↑
     *       i = 0    arr[i] = 4    此时stack是空的直接将i=0压入栈
     *                                  stack { {0} }
     *      ----------------------------------------------------------------------------------
     *      { 4    7    1    3    14    12    1    14 }
     *             ↑
     *       i = 1    arr[i] = 7    (arr[i]=7 > stack栈顶元素arr[0]=4)，将i=1压入栈
     *                                  stack { {1}, {0} }
     *      ----------------------------------------------------------------------------------
     *      { 4    7    1    3    14    12    1    14 }
     *                  ↑
     *       i = 2    arr[i] = 1    (arr[i]=1 < stack栈顶元素arr[1]=7)，弹出栈顶的元素{1}
     *                                  1位置的右边最近小于就是(i=2)
     *                                      谁让我弹出来的谁就是我的右边最近小于
     *                                  1位置的左边最近小于就是此时的栈顶元素{0}，如果没有就是-1
     *                                      在栈中，我的下面位置的元素就是我的左边最近小于，没有就是-1
     *                                  stack { {0} }
     *                                 result { 1:{0,2} }
     *                               (arr[i]=1 < stack栈顶元素arr[0]=4)，弹出栈顶的元素{0}
     *                                  0位置的右边最近小于就是(i=2)
     *                                      谁让我弹出来的谁就是我的右边最近小于
     *                                  0位置的左边最近小于就是此时的栈顶元素，没有就是-1
     *                                      在栈中，我的下面位置的元素就是我的左边最近小于，没有就是-1
     *                                  stack { }
     *                                 result { 1:{0,2}, 0:{-1,2} }
     *                               (此时stack是空)直接将i=2压入栈
     *                                  stack { {2} }
     *      ----------------------------------------------------------------------------------
     *      { 4    7    1    3    14    12    1    14 }
     *                       ↑
     *       i = 3    arr[i] = 3    (arr[i]=3 > stack栈顶元素arr[2]=1)，将i=3压入栈
     *                                  stack { {3}, {2} }
     *      ----------------------------------------------------------------------------------
     *      { 4    7    1    3    14    12    1    14 }
     *                            ↑
     *       i = 4    arr[i] = 14   (arr[i]=14 > stack栈顶元素arr[3]=3)，将i=4压入栈
     *                                  stack { {4}, {3}, {2} }
     *      ----------------------------------------------------------------------------------
     *      { 4    7    1    3    14    12    1    14 }
     *                                  ↑
     *       i = 5    arr[i] = 12   (arr[i]=12 < stack栈顶元素arr[4]=14)，弹出栈顶的元素{4}
     *                                  4位置的右边最近小于就是(i=5)
     *                                      谁让我弹出来的谁就是我的右边最近小于
     *                                  4位置的左边最近小于就是此时的栈顶元素{3}，如果没有就是-1
     *                                      在栈中，我的下面位置的元素就是我的左边最近小于，没有就是-1
     *                                  stack { {3}, {2} }
     *                                 result { 1:{0,2}, 0:{-1,2}, 4:{3,5} }
     *                              (arr[i]=12 > stack栈顶元素arr[3]=3)，将i=5压入栈
     *                                  stack { {5}, {3}, {2} }
     *      ----------------------------------------------------------------------------------
     *      { 4    7    1    3    14    12    1    14 }
     *                                        ↑
     *       i = 6    arr[i] = 1    (arr[i]=1 < stack栈顶元素arr[5]=12)，弹出栈顶的元素{5}
     *                                  5位置的右边最近小于就是(i=6)
     *                                      谁让我弹出来的谁就是我的右边最近小于
     *                                  5位置的左边最近小于就是此时的栈顶元素{3}，如果没有就是-1
     *                                      在栈中，我的下面位置的元素就是我的左边最近小于，没有就是-1
     *                                  stack { {3}, {2} }
     *                                 result { 1:{0,2}, 0:{-1,2}, 4:{3,5}, 5:{3,6} }
     *                              (arr[i]=1 < stack栈顶元素arr[3]=3)，弹出栈顶的元素{3}
     *                                  3位置的右边最近小于就是(i=6)
     *                                      谁让我弹出来的谁就是我的右边最近小于
     *                                  3位置的左边最近小于就是此时的栈顶元素{2}，如果没有就是-1
     *                                      在栈中，我的下面位置的元素就是我的左边最近小于，没有就是-1
     *                                  stack { {2} }
     *                                 result { 1:{0,2}, 0:{-1,2}, 4:{3,5}, 5:{3,6}, 3:{2,6} }
     *                              (arr[i]=1 == stack栈顶元素arr[2]=1)，由于相等，将i=6并入(晚并入的放右边)
     *                                  stack { {2,6} }
     *      ----------------------------------------------------------------------------------
     *      { 4    7    1    3    14    12    1    14 }
     *                                             ↑
     *       i = 7    arr[i] = 14   (arr[i]=14 > stack栈顶元素arr[2]=1)，将i=7压入栈
     *                                  stack { {7}, {2,6} }
     *      ----------------------------------------------------------------------------------
     *  for循环结束后stack栈中还存有元素，进行弹出操作直到栈空了为止：
     *      stack：      { {7}, {2,6} }
     *      弹出栈顶元素{7}
     *              7位置的右边最近小于是-1，因为没有谁让我弹出来的
     *              7位置的左边最近小于就是此时的栈顶元素{2,6}，因为有相等的情况，需要取最右边的6
     *                          result { 1:{0,2}, 0:{-1,2}, 4:{3,5}, 5:{3,6}, 3:{2,6}, 7:{6,-1} }
     *      ----------------------------------------------------------------------------------
     *      弹出栈顶元素{2,6}
     *              2位置的右边最近小于是-1，因为没有谁让我弹出来的
     *              2位置的左边最近小于就是此时的栈顶元素，此时栈是空的，所以是-1
     *              6位置的右边最近小于是-1，因为没有谁让我弹出来的
     *              6位置的左边最近小于就是此时的栈顶元素，此时栈是空的，所以是-1
     *                          result { 1:{0,2}, 0:{-1,2}, 4:{3,5}, 5:{3,6}, 3:{2,6}, 7:{6,-1}, 2:{-1,-1}, 6:{-1,-1} }
     *      ----------------------------------------------------------------------------------
     */

    /**
     * 暴力解法
     * O(n^2)
     */
    public static int[][] getByViolence(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        // result[i][0]表示i位置的左边最近的小于arr[i]的位置
        // result[i][1]表示i位置的右边边最近的小于arr[i]的位置
        int[][] result = new int[arr.length][2];

        for (int i = 0; i < arr.length; i++) {
            // arr[i]的左侧离i最近并且小于arr[i]的数在哪
            int leftLessIndex = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] < arr[i]) {
                    leftLessIndex = j;
                    break;
                }
            }
            // arr[i]的右侧离i最近并且小于arr[i]的数在哪
            int rightLessIndex = -1;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    rightLessIndex = j;
                    break;
                }
            }
            result[i][0] = leftLessIndex;
            result[i][1] = rightLessIndex;
        }
        return result;
    }

    /**
     * 数组不带重复数字的单调栈解法
     * O(n)
     */
    public static int[][] getByMonotonicStackNotRepeat(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        // result[i][0]表示i位置的左边最近的小于arr[i]的位置
        // result[i][1]表示i位置的右边边最近的小于arr[i]的位置
        int[][] result = new int[arr.length][2];

        // 栈底到栈顶 从小到大
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int idx = stack.pop();
                // 左边的最近的小于arr[i]的位置
                result[idx][0] = stack.isEmpty() ? -1 : stack.peek();
                // 右边的最近的小于arr[i]的位置
                result[idx][1] = i;
            }
            // 栈顶 <= arr[i]
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int idx = stack.pop();
            // 左边的最近的小于arr[i]的位置
            result[idx][0] = stack.isEmpty() ? -1 : stack.peek();
            // 右边的最近的小于arr[i]的位置
            result[idx][1] = -1;
        }
        return result;
    }

    /**
     * 数组带重复数字的单调栈解法
     * O(n)
     */
    @SuppressWarnings("DuplicatedCode")
    public static int[][] getByMonotonicStackRepeatable(int[] arr) {
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
            int[] arr = new int[RandomUtil.randomInt(0, 11)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtil.randomInt(21);
            }
            int[][] r1 = getByViolence(arr);
            int[][] r2 = getByMonotonicStackRepeatable(arr);
            if (!(isEquals(r1, r2))) {
                System.out.println("Oops======arr " + JSON.toJSONString(arr) + " =====r1 "
                        + JSON.toJSONString(r1) + " ======r2 " + JSON.toJSONString(r2));
            }
        }
        System.out.println("finish!");
        int[][] r1 = getByViolence(new int[]{3, 4, 2, 5, 6, 0, 1});
        System.out.println(JSON.toJSONString(r1, true));
        int[][] r2 = getByMonotonicStackNotRepeat(new int[]{3, 4, 2, 5, 6, 0, 1});
        System.out.println(JSON.toJSONString(r2, true));
        int[][] r3 = getByMonotonicStackNotRepeat(new int[]{3, 4, 2, 5, 6, 0, 1});
        System.out.println(JSON.toJSONString(r3, true));
        System.out.println(isEquals(r1, r2));
        int[][] r4 = getByMonotonicStackRepeatable(new int[]{3, 4, 2, 5, 6, 0, 1});
        System.out.println(JSON.toJSONString(r4, true));
        System.out.println(isEquals(r3, r4));

        int[][] r5 = getByViolence(new int[]{4, 7, 1, 3, 14, 12, 1, 14});
        int[][] r6 = getByMonotonicStackRepeatable(new int[]{4, 7, 1, 3, 14, 12, 1, 14});
        System.out.println(JSON.toJSONString(r5, true));
        System.out.println(JSON.toJSONString(r6, true));
        System.out.println(isEquals(r5, r6));
    }

    private static boolean isEquals(int[][] r1, int[][] r2) {
        if (r1 == null && r2 == null) {
            return true;
        }
        if (r1 == null || r2 == null) {
            return false;
        }
        if (r1.length != r2.length) {
            return false;
        }
        for (int i = 0; i < r1.length; i++) {
            int[] arr1 = r1[i];
            int[] arr2 = r2[i];
            if (arr1.length != arr2.length) {
                return false;
            }
            for (int j = 0; j < arr1.length; j++) {
                if (arr1[j] != arr2[j]) {
                    return false;
                }
            }
        }
        return true;
    }

}
