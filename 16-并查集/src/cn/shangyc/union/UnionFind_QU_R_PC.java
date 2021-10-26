package cn.shangyc.union;

/**
 * Quick Union - 基于rank的优化 - 路径压缩(Path Compression)
 * @author Administrator
 *
 */
public class UnionFind_QU_R_PC extends UnionFind_QU_R {

	public UnionFind_QU_R_PC(int capacity) {
		super(capacity);
	}
	/**
	 * 路径压缩使路径上的所有节点都指向根节点，所以实现成本稍高
	 */
	public int find(int v) {
		rangeCheck(v);
		if (v != parents[v]) {
			parents[v] = find(parents[v]);
		}
		return parents[v];
	}
}
