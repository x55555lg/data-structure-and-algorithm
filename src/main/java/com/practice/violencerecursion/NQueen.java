package com.practice.violencerecursion;

/**
 * N皇后问题求解
 *
 * @author Xulg
 * Description: N皇后问题求解
 * Created in 2021-05-17 21:16
 */
class NQueen {

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

    private static class BruteForce {
        public static int getQueenCount(int n) {
            if (n <= 0) {
                return 0;
            }
            int[] table = new int[n];
            return recurse(0, n, table);
        }

        /**
         * [0, line)行已经摆放了皇后，[line, N)行上怎么摆
         *
         * @param line        想要将皇后放在这行
         * @param totalQueens 皇后总数量
         * @param table       记录X行Y列是否摆放了皇后，table[2]=3表示皇后摆在2行3列
         * @return 摆法
         */
        private static int recurse(int line, int totalQueens, int[] table) {
            // base case
            if (line == totalQueens) {
                // 行已经用完了
                return 1;
            }
            int answer = 0;
            // 遍历[0, N)列，看哪个位置可以摆放皇后
            for (int column = 0; column < totalQueens; column++) {
                // 当前line行的皇后，放在column列，会不会和之前[0, line)的皇后，不共行、不共列、不共斜线
                if (isAvailable(table, line, column)) {
                    // 标记(line, column)已经使用
                    table[line] = column;
                    // 后续的
                    int next = recurse(line + 1, totalQueens, table);
                    // 恢复现场，可以不恢复，因为行不会重复使用
                    table[line] = 0;
                    answer = answer + next;
                }
            }
            return answer;
        }

        private static boolean isAvailable(int[] table, int line, int column) {
            // [0, line-1]行存放的皇后会不会和line行放皇后冲突
            for (int row = 0; row < line; row++) {
                if (table[row] == column) {
                    // column列已经有皇后了
                    return false;
                }
                if (Math.abs(column - table[row]) == Math.abs(line - row)) {
                    // 对角线已经有皇后了
                    return false;
                }
            }
            return true;
        }
    }

    private static class Compare {
        // 请不要超过32皇后问题
        public static int getQueenCount(int n) {
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
        private static int process2(
                int limit,
                int colLim,
                int leftDiaLim,
                int rightDiaLim) {
            if (colLim == limit) { // base case
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
    }

    public static void main(String[] args) {
        for (int n = 0; n < 16; n++) {
            int r0 = Compare.getQueenCount(n);
            int r1 = BruteForce.getQueenCount(n);
            if (r0 != r1) {
                System.out.println("fucking---->n=" + n + " r0=" + r0 + " r1=" + r1);
            }
        }
        System.out.println("good job");
        int count = BruteForce.getQueenCount(1);
        System.out.println(count);

        count = BruteForce.getQueenCount(8);
        System.out.println(count);

        count = BruteForce.getQueenCount(10);
        System.out.println(count);

        count = BruteForce.getQueenCount(12);
        System.out.println(count);

        count = BruteForce.getQueenCount(32);
        System.out.println(count);
    }
}
