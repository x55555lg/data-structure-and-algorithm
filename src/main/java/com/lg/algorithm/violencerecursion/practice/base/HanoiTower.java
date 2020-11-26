package com.lg.algorithm.violencerecursion.practice.base;

import java.util.Stack;

/**
 * N层的汉诺塔问题
 *
 * @author Xulg
 * Created in 2020-11-23 14:13
 */
class HanoiTower {

    /*
     * 题目：
     *  打印n层汉诺塔从最左边移动到最右边的全部过程
     *------------------------------------------------------------------------------------------------------------------
     *什么是汉诺塔
     *  汉诺塔来源于印度传说的一个故事，上帝创造世界时作了三根金刚石柱子,
     * 在一根柱子上从上往下从小到大顺序摞着64片黄金圆盘。上帝命令婆罗门把
     * 圆盘从下面开始按大小顺序重新摆放在另一根柱子上。并且规定，在小圆盘
     * 上不能放大圆盘，在三根柱子之间一回只能移动一个圆盘，只能移动在最顶
     * 端的圆盘。有预言说，这件事完成时宇宙会在一瞬间闪电式毁灭。也有人相
     * 信婆罗门至今仍在一刻不停地搬动着圆盘。恩，当然这个传说并不可信，如
     * 今汉诺塔更多的是作为一个玩具存在。
     * 汉诺塔图片：https://bkimg.cdn.bcebos.com/pic/2934349b033b5bb5347f4c4836d3d539b700bcd8?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U5Mg==,g_7,xp_5,yp_5
     *------------------------------------------------------------------------------------------------------------------
     *  现在有n个圆盘从上往下从小到大叠在第一根柱子上，要把这些圆盘全部移动
     * 到第三根柱子要怎么移动呢？请找出需要步骤数最少的方案，打印挪动过程。
     *  因此我们可以将问题简化描述为：
     *       n个盘子和3根柱子：A(源，左)、B(备用，中)、C(目的，右)，盘子的大小不同
     *      且中间有一孔，可以将盘子“串”在柱子上，每个盘子只能放在比它大的盘子上
     *      面。起初，所有盘子在A柱上，问题是将盘子一个一个地从A柱子移动到C柱子。移
     *      动过程中，可以使用B柱，但盘子也只能放在比它大的盘子上面。
     *       因此我们得出汉诺塔问题的以下几个限制条件：
     *          1.在小圆盘上不能放大圆盘
     *          2.在三根柱子之间一回只能移动一个圆盘
     *          3.只能移动在最顶端的圆盘
     *------------------------------------------------------------------------------------------------------------------
     *汉诺塔问题的结论：
     *  N层汉诺塔移动最少步数 = 2^N - 1
     *------------------------------------------------------------------------------------------------------------------
     * 3层原盘移动过程：
     *  初始状态：
     *          -       1层园盘
     *          ---     2层园盘
     *          -----   3层圆盘
     *            左柱子      中柱子      右柱子
     *              |           |           |
     *       1      -           |           |
     *       2     ---          |           |
     *       3    -----         |           |
     *              |           |           |
     *===========================================
     * 第1步挪动：
     *      1层园盘放到右柱
     *            左柱子      中柱子      右柱子
     *              |           |           |
     *              |           |           |
     *          2  ---          |           |
     *          3 -----         |         1 -
     *              |           |           |
     *===========================================
     * 第2步挪动：
     *      2层园盘放到中柱
     *            左柱子      中柱子      右柱子
     *              |           |           |
     *              |           |           |
     *              |           |           |
     *       3    -----      2 ---        1 -
     *              |           |           |
     *===========================================
     * 第3步挪动：
     *      1层园盘放到中柱
     *            左柱子      中柱子      右柱子
     *              |           |           |
     *              |           |           |
     *              |         1 -           |
     *       3    -----      2 ---          |
     *              |           |           |
     *===========================================
     * 第4步挪动：
     *      3层园盘放到右柱
     *            左柱子      中柱子      右柱子
     *              |           |           |
     *              |           |           |
     *              |         1 -           |
     *              |        2 ---      3 -----
     *              |           |           |
     *===========================================
     * 第5步挪动：
     *      1层园盘放到左柱
     *            左柱子      中柱子      右柱子
     *              |           |           |
     *              |           |           |
     *              |           |           |
     *            1 -        2 ---      3 -----
     *              |           |           |
     *===========================================
     * 第6步挪动：
     *      2层园盘放到右柱
     *            左柱子      中柱子      右柱子
     *              |           |           |
     *              |           |           |
     *              |           |        2 ---
     *            1 -           |       3 -----
     *              |           |           |
     *===========================================
     * 第7步挪动：
     *      1层园盘放到右柱
     *            左柱子      中柱子      右柱子
     *              |           |           |
     *              |           |         1 -
     *              |           |        2 ---
     *              |           |       3 -----
     *              |           |           |
     * 挪动结束！！！3个圆盘从左柱移动到了右柱
     */

