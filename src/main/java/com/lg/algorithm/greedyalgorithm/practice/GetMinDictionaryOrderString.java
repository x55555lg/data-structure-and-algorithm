package com.lg.algorithm.greedyalgorithm.practice;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

/**
 * 从头到尾将一道利用贪心算法求解的题目
 *
 * @author Xulg
 * Created in 2020-11-09 14:08
 */
class GetMinDictionaryOrderString {

    /*
     * 给定一个由字符串组成的数组String[] array，必须把所有的字符串拼接起来，返回
     * 所有可能的拼接结果中，字典序最小的结果
     *  例如：String[] array = {"a", "c", "x"};
     *      acx的拼接组合是字典序最小的
     *----------------------------------------------------------------------------
     * 暴力解法：
     *  1.求出字符串数组的所有拼接的排列组合可能
     *  2.对得到的排列组合结果一个个的比较，哪个的字典序最小
     * 贪心解法：
     *  ab <= ba
     *  bc <= cb
     *      推导出 ===>>> ac <= ca
     */

    /* 暴力解法 */

    /**
     * 使用暴力解法
     * 排列组合出字符串所有的拼接组合，一个个比较哪个字典序最小
     */
    public static String violence(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        // 保存所有的拼接组合
        ArrayList<String> all = new ArrayList<>();
        // 记录已使用的字符串的索引index
        HashSet<Integer> alreadyUsed = new HashSet<>();
        // 进行递归
        recurse(array, alreadyUsed, "", all);

        if (all.isEmpty()) {
            return null;
        }

        // 判断哪个是字典序最小的
        String min = all.get(0);
        for (String s : all) {
            if (s.compareTo(min) < 0) {
                min = s;
            }
        }
        return min;
    }

    /**
     * 深度优先遍历
     * 递归排列组合
     *
     * @param array       字符串数组
     * @param alreadyUsed 已经使用了的字符串的索引index
     * @param path        拼接结果，之前使用过的字符串，拼接成了-> path
     * @param all         所有排列祖册结果
     */
    private static void recurse(String[] array, HashSet<Integer> alreadyUsed,
                                String path, ArrayList<String> all) {
        if (alreadyUsed.size() == array.length) {
            // 所有的字符串都用过了
            all.add(path);
        } else {
            for (int idx = 0; idx < array.length; idx++) {
                String s = array[idx];
                // 字符串没有用过
                if (!alreadyUsed.contains(idx)) {
                    alreadyUsed.add(idx);
                    recurse(array, alreadyUsed, path + s, all);
                    alreadyUsed.remove(idx);
                }
            }
        }
    }

    /* 贪心解法 */

    /**
     * 使用贪心解法
     */
    public static String greedy(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        // 根据 ab < ba 规则进行排序
        Arrays.sort(array, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return (a + b).compareTo(b + a);
            }
        });
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s);
        }
        return sb.toString();
    }

    /* ************************************************************************************************************** */

    /* 对数器 */

    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static String[] copyStringArray(String[] arr) {
        return arr.clone();
    }

    /* ************************************************************************************************************** */

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 100000;
        String[] arr = generateRandomStringArray(arrLen, strLen);
        System.out.println("先打印一个生成的字符串");
        for (String str : arr) {
            System.out.print(str + ",");
        }
        System.out.println();
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            String r1 = violence(arr1);
            String r2 = greedy(arr2);
            if (!StringUtils.equals(r1, r2)) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

        String[] array = generateRandomStringArray(arrLen, strLen);
        System.out.println("原数组" + Arrays.toString(array));

        ArrayList<String> list1 = aaaaaaaaaa(array);
        System.out.println(JSON.toJSONString(list1, true));

        ArrayList<String> list2 = bbbbbbbbbb(array);
        System.out.println(JSON.toJSONString(list2, true));
    }

    /**
     * 排列组合
     */
    public static ArrayList<String> aaaaaaaaaa(String[] array) {
        if (array == null || array.length == 0) {
            return new ArrayList<>(0);
        }
        ArrayList<String> list = new ArrayList<>();
        process1(array, "", list);
        return list;
    }

    private static void process1(String[] array, String path, ArrayList<String> list) {
        if (array.length == 0) {
            list.add(path);
        } else {
            for (int idx = 0; idx < array.length; idx++) {
                String[] remainArray = ArrayUtil.remove(array, idx);
                process1(remainArray, path + array[idx], list);
            }
        }
    }

    /**
     * 排列组合
     */
    public static ArrayList<String> bbbbbbbbbb(String[] array) {
        if (array == null || array.length == 0) {
            return new ArrayList<>(0);
        }
        ArrayList<String> list = new ArrayList<>();
        process2(array, new HashSet<>(), "", list);
        return list;
    }

    private static void process2(String[] array, HashSet<String> alreadyUsed,
                                 String path, ArrayList<String> list) {
        if (alreadyUsed.size() == array.length) {
            // 所有的元素都用过了，可以结束了
            list.add(path);
        } else {
            for (String s : array) {
                if (!alreadyUsed.contains(s)) {
                    alreadyUsed.add(s);
                    process2(array, alreadyUsed, path + s, list);
                    alreadyUsed.remove(s);
                }
            }
        }
    }
}
