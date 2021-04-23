package com.lg.leetcode.topinterview.practice;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xulg
 * Created in 2021-03-04 9:31
 */
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel", "AlibabaLowerCamelCaseVariableNaming"})
class TopKFrequentElements {

    /*
     * Given a non-empty array of integers, return the k most frequent elements.
     *
     * Example 1:
     * Input: nums = [1,1,1,2,2,3], k = 2
     * Output: [1,2]
     *
     * Example 2:
     * Input: nums = [1], k = 1
     * Output: [1]
     * Note:
     *  You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
     *  Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
     *  It's guaranteed that the answer is unique, in other words the set of the top k frequent elements is unique.
     *  You can return the answer in any order.
     */

    public int[] topKFrequent(int[] nums, int k) {
        return null;
    }

    private static class BruteForce {

        public static int[] topKFrequent(int[] arr, int k) {
            if (arr == null || arr.length == 0) {
                return new int[0];
            }

            /*
            int max = arr[0];
            for (int i = 0; i < arr.length; i++) {
                max = Math.max(max, arr[i]);
            }
            int[] countArr = new int[max + 1];
            for (int i = 0; i < arr.length; i++) {
                countArr[arr[i]]++;
            }
            for (int i = 0; i < countArr.length; i++) {
                if (countArr[i] > 0) {
                }
            }
            */

            /*
             * arr = [1, 1, 1, 2, 2, 3]
             * k = 2
             *-------------------------
             * 1        3
             * 2        2
             * 3        1
             */

            HashMap<Integer, Integer> countMap = new HashMap<>(arr.length * 4 / 3 + 1);
            for (int i = 0; i < arr.length; i++) {
                if (countMap.containsKey(arr[i])) {
                    countMap.put(arr[i], countMap.get(arr[i]) + 1);
                } else {
                    countMap.put(arr[i], 1);
                }
            }
            List<Integer> list = new ArrayList<>();
            for (Integer val : countMap.keySet()) {
                if (countMap.get(val) >= k) {
                    list.add(val);
                }
            }
            int[] result = new int[list.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = list.get(i);
            }
            return result;
        }

    }

    public static void main(String[] args) {
        int[] topKFrequent = BruteForce.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2);
        System.out.println(Arrays.toString(topKFrequent));

        String json = "{\n" +
                "    \"code\": \"00\",\n" +
                "    \"message\": \"\",\n" +
                "    \"result\": [\n" +
                "        {\n" +
                "            \"date\": 1612108800000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612195200000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612281600000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612368000000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612454400000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612540800000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612627200000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612713600000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612800000000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612886400000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1612972800000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613059200000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613145600000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613232000000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613318400000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613404800000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613491200000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613577600000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613664000000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613750400000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613836800000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1613923200000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614009600000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614096000000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614182400000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614268800000,\n" +
                "            \"faceValue\": 95,\n" +
                "            \"num\": 11,\n" +
                "            \"settlementPrice\": 0.000,\n" +
                "            \"userCount\": 10\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614355200000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614441600000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614528000000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614614400000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614700800000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\": 1614787200000,\n" +
                "            \"faceValue\": 0,\n" +
                "            \"num\": 0,\n" +
                "            \"settlementPrice\": 0,\n" +
                "            \"userCount\": 0\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray result = jsonObject.getJSONArray("result");
        System.out.println(result.size());
        for (Object o : result) {
            JSONObject data = (JSONObject) o;
            System.out.println(DateUtil.formatDate(data.getDate("date")));
        }
    }

}
