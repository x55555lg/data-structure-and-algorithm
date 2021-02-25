package com.lg.leetcode.topinterview.practice;

/**
 * @author Xulg
 * Created in 2021-02-25 13:56
 */
class ValidPalindrome {

    /*
     * Given a string s, determine if it is a palindrome, considering only alphanumeric
     * characters and ignoring cases.
     *
     * Example 1:
     * Input: s = "A man, a plan, a canal: Panama"
     * Output: true
     * Explanation: "amanaplanacanalpanama" is a palindrome.
     *
     * Example 2:
     * Input: s = "race a car"
     * Output: false
     * Explanation: "raceacar" is not a palindrome.
     *
     * Constraints:
     *  1 <= s.length <= 2 * 105
     *  s consists only of printable ASCII characters.
     */

    public static boolean isPalindrome(String s) {
        return check(s);
    }

    private static boolean check(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        char[] chars = str.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char cl = chars[left];
            char cr = chars[right];
            if (!Character.isLetterOrDigit(cl) && Character.isLetterOrDigit(cr)) {
                // 左边字符忽略
                left++;
            } else if (Character.isLetterOrDigit(cl) && !Character.isLetterOrDigit(cr)) {
                // 右边字符忽略
                right--;
            } else if (!Character.isLetterOrDigit(cl) && !Character.isLetterOrDigit(cr)) {
                // 左右两边字符都需要忽略
                left++;
                right--;
            } else {
                // cl和cr是合法字符
                if (cl != cr) {
                    // 字母类型的字符大小写差值为32
                    if (Character.isLetter(cl) && Character.isLetter(cr) && Math.abs(cl - cr) == 32) {
                        // 判断下一个位置的字符去
                        left++;
                        right--;
                    } else {
                        // cl，cr其中一个不是字母，肯定不想等
                        return false;
                    }
                } else {
                    // 判断下一个位置的字符去
                    left++;
                    right--;
                }
            }
        }
        return true;
    }

    private static boolean check2(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        char[] chars = str.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            // 尽可能的过滤了两边的不合法字符，来到可以比较的位置
            while (!Character.isLetterOrDigit(chars[left])) {
                // 左边字符忽略
                left++;
                if (left >= chars.length) {
                    break;
                }
            }
            while (!Character.isLetterOrDigit(chars[right])) {
                // 右边字符忽略
                right--;
                if (right < 0) {
                    break;
                }
            }
            if (left > right) {
                // 没有合法字符
                return true;
            }

            // cl和cr是合法字符
            if (chars[left] != chars[right]) {
                // 字母类型的字符大小写差值为32
                if (Character.isLetter(chars[left]) && Character.isLetter(chars[right])
                        && Math.abs(chars[left] - chars[right]) == 32) {
                    // 判断下一个位置的字符去
                    left++;
                    right--;
                } else {
                    // cl，cr其中一个不是字母，肯定不想等
                    return false;
                }
            } else {
                // 判断下一个位置的字符去
                left++;
                right--;
            }
        }
        return true;
    }

    private static boolean isAlphanumeric(char c) {
        if (c == '0') {
            return true;
        }
        if (c == '1') {
            return true;
        }
        if (c == '2') {
            return true;
        }
        if (c == '3') {
            return true;
        }
        if (c == '4') {
            return true;
        }
        if (c == '5') {
            return true;
        }
        if (c == '6') {
            return true;
        }
        if (c == '7') {
            return true;
        }
        if (c == '8') {
            return true;
        }
        if (c == '9') {
            return true;
        }
        if (isLetter(c)) {
            return true;
        }
        return false;
    }

    private static boolean isLetter(char c) {
        if (c == 'A' || c == 'a') {
            return true;
        }
        if (c == 'B' || c == 'b') {
            return true;
        }
        if (c == 'C' || c == 'c') {
            return true;
        }
        if (c == 'D' || c == 'd') {
            return true;
        }
        if (c == 'E' || c == 'e') {
            return true;
        }
        if (c == 'F' || c == 'f') {
            return true;
        }
        if (c == 'G' || c == 'g') {
            return true;
        }
        if (c == 'H' || c == 'h') {
            return true;
        }
        if (c == 'I' || c == 'i') {
            return true;
        }
        if (c == 'J' || c == 'j') {
            return true;
        }
        if (c == 'K' || c == 'k') {
            return true;
        }
        if (c == 'L' || c == 'l') {
            return true;
        }
        if (c == 'M' || c == 'm') {
            return true;
        }
        if (c == 'N' || c == 'n') {
            return true;
        }
        if (c == 'O' || c == 'o') {
            return true;
        }
        if (c == 'P' || c == 'p') {
            return true;
        }
        if (c == 'Q' || c == 'q') {
            return true;
        }
        if (c == 'R' || c == 'r') {
            return true;
        }
        if (c == 'S' || c == 's') {
            return true;
        }
        if (c == 'T' || c == 't') {
            return true;
        }
        if (c == 'U' || c == 'u') {
            return true;
        }
        if (c == 'V' || c == 'v') {
            return true;
        }
        if (c == 'W' || c == 'w') {
            return true;
        }
        if (c == 'X' || c == 'x') {
            return true;
        }
        if (c == 'Y' || c == 'y') {
            return true;
        }
        if (c == 'Z' || c == 'z') {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println('A' - 'a');
        System.out.println('a' - 'A');
        System.out.println('b' - 'B');
        System.out.println('1' - '1');

        String str = "A man, a plan, a canal: Panama";
        System.out.println(isPalindrome(str));
        System.out.println(isPalindrome(".,"));
        System.out.println(isPalindrome("0P"));

        {
            System.out.println(check2(str));
            System.out.println(check2(".,"));
            System.out.println(check2("0P"));
        }
    }
}
