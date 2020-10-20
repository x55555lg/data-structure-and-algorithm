package com.lg.datastructure.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 堆中数据改变了，可以重新保持堆化的实现
 * 默认按照最小堆实现
 *
 * @author Xulg
 * Created in 2020-10-19 19:17
 */
class DynamicVariableHeap<T> {
    private static final int HEAP_TOP_IDX = 0;
    private final ArrayList<T> array;
    private final HashMap<T, Integer> indexMap;
    private int heapSize;
    private final Comparator<T> comparator;

    public DynamicVariableHeap(Comparator<T> c) {
        this.comparator = c;
        array = new ArrayList<>();
        indexMap = new HashMap<>();
    }

    /**
     * 往堆中增加元素
     * 时间复杂度：O(log(n))
     *
     * @param data the data
     */
    public void add(T data) {
        // 元素存入数组中
        array.add(data);
        // 记录元素所在的位置
        indexMap.put(data, heapSize);
        // 从heapSize位置开始堆化
        heapInsert(heapSize);
        heapSize++;
    }

    /**
     * 删除并返回堆顶元素
     * 时间复杂度：O(log(n))
     */
    public T deleteHeapTop() {
        if (isEmpty()) {
            return null;
        }

        // 获取堆顶元素
        T t = (T) array.get(HEAP_TOP_IDX);
        // 移除堆顶元素的位置
        indexMap.remove(t);

        // 将最后一个元素和第一个元素交换
        swap(array, HEAP_TOP_IDX, --heapSize);
        // 移除堆顶元素
        array.remove(t);

        // 剩下的元素进行堆化操作
        heapify(array, HEAP_TOP_IDX, heapSize);
        return t;
    }

