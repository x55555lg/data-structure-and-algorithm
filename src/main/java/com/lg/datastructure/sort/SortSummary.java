package com.lg.datastructure.sort;

/**
 * 排序章节总结
 *
 * @author Xulg
 * Created in 2020-10-23 9:46
 */
class SortSummary {

    /*
     *各个排序时间复杂度，空间复杂度，稳定性总结
     * ---------------------------------------------------------------
     * 排序名称         时间复杂度           空间复杂度       是否稳定
     * ---------------------------------------------------------------
     * 基于比较的排序
     * ---------------------------------------------------------------
     * 冒泡排序           O(N^2)                O(1)             Y
     * 选择排序           O(N^2)                O(1)             N
     * 插入排序           O(N^2)                O(1)             Y
     *                    常数项较小
     * 归并排序           O(N*LogN)             O(N)             Y
     * 随机快排           O(N*LogN)             O(LogN)          N
     * 堆排序             O(N*LogN)             O(1)             N
     * ---------------------------------------------------------------
     * 非基于比较的排序
     * ---------------------------------------------------------------
     * 计数排序           O(N)                  O(M)             Y
     *                                          M为数组
     *                                          中最大值
     * 基数排序           O(N)                  O(N)             Y
     *****************************************************************
     * 总结：
     *  如果没有稳定性要求，那么随机快排的效率是最高的
     *  3个时间复杂度为O(N^2)的排序中，插入排序是最好的
     *  时间复杂度好，空间复杂度好，具有稳定性的排序，目前是不存在的，必须做出取舍
     */

    /*
     * 常见的坑
     *  在整型数组中，请把奇数放在数组的左边，偶数放在数组的右边，要求：
     *      1.所有奇数之间原始的相对次序不变
     *      2.所有偶数之间原始的相对次序不变
     *      3.时间复杂度：O(N)
     *      4.空间复杂度：O(1)
     *   这个题目满足所有要求的话是无解的，分析如下：
     *      首先时间复杂度需要O(N)，需要使用荷兰旗的解法思想，数组左边设立一个奇数区。
     *      但是这个分区操作是不稳定的，无法满足次序不变；
     */

    /*
     * 常见的坑
     *  不要去看的东西
     *      1.归并排序的额外空间复杂度可以变成O(1)(可以搜索关键字"归并排序 内部缓存法")，但是
     *        这种做法归并排序将不再稳定。
     *      2."原地归并排序"是垃圾帖不要研究，会让时间复杂度变成O(N^2)。
     *      3.快速排序可以改进成稳定版，但是会对样本数据有很多要求，可以去搜索论
     *        文"01 stable sort"了解，但是很复杂。
     */
}
