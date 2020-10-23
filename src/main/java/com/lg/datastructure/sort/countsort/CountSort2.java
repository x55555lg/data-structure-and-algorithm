package com.lg.datastructure.sort.countsort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Xulg
 * Created in 2020-10-22 10:47
 */
class CountSort2 {

    public static void countSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        Map<Integer, Integer> countMap = new HashMap<>(array.length * 4 / 3 + 1);
        //Map<Integer, Integer> countMap = new TreeMap<>();
        for (int value : array) {
            if (countMap.containsKey(value)) {
                Integer count = countMap.get(value);
                countMap.put(value, count + 1);
            } else {
                countMap.put(value, 1);
            }
        }

        int idx = 0;
        for (Entry<Integer, Integer> entry : countMap.entrySet()) {
            Integer value = entry.getKey();
            Integer count = entry.getValue();
            for (; count > 0; count--) {
                array[idx++] = value;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {5, 3, 7, 2, 3, 4, 1};
        System.out.println("count sort before: " + Arrays.toString(array));
        // 计数排序
        countSort(array);
        System.out.println("count sort after:  " + Arrays.toString(array));
    }
}
