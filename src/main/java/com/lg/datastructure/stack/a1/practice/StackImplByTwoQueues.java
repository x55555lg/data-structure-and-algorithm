package com.lg.datastructure.stack.a1.practice;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 2个队列实现的栈
 *
 * @author Xulg
 * Created in 2021-01-21 9:51
 */
class StackImplByTwoQueues<Item> implements SimpleStack<Item> {

    private Queue<Item> queue;
    private Queue<Item> helper;

    public StackImplByTwoQueues() {
        this.queue = new LinkedList<>();
        this.helper = new LinkedList<>();
    }

    @Override
    public void push(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can not be null");
        }
        queue.add(item);
    }

    @Override
    public Item pop() {
        while (queue.size() > 1) {
            helper.add(queue.poll());
        }
        Item item = queue.poll();
        //noinspection ConstantConditions
        if (true) {
            // 交换2个队列，保证queue变量是一直存储数据的
            Queue<Item> temp = this.queue;
            queue = helper;
            helper = temp;
        } else {
            // 这种写法太低效了
            while (!helper.isEmpty()) {
                this.queue.add(helper.poll());
            }
        }

        // the result
        return item;
    }

    @Override
    public Item peek() {
        while (queue.size() > 1) {
            helper.add(queue.poll());
        }
        Item item = queue.poll();

        // 将拿出的数据重新放回去
        helper.add(item);

        // 交换2个队列，保证queue变量是一直存储数据的
        Queue<Item> temp = this.queue;
        queue = helper;
        helper = temp;

        // the result
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

    public static void main(String[] args) {
        StackImplByTwoQueues<Integer> stack = new StackImplByTwoQueues<>();
        stack.push(1);
        stack.push(2);
        System.out.println(stack.pop());
        stack.push(3);
        stack.push(4);
        System.out.println(stack.pop());
        stack.push(5);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
