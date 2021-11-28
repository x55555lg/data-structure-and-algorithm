package com.lg.leetcode.other.practice1;

import java.util.List;

/**
 * @author Xulg
 * Description: N皇后问题1
 * Created in 2021-05-24 14:08
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
class NQueensI {

    public static void main(String[] args) {
        for (int n = 0; n <= 14; n++) {
            List<List<String>> lists1 = null;
            List<List<String>> lists2 = null;
            if (!lists1.equals(lists2)) {
                System.out.println("fucking");
                for (List<String> list : lists1) {
                    System.out.println(list);
                }
                System.out.println("----------------------------");
                for (List<String> list : lists2) {
                    System.out.println(list);
                }
                System.out.println("============================\r\n");
            }
        }
        System.out.println("good job");
    }
}
