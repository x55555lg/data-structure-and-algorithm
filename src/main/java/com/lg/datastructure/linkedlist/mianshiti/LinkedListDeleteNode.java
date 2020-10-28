package com.lg.datastructure.linkedlist.mianshiti;

/**
 * 删除链表的节点
 *
 * @author Xulg
 * Created in 2020-10-27 19:22
 */
class LinkedListDeleteNode {

    /*
     * 能不能不给单链表的头节点，只给想要删除的节点，就能做到在链表上把这个节点删掉？
     *抖机灵的做法：
     *      将待删除的节点数据和该节点的next节点，进行数据互换。然后将next删除
     *  删除节点：
     *          ②
     *  原链表：
     *          ①→②→③
     *              ↑deleteNode
     *  交换②③节点的value之后：
     *          ①→③→②
     *              ↑deleteNode
     *  删除②节点
     *          ①→③
     *              ↑deleteNode
     *
     *这种做法错误的，因为②节点的引用还在
     */

    public static void deleteNode(Node deleteNode) {
        if (deleteNode == null || deleteNode.next == null) {
            return;
        }
        // 交换deleteNode和它的next的value
        Node next = deleteNode.next;
        int temp = deleteNode.value;
        deleteNode.value = next.value;
        next.value = temp;

        // 删除这个next节点
        deleteNode.next = next.next;
    }

    private static class Node {
        int value;
        Node next;

        Node(int val) {
            value = val;
        }
    }
}
