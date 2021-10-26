package cn.shangyc;

import java.util.HashMap;
import java.util.Map;

public class Trie<V> {
	private int size;//单词数
	private Node<V> root;
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		size = 0;
		root = null; 
	}

	public V get(String key) {
		Node<V> node = node(key);
		return node != null && node.word ? node.value:null;
	}

	public boolean contains(String key) {
		Node<V> node = node(key);
		return node != null && node.word;
	}
	
	private Node<V> node(String key) {
		keyCheck(key);
		Node<V> node = root;
		int length = key.length();
		for (int i = 0; i < length; i++) {
			if (node == null || node.children == null || node.children.isEmpty()) {
				return null;
			}
			char c = key.charAt(i);
			node = node.children.get(c);
		}
		return node;
	}
	
	public V add(String key, V value) {
		keyCheck(key);
		// 创建根节点
		if (root == null) {
			root =  new Node<V>(null);
		}
		int length = key.length();
		Node<V> node = root;
		/**
		 * 遍历key的每一个字节，如果的node的children==null或者node.children.get(c)不存在 就新增一个childNode，
		 * 存在就node = childNode
		 */
		for (int i = 0; i < length; i++) {
			char c = key.charAt(i);
			boolean emptyChildren = node.children == null;
			Node<V> childNode = emptyChildren ? null : node.children.get(c);
			if (childNode == null){//node.children == null 或者node.children.get(c)不存在
				childNode = new Node<>(node);
				childNode.character = c;
				node.children = emptyChildren ? new HashMap<>() : node.children;
				node.children.put(c, childNode);
			}
			node = childNode;
		}
		if (node.word) {// 已经存在这个单词,覆盖
			V old = node.value;
			node.value = value;
			return old;
		}
		// 新增一个单词
		node.word = true;
		node.value = value;
		size++;
		return null;
	}
	
	public V remove(String key) {
		// 找到最后一个节点
		Node<V> node = node(key);
		// 如果不是单词结尾，不用作任何处理
		if (node == null || !node.word) {
			return null;
		}
		V oldValue = node.value;
		size--;
		// 如果还有子节点,word置为false，value置空 然后直接返回
		if (node.children!= null && !node.children.isEmpty()) {
			node.word = false;
			node.value = null;
			return oldValue;
		}
		// 如果没有子节点，从下往上遍历，看其父节点是否还有其他子节点
		Node<V> parent = null;
		while ((parent = node.parent) != null) {
			parent.children.remove(node.character);
			if (parent.word || !parent.children.isEmpty()) {//如果parent.children仍不为空或者parent.word是一个单词就直接break
				break;
			}
			node = parent;
		}
		return oldValue;
	}
	
	public boolean startsWith(String prefix) {
		return node(prefix) != null;
	}
	
	private void keyCheck(String key) {
		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException("key must not be empty");
		}
	}
	
	private static class Node<V> {
		private Node<V> parent;//节点的父节点
		private Map<Character, Node<V>> children;//当前节点上所有节点
		private Character character;//节点上的每个字符
		private V value;//如果是单词的结尾（存储一个完整的单词）
		boolean word; // 是否为单词的结尾（是否为一个完整的单词）
		
		public Node(Node<V> parent) {
			this.parent = parent;
		}
	}
}
