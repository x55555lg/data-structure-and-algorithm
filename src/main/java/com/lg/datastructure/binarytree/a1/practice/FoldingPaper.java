package com.lg.datastructure.binarytree.a1.practice;

/**
 * 折纸面试题
 *
 * @author Xulg
 * Created in 2021-01-25 16:53
 */
class FoldingPaper {

    /*
     * 请把一段纸竖着放在桌上，然后从纸条的下边向上方对折1次，压出折痕后展开。
     * 此时折痕是凹下去的（即折痕凸起的方向指向纸条的背面）。如果从纸条的下边
     * 向上方连续对折2次，压出折痕后展开，此时有3条折痕，从上到下依次是下折痕，
     * 下折痕，上折痕。
     * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次，请从上到下的打印
     * 所有折痕的方向。
     *  例如：N=1时，打印：down N=2时，打印：down down up
     *------------------------------------------------------------------------
     * 思路：
     *  N = 1   凹
     *  N = 2   凹 凹 凸
     *  N = 3   凹 凹 凸 凹 凹 凸 凸
     *  N = 4   凹 凹 凸 凹 凹 凸 凸 凹 凹 凹 凸 凸 凹 凸 凸
     *  N为二叉树的层数，折痕顺序满足中序遍历的规律
     *  右节点是凸
     *  左节点是凹
     *  头节点是凹
     */

    public static void printFoldingPaper(int n) {
        doPrint(1, "凹", n);
        System.out.println();
    }

    // current表示来到了第几层
    private static void doPrint(int current, String tag, int n) {
        if (current > n) {
            return;
        }
        doPrint(current + 1, "凹", n);
        System.out.print(tag);
        doPrint(current + 1, "凸", n);
    }

    public static void main(String[] args) {
        printFoldingPaper(1);
        printFoldingPaper(2);
        printFoldingPaper(3);
        printFoldingPaper(4);
    }
}
