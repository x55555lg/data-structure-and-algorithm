package com.lg.datastructure.hash;

/**
 * @author Xulg
 * Created in 2021-03-05 23:28
 */
class HashDemo {

    /*
     * 哈希函数：out hash(in)
     *      in为输入，out为hash函数的输出结果
     *  1.in的可能性有∞
     *  2.out的可能性是有限
     *  3.无随机机制。相同的输入，一定是相同的输出
     *  4.不同的输入，可能是相同的输出(哈希碰撞)
     *  5.输出离散，散布均匀
     */

    /*
     * 有一个包含40亿个整数的文件(每行一个数)，1个整数4个字节，
     * 整数是无符号数范围是(0~2^32-1)。
     * 假设你只要1G的内存，我想统计哪个数据的出现次数是最多的？
     *---------------------------------------------------------
     * 思路：
     *      1.使用1G的内存构建一张哈希表：key为数，value为该数的出现次数
     *          key，value都使用无符号整数类型
     *      2.1G内存的哈希表可以统计多少个数？
     *          假设为了实现哈希表而占用的空间不算
     *          key为4字节，value为4字节，所以1条记录占用8字节
     *          40亿个整数如果都不一样则需要320亿字节，约32G的存储内存
     *          1G内存则大约可以存1亿条记录：count = 40/32
     *      3.从文件中一个一个的读取数num，对num进行hash计算后得到hash值，
     *        再用hash值对40取模，取模结果一定是[0, 39]。根据取模结果将数
     *        num写入到对应编号为[0, 39]的文件中。相同的数就会被写到同一个
     *        编号的文件中去。
     *          同一种数字不可能被写入到不同编号的文件中去
     *          不同的数字有可能被写入到不同编号的文件中去(哈希碰撞了)
     *          40亿个数被分摊到[0, 39]这些文件中，平均每个文件中的数字种类个数维持在1亿左右
     *                hash        %40
     *          num1 ------> out ------> 27
     *              将num1数写入27号文件
     *
     *                hash        %40
     *          num2 ------> out ------> 0
     *              将num2数写入0号文件
     *
     *                hash        %40
     *          num1 ------> out ------> 27
     *              将num1数写入27号文件
     *      4.分别对[0, 39]编号的文件计算，词频统计，次数最多是哪个数
     *      5.对40个文件的计算结果再进行比较，选出次数最多的数
     */

