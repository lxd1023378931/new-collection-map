package com.uzak.list;

import java.io.Serializable;

/**
 * Created by liangxiudou on 2018/8/19.
 */
public interface List<E> {
    public void clear();

    public int size();

    public boolean isEmpty();

    public E get(int index);

    public E set(int index, E newObj);

    public void add(E newObj);

    public void add(int index, E newObj);

    public E remove(int index);

    /**
     * 删除所有值为e的节点
     * 注：如果泛型E为Integer类型，则传入参数需为new Integer(e)，否则将调用remove(int index)方法
     *
     * @param e
     * @return
     */
    public boolean remove(E e);

    public boolean contains(E e);

    public Object[] toArray();

    /**
     * 栈
     *
     * @param <E>
     */
    public interface Stack<E> {
        /**
         * 向栈顶加入元素
         *
         * @param e
         */
        public void push(E e);

        /**
         * 判断栈顶是否有可用元素
         *
         * @return
         */
        public boolean next();

        /**
         * 获取栈顶元素
         *
         * @return
         */
        public E pop();
    }

    /**
     * 队列
     *
     * @param <E>
     */
    public interface Queue<E> {
        /**
         * 向队列添加元素
         *
         * @param e
         */
        public void enqueue(E e);

        /**
         * 判断队首是否有可用元素
         *
         * @return
         */
        public boolean next();

        /**
         * 获取队首元素
         *
         * @return
         */
        public E dequeue();

    }
}
