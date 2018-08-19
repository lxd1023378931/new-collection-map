package com.uzak.list;

import java.io.Serializable;

/**
 * Created by liangxiudou on 2018/8/19.
 */
public abstract class List<E> implements Iterable<E>, Serializable {
    public abstract void clear();

    public abstract int size();

    public abstract boolean isEmpty();

    public abstract E get(int index);

    public abstract E set(int index, E newObj);

    public abstract void add(E newObj);

    public abstract void add(int index, E newObj);

    public abstract E remove(int index);

    /**
     * 删除所有值为e的节点
     * 注：如果泛型E为Integer类型，则传入参数需为new Integer(e)，否则将调用remove(int index)方法
     *
     * @param e
     * @return
     */
    public abstract boolean remove(E e);

    public abstract boolean contains(E e);

    public abstract Object[] toArray();

    /**
     * 栈
     *
     * @param <E>
     */
    public abstract class Stack<E> {
        /**
         * 向栈顶加入元素
         *
         * @param e
         */
        public abstract void push(E e);

        /**
         * 判断栈顶是否有可用元素
         *
         * @return
         */
        public abstract boolean next();

        /**
         * 获取栈顶元素
         *
         * @return
         */
        public abstract E pop();
    }

    /**
     * 队列
     *
     * @param <E>
     */
    public abstract class Queue<E> {
        /**
         * 向队列添加元素
         *
         * @param e
         */
        public abstract void enqueue(E e);

        /**
         * 判断队首是否有可用元素
         *
         * @return
         */
        public abstract boolean next();

        /**
         * 获取队首元素
         *
         * @return
         */
        public abstract E dequeue();

    }
}
