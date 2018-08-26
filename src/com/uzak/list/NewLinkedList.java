package com.uzak.list;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 自定义链式集合（双向链表）
 * <p>
 * Created by liangxiudou on 2018/8/19.
 */
public class NewLinkedList<E> implements List<E>, Iterable<E>, Serializable {
    private Node head;
    private Node foot;
    private int size;

    /**
     * 空链表
     */
    public NewLinkedList() {
        this.head = new Node(null, null, null);
        this.foot = new Node(null, null, null);
        clear();
    }

    public NewLinkedList(List<E> list) {
        this.head = new Node(null, null, null);
        this.foot = new Node(null, null, null);
        clear();
        for (int i = 0; i < list.size(); i++) {
            add((E) list.get(i));
        }
    }

    @Override
    public void clear() {
        this.head.setNext(this.foot);
        this.foot.setPrev(this.head);
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 正向取第index个元素
     *
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        return get(index, false).getE();
    }

    /**
     * 反向取第index个元素
     *
     * @param index
     * @return
     */
    public E getPrev(int index) {
        return get(index, true).getE();
    }

    /**
     * 取第index个元素，true 反向取 false 正向取
     *
     * @param index
     * @param fromFoot
     * @return
     */
    public Node get(int index, boolean fromFoot) {
        isLegal(index);
        Node start = fromFoot ? this.foot : this.head;
        for (int i = 0; i <= index; i++) {
            start = fromFoot ? start.getPrev() : start.getNext();
        }
        return start;
    }

    /**
     * 判断正反向取所需遍历的元素个数，以最小个数方向取正向第index个元素
     *
     * @param index
     * @return
     */
    public Node getQucik(int index) {
        isLegal(index);
        return index + 1 <= this.size / 2 ? get(index, false) : get(this.size - 1 - index, true);
    }

    /**
     * 修改正向第index个元素的值
     *
     * @param index
     * @param newObj
     * @return
     */
    @Override
    public E set(int index, E newObj) {
        Node node = getQucik(index);
        E e = node.getE();
        node.setE(newObj);
        return e;
    }

    @Override
    public void add(E e) {
        Node node = new Node(e, null, null);
        this.foot.prev.setNext(node);
        node.setPrev(this.foot.prev);
        node.setNext(this.foot);
        this.foot.setPrev(node);
        this.size++;
    }

    /**
     * 在第正向index个元素位置插入元素
     *
     * @param index
     * @param e
     */
    @Override
    public void add(int index, E e) {
        Node node = new Node(e, null, null);
        Node nodeIndex = getQucik(index);
        nodeIndex.getPrev().setNext(node);
        nodeIndex.setPrev(node);
        node.setNext(nodeIndex);
        node.setPrev(nodeIndex.getPrev());
        this.size++;
    }

    @Override
    public E remove(int index) {
        Node node = getQucik(index);
        remove(node);
        return node.getE();
    }

    /**
     * 删除所有值为e的节点
     * 如果泛型E为Integer类型，则传入参数需为new Integer(e)，否则将调用remove(int index)
     *
     * @param e
     * @return
     */
    @Override
    public boolean remove(E e) {
        Node node = this.head.getNext();
        while (node != this.foot) {
            if (e == null) {
                if (node.getE() == null) {
                    remove(node);
                }
            } else if (e.equals(node.getE())) {
                remove(node);
            }
            node = node.getNext();
        }
        return true;
    }

    public boolean remove(Node node) {
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        this.size--;
        return true;
    }

    @Override
    public boolean contains(E e) {
        Node node = this.head.getNext();
        while (node != this.foot) {
            if (e == null) {
                if (node.getE() == null) {
                    return true;
                }
            } else if (e.equals(node.getE())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node start = this.head.getNext();
        while (start != this.foot) {
            sb.append(String.valueOf(start.getE()));
            start = start.getNext();
            if (start != this.foot) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];
        Node start = this.head.getNext();
        int i = 0;
        while (start != this.foot) {
            array[i] = start.getE();
            start = start.getNext();
            i++;
        }
        return array;
    }

    private void isLegal(int index) {
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private class Node {
        private E e;
        private Node next;
        private Node prev;

        public Node(E e, Node next, Node prev) {
            this.e = e;
            this.next = next;
            this.prev = prev;
        }

        public E getE() {
            return e;
        }

        public void setE(E e) {
            this.e = e;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    /**
     * 获取正向迭代器对象
     *
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return new NewLinkListASCIterator();
    }

    /**
     * 获取反向迭代器对象
     *
     * @return
     */
    public Iterator<E> iteratorDesc() {
        return new NewLinkListDESCIterator();
    }

    /**
     * 正向迭代器
     */
    private class NewLinkListASCIterator implements Iterator {
        private Node node = head.getNext();

        @Override
        public boolean hasNext() {
            return node != foot;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = (E) node.getE();
            node = node.getNext();
            return e;
        }
    }

    /**
     * 反向迭代器
     */
    private class NewLinkListDESCIterator implements Iterator {
        private Node node = foot.getPrev();

        @Override
        public boolean hasNext() {
            return node != head;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = (E) node.getE();
            node = node.getPrev();
            return e;
        }
    }

    /**
     * 链表栈
     * (new NewArrayList()).new ArrayStack()
     */
    public class LinkedStack implements Stack<E> {
        @Override
        public void push(E e) {
            add(e);
        }

        @Override
        public E pop() {
            if (isEmpty()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            Node node = get(0, true);
            remove(node);
            return node.getE();
        }

        @Override
        public boolean next() {
            return !isEmpty();
        }

        @Override
        public String toString() {
            return NewLinkedList.this.toString();
        }
    }

    /**
     * 链表队列
     * (new NewArrayList()).new ArrayStack()
     */
    public class LinkedQueue implements Queue<E> {
        @Override
        public void enqueue(E e) {
            add(e);
        }

        @Override
        public E dequeue() {
            if (isEmpty()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            E e = get(0);
            remove(0);
            return e;
        }

        @Override
        public boolean next() {
            return !isEmpty();
        }

        @Override
        public String toString() {
            return NewLinkedList.this.toString();
        }
    }
}
