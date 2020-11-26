package com.lg.algorithm.violencerecursion;

/**
 * 暴力递归
 *
 * @author Xulg
 * Created in 2020-11-09 13:49
 */
class ViolenceRecursion {

    /*
     *暴力递归：暴力递归就是尝试
     * 1.把问题转化为规模缩小了的同类问题的子问题
     * 2.有明确的不需要继续进行递归的条件(base case)
     * 3.有当得到了子问题的结果之后的决策过程
     * 4.不记录每一个子问题的解
     */

    /*
     *熟悉什么叫尝试？
     * 做题吧......
     *  1.打印n层汉诺塔从最左边移动到最右边的全部过程
     *      com.lg.algorithm.violencerecursion.practice.base.HanoiTower
     *  2.打印一个字符串的全部子序列
     *      com.lg.algorithm.violencerecursion.practice.base.StringAllSubsequence
     *  3.打印一个字符串的全部排列
     *      com.lg.algorithm.violencerecursion.practice.base.StringAllPermutation
     *  4.打印一个字符串的全部排列，要求不要出现重复的排列
     *      com.lg.algorithm.violencerecursion.practice.base.StringAllNonRedundantPermutation
     */

    /*
     *仰望好的尝试？
     * 1.给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归函数。 如何实现?
     *      com.lg.algorithm.violencerecursion.practice.base.ReverseStack
     */

    /*
     *从左往右的尝试模型1
     * 规定1和A对应、2和B对应、3和C对应...那么一个数字字符串比如"111"就可以转化为:
     * "AAA"、"KA"、"AK"。给定一个只有数字字符组成的字符串str，返回有多少种转化结果
     *      com.lg.algorithm.violencerecursion.practice.base.ConvertNumberToLetterString
     */

    /*
     *从左往右的尝试模型2
     * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表i号物品的重量和价值。
     * 给定一个正数bag，表示一个载重bag的袋子，你装的物品不能超过这个重量。返回你能装下最多的价值
     * 是多少?
     *      com.lg.algorithm.violencerecursion.practice.base.MaxValueForBag
     */

    /*
     *范围上尝试的模型
     * 给定一个整型数组arr，代表数值不同的纸牌排成一条线，玩家A和玩家B依次拿走每张纸牌，
     * 规定玩家A先拿，玩家B后拿，但是每个玩家每次只能拿走最左或最右的纸牌，玩家A和玩家B
     * 都绝顶聪明。请返回最后获胜者的分数。
     *      com.lg.algorithm.violencerecursion.practice.base.GetWinnerScore
     */

    /*
     *N皇后问题
     * N皇后问题是指在N*N的棋盘上要摆N个皇后，要求
     *      任何两个皇后不同行、不同列，也不在同一条斜线上
     * 给定一个整数n，返回n皇后的摆法有多少种。
     * n=1，返回1
     * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
     * n=8，返回92
     */

    /*
     *怎么尝试一件事？
     * 1）有经验但是没有方法论？
     * 2）怎么判断一个尝试就是最优尝试？
     * 3）难道尝试这件事真的只能拼天赋？那我咋搞定我的面试？
     * 4）动态规划是啥？好高端的样子哦…和尝试有什么关系？
     *后面的课，给你带来最强的私货！-> 暴力递归到动态规划的套路！
     *解决任何面试中的动态规划问题！
     */

}
