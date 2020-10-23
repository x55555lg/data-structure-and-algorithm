package com.lg.datastructure.trietree.test.t1;

import java.util.HashMap;

public class Right {

    public HashMap<String, Integer> box;

    public Right() {
        box = new HashMap<>();
    }

    public void insert(String word) {
        if (!box.containsKey(word)) {
            box.put(word, 1);
        } else {
            box.put(word, box.get(word) + 1);
        }
    }

    public void delete(String word) {
        if (box.containsKey(word)) {
            if (box.get(word) == 1) {
                box.remove(word);
            } else {
                box.put(word, box.get(word) - 1);
            }
        }
    }

    public int search(String word) {
        if (!box.containsKey(word)) {
            return 0;
        } else {
            return box.get(word);
        }
    }

    public int prefixNumber(String pre) {
        int count = 0;
        for (String cur : box.keySet()) {
            if (cur.startsWith(pre)) {
                count++;
            }
        }
        return count;
    }
}