    /*
     * n层原盘移动过程：
     *  初始状态：
     *          -       1层园盘
     *          ---     2层园盘
     *          -----   3层圆盘
     *            左柱子      中柱子      右柱子
     *              |           |           |
     *       1      -           |           |
     *       2     ---          |           |
     *       3    -----         |           |
     *       ...
     *       n   -------        |           |
     *              |           |           |
     *如何用代码实现
     * 大体思路：
     *  我想从左边移动到右边
     *   1.把[1, n-1]层的圆盘从左柱移动到中柱
     *   2.第n层圆盘自己从左柱移动到右柱去
     *   3.想办法把[1, n-1]层的圆盘从中柱移动到右柱来
     */

    /* ****************************************************************************************************************/

    /**
     * 打印n层汉诺塔的移动过程
     */
    public static void print1(int n) {
        /*
         * 左→右
         *      左→中，打印n层，中→右
         * 右→左
         *      右→中，打印n层，中→左
         * 左→中
         *      左→右，打印n层，右→中
         * 中→左
         *      中→右，打印n层，右→左
         * 中→右
         *      中→左，打印n层，左→右
         * 右→中
         *      右→左，打印n层，左→中
         * 无论从哪个柱子移动到哪个柱子，中间都需要借助剩余的那个柱子
         */
        if (n > 0) {
            leftToRight(n);
        }
    }

    /**
     * 把[1, n]层的圆盘从左柱移动到右柱
     */
    private static void leftToRight(int n) {
        /*
         * 左→右
         *      左→中，打印n层，中→右
         */

        // 递归的 base case
        if (n == 1) {
            System.out.println("Move 1 from left to right");
            return;
        }
        // 1.把[1, n-1]层的圆盘从左柱移动到中柱
        leftToMiddle(n - 1);
        // 2.第n层圆盘自己从左柱移动到右柱去
        System.out.println("Move " + n + " from left to right");
        // 3.把[1, n-1]层的圆盘从中柱移动到右柱来
        middleToRight(n - 1);
    }

    /**
     * 把[1, n]层的圆盘从左柱移动到中柱
     */
    private static void leftToMiddle(int n) {
        /*
         * 左→中
         *      左→右，打印n层，右→中
         */

        // 递归的 base case
        if (n == 1) {
            System.out.println("Move 1 from left to middle");
            return;
        }
        // 1.把[1, n-1]层的圆盘从左柱移动到右柱
        leftToRight(n - 1);
        // 2.第n层圆盘自己从左柱移动到中柱去
        System.out.println("Move " + n + " from left to middle");
        // 3.把[1, n-1]层的圆盘从右柱移动到中柱
        rightToMiddle(n - 1);
    }

