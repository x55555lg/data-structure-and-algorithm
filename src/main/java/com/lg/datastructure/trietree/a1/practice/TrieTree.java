package com.lg.datastructure.trietree.a1.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;

/**
 * @author Xulg
 * Created in 2021-01-20 16:16
 */
interface TrieTree {
    /*
     * 字典树(TrieTree)，又称单词查找树或键树，是一种树形结构，是一种哈希树的变种。典型应用是用于
     * 统计和排序大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。它的优点
     * 是：最大限度地减少无谓的字符串比较，查询效率比哈希表高。
     * Trie的核心思想是空间换时间。利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的。
     * 它有3个基本性质：
     *  根节点不包含字符，除根节点外每一个节点都只包含一个字符。
     *  从根节点到某一节点，路径上经过的字符连接起来，为该节点对应的字符串。
     *  每个节点的所有子节点包含的字符都不相同。
     *===============================================================================================
     * trie树结构描述
     *      将数组中的字符串建成Trie Tree: String[] array = {"abc", "ad", "be"};
     *      树结构：
     *          pass：表示节点被经过了多少次；end：表示在该节点字符串结束了多少次数
     *                              (root){pass: 3, end:0}
     *                                  ○
     *                                ╱ ╲
     *                              ╱	   ╲
     *                            a	      b
     *                          ╱			   ╲
     *                        ╱				 ╲
     *                       ○{pass: 2, end:0}	   ○{pass: 1, end:0}
     *                    ╱   ╲				    ╲
     *                  ╱		 ╲				      ╲
     *                 b		    d					 e
     *              ╱				 ╲					  ╲
     *            ╱				   ╲					╲
     *           ○{pass: 1, end:0}		 ○{pass: 1, end:1}	  ○{pass: 1, end:1}
     *           |
     *           |
     *           c
     *           |
     *           |
     *           ○{pass: 1, end:1}
     *          描述：插入"abc", "ad"的过程
     *              插入"abc"
     *                  从root节点出发，root节点的pass++，root节点没有通向字符'a'的路径，
     *                 创建root通向'a'字符的路径，pass属性++，end属性为0；
     *                  从a节点出发，a节点没有通向字符'b'的路径，创建通向'b'字符的路径，
     *                 pass属性++，end属性为0；
     *                  从b节点出发，b节点没有通向字符'c'的路径，创建通向'c'字符的路径，
     *                 pass属性++，字符'c'是字符串"abc"的结尾，所有end属性++；
     *              插入"ad"
     *                  从root节点出发，root节点的pass++，root节点有通向字符'a'的路径，
     *                 pass属性++，end属性为0；
     *                  从a节点出发，a节点没有通向字符'd'的路径，创建通向'd'字符的路径，
     *                 pass属性++，字符'd'是字符串"ad"的结尾，所有end属性++；
     *
     *      实现方式一：使用链表 + 数组   实现
     *          定义Node类：
     *                  int pass;            节点经过次数
     *                  int end;             节点结束次数
     *                  Node[] nextNodes;    节点后续节点
     *                      nextNodes数组的下标：表示字符
     *                          index = 0， 表示字符'a'
     *                          index = 1， 表示字符'b'
     *                          ...
     *                          index = 25，表示字符'z'
     *                      nextNodes数组的值：  表示当前节点通向某个字符的路有没有
     *                          nextNodes[0] == null，表示节点没有通向字符'a'的路径
     *                          nextNodes[0] != null，表示节点有通向字符'a'的路径
     *          具体实现见代码：com.lg.datastructure.trietree.TrieTree1
     *
     *      实现方式二：使用链表 + hash表 实现
     *                  int pass;                              节点经过次数
     *                  int end;                               节点结束次数
     *                  HashMap<Integer, Node> nextNodes;      节点后续节点
     *                      nextNodes Map的key：  表示字符的ASCII码值
     *                      nextNodes Map的value：表示当前节点通向某个字符的路有没有
     *                          nextNodes.get('a') == null，表示节点没有通向字符'a'的路径
     *                          nextNodes.get('a') != null，表示节点有通向字符'a'的路径
     *          具体实现见代码：com.lg.datastructure.trietree.TrieTree2
     */

    /**
     * 将字符串加入到树中
     */
    void add(String words);

    /**
     * 将字符串从树中删除
     */
    void delete(String words);

    /**
     * word这个单词之前加入过几次
     */
    int searchCount(String words);

    /**
     * 树中是否包含字符串
     */
    default boolean contains(String words) {
        return searchCount(words) > 0;
    }

    /**
     * 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
     */
    int prefixNumber(String prefix);

    /**
     * 获取树中一共有多少个字符串存进去了
     */
    int getTotalCount();

    static void main(String[] args) {
        TrieTree trieTree1 = new TrieTreeImplByNodeArray();
        TrieTree trieTree2 = new TrieTreeImplByNodeHash();
        TrieTree trieTree3 = new TrieTreeImplByMock();

        ArrayList<String> all = new ArrayList<>();
        int times = 1000000;
        String baseStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int time = 0; time < times; time++) {
            String randomStr = RandomUtil.randomString(baseStr, RandomUtil.randomInt(0, 21));
            all.add(randomStr);
            trieTree1.add(randomStr);
            trieTree2.add(randomStr);
            trieTree3.add(randomStr);
        }

        if (trieTree1.getTotalCount() != trieTree2.getTotalCount() || trieTree2.getTotalCount() != trieTree3.getTotalCount()) {
            System.out.println("Oops " + trieTree1.getTotalCount() + " " + trieTree2.getTotalCount() + " " + trieTree3.getTotalCount());
            return;
        }

        for (String str : all) {
            if (trieTree1.contains(str) != trieTree2.contains(str) || trieTree2.contains(str) != trieTree3.contains(str)) {
                System.out.println("Oops " + str + " " + trieTree1.contains(str) + " " + trieTree2.contains(str) + " " + trieTree3.contains(str));
                break;
            }
            if (trieTree1.searchCount(str) != trieTree2.searchCount(str) || trieTree2.searchCount(str) != trieTree3.searchCount(str)) {
                System.out.println("Oops " + str + " " + trieTree1.searchCount(str) + " " + trieTree2.searchCount(str) + " " + trieTree3.searchCount(str));
                break;
            }
            trieTree1.delete(str);
            trieTree2.delete(str);
            trieTree3.delete(str);
        }
        if (trieTree1.getTotalCount() != 0 || trieTree2.getTotalCount() != 0 || trieTree3.getTotalCount() != 0) {
            System.out.println("Oops " + trieTree1.getTotalCount() + " " + trieTree2.getTotalCount() + " " + trieTree3.getTotalCount());
            return;
        }
        System.out.println("finish!");
    }

}
