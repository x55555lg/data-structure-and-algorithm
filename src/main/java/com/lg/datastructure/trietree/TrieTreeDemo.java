package com.lg.datastructure.trietree;

import java.util.HashMap;

/**
 * 字典树|单词查找树
 * 第三方实现
 * org.apache.commons.collections4.trie.PatriciaTrie
 *
 * @author Xulg
 * Created in 2020-10-20 11:15
 */
class TrieTreeDemo {

    /*
     * 字典树(TrieTree)，又称单词查找树或键树，是一种树形结构，是一种哈希树的变种。典型应用是用于
     * 统计和排序大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。它的优点
     * 是：最大限度地减少无谓的字符串比较，查询效率比哈希表高。
     * Trie的核心思想是空间换时间。利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的。
     * 它有3个基本性质：
     *  根节点不包含字符，除根节点外每一个节点都只包含一个字符。
     *  从根节点到某一节点，路径上经过的字符连接起来，为该节点对应的字符串。
     *  每个节点的所有子节点包含的字符都不相同。
     *
     * trie树结构描述
     *      将数组中的字符串建成Trie Tree: String[] array = {"abc", "ad", "be"};
     *      树结构：
     *          pass：表示节点被经过了多少次；end：表示在该节点字符串结束了多少次数
     *                              (root){pass: 3, end:0}
     *                                  ○
     *                                ╱ ╲
     *                              ╱	   ╲
     *                            a	          b
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
     *=================================================================
     * Java中TrieTree实现：
     *  org.apache.commons.collections4.trie.PatriciaTrie
     */

    /**
     * 对数器，粗暴的字典树实现，用于校验上面实现的TrieTree1, TrieTree2的正确性
     */
    @SuppressWarnings("all")
    static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int n = 0; n < 10; n++) {
            for (int i = 0; i < testTimes; i++) {
                String[] arr = generateRandomStringArray(arrLen, strLen);
                TrieTree1 trie1 = new TrieTree1();
                TrieTree2 trie2 = new TrieTree2();
                Right right = new Right();
                for (String s : arr) {
                    trie1.insert(s);
                    trie2.insert(s);
                    right.insert(s);
                }
                for (int j = 0; j < arr.length; j++) {
                    double decide = Math.random();
                    if (decide < 0.5) {
                        trie1.delete(arr[j]);
                        trie2.delete(arr[j]);
                        right.delete(arr[j]);
                    } else if (decide < 0.75) {
                        int ans1 = trie1.search(arr[j]);
                        int ans2 = trie2.search(arr[j]);
                        int ans3 = right.search(arr[j]);
                        if (ans1 != ans2 || ans2 != ans3) {
                            System.out.println("Oops!1111111");
                        }
                    } else {
                        int ans1 = trie1.prefixNumber(arr[j]);
                        int ans2 = trie2.prefixNumber(arr[j]);
                        int ans3 = right.prefixNumber(arr[j]);
                        if (ans1 != ans2 || ans2 != ans3) {
                            System.out.println("Oops!2222222");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    // for test
    private static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    private static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    private static String[] generateRandomStringArray2(int arrLen, int strLen) {
        String[] ans = new String[arrLen];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

}
