package com.lg.datastructure.trietree.a1.practice;

import java.util.ArrayList;

/**
 * 字典树的暴力实现版本
 *
 * @author Xulg
 * Created in 2021-01-20 9:02
 */
class TrieTreeImplByMock implements TrieTree {

    private final ArrayList<String> list = new ArrayList<>();

    /**
     * 将字符串加入到树中
     */
    @Override
    public void add(String words) {
        if (words == null) {
            return;
        }
        list.add(words);
    }

    /**
     * 将字符串从树中删除
     */
    @Override
    public void delete(String words) {
        if (words == null) {
            return;
        }
        if (!contains(words)) {
            // 没在树中，删除个毛
            return;
        }
        list.remove(words);
    }

    /**
     * word这个单词之前加入过几次
     */
    @Override
    public int searchCount(String words) {
        if (words == null) {
            return 0;
        }
        int count = 0;
        for (String str : list) {
            if (words.equals(str)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 树中是否包含字符串
     */
    @Override
    public boolean contains(String words) {
        if (words == null) {
            return false;
        }
        return list.contains(words);
    }

    /**
     * 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
     */
    @Override
    public int prefixNumber(String prefix) {
        if (!contains(prefix)) {
            return 0;
        }
        int count = 0;
        for (String str : list) {
            if (str.startsWith(prefix)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取树中一共有多少个字符串存进去了
     */
    @Override
    public int getTotalCount() {
        return list.size();
    }

}