    private static void middleToRight(int n) {
        /*
         * 中→右
         *      中→左，打印n层，左→右
         */

        // 递归的 base case
        if (n == 1) {
            System.out.println("Move 1 from middle to right");
            return;
        }
        middleToLeft(n - 1);
        System.out.println("Move " + n + " from middle to right");
        leftToRight(n - 1);
    }

    private static void rightToMiddle(int n) {
        /*
         * 右→中
         *      右→左，打印n层，左→中
         */

        // 递归的 base case
        if (n == 1) {
            System.out.println("Move 1 from right to middle");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("Move " + n + " from right to middle");
        leftToMiddle(n - 1);
    }

    private static void rightToLeft(int n) {
        /*
         * 右→左
         *      右→中，打印n层，中→左
         */

        // 递归的 base case
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }
        rightToMiddle(n - 1);
        System.out.println("Move " + n + " from right to left");
        middleToLeft(n - 1);
    }

    private static void middleToLeft(int n) {
        /*
         * 中→左
         *      中→右，打印n层，右→左
         */

        // 递归的 base case
        if (n == 1) {
            System.out.println("Move 1 from middle to left");
            return;
        }
        middleToRight(n - 1);
        System.out.println("Move " + n + " from middle to left");
        rightToLeft(n - 1);
    }

    /* ****************************************************************************************************************/

    /**
     * 打印n层汉诺塔的移动过程
     * 代码精简版本
     */
    public static void print2(int n) {
        if (n > 0) {
            /*
             * 左→右
             *      左→中，打印n层，中→右
             * 右→左
             *      右→中，打印n层，中→左
             * 左→中
             *      左→右，打印n层，右→中
             * 中→左
             *      中→右，打印n层，右→左
             * 中→右
             *      中→左，打印n层，左→右
             * 右→中
             *      右→左，打印n层，左→中
             * 无论从哪个柱子移动到哪个柱子，中间都需要借助剩余的那个柱子
             */
            move("left", "right", "middle", n);
        }
    }

    /**
     * 3个柱子间相互移动
     * 从from柱移动到to柱，other为剩余那一个柱子
     * ----------------
     * from    左柱
     * to      右柱
     * other   中柱
     * ----------------
     * from    右柱
     * to      左柱
     * other   中柱
     * ----------------
     * from    中柱
     * to      左柱
     * other   右柱
     * ----------------
     * ......
     */
    private static void move(String from, String to, String other, int n) {
        // 递归的 base case
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
            return;
        }
        move(from, other, to, n - 1);
        System.out.println("Move " + n + " from " + from + " to " + to);
        move(other, to, from, n - 1);
    }

    /* ****************************************************************************************************************/

    /**
     * 打印n层汉诺塔的移动过程
     * 非递归版本
     */
    public static void print3(int n) {
        if (n < 1) {
            return;
        }
        Stack<Record> stack = new Stack<>();
        stack.add(new Record(false, n, "left", "right", "middle"));
        while (!stack.isEmpty()) {
            Record cur = stack.pop();
            if (cur.base == 1) {
                System.out.println("Move 1 from " + cur.from + " to " + cur.to);
                if (!stack.isEmpty()) {
                    stack.peek().finish1 = true;
                }
            } else {
                if (!cur.finish1) {
                    stack.push(cur);
                    stack.push(new Record(false, cur.base - 1, cur.from, cur.other, cur.to));
                } else {
                    System.out.println("Move " + cur.base + " from " + cur.from + " to " + cur.to);
                    stack.push(new Record(false, cur.base - 1, cur.other, cur.to, cur.from));
                }
            }
        }
    }

    private static class Record {
        // 有没有做完第一步
        private boolean finish1;
        // 几层圆盘
        private final int base;
        private final String from;
        private final String to;
        private final String other;

        Record(boolean f1, int b, String f, String t, String o) {
            finish1 = false;
            base = b;
            from = f;
            to = t;
            other = o;
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        print1(3);
        System.out.println("--------------------------");
        print2(3);
        System.out.println("--------------------------");
        print3(3);
    }

}
