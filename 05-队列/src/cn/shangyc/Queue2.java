package cn.shangyc;

import java.util.Stack;

/**
 * 栈实现队列
 */
public class Queue2<E> {
	private Stack<E> inStack = new Stack<>();
	private Stack<E> outStack = new Stack<>();
//
//	public int size() { // 元素的数量
//		return inStack.size() + outStack.size();
//	}
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty() { // 是否为空
		return inStack.isEmpty() && outStack.isEmpty();
	}
//
//	public void clear() { // 清空
//		inStack.clear();
//		outStack.clear();
//	}

	public void enQueue(E element) { // 入队
		inStack.push(element);
	}

	public E deQueue() { // 出队
		if (inStack.isEmpty() && outStack.isEmpty()) return null;
		if (outStack.size() > 0) {
			return outStack.pop();
		}else {
			while (!inStack.isEmpty()) {
				outStack.push(inStack.pop());
			}
			return outStack.pop();
		}
	}

	public E front() { // 获取队列的头元素
		if (inStack.isEmpty() && outStack.isEmpty()) return null;
		if (outStack.size() > 0) {
			return outStack.peek();
		}else {
			while (!inStack.isEmpty()) {
				outStack.push(inStack.pop());
			}
			return outStack.peek();
		}
	}
}
