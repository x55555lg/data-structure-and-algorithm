package com.lg.datastructure.trietree.test.t1;

public class Node1 {
    public int pass;
    public int end;
    public Node1[] nexts;

    public Node1() {
        pass = 0;
        end = 0;
        nexts = new Node1[26];
    }
}