    /*
     * 一致性哈希
     *  哈希域变成环的设计
     *  虚拟节点技术
     *--------------------------
     * 分布式存储中传统的哈希算法路由：
     *  使用哈希算法加取模，来路由数据存储到哪台服务器上
     *      哈希算法：       MD5(in) out的范围：[0, 2^64-1]
     *      哈希值：         MD5算法的输出hash
     *      初始机器数量：   M
     *  例如：
     *      现有机器3台(0:M1, 1:M2, 2:M3)，哈希算法MD5
     *      对输入数据"abc"，求哈希值，再用哈希值对机器个数取模，得到存储数据的目标机器位置P。
     *              P = MD5("abc") % 3
     *      算出P为1，数据将存储到M2机器上。
     * 这种方式简单，但是当机器数量需要扩容或者缩减的时候，全部的数据都要重新哈希计算取模，重新路由到机器上，
     * 非常影响效率和性能。
     *
     *------------------------------------------------------------------------------------------------------------------
     *
     * 分布式存储中一致性哈希算法：
     *  对输入直接求哈希值，然后按照顺时针方向找槽位存储数据
     *  1.将哈希算法的取值范围(例如：[0, 2^64-1])围成一个首尾相连的圆环；
     *  2.每一个哈希值都代表一个槽位，槽位可以用来表示服务机器；
     *                            . 0 1 2 3 4 5 6 7 8 9 10
     *                           .                       11
     *                          .                         12
     *                         33                          13
     *                        32                            14
     *                       31                              15
     *                        30                            16
     *                         29                          17
     *                          28                        18
     *                           27 26 25 24 23 22 21 20 19
     *  3.对机器的标识(例如：IP，MAC地址，Host等信息)求哈希值，将对应哈希值的槽位来代表机器的位置。
     *
     *  下图表示一个哈希环，M1，M2，M3为3台机器，对于任何输入in求的的哈希值按照顺时针方向，遇到哪个机器节点就存储在哪。
     *      哈希值为0~2000的，    存储在M2机器
     *      哈希值为2001~9999的， 存储在M3机器
     *      哈希值为10000~...的， 存储在M1机器
     *                        M1(0)
     *
     *               ↗                   ↘
     *
     *          M3(9999)       ←             M2(2000)
     *  例如：
     *      输入数据"abc"，MD5("abc")算出哈希值为11231，存储在M1机器
     *      输入数据"dde"，MD5("dde")算出哈希值为341，  存储在M2机器
     *      输入数据"ffg"，MD5("ffg")算出哈希值为6213， 存储在M3机器
     *
     * 当机器数量需要扩容或者缩减时，只需要迁移一部分的数据即可。
     *  例如：
     *      1.现在加入新机器M4，求得M4的哈希为1500，原先0~1500部分数据在M2机器上的，现只需要将0~1500这部分
     *      数据按照顺时针方向从M2迁移到M4即可
     *                          M1(0)
     *                    ↗             ↘
     *                 ↗                 M4(1500)
     *               ↗                       ↘
     *          M3(9999)           ←         M2(2000)
     *      2.现在需要将M2机器下线，原先0~2000部分的数据在M2机器上，现只需要将0~2000部分数据按照顺时针方向迁移
     *      到M3即可
     *                        M1(0)
     *               ↗                   ↘
     *          M3(9999)       ←             M2(2000)
     *
     * 上述的一致性哈希解决方法还存在的问题，如何保证M1,M2，M3分配的很均匀，因为很有可能求哈希后，M1，M2，M3机器
     * 位置挨得很近，导致哈希环分配的不均衡？
     *  例如：M2机器只有0~399部分，M3机器只有399~721部分，M1机器有721~MAX部分，M1机器的压力太大
     *                          M1(0)
     *                    ↗             ↘
     *                 ↗                   ↘
     *               ↗                       ↘
     *          M3(721)           ←         M2(399)
     *
     * 解决措施：
     *  增加各个机器的虚拟节点，无论是扩容还是缩减，都能保证整个哈希环是均匀的
     *  例如：
     *      1.M1机器，使用1000个不重复的字符串来代表1000个虚拟节点，将这1000个字符串求哈希值，然后将
     *        虚拟节点的哈希值映射到哈希环中。在环中用R(Red)来表示
     *      2.M2机器，使用1000个不重复的字符串来代表1000个虚拟节点，将这1000个字符串求哈希值，然后将
     *        虚拟节点的哈希值映射到哈希环中。在环中用G(Green)来表示
     *      2.M2机器，使用1000个不重复的字符串来代表1000个虚拟节点，将这1000个字符串求哈希值，然后将
     *        虚拟节点的哈希值映射到哈希环中。在环中用Y(Yellow)来表示
     *      4.由于哈希函数的离散性，3000个虚拟节点可以将整个哈希环均分。
     *      5.控制各个机器的虚拟节点数量，可以控制各个机器的权重。
     *          例如：
     *              M1机器性能好，有1000个虚拟节点
     *              M2机器性能一般，有400个虚拟节点
     *              M3机器性能差，有200个虚拟节点
     *              这样在整个哈希环中，M1机器承担了大部分的数据
     *      存储数据流程：
     *          对输入数据in求哈希，按照顺时针方向找到对应的虚拟节点，根据虚拟节点可以知道真正的机器，然后将数据保存。
     *      增加机器的流程
     *          1.增加机器M4，使用1000个不重复的字符串来代表1000个虚拟节点
     *          2.将这1000个字符串求哈希值，然后将虚拟节点的哈希值映射到哈希环中
     *          3.如果M4的这1000个虚拟节点和M1，M2，M3的虚拟节点冲突了，则将冲突部分的数据按照顺时针原则迁移到
     *            就近的机器上
     *      减少机器的流程
     *          1.下线M2机器，哈希环中的所有节点G管理的数据，都按照顺时针原则迁移到就近的机器上
     *                                  R Y Y G R G R Y R
     *                                R                    G
     *                               Y                      Y
     *                              G                       R
     *                              G                       G
     *                              Y                      Y
     *                               R                    G
     *                                 Y G R R G Y R G Y R
     */

}
