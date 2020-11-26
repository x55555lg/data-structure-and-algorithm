package com.lg.algorithm.violencerecursion.practice.base;

/**
 * @author Xulg
 * Created in 2020-11-26 16:01
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
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
        int firstScore = first(cards, 0, cards.length - 1);
        int secondScore = second(cards, 0, cards.length - 1);
        return Math.max(firstScore, secondScore);
    }

    // 第一个拿牌的玩家，第一个拿牌的玩家要拿对自己最有利的，对方必须取最小的牌
    private static int first(int[] cards, int left, int right) {
        if (left == right) {
            // 只有一个牌了。没的选了，直接返回
            return cards[left];
        }

        // 第一个拿牌的玩家选择了最左边的牌，那么第二个玩家只能从[left+1, right]上选牌了
        int val1 = cards[left] + second(cards, left + 1, right);

        // 第一个拿牌的玩家选择了最右边的牌，那么第二个玩家只能从[left, right-1]上选牌了
        int val2 = cards[right] + second(cards, left, right - 1);

        // 两种情况，取分最大的那个
        return Math.max(val1, val2);
    }

    // 第二个拿牌的玩家
    private static int second(int[] cards, int left, int right) {
        if (left == right) {
            // 只有一个牌了，这个牌肯定是先选的人拿的，后选的人拿不到这个牌
            return 0;
        }
        int val1 = first(cards, left + 1, right);
        int val2 = first(cards, left, right - 1);
        return Math.min(val1, val2);
    }

    public static void main(String[] args) {
        int winnerScore = getWinnerScore(new int[]{70, 100, 1});
        System.out.println(winnerScore);
    }
}
