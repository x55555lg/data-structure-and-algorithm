package com.lg;

import lombok.SneakyThrows;

/**
 * @author Xulg
 * Created in 2020-09-18 20:48
 */
class TwoThreadTakeTurnsPrint {

    /**
     * 题目
     * 两个线程交替的输出1A2B3C4D......
     */

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        Object lock = new Object();
        int[] count = new int[]{1};
        boolean[] flag = new boolean[]{false};
        final String[] array = {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };

        Runnable r1 = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (count[0] <= array.length) {
                    synchronized (lock) {
                        while (flag[0]) {
                            lock.wait();
                        }
                        if (count[0] <= array.length) {
                            System.out.println(count[0]);
                            flag[0] = true;
                            lock.notifyAll();
                        }
                    }
                    Thread.sleep(1000);
                }
                System.out.println("--------------------------");
            }
        };

        Runnable r2 = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (count[0] <= array.length) {
                    synchronized (lock) {
                        while (!flag[0]) {
                            lock.wait();
                        }
                        if (count[0] <= array.length) {
                            System.out.println(array[count[0] - 1] + "\r\n");
                            count[0]++;
                            flag[0] = false;
                            lock.notifyAll();
                        }
                    }
                    Thread.sleep(100);
                }
                System.out.println("=========================");
            }
        };

        new Thread(r1).start();
        new Thread(r2).start();

        Thread.sleep(10000);
    }

}
