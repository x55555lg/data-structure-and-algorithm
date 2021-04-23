package com.lg.leetcode.topinterview.practice;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Xulg
 * Created in 2021-03-05 13:29
 */
class NumberOfIslands {

    /*
     * Given an m x n 2d grid map of '1's (land) and '0's (water), return the number of islands.
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
     * You may assume all four edges of the grid are all surrounded by water.
     *
     * Example 1:
     * Input: grid = [
     *   ["1","1","1","1","0"],
     *   ["1","1","0","1","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","0","0","0"]
     * ]
     * Output: 1
     *
     * Example 2:
     * Input: grid = [
     *   ["1","1","0","0","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","1","0","0"],
     *   ["0","0","0","1","1"]
     * ]
     * Output: 3
     *
     * Constraints:
     *  m == grid.length
     *  n == grid[i].length
     *  1 <= m, n <= 300
     *  grid[i][j] is '0' or '1'.
     */

    public static int numIslands(char[][] grid) {
        return 0;
    }

    /**
     * 时间复杂度：O(N*M)
     * 感染法
     */
    private static class Solution1 {

        public static int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0
                    || grid[0] == null || grid[0].length == 0) {
                return 0;
            }
            // 矩阵的长宽
            int maxX = grid.length, maxY = grid[0].length;
            int count = 0;
            for (int positionX = 0; positionX < maxX; positionX++) {
                for (int positionY = 0; positionY < maxY; positionY++) {
                    if (grid[positionX][positionY] == '1') {
                        count++;
                        // 将当前位置和上下左右4个位置遇到的1都设置为2
                        infect(grid, positionX, positionY, maxX, maxY);
                    }
                }
            }
            return count;
        }

        /**
         * 递归将(positionX, positionY)上下左右4个方向遇到的1都变成2
         */
        private static void infect(char[][] grid, int positionX, int positionY, int maxX, int maxY) {
            if (positionX < 0 || positionX >= maxX || positionY < 0 || positionY >= maxY) {
                // 坐标越界
                return;
            }
            if (grid[positionX][positionY] != '1') {
                // 这个位置已经是2了，不需要处理
                return;
            }
            grid[positionX][positionY] = '2';
            // 上下左右4个方向
            infect(grid, positionX, positionY + 1, maxX, maxY);
            infect(grid, positionX, positionY - 1, maxX, maxY);
            infect(grid, positionX - 1, positionY, maxX, maxY);
            infect(grid, positionX + 1, positionY, maxX, maxY);
        }

    }

    /**
     * 时间复杂度：O(N*M)
     * 使用并查集
     */
    private static class Solution2 {

        public static int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
                return 0;
            }

            // 记录矩阵的所有坐标
            List<String> list = new ArrayList<>();
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[row].length; column++) {
                    if (grid[row][column] == '1') {
                        String position = row + "_" + column;
                        list.add(position);
                    }
                }
            }

            // 所有坐标加入并查集
            UnionFindSet<String> unionFindSet = new UnionFindSet<>(list);
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[row].length; column++) {
                    if (grid[row][column] == '1') {
                        String curPosition = row + "_" + column;
                        if (row - 1 >= 0 && grid[row - 1][column] == '1') {
                            // 左边点也是1
                            String up = (row - 1) + "_" + column;
                            unionFindSet.union(up, curPosition);
                        }
                        if (column - 1 >= 0 && grid[row][column - 1] == '1') {
                            // 下边点也是1
                            String left = row + "_" + (column - 1);
                            unionFindSet.union(left, curPosition);
                        }
                    }
                }
            }

            return unionFindSet.size();
        }

        @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
        private static class UnionFindSet<V> {
            private final Map<V, Node<V>> valueNodeMap;
            private final Map<Node<V>, Node<V>> parentMap;
            private final Map<Node<V>, Integer> sizeMap;

            UnionFindSet(Collection<V> coll) {
                valueNodeMap = new HashMap<>();
                parentMap = new HashMap<>();
                sizeMap = new HashMap<>();
                for (V v : coll) {
                    Node<V> node = new Node<>(v);
                    valueNodeMap.put(v, node);
                    parentMap.put(node, node);
                    sizeMap.put(node, 1);
                }
            }

            public boolean isSameSet(V x, V y) {
                Node<V> xNode = valueNodeMap.get(x);
                Node<V> yNode = valueNodeMap.get(y);
                if (xNode == null || yNode == null) {
                    return false;
                }
                return findFather(xNode) == findFather(yNode);
            }

            public void union(V x, V y) {
                Node<V> xNode = valueNodeMap.get(x);
                Node<V> yNode = valueNodeMap.get(y);
                if (xNode == null || yNode == null) {
                    return;
                }
                Node<V> parentX = this.findFather(xNode);
                Node<V> parentY = this.findFather(yNode);
                if (parentX == parentY) {
                    // already union
                    return;
                }
                int sizeX = sizeMap.get(parentX);
                int sizeY = sizeMap.get(parentY);
                Node<V> bigger = sizeX > sizeY ? parentX : parentY;
                Node<V> smaller = bigger == parentX ? parentY : parentX;

                // 小的指向大的
                parentMap.put(smaller, bigger);
                sizeMap.put(bigger, sizeX + sizeY);
                sizeMap.remove(smaller);
            }

            public int size() {
                return sizeMap.size();
            }

            private Node<V> findFather(Node<V> node) {
                Queue<Node<V>> path = new LinkedList<>();
                while (node != parentMap.get(node)) {
                    path.add(node);
                    node = parentMap.get(node);
                }
                // 路径压缩
                while (!path.isEmpty()) {
                    parentMap.put(path.poll(), node);
                }
                // return the parent node
                return node;
            }

            private static class Node<V> {
                V val;

                Node(V val) {
                    this.val = val;
                }
            }

        }

    }

    public static void main(String[] args) {
        {
            // Solution1
            char[][] grid = new char[][]{
                    {'1', '1', '1', '1', '1'},
                    {'1', '1', '0', '1', '0'},
                    {'1', '1', '0', '0', '0'},
                    {'0', '0', '0', '0', '0'}
            };
            System.out.println(Solution1.numIslands(grid));

            grid = new char[][]{
                    {'1', '1', '0', '0', '0'},
                    {'1', '1', '0', '0', '0'},
                    {'0', '0', '1', '0', '0'},
                    {'0', '0', '0', '1', '1'}
            };
            System.out.println(Solution1.numIslands(grid));

            // Solution2
            grid = new char[][]{
                    {'1', '1', '1', '1', '1'},
                    {'1', '1', '0', '1', '0'},
                    {'1', '1', '0', '0', '0'},
                    {'0', '0', '0', '0', '0'}
            };
            System.out.println(Solution2.numIslands(grid));

            grid = new char[][]{
                    {'1', '1', '0', '0', '0'},
                    {'1', '1', '0', '0', '0'},
                    {'0', '0', '1', '0', '0'},
                    {'0', '0', '0', '1', '1'}
            };
            System.out.println(Solution2.numIslands(grid));
        }

        long betweenDay = DateUtil.betweenDay(DateUtil.parse("2021-02-02"),
                DateUtil.parse("2021-03-05"), false);
        System.out.println(betweenDay);

        DateTime start = DateUtil.parse("2021-02-19");
        DateTime end = DateUtil.parse("2021-02-23");
        end = end.offset(DateField.DAY_OF_MONTH, 1);
        List<String> datetimeList = new ArrayList<>();
        datetimeList.add(start.toDateStr());
        long between = DateUtil.between(start, end, DateUnit.DAY);
        for (int day = 0; day < between - 1; day++) {
            String dateStr = DateUtil.offsetDay(start, day + 1).toDateStr();
            datetimeList.add(dateStr);
        }
        System.out.println(datetimeList);
    }

}
