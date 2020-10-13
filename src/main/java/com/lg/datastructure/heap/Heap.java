package com.lg.datastructure.heap;

/**
 * 堆
 *
 * @author Xulg
 * Created in 2020-10-13 20:38
 */
class Heap {

    /*
     * 由数组实现的完全二叉树
     *
     * i = 0作为树的根节点
     * 数组
     * a    b   c   d   e   f   g
     * 0    1   2   3   4   5   6
     *
     * 二叉树
     *                    0
     *              1           2
     *           3     4     5     6
     * 任意节点的左子节点索引计算公式：
     *      leftIdx = 2 * i + 1
     * 任意节点的右子节点索引计算公式：
     *      rightIdx = 2 * i + 2
     * 任意节点的父节点索引计算公式：
     *      parentIdx = (i - 1) / 2
     *====================================================
     * i = 1作为树的根节点，i = 0处不使用，这种可以用到位运算提高计算速度
     * 数组
     *      a   b   c   d   e   f   g
     * 0    1   2   3   4   5   6   7
     *
     * 二叉树
     *                    1
     *              2           3
     *           4     5     6     7
     * 任意节点的左子节点索引计算公式：
     *      leftIdx = 2 * i      可以使用位运算=====>>> leftIdx = i << 1
     * 任意节点的右子节点索引计算公式：
     *      rightIdx = 2 * i + 1 可以使用位运算=====>>> rightIdx = i << 1 | 1
     * 任意节点的父节点索引计算公式：
     *      parentIdx = i / 2    可以使用位运算=====>>> parentIdx = i >> 1
     */

    /*
     * 堆：本质上是一个由数组实现的完全二叉树
     * 大根堆：
     *      每一颗子树的最大值都是该子树的头(父)节点的值
     *          数组：[6, 5, 3, 0, 1]
     *          二叉树：
     *                       6
     *                  5         3
     *               0      1
     *            6,5,3这颗子树的头节点值为6，比子节点5,3都大
     *            5,0,1这颗子树的头节点值为5，比子节点0,1都大
     *            0这颗子树的头节点值为0，没有子节点也是最大
     *            1这颗子树的头节点值为1，没有子节点也是最大
     *      左右子节点的值都比父节点要小
     * 小根堆
     *      每一颗子树的最小值都是该子树的头(父)节点的值
     *其他情况都不是堆
     */

    /**
     * 给定一个容量足够的数组，将输入的数字按照大根堆存入进去
     */
}