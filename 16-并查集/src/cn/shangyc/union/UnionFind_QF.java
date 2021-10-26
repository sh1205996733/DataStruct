package cn.shangyc.union;

public class UnionFind_QF extends UnionFind {
	
	public UnionFind_QF(int capacity) {
		super(capacity);
	}
	
	/**
	 * 父节点就是根节点 O()
	 */
	public int find(int v) {
		rangeCheck(v);
		return parents[v];
	}
	
	/**
	 * 将v1所在集合的所有元素，都嫁接到v2的父节点上 O(n)
	 */
	public void union(int v1, int v2) {
		int p1 = find(v1);
		int p2 = find(v2);
		if (p1 == p2) return;//v1 == v2 直接返回
		for (int i = 0; i < parents.length; i++) {
			if (parents[i] == p1) {//错误写法parents[i] == parents[v1],parents[v1]会被改变
				parents[i] = p2;
			}
		}
	}

}
