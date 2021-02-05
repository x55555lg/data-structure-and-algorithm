package com.lg.datastructure.heap.a1.practice;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author Xulg
 * Created in 2021-01-25 15:19
 */
class SortWithDistanceLimit {

    /*
     *  已知有一个几乎有序的数组，几乎有序是指，如果把数组排好序的话，
     * 每个元素移动的距离一定不超过k，并且k相对于数组长度来说比较小。
     * (每个数原始位置，距离自己排完序的位置，不超过k)
     *      移动前：array[i]
     *      移动后：array[i']
     *      要求：i - i' < k
     * 请选择一种合适的排序策略，对数组进行排序
     *
     * 思路：
     *      前提：根据给定的数组选择一个合适的k值
     *      1）建立一个大小为k的小根堆，并取堆顶元素保存到原数组中。
     *      2）依次加入数组中的后一个元素并对堆结构进行调整，始终维持大小为k的小根堆。
     *      3）采用普通堆排序的方法将最后k个元素排序并保存到原数组中。
     */

    @SuppressWarnings("ConstantConditions")
    public static void sort(int[] arr, int k) {
        if (arr == null || arr.length < 2 || k <= 0) {
            return;
        }

        PriorityQueue<Integer> smallHeap = new PriorityQueue<>();
        // arr[0, k-1]入堆
        // addToIdx记录堆加到了数组的哪个位置
        int addToIdx = 0;
        for (int idx = 0; idx < Math.min(arr.length, k); idx++) {
            smallHeap.add(arr[idx]);
            addToIdx++;
        }

        int index = 0;
        for (int idx = addToIdx; idx < arr.length; idx++) {
            // 数组加到堆中
            smallHeap.add(arr[idx]);
            // 从堆中弹出一个数写回原数组
            arr[index++] = smallHeap.poll();
        }

        // 堆中剩余的数写回原数组中
        while (!smallHeap.isEmpty()) {
            arr[index++] = smallHeap.poll();
        }
    }

    /* ****************************************************************************************************************/

    private static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int k) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        // 先排个序
        Arrays.sort(arr);
        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (k + 1)), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return arr;
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        System.out.println("test begin");
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            sort(arr1, k);
            Arrays.sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                succeed = false;
                System.out.println("K : " + k);
                System.out.println(Arrays.toString(arr));
                System.out.println(Arrays.toString(arr1));
                System.out.println(Arrays.toString(arr2));
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
