package com.lg.algorithm.dynamicprogramming.practice;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @author Xulg
 * Created in 2020-12-02 13:38
 */
@SuppressWarnings("DuplicatedCode")
class ComposeCurrencyValue {

    /*
     * 给定数组arr，arr中所有的值都为正数且不重复
     * 每个值代表一种面值的货币，每种面值的货币可以使用任意张
     * 再给定一个整数aim，代表要找的钱数
     * 求组成aim的方法数
     *--------------------------------------------------------
     * 思路1：这种思路复杂度好
     * 对于每一个面值的货币我可以选择：
     *  继续选这个面值的货币
     *  选下一个面值的货币
     *--------------------------------------------------------
     * 思路2：视频中的，这种思路复杂度高，但是
     *        经典方式的动态规划是可以优化的
     * 对于每一个面值的货币我可以选择：
     *  用几张这个面值的货币
     */

    /* ****************************************************************************************************************/

    /* 暴力递归解法 */

    private static final boolean DEBUG = false;

    /**
     * array中都是正数且无重复值，返回组成aim的方法数
     * 暴力递归解法1
     * 对于每一个面值的货币我可以选择：
     * 继续选这个面值的货币
     * 选下一个面值的货币
     */
    public static int composeAim1(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }
        return recurse1(array, 0, "", aim);
    }

    /**
     * 递归
     *
     * @param array   面值货币数组，不变参数
     * @param curIdx  当前在货币数组哪个位置
     * @param path    组合路径，只是调试用
     * @param restAim 距离目标值还差多少
     * @return 组合数量
     */
    private static int recurse1(int[] array, int curIdx, String path, int restAim) {
        if (restAim < 0) {
            // 剩余差值为负数了，是一种无效的组合方式
            return -1;
            //return 0;
        }
        if (restAim == 0) {
            // 剩余差值为0了，表示组成了aim值了，是一种有效的组合方式
            if (DEBUG) {
                System.out.println(path);
            }
            return 1;
        }
        if (curIdx == array.length) {
            // 没有货币可以选择了，但是还有距离目标差值
            return -1;
            //return 0;
        }

        int count = 0;

        // 继续选这个面额
        int val1 = recurse1(array, curIdx, path + array[curIdx], restAim - array[curIdx]);
        if (val1 != -1) {
            count += val1;
        }

        // 不选这个面额，选择下一个面额
        if (curIdx + 1 < array.length) {
            int val2 = recurse1(array, curIdx + 1, path, restAim);
            if (val2 != -1) {
                count += val2;
            }
        }
        //int val2 = recurse1(array, curIdx + 1, path, restAim);
        //count += val2;

        return count;
    }

    /**
     * array中都是正数且无重复值，返回组成aim的方法数
     * 暴力递归解法2
     * 对于每一个面值的货币我可以选择：
     * 用几张这个面值的货币
     */
    public static int composeAim2(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }
        return recurse2(array, 0, aim);
    }

    /**
     * 递归
     *
     * @param array   面值货币数组，不变参数
     * @param curIdx  当前在货币数组哪个位置
     * @param restAim 距离目标值还差多少
     * @return 组合数量
     */
    private static int recurse2(int[] array, int curIdx, int restAim) {
        // base case
        if (curIdx == array.length) {
            // 货币选完了，看剩余目标值是不是0，restAim为0则是有效的组合
            return restAim == 0 ? 1 : 0;
        }

        int ways = 0;

        // 当前位置的货币选几张，每种情况都尝试一下
        // 从0张当前货币开始尝试，前提是当前货币面值乘以张数不能大于剩余目标值
        // 当前位置的货币的面值
        int faceVal = array[curIdx];
        for (int count = 0; faceVal * count <= restAim; count++) {
            // 当前货币选了count张，选下一个货币面值去吧，看有几种组合方法
            int val = recurse2(array, curIdx + 1, restAim - faceVal * count);
            ways = ways + val;
        }
        return ways;
    }

    /* ****************************************************************************************************************/

    /* 动态规划解法 */

    /**
     * 暴力解法1对应的动态规划解法
     * 时间复杂度：O(N*M) N为数组array大小，M为目标值aim大小
     */
    public static int dp1(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }
        int n = array.length;
        // 动态规划表：行表示数组中的哪个货币curIdx，列表示剩余的目标值restAim
        int[][] dpTable = new int[n + 1][aim + 1];
        // restAim为0就是1种有效的组合
        for (int curIdx = 0; curIdx < n; curIdx++) {
            dpTable[curIdx][0] = 1;
        }
        // curIdx为n的时候，越界了，无效组合
        for (int restAim = 0; restAim <= aim; restAim++) {
            dpTable[n][restAim] = -1;
        }

        // 第n行不需要处理了，从n-1行开始处理
        for (int curIdx = n - 1; curIdx >= 0; curIdx--) {
            // 第0列已经处理了，从第1列开始
            for (int restAim = 1; restAim <= aim; restAim++) {
                int count = 0;

                // 继续选这个面额
                int val1;
                if (restAim - array[curIdx] < 0) {
                    val1 = -1;
                } else {
                    val1 = dpTable[curIdx][restAim - array[curIdx]];
                }
                if (val1 != -1) {
                    count += val1;
                }

                // 不选这个面额，选择下一个面额
                int val2;
                if (curIdx + 1 < array.length) {
                    val2 = dpTable[curIdx + 1][restAim];
                } else {
                    val2 = -1;
                }
                if (val2 != -1) {
                    count += val2;
                }

                dpTable[curIdx][restAim] = count;
            }
        }

        return dpTable[0][aim];
    }

    /**
     * 暴力解法2对应的动态规划解法1
     * 时间复杂度：O(N*M) N为数组array大小，M为目标值aim大小
     * 这里还有优化空间
     */
    public static int dp2(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }
        int n = array.length;
        // 动态规划表：行表示数组中的哪个货币curIdx，列表示剩余的目标值restAim
        int[][] dpTable = new int[n + 1][aim + 1];
        // base case：货币选完了，剩余目标值是0，是一种有效组合方式
        dpTable[n][0] = 1;
        // n行除了0列，其余值都是0
        for (int restAim = 1; restAim <= aim; restAim++) {
            dpTable[n][restAim] = 0;
        }

        // 从n-1行开始
        for (int curIdx = n - 1; curIdx >= 0; curIdx--) {
            for (int restAim = 0; restAim <= aim; restAim++) {
                // 当前位置的货币的面值
                int faceVal = array[curIdx];
                // 当前位置的货币选几张，每种情况都尝试一下
                // 从0张当前货币开始尝试，前提是当前货币面值乘以张数不能大于剩余目标值
                int ways = 0;
                for (int count = 0; faceVal * count <= restAim; count++) {
                    // 当前货币选了count张，选下一个货币面值去吧，看有几种组合方法
                    int val = dpTable[curIdx + 1][restAim - faceVal * count];
                    ways = ways + val;
                }
                dpTable[curIdx][restAim] = ways;
            }
        }

        return dpTable[0][aim];
    }

    /**
     * 暴力解法2对应的动态规划解法2
     * 时间复杂度：O(N*M) N为数组array大小，M为目标值aim大小
     * 将for循环优化掉了
     */
    public static int dp3(int[] array, int aim) {
        if (array == null || array.length == 0 || aim < 0) {
            return 0;
        }

        int n = array.length;
        // 动态规划表：行表示数组中的哪个货币curIdx，列表示剩余的目标值restAim
        int[][] dpTable = new int[n + 1][aim + 1];
        // base case：货币选完了，剩余目标值是0，是一种有效组合方式
        dpTable[n][0] = 1;
        // n行除了0列，其余值都是0
        for (int restAim = 1; restAim <= aim; restAim++) {
            dpTable[n][restAim] = 0;
        }

        // 从n-1行开始
        for (int curIdx = n - 1; curIdx >= 0; curIdx--) {
            for (int restAim = 0; restAim <= aim; restAim++) {
                // 当前位置的货币的面值
                int faceVal = array[curIdx];

                /*
                    这一段逻辑其实可以优化掉的，通过画表之后可以发现：
                     前提：列的角标不能越界
                        dp[curIdx][restAim] = dp[curIdx+1][restAim-faceVal*0] + ... + dp[curIdx+1][restAim-faceVal*count];
                     等价于：
                        dp[curIdx][restAim] = dp[curIdx+1][restAim] + dp[curIdx][restAim - faceVal];

                // 当前位置的货币选几张，每种情况都尝试一下
                // 从0张当前货币开始尝试，前提是当前货币面值乘以张数不能大于剩余目标值
                int ways = 0;
                for (int count = 0; faceVal * count <= restAim; count++) {
                    // 当前货币选了count张，选下一个货币面值去吧，看有几种组合方法
                    int val = dpTable[curIdx + 1][restAim - faceVal * count];
                    ways = ways + val;
                }
                */
                if (restAim - faceVal >= 0) {
                    // 当前位置的下面一个 + 当前位置的左边一个
                    dpTable[curIdx][restAim] = dpTable[curIdx + 1][restAim] + dpTable[curIdx][restAim - faceVal];
                } else {
                    // 当前位置的下面一个
                    dpTable[curIdx][restAim] = dpTable[curIdx + 1][restAim];
                }
            }
        }
        return dpTable[0][aim];
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
        if (true) {
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
                int count2 = composeAim1(array, aim);
                int count3 = composeAim2(array, aim);
                int count4 = dp1(array, aim);
                int count5 = dp2(array, aim);
                int count6 = dp3(array, aim);
                if (!(count1 == count2 && count2 == count3
                        && count3 == count4 && count4 == count5
                        && count5 == count6)) {
                    System.out.println("Oops");
                }
            }
            System.out.println("finished!");
        }

        int count = composeAim1(new int[]{1, 2}, 4);
        System.out.println(count);
        count = composeAim2(new int[]{1, 2}, 4);
        System.out.println(count);
        count = dp1(new int[]{1, 2}, 4);
        System.out.println(count);
        count = dp2(new int[]{1, 2}, 4);
        System.out.println(count);
        count = dp3(new int[]{1, 2}, 4);
        System.out.println(count);

        count = ways4(new int[]{1, 2}, 4);
        System.out.println(count);
    }

    public static int ways4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;// dp[N][1...aim] = 0;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                int faceVal = arr[index];
                if (rest - faceVal >= 0) {
                    dp[index][rest] += dp[index][rest - faceVal];
                }
            }
        }
        return dp[0][aim];
    }
}
