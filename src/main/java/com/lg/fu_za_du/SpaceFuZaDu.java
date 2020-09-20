package com.lg.fu_za_du;

/**
 * 时间复杂度
 *
 * @author Xulg
 * Created in 2019-10-23 15:08
 */
@SuppressWarnings("ManualArrayCopy")
public class SpaceFuZaDu {

    /*
     * 例如对一个数组array进行操作，返回一个int值
     *      int calc(int[] array) {
     *          return 0;
     *      }
     * 如果额外需要的空间是有限的，和array的大小没有关系，则空间复杂度为O(1)，
     * 否则就是别的复杂度了，例如O(n)。
     */

    /**
     * 空间复杂度o(1)
     */
    public int[] copy(int[] array) {
        int[] newArr = new int[array.length];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = array[i];
        }
        return newArr;
    }

}
