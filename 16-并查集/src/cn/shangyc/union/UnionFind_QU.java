package cn.shangyc.union;

public class UnionFind_QU extends UnionFind {
	
	public UnionFind_QU(int capacity) {
		super(capacity);
	}
	
	/**
	 * 通过parent链条不断地向上找，直到找到根节点 O(logn)
	 */
	public int find(int v) {
		rangeCheck(v);
		while (v != parents[v]) {//自己指向自己的节点即为根节点
			v = parents[v];
		}
		return v;
	}
	
	/**
	 * 将v1的根节点嫁接到v2的根节点上 O(logn)
	 */
	public void union(int v1, int v2) {
		int p1 = find(v1); 
		int p2 = find(v2);
		if (p1 == p2) return;
		parents[p1] = p2;
	}

}
