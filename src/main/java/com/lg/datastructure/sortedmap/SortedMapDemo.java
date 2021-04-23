package com.lg.datastructure.sortedmap;

import java.util.TreeMap;

/**
 * 有序表
 *
 * @author Xulg
 * Created in 2021-03-14 22:11
 */
class SortedMapDemo {

    public static void main(String[] args) {
        // 底层是平衡搜索二叉树
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(4, "我是4");
        treeMap.put(5, "我是5");
        treeMap.put(0, "我是0");
        treeMap.put(7, "我是7");
        treeMap.put(2, "我是2");
        treeMap.put(6, "我是6");

        // 离3最近的>=3的key是什么
        System.out.println(treeMap.ceilingKey(3));

        // 最小的key
        System.out.println(treeMap.firstKey());

        // 最大的key
        System.out.println(treeMap.lastKey());
    }

}
