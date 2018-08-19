package com.uzak.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 自定义数组集合
 * Created by liangxiudou on 2018/8/19.
 */
public class NewArrayList<E> extends List<E> {
    /**
     * 默认扩展大小
     */
    private static final int DEFAULT_SIZE = 10;
    /**
     * 默认空队列
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};
    /**
     * 集合元素
     */
    private Object[] elementData;
    /**
     * 集合当前元素数量
     */
    private int size;
    /**
     * 集合当前实际大小
     */
    private int maxSize;

    public NewArrayList() {
        this.elementData = EMPTY_ELEMENTDATA;
        this.size = 0;
        this.maxSize = 0;
    }

    /**
     * 指定初始化大小的集合
     *
     * @param size
     */
    public NewArrayList(int size) {
        copyBiggerElementData(size);
    }

    /**
     * 初始化一个集合并将传入的集合元素传入
     *
     * @param list
     */
    public NewArrayList(List<E> list) {
        this.elementData = new Object[list.size()];
        this.size = list.size();
        this.maxSize = list.size();
        //赋值
        for (int i = 0; i < this.size; i++) {
            this.elementData[i] = list.get(i);
        }
    }

    @Override
    public void clear() {
        this.elementData = EMPTY_ELEMENTDATA;
        this.size = 0;
        this.maxSize = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public E get(int index) {
        isLegal(index);
        return (E) this.elementData[index];
    }

    @Override
    public E set(int index, E newObj) {
        Object oldObj = null;
        isLegal(index);
        oldObj = this.elementData[index];
        this.elementData[index] = newObj;
        return (E) oldObj;
    }

    @Override
    public void add(E newObj) {
        if (this.size + 1 >= this.maxSize) {
            copyBiggerElementData(0);
        }
        this.elementData[this.size] = newObj;
        this.size++;
    }

    /**
     * 指定位置插入元素
     *
     * @param index
     * @param newObj
     */
    @Override
    public void add(int index, E newObj) {
        if (index < 0 || index > this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (this.size + 1 == this.maxSize) {
            copyBiggerElementData(0);
        }
        this.size++;
        Object[] tempElementData = this.elementData;
        this.elementData = new Object[this.maxSize];
        for (int i = 0; i < this.size; i++) {
            if (i < index) {
                this.elementData[i] = (E) tempElementData[i];
            } else if (i == index) {
                this.elementData[index] = newObj;
            } else {
                this.elementData[i] = (E) tempElementData[i - 1];
            }
        }
    }

    @Override
    public E remove(int index) {
        isLegal(index);
        Object o = get(index);
        for (int i = index; i < this.size - 1; i++) {
            this.elementData[i] = this.elementData[i + 1];
        }
        this.elementData[this.size - 1] = null;
        this.size--;
        return (E) o;
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
        Integer[] index = new Integer[this.size];
        if (contains(e)) {
            for (int i = 0, j = 0; i < this.size; i++) {
                if (e == null) {
                    if (get(i) == null) {
                        index[j] = i;
                        j++;
                    } else {
                        continue;
                    }
                } else if (e.equals(get(i))) {
                    index[j] = i;
                    j++;
                }
            }
        }
        int n = 0;
        for (Integer i : index) {
            if (i != null) {
                remove(i - n);
                n++;
            }
        }
        return true;
    }

    @Override
    public boolean contains(E e) {
        for (int i = 0; i < this.size; i++) {
            if (e == null) {
                if (get(i) == null) {
                    return true;
                } else {
                    continue;
                }
            } else if (e.equals(get(i))) {
                return true;
            }
        }
        return false;
    }

    private void isLegal(int index) {
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            array[i] = this.elementData[i];
        }
        return array;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for (int i = 0; i < this.size; i++) {
            sb.append(String.valueOf(this.elementData[i]));
            if (i != this.size - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 扩展数组大小，如果newSize小于DEFAULT_SIZE则一次性扩展DEFAULT_SIZE
     *
     * @param newSize
     */
    private void copyBiggerElementData(int newSize) {
        if (newSize < DEFAULT_SIZE) {
            newSize = DEFAULT_SIZE;
        }
        Object[] tempElementData = this.elementData;
        this.elementData = new Object[this.size + newSize];
        this.maxSize = this.size + newSize;
        if (tempElementData == null) {
            return;
        }
        for (int i = 0; i < this.size; i++) {
            this.elementData[i] = tempElementData[i];
        }
    }

    /**
     * 获取迭代器对象
     *
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return new NewArrayListIterator();
    }

    /**
     * 自定义迭代器
     */
    private class NewArrayListIterator implements Iterator {
        private int currentSize = 0;

        @Override
        public boolean hasNext() {
            return currentSize < size();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (E) elementData[currentSize++];
        }
    }

    /**
     * 数组栈
     * (new NewArrayList()).new ArrayStack()
     */
    public class ArrayStack extends Stack<E> {
        @Override
        public void push(E e) {
            add(e);
        }

        @Override
        public E pop() {
            if (isEmpty()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            E e = get(size - 1);
            remove(size - 1);
            return e;
        }

        @Override
        public boolean next() {
            return !isEmpty();
        }

        @Override
        public String toString() {
            return NewArrayList.this.toString();
        }
    }

    /**
     * 数组队列
     * (new NewArrayList()).new ArrayStack()
     */
    public class ArrayQueue extends Queue<E> {
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
            return NewArrayList.this.toString();
        }
    }
}
