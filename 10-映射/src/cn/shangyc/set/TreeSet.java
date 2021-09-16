package cn.shangyc.set;

import cn.shangyc.map.Map;
import cn.shangyc.map.TreeMap;

//使用TreeMap来实现集合
public class TreeSet<E> implements Set<E> {
	Map<E, Object> map = new TreeMap<>(); 
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean contains(E element) {
		return map.containsValue(element);
	}

	@Override
	public void add(E element) {
		map.put(element, null);
	}

	@Override
	public void remove(E element) {
		map.remove(element);
	}

	@Override
	public void traversal(Visitor<E> visitor) {
		map.traversal(new Map.Visitor<E,Object>() {
			@Override
			public boolean visit(E key, Object value) {
				return visitor.visit(key);
			}
		});
	}

}
