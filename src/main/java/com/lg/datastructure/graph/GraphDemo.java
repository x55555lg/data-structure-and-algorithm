package com.lg.datastructure.graph;

/**
 * 数据结构：图
 *
 * @author Xulg
 * Created in 2020-11-17 14:02
 */
class GraphDemo {

    /*
     *无向图
     *	   ②－⑥
     *	   ｜  ｜
     * ①－③－⑤－⑦－⑨
     *     ｜     ╱
     *     ④－ ⑧
     *有向图
     *	   ②←⑥
     *	   ↓  ↑
     * ①↔③→⑤→⑦→⑨
     *     ↑     ↗
     *     ④← ⑧
     *-------------------------------------------------------------
     *-------------------------------------------------------------
     *图(Graph)结构：由顶点(节点Node)，边(Edge)组成，边有方向的就叫有向图，边没有方向的就是无向图
     *               虽然存在有向图和无向图的概念，但实际上都可以用有向图来表达
     *               边上可能带有权值
     * 一些概念：
     *  什么是邻接点：和当前节点X直接相连，且能到达的节点
     *                  无向图中：顶点③的邻接点有①②④⑤
     *                  有向图中：顶点③的邻接点有①⑤
     *  什么是入度：  有多少节点指向X节点，X节点的入度就是多少
     *                  有向图中：顶点③的入度是3
     *  什么是出度：  从X节点出发指向多少节点，X节点的出度就是多少
     *                  有向图中：顶点③的出度是2
     *-------------------------------------------------------------
     *-------------------------------------------------------------
     *有向图如下：
     *              2
     * ╭─╮→→→→→→→→→╭─╮
     * │A │       7          │C │
     * ╰─╯←←←←←←←←←╰─╯
     * ↓  ↑                  ↓  ↑
     * ↓  ↑                  ↓  ↑
     * ↓3 ↑4               5 ↓ 1↑
     * ↓  ↑                  ↓  ↑
     * ↓  ↑       6          ↓  ↑
     * ╭─╮→→→→→→→→→╭─╮
     * │D │       9          │B │
     * ╰─╯←←←←←←←←←╰─╯
     *图结构的表示方法：
     *  1.邻接表
     *         结构比较像散列表，每个顶点对应一条链，链表中存储的是与这个顶点相连的其他顶点
     *         比较省内存空间，但查询费劲
     *         节点的邻接点，括号中的表示边的权重
     *      A: C(2)    D(3)
     *      B: C(1)    D(9)
     *      C: A(7)    B(5)
     *      D: B(6)    A(4)
     *  2.邻接矩阵
     *      矩阵的行和列都表示节点，矩阵的值表示权重，如果值为∞表示两节点不通，值为0表示是同一个节点
     *      比较费内存空间，但查询简单
     *      矩阵如下：
     *            A    B    C    D
     *       A    0    ∞   2    3
     *       B    ∞   0    1    9
     *       C    7    5    0    ∞
     *       D    4    6    ∞   0
     *  3.除此之外还有很多的表示方法
     *      例如：  [
     *                [7, 5, 4],
     *                [4, 1, 3],
     *                [3, 2, 3],
     *                [8, 2, 4]
     *              ]
     *       值表示节点编号，每个子数组从左到右分别表示[weight, from, to]，比如，
     *          [7, 0, 4]  weight = 7    fromNode = 5    toNode = 4
     *          图结构如下：
     *                     ②→8→④←7←⑤
     *                     ↓
     *                     ↓3
     *                     ↓
     *              ①→4→③
     *
     *-------------------------------------------------------------
     *-------------------------------------------------------------
     *图的遍历方式：
     *  图的遍历，不一定能访问到所有的图节点(从某个节点出发的)
     *  宽度优先遍历(BFS)
     *      1.利用队列实现
     *      2.从源节点开始依次按照宽度进队列，然后弹出
     *      3.每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
     *      4.直到队列变空
     *  深度优先遍历(DFS)
     *      1.利用栈实现
     *      2.从源节点开始把节点按照深度放入栈，然后弹出
     *      3.每弹出一个点，把该节点下某一个没有进过栈的邻接点放入栈
     *      4.直到栈变空
     *-------------------------------------------------------------
     *-------------------------------------------------------------
     *有向无环图的拓扑排序
     * 前提：必须是有向无环图
     * 应用：事件安排，编译顺序
     * 什么是拓扑排序：
     *          ③→⑤→⑦→⑨
     *              ↑
     *              ④←⑧
     *      事件③的执行前提是事件⑤已经执行，而事件⑤又依赖事件⑦的执行......
     *      拓扑排序可以将这些事件进行排序，哪个先执行哪个后执行。
     * 实现思路：
     *      1.在图中找到所有入度为0的点输出
     *      2.把所有的入度为0的点从图中删除，继续寻找入度为0的点输出，周而复始
     *      3.图的所有点都被删除后，依次输出的顺序就是拓扑排序
     * 实现代码：com.lg.datastructure.graph.TopologySort
     *-------------------------------------------------------------
     *-------------------------------------------------------------
     *什么是图的生成树，最小生成树
     * 图中的所有顶点和一部分边组成的一个子图。这个子图应该满足两个性质：
     *      没有环路
     *      所有的顶点都有边相连，不能出现孤立的点
     * 生成树有这个特性：图中有n个顶点，则图的生成树应该有n-1条边
     * 如果生成树的权重在所有生成树中是最小的，那么就叫做最小生成树
     *-------------------------------------------------------------
     *-------------------------------------------------------------
     *求解图的最小生成树
     *  注意：在求最小生成树的时候，如果求的是权重值，有向图和无向图都一样。
     *        如果是求边集合，那么对于无向图只是求了一半的边，需要对最后求得的边集合遍历求取每个边的反向边。
     * Kruskal算法
     *  时间复杂度：O(M*logM) M为图中的边的数量，适用边比较少的场景
     *  实现思路：
     *      需要使用并查集这个工具
     *      1.总是从权重最小的边开始考虑，依次考察权重依次变大的边(按照权重从小到大的顺序访问边)
     *      2.当前的边要么进入最小生成树的集合，要么丢弃
     *      3.如果当前的边进入最小生成树的集合中不会形成环，就要当前边
     *      4.如果当前的边进入最小生成树的集合中会形成环，就不要当前边
     *      5.考察完所有边之后，最小生成树的集合也得到了
     *  代码：com.lg.datastructure.graph.MinimumSpanningTree#kruskal()
     * Prim算法
     *  实现思路：
     *      1.可以从任意节点出发来寻找最小生成树
     *      2.某个点加入到被选取的点中后，解锁这个点出发的所有新的边
     *      3.在所有解锁的边中选最小的边，然后看看这个边会不会形成环
     *      4.如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3）
     *      5.如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
     *      6.当所有点都被选取，最小生成树就得到了
     *  代码：com.lg.datastructure.graph.MinimumSpanningTree#prim()
     *-------------------------------------------------------------
     *-------------------------------------------------------------
     *Dijkstra算法
     * 算最短路径的，算法解决的是有向图中单个源接点到其他顶点的最短路径问题
     * 所谓的最短是指权重和最小
     *  1.Dijkstra算法必须指定一个源点
     *  2.生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到
     *    自己的最小距离为0，源点到其他所有点的最小距离都为正无穷大
     *  3.从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新
     *    源点到各个点的最小距离表，不断重复这一步
     *  4.源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
     * 代码：com.lg.datastructure.graph.MinimumDistance#dijkstra()
     * 代码：com.lg.datastructure.graph.MinimumDistance2#dijkstra()
     */

    /*
     *图的面试题如何搞定
     * 图的算法都不算太难，只不过coding的代价比较高
     *  1）先用自己最熟悉的方式，实现图结构的表达
     *  2）在自己熟悉的结构上，实现所有常用的图算法作为模板
     *  3）把面试题提供的图结构转化为自己熟悉的图结构，再套用算法模板或者改写即可
     */

}
