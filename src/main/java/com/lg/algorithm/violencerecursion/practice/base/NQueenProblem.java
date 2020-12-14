package com.lg.algorithm.violencerecursion.practice.base;

/**
 * @author Xulg
 * Created in 2020-11-27 14:59
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
class NQueenProblem {

    /*
     *N皇后问题
     * N皇后问题是指在N*N的棋盘上要摆N个皇后，要求
     *      任何两个皇后不同行、不同列，也不在同一条斜线上
     * 给定一个整数n，返回n皇后的摆法有多少种。
     * n=1，返回1
     * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
     * n=8，返回92
     *-----------------------------------------------------
     */

    /**
     * N皇后摆法
     * 时间复杂度O(N!)
     *
     * @param queenCount 皇后数量
     * @return 摆法
     */
    public static int getQueen(int queenCount) {
        if (queenCount <= 0) {
            return 0;
        }

        /*
         * 存放皇后位置的记录表
         * 数组的index表示行，record[index]表示列，record[index]表示皇后放在index行record[index]列
         * record[3]=2表示皇后放在了3行2列
         */
        int[] record = new int[queenCount];

        return recurse(record, 0, queenCount);
    }

    /**
     * 潜台词：record[0, line-1]的皇后，任何两个皇后一定都不共行、不共列，不共斜线
     * 目前来到了第line行
     * record[0, line-1]表示之前的行，放了的皇后位置
     * n代表整体一共有多少行  [0, n-1]行
     * 返回值是，摆完所有的皇后，合理的摆法有多少种
     *
     * @param record     放皇后的记录表
     * @param line       想要将皇后放在line行
     * @param queenCount 皇后数量
     * @return 摆法
     */
    private static int recurse(int[] record, int line, int queenCount) {
        if (line == queenCount) {
            // 行已经越界了，这是一种有效的摆法，返回1
            return 1;
        }

        // 没有到终止位置，还有皇后要摆
        int result = 0;

        // 判断line行哪列可以放皇后啊
        // 当前行在line行，尝试line行所有的列
        for (int column = 0; column < queenCount; column++) {
            /*
             * 当前line行的皇后，放在column列，会不会和之前[0, line-1]的皇后，不共行共列或者共斜线
             * 如果是，认为有效
             * 如果不是，认为无效
             */
            if (isValid(record, line, column)) {
                // 这个位置是合法的，可以放入皇后
                record[line] = column;
                // 判断下一行去
                result += recurse(record, line + 1, queenCount);
                // 恢复现场，其实不恢复也没问题，因为行line不会重复判断
                record[line] = 0;
            }
        }
        return result;
    }

    /**
     * 判断(line, column)点可不可以放皇后
     * line行之前的某个行，对角线有皇后，则不能放皇后
     *
     * @param record 放皇后的记录表
     * @param line   将要存放皇后的行
     * @param column 将要存放皇后的列
     * @return 是否可以放皇后
     */
    private static boolean isValid(int[] record, int line, int column) {
        // [0, line-1]行存放的皇后会不会和line行放皇后冲突
        // line行之前的行和line行会不会出现同行|同列|同对角线的皇后
        for (int l = 0; l < line; l++) {
            // record[l]表示皇后在l行record[l]列
            if (column == record[l]) {
                // 同列了
                return false;
            }
            // 两个点如果是对角线的话，那么两个点的行列差值是相等的
            if (Math.abs(line - column) == Math.abs(l - record[l])) {
                // 同对角线了
                return false;
            }
        }
        return true;
    }

    /**
     * N皇后摆法，使用了位运算，速度提高了很多
     * 时间复杂度O(N!)
     * 请不要超过32皇后问题
     */
    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    // limit 划定了问题的规模 -> 固定
    // colLim 列的限制，1的位置不能放皇后，0的位置可以
    // leftDiaLim 左斜线的限制，1的位置不能放皇后，0的位置可以
    // rightDiaLim 右斜线的限制，1的位置不能放皇后，0的位置可以
    public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if (colLim == limit) {
            // base case
            return 1;
        }
        // 所有可以放皇后的位置，都在pos上
        // colLim | leftDiaLim | rightDiaLim   -> 总限制
        // ~ (colLim | leftDiaLim | rightDiaLim) -> 左侧的一坨0干扰，右侧每个1，可尝试
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            // 其取出pos中，最右侧的1来，剩下位置都是0
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process2(limit,
                    colLim | mostRightOne,
                    (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args) {
        int count = getQueen(1);
        System.out.println(count);

        count = getQueen(8);
        System.out.println(count);

        count = getQueen(10);
        System.out.println(count);

        count = getQueen(12);
        System.out.println(count);

        count = getQueen(32);
        System.out.println(count);
    }
}
