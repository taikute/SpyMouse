package com.badlogic.spymouse.helper;

import java.util.NoSuchElementException;

public final class Deque<T> {
    private static class Node<T> {
        private final T data;
        private Node<T> next;
        private Node<T> prev;
        
        private Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    
    private Node<T> head;
    private Node<T> tail;
    
    public Deque() {
        clear();
    }
    
    public void clear() {
        head = tail = null;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public boolean notEmpty() {
        return head != null;
    }
    
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T removeItem = head.data;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        return removeItem;
    }
    
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T removeItem = tail.data;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        return removeItem;
    }
    
    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }
    
    public void addLast(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
    }
    
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return head.data;
    }
    
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return tail.data;
    }
    
    public Deque<T> copy() {
        Deque<T> copyDeque = new Deque<>();
        Node<T> current = head;
        
        while (current != null) {
            copyDeque.addLast(current.data);
            current = current.next;
        }
        return copyDeque;
    }
}
