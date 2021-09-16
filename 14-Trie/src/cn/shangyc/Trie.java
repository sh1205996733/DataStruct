package cn.shangyc;

import java.util.HashMap;
import java.util.Map;

public class Trie<V> {
	private int size;
	private Node<V> root = new Node<>();
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		size = 0;
		root.children.clear(); 
	}

	public V get(String key) {
		Node<V> node = node(key);
		return node == null? null:node.value;
	}

	public boolean contains(String key) {
		return node(key) != null;
	}
	
	private Node<V> node(String key) {
		keyCheck(key);
		Node<V> node = root;
		int length = key.length();
		for (int i = 0; i < length; i++) {
			char c = key.charAt(i);
			node = node.getChildren().get(c);
		}
		return null;
	}
	
	public V add(String key, V value) {
		keyCheck(key);
		//TODO
		return null;
	}
	
	public V remove(String key) {
		//TODO
		return null;
	}
	
	public boolean startsWith(String prefix) {
		//TODO
		return false;
	}
	
	private void keyCheck(String key) {
		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException("key must not be empty");
		}
	}
	
	private static class Node<V> {
		Map<Character, Node<V>> children;
		V value;
		boolean word; // 是否为单词的结尾（是否为一个完整的单词）
		
		public Map<Character, Node<V>> getChildren() {
			return children == null? new HashMap<>():children;
		}
		
	}
}
