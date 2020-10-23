package com.lg.datastructure.trietree.test.t2;

import java.util.HashMap;

public class Node2 {
    public int pass;
    public int end;
    public HashMap<Integer, Node2> nexts;

    public Node2() {
        pass = 0;
        end = 0;
        nexts = new HashMap<>();
    }
}
