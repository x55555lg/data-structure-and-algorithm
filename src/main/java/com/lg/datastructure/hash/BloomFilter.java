package com.lg.datastructure.hash;

import cn.hutool.crypto.digest.MD5;

import java.math.BigInteger;

/**
 * @author Xulg
 * Created in 2021-03-06 13:20
 */
class BloomFilter {

    /*
     * 布隆过滤器
     *  长度为m的位数组(BitArray)，每一位的值只能是0|1
     *                     [ 0  0  0  0  0  0  0  0  0 ...... ]
     *                       0  1  2  3  4  5  6  7  8    m-1
     *
     *  准备k个hash函数，对输入in求hash值，再对m取余，将位数组上对应位置设1，共执行k次
     *      in -> hash1() -> hash%m -> BitArray[hash%m]=1
     *      in -> hash2() -> hash%m -> BitArray[hash%m]=1
     *          ...
     *      in -> hashK() -> hash%m -> BitArray[hash%m]=1
     *      [ 0  1  0  1  1  0  1  0  0 ...... ]
     *        0  1  2  3  4  5  6  7  8    m-1
     *
     * 如何判断一个输入in是否在布隆过滤器内？
     *      对输入in做k次hash后取余操作，看位数组上这几个位置都是1。
     *      如果有0，则一定不包含
     *      如果都为1，则可能包含，可能不包含
     */

    /*
     * 布隆过滤器的三个公式
     *  1.假设数据量为n，预期的失误率为p。
     *  2.根据n和p，算出BloomFilter一共需要多少bit位，向上取整，几位m。
     *  3.根据m和n，算出BloomFilter需要多少个哈希函数，向上取整，记为k。
     *  4.根据修正公式，算出真实的失误率p_true
     *      m = (- n*lnp)/(ln2)^2
     *      k = ln2 * (m/n) = 0.7 * (m/n)
     *      p = (1 - e^(-nk/m))^k
     *-----------------------------------------------------------------
     *  例如：数据量为100亿，失误率为0.0001
     *        n = 100亿，p = 0.0001，根据公式求m，k
     *        m = (-100亿*ln0.0001)/(ln2)^2 = ...
     *        k = 0.7 * (m/100亿) = ...
     */

    /*
     * 如何准备k个hash函数？
     *  准备2个求hash值的函数
     *      f() {md5}
     *      g() {sha}
     *  第1个哈希函数：
     *      f() + 1 * g()
     *  第2个哈希函数：
     *      f() + 2 * g()
     *  第3个哈希函数：
     *      f() + 3 * g()
     *  第4个哈希函数：
     *      f() + 4 * g()
     *  第5个哈希函数：
     *      f() + 5 * g()
     *  ...
     *  第k个哈希函数：
     *      f() + k * g()
     */

    public static void main(String[] args) {
        System.out.println(Math.pow(2, 31) - 1);
        System.out.println(Integer.MAX_VALUE);
        BigInteger abc = new BigInteger(MD5.create().digestHex("abc"), 16);
        System.out.println(abc);
    }
}
