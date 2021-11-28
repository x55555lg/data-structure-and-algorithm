package com.practice2.kmp;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xulg
 * @since 2021-10-26 9:53
 * Description: indexOf()的BF算法实现
 */
class BF {

    private static class IndexOfByBF {
        public static int indexOf(String str, String match) {
            if (str == null || match == null || str.length() < match.length()) {
                return -1;
            }
            char[] strChars = str.toCharArray();
            char[] matchChars = match.toCharArray();
            int strIdx = 0, matchIdx = 0;
            while (strIdx < strChars.length && matchIdx < matchChars.length) {
                if (strChars[strIdx] == matchChars[matchIdx]) {
                    strIdx++;
                    matchIdx++;
                } else {
                    strIdx = strIdx - matchIdx + 1;
                    matchIdx = 0;
                }
            }
            return matchIdx == matchChars.length ? strIdx - matchIdx : -1;
        }
    }

    /* ****************************************************************************************************************/

    /* 对数器 */

    static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>(16);
        map.put(-1, 0);
        int times = 1000000;
        String baseString = "ABCDEFG";
        for (int time = 0; time < times; ) {
            String str = RandomUtil.randomString(baseString, RandomUtil.randomInt(0, 13));
            String match = RandomUtil.randomString(baseString, RandomUtil.randomInt(0, 7));
            int r0 = StringUtils.indexOf(str, match);
            int r1 = IndexOfByBF.indexOf(str, match);
            if (r0 != -1) {
                time++;
            }
            if (map.containsKey(r0)) {
                map.put(r0, map.get(r0) + 1);
            } else {
                map.put(r0, 1);
            }
            if (!(r0 == r1)) {
                System.out.println("Oops str=" + str + "     match=" + match);
                break;
            }
        }
        System.out.println("finish!");
    }

}
