package cn.shangyc.union;

/**
 * Quick Union - 基于rank的优化 - 路径减半(Path Halving) (推荐)
 * @author Administrator
 *
 */
public class UnionFind_QU_R_PH extends UnionFind_QU_R {

	public UnionFind_QU_R_PH(int capacity) {
		super(capacity);
	}
	/**
	 * 路径减半：使路径上每隔一个节点就指向其祖父节点（parent的parent）
	 */
	public int find(int v) {
		rangeCheck(v);
		while (v != parents[v]) {
			parents[v] = parents[parents[v]];
			v = parents[v];
		}
		return v;
	}
}
