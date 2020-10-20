package com.lg.datastructure.heap;

import java.util.PriorityQueue;

/**
 * java中提供的堆的使用
 * java中的类java.util.PriorityQueue底层就是堆
 *
 * @author Xulg
 * Created in 2020-10-19 15:53
 */
class HeapDemo {

    public static void main(String[] args) {
        // 默认是小根堆
        PriorityQueue<Integer> smallHeap = new PriorityQueue<>();
        smallHeap.add(5);
        smallHeap.add(7);
        smallHeap.add(3);
        smallHeap.add(0);
        smallHeap.add(2);
        smallHeap.add(5);
        while (!smallHeap.isEmpty()) {
            System.out.println(smallHeap.poll());
        }

        System.out.println("===========================================");

        // 大根堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2.compareTo(o1));
        maxHeap.add(5);
        maxHeap.add(7);
        maxHeap.add(3);
        maxHeap.add(0);
        maxHeap.add(2);
        maxHeap.add(5);
        while (!maxHeap.isEmpty()) {
            System.out.println(maxHeap.poll());
        }
    }

}
