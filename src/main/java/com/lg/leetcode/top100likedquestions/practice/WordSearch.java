package com.lg.leetcode.top100likedquestions.practice;

import java.util.HashMap;

/**
 * @author Xulg
 * Created in 2021-03-12 15:50
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
class WordSearch {

    /*
     * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
     * The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
     * Note: There will be some test cases with a board or a word larger than constraints to test if your solution is using pruning.
     *
     * Example 1:
     * Input:
     * board = [
     * ["A","B","C","E"],
     * ["S","F","C","S"],
     * ["A","D","E","E"]
     * ]
     * word = "ABCCED"
     * Output: true
     *
     * Example 2:
     * Input:
     * board = [
     * ["A","B","C","E"],
     * ["S","F","C","S"],
     * ["A","D","E","E"]
     * ]
     * word = "SEE"
     * Output: true
     *
     * Example 3:
     * Input:
     * board = [
     * ["A","B","C","E"],
     * ["S","F","C","S"],
     * ["A","D","E","E"]
     * ]
     * word = "ABCB"
     * Output: false
     *
     * Constraints:
     * m == board.length
     * n = board[i].length
     * 1 <= m, n <= 6
     * 1 <= word.length <= 15
     * board and word consists of only lowercase and uppercase English letters.
     */

    public static boolean exist(char[][] board, String word) {
        return false;
    }

    private static class BruteForce {
        public static boolean exist(char[][] board, String word) {
            if (board == null || board[0] == null) {
                return false;
            }
            if (board.length * board[0].length < word.length()) {
                return false;
            }
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[x].length; y++) {
                    if (word.startsWith("" + board[x][y])) {
                        boolean[][] usedPosition = new boolean[board.length][board[0].length];
                        if (recurse(board, word, x, y, usedPosition)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        // 从(boardX, boardY)位置出发，是否存在一条路径可以减完目标字符串
        // usedPosition记录board上某位置的字符有没有使用过
        private static boolean recurse(char[][] board, String restWord,
                                       int boardX, int boardY, boolean[][] usedPosition) {
            if (boardX < 0 || boardX >= board.length || boardY < 0 || boardY >= board[0].length) {
                // 越界了已经，看看还有没有剩余的字符串
                return restWord.isEmpty();
            }
            if ("".equals(restWord)) {
                return true;
            }
            if (!restWord.startsWith("" + board[boardX][boardY])) {
                // 剩余字符串没有目标字符，这条路肯定不通
                return false;
            }
            if (usedPosition[boardX][boardY]) {
                // 这个字符已经用过了，这个路不通
                return false;
            }

            // 减掉目标字符，得到剩余的字符串
            String newRestWord = restWord.replaceFirst("" + board[boardX][boardY], "");
            if ("".equals(newRestWord)) {
                // 减完了，提前结束
                return true;
            }
            // 标记字符已经使用了
            usedPosition[boardX][boardY] = true;

            // 从当前位置，往上下左右4个方向继续走
            boolean up = recurse(board, newRestWord, boardX - 1, boardY, usedPosition);
            if (up) {
                // 提前结束
                return true;
            }
            boolean down = recurse(board, newRestWord, boardX + 1, boardY, usedPosition);
            if (down) {
                return true;
            }
            boolean left = recurse(board, newRestWord, boardX, boardY - 1, usedPosition);
            if (left) {
                return true;
            }
            boolean right = recurse(board, newRestWord, boardX, boardY + 1, usedPosition);
            if (right) {
                return true;
            }
            // 恢复这个位置为未使用
            usedPosition[boardX][boardY] = false;
            return false;
        }
    }

    private static class BruteForce2 {
        public static boolean exist(char[][] board, String word) {
            if (board == null || board[0] == null) {
                return false;
            }
            if (board.length * board[0].length < word.length()) {
                return false;
            }
            return recurse(board, word, 0, 0, new boolean[board.length][board[0].length]);
        }

        // 从(boardX, boardY)位置出发，是否存在一条路径可以减完目标字符串
        // usedPosition记录board上某位置的字符有没有使用过
        private static boolean recurse(char[][] board, String restWord,
                                       int boardX, int boardY, boolean[][] usedPosition) {
            if (boardX < 0 || boardX >= board.length || boardY < 0 || boardY >= board[0].length) {
                // 越界了已经，看看还有没有剩余的字符串
                return restWord.isEmpty();
            }
            if ("".equals(restWord)) {
                return true;
            }
            if (!restWord.startsWith("" + board[boardX][boardY])) {
                // 剩余字符串没有目标字符，这条路肯定不通
                return false;
            }
            if (usedPosition[boardX][boardY]) {
                // 这个字符已经用过了，这个路不通
                return false;
            }

            // 减掉目标字符，得到剩余的字符串
            String newRestWord = restWord.replaceFirst("" + board[boardX][boardY], "");
            if ("".equals(newRestWord)) {
                // 减完了，提前结束
                return true;
            }
            // 标记字符已经使用了
            usedPosition[boardX][boardY] = true;

            // 从当前位置，往上下左右4个方向继续走
            boolean up = recurse(board, newRestWord, boardX - 1, boardY, usedPosition);
            if (up) {
                // 提前结束
                return true;
            }
            boolean down = recurse(board, newRestWord, boardX + 1, boardY, usedPosition);
            if (down) {
                return true;
            }
            boolean left = recurse(board, newRestWord, boardX, boardY - 1, usedPosition);
            if (left) {
                return true;
            }
            boolean right = recurse(board, newRestWord, boardX, boardY + 1, usedPosition);
            if (right) {
                return true;
            }
            // 恢复这个位置为未使用
            usedPosition[boardX][boardY] = false;

            // next position
            boolean recurse = recurse(board, newRestWord, boardX + 1, boardY + 1, new boolean[board.length][board[0].length]);

            return false || recurse;
        }
    }

    private static class MemorySearch {

        public static boolean exist(char[][] board, String word) {
            if (board == null || board[0] == null) {
                return false;
            }
            if (board.length * board[0].length < word.length()) {
                return false;
            }
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[x].length; y++) {
                    if (word.startsWith("" + board[x][y])) {
                        boolean[][] usedPosition = new boolean[board.length][board[0].length];
                        if (recurse(board, word, x, y, usedPosition, new HashMap<>(16))) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        // 从(boardX, boardY)位置出发，是否存在一条路径可以减完目标字符串
        // usedPosition记录board上某位置的字符有没有使用过
        private static boolean recurse(char[][] board, String restWord,
                                       int boardX, int boardY, boolean[][] usedPosition, HashMap<String, Boolean> table) {
            String key = restWord + "-" + boardX + "-" + boardY;
            if (table.containsKey(key)) {
                return table.get(key);
            }
            if (boardX < 0 || boardX >= board.length || boardY < 0 || boardY >= board[0].length) {
                // 越界了已经，看看还有没有剩余的字符串
                boolean exist = restWord.isEmpty();
                table.put(key, exist);
                return exist;
            }
            if ("".equals(restWord)) {
                table.put(key, true);
                return true;
            }
            if (!restWord.startsWith("" + board[boardX][boardY])) {
                // 剩余字符串没有目标字符，这条路肯定不通
                table.put(key, false);
                return false;
            }
            if (usedPosition[boardX][boardY]) {
                // 这个字符已经用过了，这个路不通
                table.put(key, false);
                return false;
            }

            // 减掉目标字符，得到剩余的字符串
            String newRestWord = restWord.replaceFirst("" + board[boardX][boardY], "");
            if ("".equals(newRestWord)) {
                // 减完了，提前结束
                table.put(key, true);
                return true;
            }
            // 标记字符已经使用了
            usedPosition[boardX][boardY] = true;

            // 从当前位置，往上下左右4个方向继续走
            boolean up = recurse(board, newRestWord, boardX - 1, boardY, usedPosition, table);
            if (up) {
                // 提前结束
                table.put(key, true);
                return true;
            }
            boolean down = recurse(board, newRestWord, boardX + 1, boardY, usedPosition, table);
            if (down) {
                table.put(key, true);
                return true;
            }
            boolean left = recurse(board, newRestWord, boardX, boardY - 1, usedPosition, table);
            if (left) {
                table.put(key, true);
                return true;
            }
            boolean right = recurse(board, newRestWord, boardX, boardY + 1, usedPosition, table);
            if (right) {
                table.put(key, true);
                return true;
            }
            // 恢复这个位置为未使用
            usedPosition[boardX][boardY] = false;
            table.put(key, false);
            return false;
        }
    }

    private static class BestSolution {
        public static boolean exist(char[][] board, String word) {
            if (board == null) {
                return false;
            }
            // 对board进行词频统计
            int[] lowerLetters = new int[26];
            int[] upperLetters = new int[26];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    char c = board[i][j];
                    if (c >= 65 && c <= 90) {
                        // 大写字符
                        upperLetters[c - 'A']++;
                    } else {
                        // c >= 97 && c <= 122
                        // 小写字符
                        lowerLetters[c - 'a']++;
                    }
                }
            }
            // 对word进行词频统计
            int[] wordLowerLetters = new int[26];
            int[] wordUpperLetters = new int[26];
            char[] wordChars = word.toCharArray();
            for (int i = 0; i < wordChars.length; i++) {
                char c = wordChars[i];
                if (c >= 65 && c <= 90) {
                    // 大写字符
                    wordUpperLetters[c - 'A']++;
                } else {
                    // c >= 97 && c <= 122
                    // 小写字符
                    wordLowerLetters[c - 'a']++;
                }
            }
            for (int i = 0; i < wordLowerLetters.length; i++) {
                if (wordLowerLetters[i] > 0 && lowerLetters[i] > 0) {
                    wordLowerLetters[i] = wordLowerLetters[i] - Math.min(wordLowerLetters[i], lowerLetters[i]);
                }
            }
            for (int i = 0; i < wordUpperLetters.length; i++) {
                if (wordUpperLetters[i] > 0 && upperLetters[i] > 0) {
                    wordUpperLetters[i] = wordUpperLetters[i] - Math.min(wordUpperLetters[i], upperLetters[i]);
                }
            }
            for (int i = 0; i < wordLowerLetters.length; i++) {
                if (wordLowerLetters[i] > 0) {
                    return false;
                }
            }
            for (int i = 0; i < wordUpperLetters.length; i++) {
                if (wordUpperLetters[i] > 0) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        {
            char[][] board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            String word = "ABCCED";
            // expect true
            System.out.println(BruteForce.exist(board, word));

            board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            word = "SEE";
            // expect true
            System.out.println(BruteForce.exist(board, word));

            board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            word = "ABCB";
            // expect false
            System.out.println(BruteForce.exist(board, word));

            board = new char[][]{
                    {'a', 'b'},
                    {'c', 'd'}
            };
            word = "abcd";
            // expect false
            System.out.println(BruteForce.exist(board, word));

            board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'E', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            word = "ABCESEEEFS";
            // expect true
            System.out.println(BruteForce.exist(board, word));

            board = new char[][]{
                    {'a', 'a', 'a', 'a'},
                    {'a', 'a', 'a', 'a'},
                    {'a', 'a', 'a', 'a'}
            };
            word = "aaaaaaaaaaaaa";
            // expect false
            System.out.println(BruteForce.exist(board, word));

            board = new char[][]{
                    {'a', 'a', 'b', 'a', 'a', 'b'},
                    {'a', 'a', 'b', 'b', 'b', 'a'},
                    {'a', 'a', 'a', 'a', 'b', 'a'},
                    {'b', 'a', 'b', 'b', 'a', 'b'},
                    {'a', 'b', 'b', 'a', 'b', 'a'},
                    {'b', 'a', 'a', 'a', 'a', 'b'}
            };
            word = "bbbaabbbbbab";
            // expect false
            System.out.println(BruteForce.exist(board, word));
        }

        {
            char[][] board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            String word = "ABCCED";
            // expect true
            System.out.println(MemorySearch.exist(board, word));

            board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            word = "SEE";
            // expect true
            System.out.println(MemorySearch.exist(board, word));

            board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            word = "ABCB";
            // expect false
            System.out.println(MemorySearch.exist(board, word));

            board = new char[][]{
                    {'a', 'b'},
                    {'c', 'd'}
            };
            word = "abcd";
            // expect false
            System.out.println(MemorySearch.exist(board, word));

            board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'E', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            word = "ABCESEEEFS";
            // expect true
            System.out.println(MemorySearch.exist(board, word));

            board = new char[][]{
                    {'a', 'a', 'a', 'a'},
                    {'a', 'a', 'a', 'a'},
                    {'a', 'a', 'a', 'a'}
            };
            word = "aaaaaaaaaaaaa";
            // expect false
            System.out.println(MemorySearch.exist(board, word));

            board = new char[][]{
                    {'a', 'a', 'b', 'a', 'a', 'b'},
                    {'a', 'a', 'b', 'b', 'b', 'a'},
                    {'a', 'a', 'a', 'a', 'b', 'a'},
                    {'b', 'a', 'b', 'b', 'a', 'b'},
                    {'a', 'b', 'b', 'a', 'b', 'a'},
                    {'b', 'a', 'a', 'a', 'a', 'b'}
            };
            word = "bbbaabbbbbab";
            // expect false
            System.out.println(MemorySearch.exist(board, word));
        }

        {
            char[][] board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            String word = "ABCCED";
            // expect true
            System.out.println(BruteForce2.exist(board, word));

            board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            word = "SEE";
            // expect true
            System.out.println(BruteForce2.exist(board, word));

            board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            word = "ABCB";
            // expect false
            System.out.println(BruteForce2.exist(board, word));

            board = new char[][]{
                    {'a', 'b'},
                    {'c', 'd'}
            };
            word = "abcd";
            // expect false
            System.out.println(BruteForce2.exist(board, word));

            board = new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'E', 'S'},
                    {'A', 'D', 'E', 'E'}
            };
            word = "ABCESEEEFS";
            // expect true
            System.out.println(BruteForce2.exist(board, word));

            board = new char[][]{
                    {'a', 'a', 'a', 'a'},
                    {'a', 'a', 'a', 'a'},
                    {'a', 'a', 'a', 'a'}
            };
            word = "aaaaaaaaaaaaa";
            // expect false
            System.out.println(BruteForce2.exist(board, word));

            board = new char[][]{
                    {'a', 'a', 'b', 'a', 'a', 'b'},
                    {'a', 'a', 'b', 'b', 'b', 'a'},
                    {'a', 'a', 'a', 'a', 'b', 'a'},
                    {'b', 'a', 'b', 'b', 'a', 'b'},
                    {'a', 'b', 'b', 'a', 'b', 'a'},
                    {'b', 'a', 'a', 'a', 'a', 'b'}
            };
            word = "bbbaabbbbbab";
            // expect false
            System.out.println(BruteForce2.exist(board, word));
        }

    }

}
