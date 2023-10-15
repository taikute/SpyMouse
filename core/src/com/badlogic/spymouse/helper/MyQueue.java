package com.badlogic.spymouse.helper;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public final class MyQueue<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node<T> first;
    private Node<T> last;
    
    public MyQueue() {
        clear();
    }
    
    public T first() {
        return first.data;
    }
    
    public T last() {
        return last.data;
    }
    
    public boolean isEmpty() {
        return first == null;
    }
    
    public boolean notEmpty() {
        return first != null;
    }
    
    public void clear() {
        first = null;
        last = null;
    }
    
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        T removeItem = first();
        first = first.next;
        if (first == null) {
            last = null;
        }
        return removeItem;
    }
    
    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
    }
    
    public LinkedList<T> toList() {
        LinkedList<T> list = new LinkedList<>();
        Node<T> current = first;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
}
