package cn.shangyc.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ◼ 图由顶点（vertex）和边（edge）组成，通常表示为 G = (V, E)
 * 	G表示一个图，V是顶点集，E是边集
 * 	顶点集V有穷且非空
 * 	任意两个顶点之间都可以用边来表示它们之间的关系，边集E可以是空的
 *
 */
public abstract class Graph<V,E> {//V 顶点类型，权值类型
	//图的基础接口
	public abstract int edgesSize();//获取边数
	public abstract int verticesSize();//获取定点数
	
	public abstract void addVertex(V v);//添加定点
	public abstract void addEdge(V from, V to);//添加from->to边
	public abstract void addEdge(V from, V to, E weight);//添加from->to边带权值
	
	public abstract void removeVertex(V v);//删除顶点
	public abstract void removeEdge(V from, V to);//删除from->to这条边
	
	public abstract void bfs(V begin, VertexVisitor<V> visitor);//广度优先遍历
	public abstract void dfs(V begin, VertexVisitor<V> visitor);//深度优先遍历
	
	public abstract List<V> topologicalSort();//拓扑排序
	
	public abstract Set<EdgeInfo<V, E>> mst();//最小生成树
	protected WeightManager<E> weightManager;//权重比较器 搭配mst方法使用
	
	public abstract Map<V, E> shortestPath0(V begin);//单源最短路径
	public abstract Map<V, PathInfo<V, E>> shortestPath(V begin);//单源最短路径
	public abstract Map<V, Map<V, PathInfo<V, E>>> shortestPath();//多源最短路径
	
	public Graph() {}
	
	public Graph(WeightManager<E> weightManager) {
		this.weightManager = weightManager;
	}
	public interface WeightManager<E> {
		int compare(E w1, E w2);
		E add(E w1, E w2);
		E zero();
	}
	
	public interface VertexVisitor<V> {
		boolean visit(V v);
	}
	public static class EdgeInfo<V, E> {
		private V from;
		private V to;
		private E weight;
		public EdgeInfo(V from, V to, E weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		public V getFrom() {
			return from;
		}
		public void setFrom(V from) {
			this.from = from;
		}
		public V getTo() {
			return to;
		}
		public void setTo(V to) {
			this.to = to;
		}
		public E getWeight() {
			return weight;
		}
		public void setWeight(E weight) {
			this.weight = weight;
		}
		@Override
		public String toString() {
			return "EdgeInfo [from=" + from + ", to=" + to + ", weight=" + weight + "]";
		}
	}
	
	public static class PathInfo<V, E> {
		protected E weight;
		protected List<EdgeInfo<V, E>> edgeInfos = new LinkedList<>();
		public PathInfo() {}
		public PathInfo(E weight) {
			this.weight = weight;
		}
		public E getWeight() {
			return weight;
		}
		public void setWeight(E weight) {
			this.weight = weight;
		}
		public List<EdgeInfo<V, E>> getEdgeInfos() {
			return edgeInfos;
		}
		public void setEdgeInfos(List<EdgeInfo<V, E>> edgeInfos) {
			this.edgeInfos = edgeInfos;
		}
		@Override
		public String toString() {
			return "PathInfo [weight=" + weight + ", edgeInfos=" + edgeInfos + "]";
		}
	}
}
