package com.lg.datastructure.graph;

import com.lg.datastructure.graph.base.Graph;
import com.lg.datastructure.graph.base.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 图的遍历
 * 深度优先遍历DFS
 * 广度优先遍历BFS
 *
 * @author Xulg
 * Created in 2020-11-17 19:06
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
class GraphLoop {

    /**
     * 从node出发，进行广度优先遍历图结构
     * 1.利用队列实现
     * 2.从源节点开始依次按照宽度进队列，然后弹出
     * 3.每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
     * 4.直到队列变空
     */
    public static void bfs(Node node) {
        if (node == null) {
            return;
        }

        // 记录已经入队过的图节点
        HashSet<Node> recordSet = new HashSet<>();
        // 存放节点的队列
        Queue<Node> queue = new LinkedList<>();
        // 第一个节点入队
        queue.add(node);
        // 第一个节点记录为已入队过了
        recordSet.add(node);

        while (!queue.isEmpty()) {
            // 弹出节点，执行打印
            Node current = queue.poll();
            System.out.println(current.value);
            // 遍历节点的所有邻接点，将其中没访问过的节点入队操作
            for (Node next : current.nexts) {
                if (!recordSet.contains(next)) {
                    recordSet.add(next);
                    queue.add(next);
                }
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
    public static void dfs(Node node) {
        if (node == null) {
            return;
        }

        // 记录已经入栈过的图节点
        HashSet<Node> recordSet = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        // 第一个节点入栈
        stack.add(node);
        // 第一个节点记录为已入栈过了
        recordSet.add(node);

        // 打印
        System.out.println(node.value);

        while (!stack.isEmpty()) {
            // 先从栈中弹出节点
            Node current = stack.pop();
            for (Node next : current.nexts) {
                if (!recordSet.contains(next)) {
                    // 当前节点重新入栈
                    stack.push(current);
                    // 当前节点的第一个邻接点入栈
                    stack.push(next);
                    // 标记已经入栈过了
                    recordSet.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }

    /* ****************************************************************************************************************/

    public static void main(String[] args) {
        /*
         *        ②→→④←←⑤
         *        ↓
         *        ↓
         * ①→→③
         */
        Integer[][] matrix = {
                {7, 5, 4},
                {4, 1, 3},
                {3, 2, 3},
                {8, 2, 4}
        };
        Graph graph = GraphGenerator.createGraph(matrix);

        bfs(graph.nodes.get(1));
        System.out.println("-------------");
        bfs(graph.nodes.get(2));
        System.out.println("-------------");
        bfs(graph.nodes.get(3));
        System.out.println("-------------");
        bfs(graph.nodes.get(4));
        System.out.println("-------------");
        bfs(graph.nodes.get(5));
        System.out.println("-------------");
    }

}