    /**
     * 修改元素内部的值之后重新堆化
     *
     * @param data the data
     */
    public void resign(T data) {
        // 找到元素的位置
        Integer index = indexMap.get(data);
        /*
         * 修改某个堆内对象的属性后，需要重新堆化
         * heapInsert()和heapify()只会执行其中一个方法
         */
        // 从元素所在位置开始向上重新堆化
        heapInsert(index);
        // 从元素所在位置开始向下重新堆化
        heapify(array, index, heapSize);
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * 从index位置开始，自下而上的堆化操作
     */
    private void heapInsert(int index) {
        while (index > 0) {
            // 计算出父节点的位置
            int parentIdx = (index - 1) / 2;
            // 如果子节点小于它的父节点，就进行交换
            if (comparator.compare(array.get(index), array.get(parentIdx)) < 0) {
                swap(array, index, parentIdx);
            }
            // 看父节点的状况
            index = parentIdx;
        }
    }

    /**
     * 堆化操作
     * 自上而下的进行堆化操作
     *
     * @param array    the array
     * @param index    堆化开始的index，一般是堆顶
     * @param heapSize 堆的大小
     */
    private void heapify(ArrayList<T> array, int index, int heapSize) {
        // index是父节点
        while (true) {
            // 左子节点
            int leftIdx = 2 * index + 1;
            // 右子节点
            int rightIdx = leftIdx + 1;

            // 左子节点索引越界了，右子节点肯定也越界了，直接break结束
            if (leftIdx >= heapSize) {
                break;
            }

            // minSubIdx就是左右子节点中值最小的那个索引
            int minSubIdx;
            if (rightIdx < heapSize) {
                // 右节点没有越界，这里说明左右节点都在
                // 左右节点都有，取左右节点中值小的和父节点比较
                minSubIdx = (comparator.compare(array.get(rightIdx), array.get(leftIdx)) < 0)
                        ? rightIdx : leftIdx;
            } else {
                // 只有左节点，那么左子节点就是最小的子节点
                minSubIdx = leftIdx;
            }

            // 比较父节点值和子节点值的大小
            int oldIndex = index;
            if (comparator.compare(array.get(minSubIdx), array.get(index)) < 0) {
                // 子节点值小于父节点值，交换父节点和子节点的位置
                swap(array, minSubIdx, index);
                // 将交换的这个子节点作为下一个父节点进行处理
                index = minSubIdx;
            }
            // 父节点没有变化，说明堆化完成了咯
            if (oldIndex == index) {
                break;
            }
        }
    }

    private void swap(ArrayList<T> array, int a, int b) {
        // 记录的索引也要交换
        indexMap.put(array.get(a), b);
        indexMap.put(array.get(b), a);
        T temp = array.get(a);
        array.set(a, array.get(b));
        array.set(b, temp);
    }

    /* ************************************************************************************************************** */

    static class Student {
        public int classNo;
        public int age;
        public int id;

        Student(int classNo, int age, int id) {
            this.classNo = classNo;
            this.age = age;
            this.id = id;
        }
    }

    static class StudentAgeComparator implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.age - s2.age;
        }
    }

    public static void main(String[] args) {
        Student s1 = null;
        Student s2 = null;
        Student s3 = null;
        Student s4 = null;
        Student s5 = null;
        Student s6 = null;

        s1 = new Student(2, 50, 11111);
        s2 = new Student(1, 60, 22222);
        s3 = new Student(6, 10, 33333);
        s4 = new Student(3, 20, 44444);
        s5 = new Student(7, 72, 55555);
        s6 = new Student(1, 14, 66666);

        PriorityQueue<Student> heap = new PriorityQueue<>(new StudentAgeComparator());
        heap.add(s1);
        heap.add(s2);
        heap.add(s3);
        heap.add(s4);
        heap.add(s5);
        heap.add(s6);
        while (!heap.isEmpty()) {
            Student cur = heap.poll();
            System.out.println(cur.classNo + "," + cur.age + "," + cur.id);
        }

        System.out.println("===============");

        DynamicVariableHeap<Student> myHeap = new DynamicVariableHeap<>(new StudentAgeComparator());
        myHeap.add(s1);
        myHeap.add(s2);
        myHeap.add(s3);
        myHeap.add(s4);
        myHeap.add(s5);
        myHeap.add(s6);
        while (!myHeap.isEmpty()) {
            Student cur = myHeap.deleteHeapTop();
            System.out.println(cur.classNo + "," + cur.age + "," + cur.id);
        }

        System.out.println("===============");

        s1 = new Student(2, 50, 11111);
        s2 = new Student(1, 60, 22222);
        s3 = new Student(6, 10, 33333);
        s4 = new Student(3, 20, 44444);
        s5 = new Student(7, 72, 55555);
        s6 = new Student(1, 14, 66666);

        heap = new PriorityQueue<>(new StudentAgeComparator());

        heap.add(s1);
        heap.add(s2);
        heap.add(s3);
        heap.add(s4);
        heap.add(s5);
        heap.add(s6);

        s2.age = 6;
        s4.age = 12;
        s5.age = 10;
        s6.age = 84;

        while (!heap.isEmpty()) {
            Student cur = heap.poll();
            System.out.println(cur.classNo + "," + cur.age + "," + cur.id);
        }

        System.out.println("===============");

        s1 = new Student(2, 50, 11111);
        s2 = new Student(1, 60, 22222);
        s3 = new Student(6, 10, 33333);
        s4 = new Student(3, 20, 44444);
        s5 = new Student(7, 72, 55555);
        s6 = new Student(1, 14, 66666);

        myHeap = new DynamicVariableHeap<>(new StudentAgeComparator());

        myHeap.add(s1);
        myHeap.add(s2);
        myHeap.add(s3);
        myHeap.add(s4);
        myHeap.add(s5);
        myHeap.add(s6);

        s2.age = 6;
        myHeap.resign(s2);
        s4.age = 12;
        myHeap.resign(s4);
        s5.age = 10;
        myHeap.resign(s5);
        s6.age = 84;
        myHeap.resign(s6);

        while (!myHeap.isEmpty()) {
            Student cur = myHeap.deleteHeapTop();
            System.out.println(cur.classNo + "," + cur.age + "," + cur.id);
        }

        System.out.println("===============");

        // 对数器
        System.out.println("test begin");
        int maxValue = 100000;
        int pushTime = 1000000;
        int resignTime = 100;
        DynamicVariableHeap<Student> test = new DynamicVariableHeap<>(new StudentAgeComparator());
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < pushTime; i++) {
            Student cur = new Student(1, (int) (Math.random() * maxValue), 1000);
            list.add(cur);
            test.add(cur);
        }
        for (int i = 0; i < resignTime; i++) {
            int index = (int) (Math.random() * pushTime);
            list.get(index).age = (int) (Math.random() * maxValue);
            test.resign(list.get(index));
        }
        int preAge = Integer.MIN_VALUE;
        while (test.isEmpty()) {
            Student cur = test.deleteHeapTop();
            if (cur.age < preAge) {
                System.out.println("Oops!");
            }
            preAge = cur.age;
        }
        System.out.println("test finish");
    }
}
