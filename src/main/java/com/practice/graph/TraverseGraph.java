package com.practice.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 图的遍历
 *
 * @author Xulg
 * Created in 2021-05-12 14:38
 */
class TraverseGraph {

    private static class BFS {
        public static void traverse(Graph graph) {
            if (graph == null) {
                return;
            }
            bfs(graph.nodes.get(0));
        }

        private static void bfs(Node node) {
            if (node == null) {
                return;
            }
            HashSet<Node> visited = new HashSet<>();
            visited.add(node);
            Queue<Node> queue = new LinkedList<>();
            queue.add(node);
            while (!queue.isEmpty()) {
                Node temp = queue.poll();
                // 打印
                System.out.println(temp.value);
                for (Node nextNode : temp.nexts) {
                    // 将节点的未访问过的邻接节点加入队列
                    if (!visited.contains(nextNode)) {
                        queue.add(nextNode);
                        // 标记节点已访问
                        visited.add(nextNode);
                    }
                }
            }
        }
    }

    private static class DFS {
        public static void traverse(Graph graph) {
            if (graph == null) {
                return;
            }
            dfs(graph.nodes.get(1));
        }

        private static void dfs(Node node) {
            if (node == null) {
                return;
            }

            HashSet<Node> visited = new HashSet<>();
            visited.add(node);
            Stack<Node> stack = new Stack<>();
            stack.add(node);

            System.out.println(node.value);
            while (!stack.isEmpty()) {
                Node current = stack.pop();
                for (Node nextNode : current.nexts) {
                    if (!visited.contains(nextNode)) {
                        stack.push(current);
                        stack.push(nextNode);
                        visited.add(nextNode);
                        System.out.println(nextNode.value);
                        break;
                    }
                }
            }
        }
    }
}
