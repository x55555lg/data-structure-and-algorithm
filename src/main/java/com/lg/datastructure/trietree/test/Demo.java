package com.lg.datastructure.trietree.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

/**
 * @author Xulg
 * Created in 2020-10-22 14:59
 */
class Demo {

    /**
     * 测试对数器t1.Right, t2.Right2
     */
    @Test
    public void test1() {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            com.lg.datastructure.trietree.test.t1.Right right1 = new com.lg.datastructure.trietree.test.t1.Right();
            com.lg.datastructure.trietree.test.t2.Right right2 = new com.lg.datastructure.trietree.test.t2.Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    right1.insert(arr[j]);
                    right2.insert(arr[j]);
                } else if (decide < 0.5) {
                    right1.delete(arr[j]);
                    right2.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = right1.search(arr[j]);
                    int ans2 = right2.search(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println("Oops!1111111111");
                    }
                } else {
                    int ans1 = right1.prefixNumber(arr[j]);
                    int ans2 = right2.prefixNumber(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println(arr[j]);
                        System.out.println(JSON.toJSONString(right1.box));
                        System.out.println(JSON.toJSONString(right2.box));
                        System.out.println("Oops!2222222222");
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    /**
     * 测试对数器t1.Trie1, t2.Trie1
     */
    @Test
    public void test2() {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            com.lg.datastructure.trietree.test.t1.Trie1 right1 = new com.lg.datastructure.trietree.test.t1.Trie1();
            com.lg.datastructure.trietree.test.t2.Trie1 right2 = new com.lg.datastructure.trietree.test.t2.Trie1();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    right1.insert(arr[j]);
                    right2.insert(arr[j]);
                } else if (decide < 0.5) {
                    right1.delete(arr[j]);
                    right2.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = right1.search(arr[j]);
                    int ans2 = right2.search(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println("Oops!1111111111");
                    }
                } else {
                    int ans1 = right1.prefixNumber(arr[j]);
                    int ans2 = right2.prefixNumber(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println("Oops!2222222222");
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    /**
     * 测试对数器t1.Trie2, t2.Trie2
     */
    @Test
    public void test3() {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            com.lg.datastructure.trietree.test.t1.Trie2 right1 = new com.lg.datastructure.trietree.test.t1.Trie2();
            com.lg.datastructure.trietree.test.t2.Trie2 right2 = new com.lg.datastructure.trietree.test.t2.Trie2();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    right1.insert(arr[j]);
                    right2.insert(arr[j]);
                } else if (decide < 0.5) {
                    right1.delete(arr[j]);
                    right2.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = right1.search(arr[j]);
                    int ans2 = right2.search(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println("Oops!1111111111");
                    }
                } else {
                    int ans1 = right1.prefixNumber(arr[j]);
                    int ans2 = right2.prefixNumber(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println("Oops!2222222222");
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    /**
     * 测试对数器t1.Right, t2.Trie1, t2.Trie2
     */
    @Test
    public void test4() {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            com.lg.datastructure.trietree.test.t1.Right right = new com.lg.datastructure.trietree.test.t1.Right();
            com.lg.datastructure.trietree.test.t2.Trie1 right1 = new com.lg.datastructure.trietree.test.t2.Trie1();
            com.lg.datastructure.trietree.test.t2.Trie2 right2 = new com.lg.datastructure.trietree.test.t2.Trie2();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    right.insert(arr[j]);
                    right1.insert(arr[j]);
                    right2.insert(arr[j]);
                } else if (decide < 0.5) {
                    right.delete(arr[j]);
                    right1.delete(arr[j]);
                    right2.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans = right.search(arr[j]);
                    int ans1 = right1.search(arr[j]);
                    int ans2 = right2.search(arr[j]);
                    if (ans != ans1 || ans1 != ans2) {
                        System.out.println("Oops!1111111111");
                    }
                } else {
                    int ans = right.prefixNumber(arr[j]);
                    int ans1 = right1.prefixNumber(arr[j]);
                    int ans2 = right2.prefixNumber(arr[j]);
                    if (ans != ans1 || ans1 != ans2) {
                        System.out.println("Oops!2222222222");
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    /**
     * 测试对数器t2.Right, t1.Trie1, t2.Trie1, t1.Trie2, t2.Trie2
     */
    @Test
    public void test5() {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            com.lg.datastructure.trietree.test.t2.Right right = new com.lg.datastructure.trietree.test.t2.Right();
            com.lg.datastructure.trietree.test.t1.Trie1 right1 = new com.lg.datastructure.trietree.test.t1.Trie1();
            com.lg.datastructure.trietree.test.t1.Trie1 right2 = new com.lg.datastructure.trietree.test.t1.Trie1();
            com.lg.datastructure.trietree.test.t1.Trie2 right12 = new com.lg.datastructure.trietree.test.t1.Trie2();
            com.lg.datastructure.trietree.test.t2.Trie2 right22 = new com.lg.datastructure.trietree.test.t2.Trie2();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    right.insert(arr[j]);
                    right1.insert(arr[j]);
                    right2.insert(arr[j]);
                    right12.insert(arr[j]);
                    right22.insert(arr[j]);
                } else if (decide < 0.5) {
                    right.delete(arr[j]);
                    right1.delete(arr[j]);
                    right2.delete(arr[j]);
                    right12.delete(arr[j]);
                    right22.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans = right.search(arr[j]);
                    int ans1 = right1.search(arr[j]);
                    int ans2 = right2.search(arr[j]);
                    int ans3 = right12.search(arr[j]);
                    int ans4 = right22.search(arr[j]);
                    if (ans != ans1 || ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                        System.out.println("Oops!1111111111");
                    }
                } else {
                    int ans = right.prefixNumber(arr[j]);
                    int ans1 = right1.prefixNumber(arr[j]);
                    int ans2 = right2.prefixNumber(arr[j]);
                    int ans3 = right12.prefixNumber(arr[j]);
                    int ans4 = right22.prefixNumber(arr[j]);
                    if (ans != ans1 || ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                        System.out.println("Oops!2222222222");
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    public int prefixNumber1(String pre, JSONObject box) {
        int count = 0;
        for (String cur : box.keySet()) {
            if (cur.startsWith(pre)) {
                count++;
            }
        }
        return count;
    }

    public int prefixNumber2(String pre, JSONObject box) {
        int count = 0;
        for (String cur : box.keySet()) {
            if (cur.startsWith(pre)) {
                count += box.getInteger(cur);
            }
        }
        return count;
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

}
