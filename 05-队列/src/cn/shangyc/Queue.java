package cn.shangyc;

import cn.shangyc.list.LinkedList;
import cn.shangyc.list.List;

public class Queue<E> {
	private List<E> list = new LinkedList<>();

	public int size() { // 元素的数量
		return list.size();
	}

	public boolean isEmpty() { // 是否为空
		return list.isEmpty();
	}

	public void clear() { // 清空
		list.clear();
	}

	public void enQueue(E element) { // 入队
		list.add(element);
	}

	public E deQueue() { // 出队
		return list.remove(0);
	}

	public E front() { // 获取队列的头元素
		return list.get(0);
	}
}
