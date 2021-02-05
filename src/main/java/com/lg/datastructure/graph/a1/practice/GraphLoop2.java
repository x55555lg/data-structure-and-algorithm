package com.lg.datastructure.graph.a1.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Xulg
 * Created in 2021-02-03 9:33
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals", "MismatchedQueryAndUpdateOfCollection"})
class GraphLoop2 {

    /***
     * 广度优先遍历
     */
    public static void bfs(Node node) {
        HashSet<Node> recordedNodes = new HashSet<>();
        recordedNodes.add(node);
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.println(temp.value);
            for (Node nextNode : temp.nexts) {
                if (!recordedNodes.contains(nextNode)) {
                    recordedNodes.add(nextNode);
                    queue.add(nextNode);
                }
            }
        }
    }

    /**
     * 深度优先遍历
     */
    public static void dfs(Node node) {
        HashSet<Node> recordedNodes = new HashSet<>();
        recordedNodes.add(node);
        Stack<Node> stack = new Stack<>();
        stack.add(node);
        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            for (Node nextNode : currentNode.nexts) {
                if (!recordedNodes.contains(nextNode)) {
                    stack.push(currentNode);
                    stack.push(nextNode);
                    recordedNodes.add(nextNode);
                    System.out.println(nextNode.value);
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
        // 从当前节点出发的边有哪些
        ArrayList<Edge> edges;

        Node(int value) {
            this.value = value;
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
