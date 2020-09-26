package com.lg.datastructure.bitcalc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 位运算
 *
 * @author Xulg
 * Created in 2020-09-18 15:10
 */
public class BitCalc {

    /*
     * 异或^：相同为0，不同为1
     *      0110
     *      1010
     *      1100
     * 异或运算的那些特性：
     *      0 ^ N = N
     *      N ^ N = 0
     *      异或运算满足交换律和结合律
     *          a ^ b ^ c = a ^ c ^ b
     *          (a ^ b) ^ c = a ^ (b ^ c)
     */

    /*
     * 异或的一些骚操作：
     *      1.如何不使用额外的变量交换两个数（前提：两个数的内存空间不是同一个，否则会变成0）
     *          int a = 甲, b = 乙;
     *          a = a ^ b;      a = 甲^乙，b = 乙
     *          b = a ^ b;      b = 甲^乙^乙 = 甲^0 = 甲，a = 甲^乙
     *          a = a ^ b;      a = 甲^乙^甲 = 乙^0 = 乙，b = 甲
     *         不建议使用
     *      2.一个数组中有一种数出现了奇数次，其他的数都出现了偶数次，怎么找到这个技术次的数字
     *          见test1()，test2();
     *      3.如何把一个int类型的数，提取出最右侧的1来，即二进制中只保留最右边的1,其余都变0。
     *          这个技巧比较常用
     *              实现效果：int a = 0011,0101,0000 -> 0000,0001,0000
     *              int result = N & ((~N) + 1);
     *          见test3();
     *
     */

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    public static void test1() {
        /*
         * 数组：1    2    1    2    3    3    3
         *  创建一个Map，key为数组中的值，value为异或结果，没出现一次就异或下1
         */

        int[] array = {1, 2, 1, 2, 3, 3, 3};
        Map<Integer, Integer> map = new HashMap<>();
        for (int value : array) {
            if (map.containsKey(value)) {
                int r = map.get(value) ^ 1;
                map.put(value, r);
            } else {
                map.put(value, 1);
            }
        }
        for (Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println("奇数次的值是：" + entry.getKey());
            } else {
                System.out.println("偶数次的值是：" + entry.getKey());
            }
        }
    }

    public static void test2() {
        /*
         * 数组：1    2    1    2    3    3    3
         * 定义变量eor = 0,初始值必须是0(因为0 ^ N = N)
         *  eor = 0 ^ 1 ^ 2 ^ 1 ^ 2 ^ 3 ^ 3 ^ 3
         *      = 0 ^ 1 ^ 1 ^ 2 ^ 2 ^ 3 ^ 3 ^ 3
         *      = 0 ^ 0 ^ 0 ^ 3
         *      = 3
         */
        int[] array = {1, 2, 1, 2, 3, 3, 3};
        int eor = 0;
        for (int i = 0; i < array.length; i++) {
            eor = eor ^ array[i];
        }
        System.out.println(eor);
    }

    public static void test3() {
        int a = Integer.parseInt("001101010000", 2);
        System.out.println("before: " + a);
        int r = a & ((~a) + 1);
        System.out.println("after: " + r);
        System.out.println(Integer.toBinaryString(r));
    }

    /* ************************************************************************************************************** */
}
