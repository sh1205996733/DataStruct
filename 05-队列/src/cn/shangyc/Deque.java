package cn.shangyc;

import cn.shangyc.list.LinkedList;
import cn.shangyc.list.List;

//双端队列
public class Deque<E> {
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

	public void enQueueFront(E element) { // 头部入队
		list.add(0,element);
	}
	public void enQueueRear(E element) { // 尾部入队
		list.add(element);
	}

	public E deQueueFront() { // 头部出队
		return list.remove(0);
	}
	public E deQueueRear() { // 尾部出队
		return list.remove(list.size()-1);
	}

	public E front() { // 获取队列的头元素
		return list.get(0);
	}
	public E rear() { // 获取队列的尾元素
		return list.get(list.size()-1);
	}
}
