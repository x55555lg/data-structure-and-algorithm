package com.lg.algorithm.sort.a2.practice;

import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 插入排序
 * 时间复杂度：O(n^2) 常数项较小
 * 空间复杂度：O(1)
 * 稳定性：稳定
 *
 * @author Xulg
 * Created in 2021-01-18 10:53
 */
@SuppressWarnings("all")
class InsertSort {

    public static void sort1(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        // 左神视频中的写法
        for (int i = 0; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public static void sort2(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        /*
        JDK类中的写法
            java.util.Arrays.mergeSort(Object[] src, Object[] dest, int low, int high, int off, Comparator c) {
                // ...
                int length = high - low;
                // 判断使用插入排序
                if (length < 7) {
                    for (int i=low; i<high; i++)
                        for (int j=i; j>low && c.compare(dest[j-1], dest[j])>0; j--)
                            swap(dest, j, j-1);
                }
                // ...
            }
        */

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) {
        {
            int times = 1000000;
            for (int time = 0; time < times; time++) {
                int[] arr = new int[RandomUtil.randomInt(0, 100)];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                }
                int[] arr1 = arr.clone();
                int[] arr2 = arr.clone();
                int[] arr3 = arr.clone();
                Arrays.sort(arr1);
                sort1(arr2);
                sort2(arr3);
                if (!Arrays.equals(arr1, arr2) || !Arrays.equals(arr2, arr3)) {
                    System.out.println("Oops");
                }
            }
            System.out.println("finish!");
        }

        AtomicInteger flag = new AtomicInteger(2);

        {
            new Thread(() -> {
                int times = 1000000;
                long min = Long.MAX_VALUE;
                long max = Long.MIN_VALUE;
                for (int time = 0; time < times; time++) {
                    int[] arr = new int[100];
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                    }
                    long start1 = System.nanoTime();
                    sort1(arr);
                    long end1 = System.nanoTime();
                    long between1 = end1 - start1;

                    min = Math.min(min, between1);
                    max = Math.max(max, between1);
                }
                System.out.println("finish!   sort1()耗时区间" + min + "," + max);
                flag.decrementAndGet();
            }).start();
        }

        {
            new Thread(() -> {
                int times = 1000000;
                long min = Long.MAX_VALUE;
                long max = Long.MIN_VALUE;
                for (int time = 0; time < times; time++) {
                    int[] arr = new int[100];
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = RandomUtil.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                    }
                    long start1 = System.nanoTime();
                    sort2(arr);
                    long end1 = System.nanoTime();
                    long between1 = end1 - start1;

                    min = Math.min(min, between1);
                    max = Math.max(max, between1);
                }
                System.out.println("finish!   sort2()耗时区间" + min + "," + max);
                flag.decrementAndGet();
            }).start();
        }

        while (flag.get() > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
