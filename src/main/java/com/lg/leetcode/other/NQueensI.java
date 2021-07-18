package com.lg.leetcode.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xulg
 * Description: N皇后问题1
 * Created in 2021-05-24 14:08
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
class NQueensI {

    private static class BruteForce {
        public static List<List<String>> solveNQueens(int n) {
            if (n <= 0) {
                return new ArrayList<>();
            }
            List<char[]> path = new ArrayList<>();
            for (int idx = 0; idx < n; idx++) {
                char[] chars = new char[n];
                Arrays.fill(chars, '.');
                path.add(chars);
            }
            List<List<String>> result = new ArrayList<>();
            recurse(0, n, new int[n], path, result);
            return result;
        }

        /**
         * [0, currentRow)上已经摆了皇后，[currentRow, N)上还没有摆，返回有多少种摆法
         *
         * @param currentRow  当前想要摆放皇后的行号
         * @param totalQueens 总共皇后数
         * @param table       摆放记录表，table[row]=column，表示row行column列摆放了皇后
         */
        private static void recurse(int currentRow, int totalQueens, int[] table,
                                    List<char[]> path, List<List<String>> result) {
            // base case
            if (currentRow == totalQueens) {
                // 行用完了
                result.add(path.stream().map(String::new).collect(Collectors.toList()));
                return;
            }
            for (int column = 0; column < totalQueens; column++) {
                if (isAvailablePosition(table, currentRow, column)) {
                    table[currentRow] = column;
                    path.get(currentRow)[column] = 'Q';
                    recurse(currentRow + 1, totalQueens, table, path, result);
                    // recover
                    table[currentRow] = 0;
                    path.get(currentRow)[column] = '.';
                }
            }
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

    private static class BruteForce2 {

        public static List<List<String>> solveNQueens(int n) {
            if (n <= 0) {
                return new ArrayList<>();
            }
            List<List<String>> result = new ArrayList<>();
            int[] table = new int[n];
            Arrays.fill(table, -1);
            recurse(0, n, table, result);
            return result;
        }

        /**
         * [0, currentRow)上已经摆了皇后，[currentRow, N)上还没有摆，返回有多少种摆法
         *
         * @param currentRow  当前想要摆放皇后的行号
         * @param totalQueens 总共皇后数
         * @param table       摆放记录表，table[row]=column，表示row行column列摆放了皇后
         */
        private static void recurse(int currentRow, int totalQueens, int[] table, List<List<String>> result) {
            // base case
            if (currentRow == totalQueens) {
                // all the queens are int the table, just generate the position path
                List<String> path = new ArrayList<>();
                for (int row = 0; row < totalQueens; row++) {
                    char[] chars = new char[totalQueens];
                    Arrays.fill(chars, '.');
                    if (table[row] != -1) {
                        chars[table[row]] = 'Q';
                    }
                    path.add(new String(chars));
                }
                result.add(path);
                return;
            }
            // enumerate all available columns
            for (int column = 0; column < totalQueens; column++) {
                if (isAvailablePosition(table, currentRow, column)) {
                    // mark the position has used
                    table[currentRow] = column;
                    recurse(currentRow + 1, totalQueens, table, result);
                    // recover
                    table[currentRow] = -1;
                }
            }
        }

        private static boolean isAvailablePosition(int[] table, int row, int column) {
            for (int prevRow = 0; prevRow < row; prevRow++) {
                if (table[prevRow] == column) {
                    // same column
                    return false;
                }
                if (Math.abs(row - prevRow) == Math.abs(column - table[prevRow])) {
                    // same diagonal
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 14; n++) {
            List<List<String>> lists1 = BruteForce.solveNQueens(n);
            List<List<String>> lists2 = BruteForce2.solveNQueens(n);
            if (!lists1.equals(lists2)) {
                System.out.println("fucking");
                for (List<String> list : lists1) {
                    System.out.println(list);
                }
                System.out.println("----------------------------");
                for (List<String> list : lists2) {
                    System.out.println(list);
                }
                System.out.println("============================\r\n");
            }
        }
        System.out.println("good job");
    }
}
