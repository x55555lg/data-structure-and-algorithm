package com.lg.datastructure.stack;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 两个队列实现一个栈
 *
 * @author Xulg
 * Created in 2020-09-23 15:40
 */
public class TwoQueueStack<Item> implements SimpleStack<Item> {

    private Queue<Item> queue = new LinkedList<>();
    private Queue<Item> helper = new LinkedList<>();

    @Override
    public void push(Item item) {
        queue.offer(item);
    }

    @Override
    public Item pop() {
        // 将queue中的数据挪到helper中，只留下最后一个
        while (queue.size() > 1) {
            helper.offer(queue.poll());
        }
        // queue中留着的最后一个就是最近加进去的元素
        Item item = queue.poll();
        // queue和helper互换
        Queue<Item> temp = queue;
        queue = helper;
        helper = temp;
        return item;
    }

    public Item peek() {
        // 将queue中的数据挪到helper中，只留下最后一个
        while (queue.size() > 1) {
            helper.offer(queue.poll());
        }
        // queue中留着的最后一个就是最近加进去的元素
        Item item = queue.poll();
        // 将拿出的数据重新放回去
        helper.offer(item);
        // queue和helper互换
        Queue<Item> temp = queue;
        queue = helper;
        helper = temp;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Nonnull
    @Override
    public Iterator<Item> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        SimpleStack<String> push = new TwoQueueStack<>();
        push.push("1");
        push.push("2");
        push.push("3");

        System.out.println(push.pop());
        System.out.println(push.pop());
        System.out.println(push.pop());
    }
}
