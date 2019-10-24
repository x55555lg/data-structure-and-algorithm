package com.lg.fu_za_du;

/**
 * 时间复杂度
 *
 * @author Xulg
 * Created in 2019-10-23 15:07
 */
public class TimeFuZaDu {

    /*
     * 大O复杂度表示法(时间复杂度)
     *      T(n) = O(f(n))
     *          T(n)：代码执行的时间
     *          n：数据规模大小(每行代码执行次数)
     *          f(n)：每行代码执行的次数总和
     *          O：表示T(n)和f(n)成正比
     */

    /*
     * 时间复杂度分析技巧：
     *      1.只关注循环次数最多的一段代码
     *          例如：
                    int cal(int n) {
                        int sum = 0;
                        int i=1;
                        for(; i<= n; i++) {
                            sum = sum + i;
                        }
                        return sum;
                    }
     *              分析：T(n) = O(n)
     *
     *      2.加法法则，即总复杂度等于量级最大的那段代码的复杂度
     *          例如：
                    int cal(int n) {
                        int sum_1 = 0;
                        int p = 1;
                        for (; p < 100; ++p) {
                            sum_1 = sum_1 + p;
                        }

                        int sum_2 = 0;
                        int q = 1;
                        for (; q < n; ++q) {
                            sum_2 = sum_2 + q;
                        }

                        int sum_3 = 0;
                        int i = 1;
                        int j = 1;
                        for (; i <= n; ++i) {
                            j = 1;
                            for (; j <= n; ++j) {
                                sum_3 = sum_3 +  i * j;
                            }
                        }
                        return sum_1 + sum_2 + sum_3;
                    }
                    分析：第一段代码是O(1), 第二段代码是O(n)，第三段代码是O(n²)，
                          所以根据加法法则，T(n) = O(n²)
     *      3.乘法法则，即嵌套代码的复杂度等于嵌套内外代码复杂度的乘积
     *          例如：
                    int cal(int n) {
                        int ret = 0;
                        int i = 1;
                        for (; i < n; ++i) {
                            ret = ret + fun(i);
                        }
                    }
                    int fun(int n) {
                        int sum = 0;
                        int i = 1;
                        for (; i < n; ++i) {
                            sum = sum + i;
                        }
                        return sum;
                    }
                    分析：cal()方法的复杂度是O(n)，fun()方法的复杂度是O(n)，
                          所以根据乘法法则，T(n) = O(n²)
     */

    /*
     * 常见的时间复杂度实例
     *      如下：
     *          O(1)        常量阶
     *          O(2^n)      指数阶
     *          O(logn)     对数阶
     *          O(n!)       阶乘阶
     *          O(n)        线性阶
     *          O(nlogn)    线性对数阶
     *          O(n^k)      k次方阶
     *      这些复杂度量级可以粗略的分为两类：多项式量级、非多项式量级(NP)。
     *      非多项式量级(NP)只有两个：O(2^n)，O(n!)。
     *          当数据规模n越来越大时，非多项式量级(NP)算法
     *          的执行时间会无限增长，因此是效率低下的。
     */

}
