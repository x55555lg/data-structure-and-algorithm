package com.lg.datastructure.graph.a2.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-02-07 20:21
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class GraphLoop {

    public static void bfs(Node node) {
        if (node == null) {
            return;
        }
        HashSet<Node> visitedNodes = new HashSet<>();
        visitedNodes.add(node);
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.println(temp.value);
            for (Node nextNode : temp.nexts) {
                if (!visitedNodes.contains(nextNode)) {
                    queue.add(nextNode);
                    visitedNodes.add(nextNode);
                }
            }
        }
    }

    public static void dfs(Node node) {
        if (node == null) {
            return;
        }

        HashSet<Node> visitedNodes = new HashSet<>();
        visitedNodes.add(node);
        Stack<Node> stack = new Stack<>();
        stack.add(node);

        // 打印
        System.out.println(node.value);

        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            for (Node nextNode : temp.nexts) {
                if (!visitedNodes.contains(nextNode)) {
                    stack.push(temp);
                    stack.push(nextNode);
                    visitedNodes.add(nextNode);
                    System.out.println(temp.value);
                    break;
                }
            }
        }
    }

    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/
    /* ****************************************************************************************************************/

    private static class Node {
        int value;
        int in;
        int out;
        ArrayList<Node> nexts;
        ArrayList<Edge> edges;

        Node(int val) {
            value = val;
            in = 0;
            out = 0;
            nexts = new ArrayList<>();
            edges = new ArrayList<>();
        }
    }

    private static class Edge {
        Node from;
        Node to;
        int weight;

        Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    private static class Graph {
        HashMap<Integer, Node> nodes;
        HashSet<Edge> edges;

        Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }
}
