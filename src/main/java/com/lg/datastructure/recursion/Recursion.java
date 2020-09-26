package com.lg.datastructure.recursion;

/**
 * 递归
 *
 * @author Xulg
 * Created in 2020-09-24 15:35
 */
public class Recursion {

    /*
     * 某一种递归的时间复杂度计算公式：
     *      T(N) = a*T(N/b) + O(n^d)
     *          N是数据量
     *          a是递归次数
     *          b是子规模大小
     *          O(n^d)是除递归之外主体代码执行的时间复杂度
     *      如果
     *          log(b)^a > d     时间复杂度：O( N^(log(b)^a) )
     *          log(b)^a < d     时间复杂度：O( N^d )
     *          log(b)^a == d    时间复杂度：O( N^d * log(N) )
     * 例如：
     *      GetMax.getMax()方法的复杂度计算：
     *          递归了2次，a = 2
     *          每次递归数组的一半即N/2（N为数组长度），b = 2
     *          除了递归代码之外，其余代码执行的复杂度是O(1)，d = 0
     *          所以：T(N) = 2*T(N/2) + O(n^0)
     *                T(N) = 2T(N/2) + O(1)
     *                log(2)^2 == 1 > 0  时间复杂度：O(N)
     */

    public static void main(String[] args) {
        System.out.println(GetMax.getMax(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
    }

    /**
     * 使用递归获取数组中的最大值
     */
    private static class GetMax {
        public static int getMax(int[] array) {
            return doGetMax(array, 0, array.length - 1);
        }

        private static int doGetMax(int[] array, int left, int right) {
            if (left == right) {
                return array[left];
            }
            // 中间点啊
            int mid = left + ((right - left) >> 1);
            // 左边的最大值
            int leftMax = doGetMax(array, left, mid);
            // 右边的最大值
            int rightMax = doGetMax(array, mid + 1, right);
            return Math.max(leftMax, rightMax);
        }
    }
}
