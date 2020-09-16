package com.lg.datastructure.bag;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * 基于数组实现简单的包结构
 *
 * @author Xulg
 * Created in 2020-09-16 16:39
 */
class SimpleArrayBag<Item> implements SimpleBag<Item> {

    private Item[] table;

    private int size;

    public SimpleArrayBag(int capacity) {
        //noinspection unchecked
        table = (Item[]) new Object[capacity];
    }

    @Override
    public void add(Item item) {
        if (table.length == size) {
            resize(table.length * 2);
        }
        table[size++] = item;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Nonnull
    @Override
    public Iterator<Item> iterator() {
        return new BagIterator();
    }

    private void resize(int maxSize) {
        @SuppressWarnings("unchecked")
        Item[] newTable = (Item[]) new Object[maxSize];
        //noinspection ManualArrayCopy
        for (int i = 0; i < size; i++) {
            newTable[i] = table[i];
        }
        table = newTable;
    }

    private class BagIterator implements Iterator<Item> {

        private int cursor = size - 1;

        @Override
        public boolean hasNext() {
            return cursor >= 0;
        }

        @Override
        public Item next() {
            return table[cursor--];
        }
    }

    public static void main(String[] args) {
        SimpleArrayBag<String> stack = new SimpleArrayBag<>(3);
        stack.add("a");
        stack.add("b");
        stack.add("c");
        stack.add("d");
        stack.add("e");
        stack.add("f");

        for (String s : stack) {
            System.out.println(s);
        }

    }
}
