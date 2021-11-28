package com.lg.algorithm.dynamicprogramming.practice;

/**
 * @author Xulg
 * @since 2021-10-29 12:47
 * Description: 11层楼梯每次下1层或者2层，需要下几次？
 */
class 十一层楼梯每次下一层或者两层需要下几次 {


    private static class BruteForce {
        public static int count(int floors) {
            if (floors <= 0) {
                return 0;
            }
            return recurse(floors);
        }

        private static int recurse(int floors) {
            if (floors < 0) {
                return 0;
            }
            if (floors == 0) {
                return 1;
            }
            return recurse(floors - 1) + recurse(floors - 2);
        }
    }

}
