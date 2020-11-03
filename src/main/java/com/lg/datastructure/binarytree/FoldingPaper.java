package com.lg.datastructure.binarytree;

/**
 * 二叉树的递归套路练习
 *
 * @author Xulg
 * Created in 2020-11-02 9:37
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

    /**
     * 打印折痕
     */
    public static void printCreases(int foldingCount) {
        if (foldingCount <= 0) {
            return;
        }
        // 第一层，头节点是凹
        doPrintCreases(1, true, foldingCount);
    }

    /**
     * 中序打印
     *
     * @param currentLevel 现在在哪层
     * @param isDown       这个节点是凹还是凸
     * @param maxLevel     总共有多少层
     */
    private static void doPrintCreases(int currentLevel, boolean isDown, int maxLevel) {
        if (currentLevel > maxLevel) {
            // 当前层大于总层数了，退出
            return;
        }
        // 左节点
        doPrintCreases(currentLevel + 1, true, maxLevel);
        System.out.println(isDown ? "凹" : "凸");
        // 右节点
        doPrintCreases(currentLevel + 1, false, maxLevel);
    }

    public static void main(String[] args) {
        printCreases(3);
    }
}
