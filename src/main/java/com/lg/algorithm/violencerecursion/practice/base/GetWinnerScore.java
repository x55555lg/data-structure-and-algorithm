package com.lg.algorithm.violencerecursion.practice.base;

/**
 * @author Xulg
 * Created in 2020-11-26 16:01
 */
class GetWinnerScore {

    /*
     *范围上尝试的模型
     * 给定一个整型数组arr，代表数值不同的纸牌排成一条线，玩家A和玩家B依次拿走每张纸牌，
     * 规定玩家A先拿，玩家B后拿，但是每个玩家每次只能拿走最左或最右的纸牌，玩家A和玩家B
     * 都绝顶聪明。请返回最后获胜者的分数。
     *------------------------------------------------------------------------------------
     *解法：
     * 写出递归
     *   1.当纸牌长度为N时，假设赌怪1是先手，那么赌怪2就是后手；但纸牌长度为N-1时，赌怪1就变成了后手，而赌怪2是先手。故需要两个函数来进行递归。
     *   2.先手函数：当left==right，只剩一张牌了，那肯定是先手的拿这张牌，所以返回这张牌。若不止一张牌，那么先手就要在这两种情况中选取较大值：
     *      拿掉左边的牌然后在剩下的纸牌中充当后手；
     *      拿掉右边的牌然后在剩下的牌中充当后手。
     *   3.后手函数：当left==right，只剩一张牌了，那么后手只能拿空气，所以返回0。若不止一张牌，那么后手要想怎么让对方下一次拿牌拿较小值。
     *      拿掉左边的牌，在剩下的牌中充当先手；
     *      拿掉右边的牌，在剩下的牌中充当先手。
     */

    public static int getWinnerScore(int[] cards) {
        if (cards == null || cards.length == 0) {
            return 0;
        }
        // 先手玩家的分数
        int firstScore = first(cards, 0, cards.length - 1);
        // 后手玩家的分数
        int secondScore = second(cards, 0, cards.length - 1);
        // 赢家的分数
        return Math.max(firstScore, secondScore);
    }

    // 先手
    private static int first(int[] cards, int left, int right) {
        if (left == right) {
            // 只有一个牌了。没的选了，直接返回
            return cards[left];
        }

        /*
         * 先手有2种选择：
         *  选择左边的牌
         *  选择右边的牌
         * 看哪种选择对先手利益最大
         */

        // 先手拿了左边的牌，然后先手就成为了后手了，只能从[left+1, right]上选牌
        int val1 = cards[left] + second(cards, left + 1, right);

        // 先手拿了右边的牌，然后先手就成为了后手了，只能从[left, right-1]上选牌
        int val2 = cards[right] + second(cards, left, right - 1);

        // 两种情况，取分最大的那个
        return Math.max(val1, val2);
    }

    // 后手
    private static int second(int[] cards, int left, int right) {
        if (left == right) {
            // 只有一个牌了，这个牌肯定是先手的人拿的，后手的人拿不到这个牌
            return 0;
        }

        /*
         * 作为后手的时候是没有选择权的，先手肯定会把对他最有利的取走，留下最小的牌给后手
         * 先手取走左边的牌，先手取走右边的牌，看2种情况哪种留给后手的牌最小
         */
        int val1 = first(cards, left + 1, right);
        int val2 = first(cards, left, right - 1);
        return Math.min(val1, val2);
    }

    /* ****************************************************************************************************************/

    public static int dp(int[] cards) {
        if (cards == null || cards.length == 0) {
            return 0;
        }
        int n = cards.length;

        // 先手的动态规划表
        int[][] firstTable = new int[n][n];
        // 先手中如果left等于right，那么就去left位置的值
        for (int idx = 0; idx < n; idx++) {
            firstTable[idx][idx] = cards[idx];
        }

        // 后手的动态规划表
        int[][] secondTable = new int[n][n];
        // 后手中如果left等于right，那么就是0
        for (int idx = 0; idx < n; idx++) {
            secondTable[idx][idx] = 0;
        }

        /*
         * int[] cards = {1, 2, 3};
         * 先手表格：行为left，列为right
         *         |  0    1    2
         *     |--------------------            left <= right，所以表格的左下部分是无效的
         *     | 0 |  1    ?    ?
         *     |--------------------
         *     | 1 |  X    2    ?
         *     |--------------------
         *     | 2 |  X    X    3
         *     |--------------------
         * 后手表格：行为left，列为right
         *         |  0    1    2
         *     |--------------------            left <= right，所以表格的左下部分是无效的
         *     | 0 |  0    ?    ?
         *     |--------------------
         *     | 1 |  X    0    ?
         *     |--------------------
         *     | 2 |  X    X    0
         *     |--------------------
         * 填写2个表格的对角线的值，从已知的对角线开始，向右上方推进
         */

        // 遍历每一列，第0列已经都有值了，无需处理了
        for (int index = 1; index < n; index++) {
            // 行
            int left = 0;
            // 列
            int right = index;
            // 不能越界
            while (left < n && right < n) {
                // 先手拿了左边的牌，然后先手就成为了后手了，只能从[left+1, right]上选牌
                int firstVal1 = cards[left] + secondTable[left + 1][right];
                // 先手拿了右边的牌，然后先手就成为了后手了，只能从[left, right-1]上选牌
                int firstVal2 = cards[right] + secondTable[left][right - 1];
                // 两种情况，取分最大的那个
                firstTable[left][right] = Math.max(firstVal1, firstVal2);

                int secondVal1 = firstTable[left + 1][right];
                int secondVal2 = firstTable[left][right - 1];
                secondTable[left][right] = Math.min(secondVal1, secondVal2);

                // 去这条对角线的下一个位置
                left++;
                right++;
            }
        }

        // 赢家的分数
        return Math.max(firstTable[0][cards.length - 1], secondTable[0][cards.length - 1]);
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        int winnerScore = getWinnerScore(new int[]{70, 100, 1});
        System.out.println(winnerScore);

        winnerScore = dp(new int[]{70, 100, 1});
        System.out.println(winnerScore);
    }

}
