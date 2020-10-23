package com.lg.datastructure.trietree.test.t2;

public class Node1 {
    public int pass;
    public int end;
    public Node1[] nexts;

    public Node1() {
        pass = 0;
        end = 0;
        // 0    a
        // 1    b
        // 2    c
        // ..   ..
        // 25   z
        // nexts[i] == null   i方向的路不存在
        // nexts[i] != null   i方向的路存在
        nexts = new Node1[26];
    }
}
