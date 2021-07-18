package com.lg.leetcode.other;

/**
 * @author Xulg
 * Description: N皇后问题2
 * Created in 2021-05-24 14:08
 */
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel", "AlibabaLowerCamelCaseVariableNaming"})
class NQueensII {

    /*
     * The n-queens puzzle is the problem of placing n queens on an n x n chessboard
     * such that no two queens attack each other.
     *
     * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
     *
     * Example 1:
     * Input: n = 4
     * Output: 2
     * Explanation: There are two distinct solutions to the 4-queens puzzle as shown.
     *
     * Example 2:
     * Input: n = 1
     * Output: 1
     *
     * Constraints:
     * 1 <= n <= 9
     */

    public static int totalNQueens(int n) {
        return BruteForce.totalNQueens(n);
    }

    private static class BruteForce {
        public static int totalNQueens(int n) {
            if (n <= 0) {
                return 0;
            }
            return recurse(0, n, new int[n]);
        }

        /**
         * [0, currentRow)上已经摆了皇后，[currentRow, N)上还没有摆，返回有多少种摆法
         *
         * @param currentRow  当前想要摆放皇后的行号
         * @param totalQueens 总共皇后数
         * @param table       摆放记录表，table[row]=column，表示row行column列摆放了皇后
         * @return 摆放数量
         */
        private static int recurse(int currentRow, int totalQueens, int[] table) {
            // base case
            if (currentRow == totalQueens) {
                // 行用完了
                return 1;
            }
            int answer = 0;
            for (int column = 0; column < totalQueens; column++) {
                if (isAvailablePosition(table, currentRow, column)) {
                    table[currentRow] = column;
                    answer += recurse(currentRow + 1, totalQueens, table);
                    table[currentRow] = 0;
                }
            }
            return answer;
        }

        private static boolean isAvailablePosition(int[] table, int row, int column) {
            for (int prevRow = 0; prevRow < row; prevRow++) {
                if (table[prevRow] == column) {
                    // 同列了
                    return false;
                }
                if (Math.abs(row - prevRow) == Math.abs(column - table[prevRow])) {
                    // 同对角线了
                    return false;
                }
            }
            return true;
        }
    }

    private static class Compare {
        // 请不要超过32皇后问题
        public static int totalNQueens(int n) {
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
            int r0 = Compare.totalNQueens(n);
            int r1 = BruteForce.totalNQueens(n);
            if (r0 != r1) {
                System.out.println("fucking---->n=" + n + " r0=" + r0 + " r1=" + r1);
            }
        }
        System.out.println("good job");
        int count = BruteForce.totalNQueens(1);
        System.out.println(count);

        count = BruteForce.totalNQueens(8);
        System.out.println(count);

        count = BruteForce.totalNQueens(10);
        System.out.println(count);

        count = BruteForce.totalNQueens(12);
        System.out.println(count);

        count = BruteForce.totalNQueens(32);
        System.out.println(count);
    }

}
