package com.lg.datastructure.stack.a1.practice;

/**
 * 数组实现的栈
 *
 * @author Xulg
 * Created in 2021-01-21 9:51
 */
@SuppressWarnings("unchecked")
class StackImplByArray<Item> implements SimpleStack<Item> {

    private final Object[] table;
    private int count;

    public StackImplByArray(int capacity) {
        this.table = new Object[capacity];
        count = 0;
    }

    @Override
    public void push(Item item) throws IllegalStateException {
        if (count >= table.length) {
            throw new IllegalStateException("栈已经满了");
        }
        table[count++] = item;
    }

    @Override
    public Item pop() throws IllegalStateException {
        if (count <= 0) {
            throw new IllegalStateException("栈已经空了");
        }
        Object item = table[--count];
        table[count] = null;
        return (Item) item;
    }

    @Override
    public Item peek() {
        if (count <= 0) {
            throw new IllegalStateException("栈已经空了");
        }
        Object item = table[count - 1];
        return (Item) item;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    public static void main(String[] args) {
        StackImplByArray<Integer> stack = new StackImplByArray<>(5);
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
