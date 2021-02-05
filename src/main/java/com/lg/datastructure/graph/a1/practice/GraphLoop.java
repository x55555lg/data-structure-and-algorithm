package com.lg.datastructure.graph.a1.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 图的遍历
 * 广度优先遍历BFS
 * 深度优先遍历DFS
 *
 * @author Xulg
 * Created in 2021-02-02 14:27
 */
@SuppressWarnings({"MapOrSetKeyShouldOverrideHashCodeEquals", "AlibabaLowerCamelCaseVariableNaming"})
class GraphLoop {

    public static void loopGraphByBFS(Graph graph) {
        if (graph == null || graph.nodes.isEmpty()) {
            return;
        }

        // 已经访问过的节点
        HashSet<Node> visitedNodes = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            if (!visitedNodes.contains(node)) {
                bfs(node);
                visitedNodes.add(node);
            }
        }
    }

    /**
     * 从node出发，进行广度优先遍历图结构
     * 1.利用队列实现
     * 2.从源节点开始依次按照宽度进队列，然后弹出
     * 3.每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
     * 4.直到队列变空
     */
    private static void bfs(Node node) {
        // 节点入队
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        // 标记节点已经入队
        HashSet<Node> recordedNodes = new HashSet<>();
        recordedNodes.add(node);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.println(temp.value);
            // 加入当前访问节点的那些没入队过的邻接节点
            for (Node nextNode : temp.nexts) {
                if (!recordedNodes.contains(nextNode)) {
                    recordedNodes.add(temp);
                    queue.add(nextNode);
                }
            }
        }
    }

    public static void loopGraphByDFS(Graph graph) {
        if (graph == null || graph.nodes.isEmpty()) {
            return;
        }
        // 已经访问过的节点
        HashSet<Node> visitedNodes = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            if (!visitedNodes.contains(node)) {
                dfs(node);
                visitedNodes.add(node);
            }
        }
    }

    /**
     * 从node出发，进行深度优先遍历图结构
     * 1.利用栈实现
     * 2.从源节点开始把节点按照深度放入栈，然后弹出
     * 3.每弹出一个点，把该节点下某一个没有进过栈的邻接点放入栈
     * 4.直到栈变空
     */
    private static void dfs(Node node) {
        // 节点入栈
        Stack<Node> stack = new Stack<>();
        stack.add(node);
        // 标记节点已经入栈
        HashSet<Node> recordedNodes = new HashSet<>();
        recordedNodes.add(node);
        while (!stack.isEmpty()) {
            // 先从栈中弹出节点
            Node temp = stack.pop();
            for (Node nextNode : temp.nexts) {
                if (!recordedNodes.contains(nextNode)) {
                    stack.push(temp);
                    stack.push(nextNode);
                    recordedNodes.add(nextNode);
                    System.out.println(nextNode.value);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        /*
         *        ②→→④←←⑤
         *        ↓
         *        ↓
         * ①→→③
         */
        int[][] matrix = {
                {7, 5, 4},
                {4, 1, 3},
                {3, 2, 3},
                {8, 2, 4}
        };
        //Graph graph = ConvertToGraph.convert(matrix);
    }

    /* *******************************************************************************************************/
    /* *******************************************************************************************************/
    /* *******************************************************************************************************/

    private static class Node {
        // 节点的编号|值
        int value;
        // 入度
        int in;
        // 出度
        int out;
        // 邻接节点
        ArrayList<Node> nexts;
        // 从当前节点X出发的，有哪些边
        ArrayList<Edge> edges;

        Node(int value, int in, int out) {
            this.value = value;
            this.in = in;
            this.out = out;
            nexts = new ArrayList<>();
            edges = new ArrayList<>();
        }
    }

    private static class Edge {
        // 边的起点
        Node from;
        // 边的终点
        Node to;
        // 边的权重
        int weight;

        Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    private static class Graph {
        // 所有的节点
        HashMap<Integer, Node> nodes;
        // 所有的边
        HashSet<Edge> edges;

        Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }

}
