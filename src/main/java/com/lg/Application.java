package com.lg;

import cn.hutool.core.date.DateTime;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * 数据结构和算法学习
 *
 * @author Xulg
 */
public class Application {

    /*
     * 认识对数器
     *  说白了就是用来验证自己写的算法正确性的。
     *
     *  流程：
     *      1.你想要测试的方法a；
     *      2.实现复杂度不好但是容易实现的方法b；
     *      3.实现一个随机样本产生器
     *      4.把方法a和方法b跑相同的随机样本，看看得到的结果是否一样；
     *      5.如果有一个随机样本使得比对结果不一致，打印样本进行人工干预，改对方法a和方法b；
     *      6.当样本数量很多时比对结果依然正确，可以确定方法a已经正确。
     */

    /*
     * 算法学习的工具网站
     * https://www.geeksforgeeks.org/
     * https://visualgo.net/zh
     * https://www.cs.usfca.edu/~galles/visualization/Algorithms.html
     */

    public static void main(String[] args) {
        Date date = DateUtils.addDays(new Date(), 21);
        System.out.println(DateTime.of(date).toString());
    }
}
