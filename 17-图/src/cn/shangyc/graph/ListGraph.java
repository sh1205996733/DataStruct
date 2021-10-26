package cn.shangyc.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import cn.shangyc.MinHeap;
import cn.shangyc.UnionFind;

@SuppressWarnings("unchecked")
//邻接表实现图
public class ListGraph<V,E> extends Graph<V, E> {
	private Map<V, Vertex<V, E>> vertices = new HashMap<>();//图中所有的顶点集合
	private Set<Edge<V, E>> edges = new HashSet<>();//图中所有的边集合
	//顶点的定义
	private static class Vertex<V, E> {
		V value;
		Set<Edge<V, E>> inEdges = new HashSet<>();//入度集合
		Set<Edge<V, E>> outEdges = new HashSet<>();//出度集合
		public Vertex(V value) {
			this.value = value;
		}
		@Override
		public boolean equals(Object obj) {
			return Objects.equals(value,((Vertex<V, E>)obj).value);
		}
		
		@Override
		public int hashCode() {
			return value == null ? 0 : value.hashCode();
		}
		
		@Override
		public String toString() {
			return value == null ? "null" : value.toString();
		}
		
	}
	//边的定义
	private static class Edge<V, E> {
		Vertex<V, E> from;//起点
		Vertex<V, E> to;//终点
		E weight;//权值
		
		public Edge(Vertex<V, E> from, Vertex<V, E> to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			return result;
			//return from.hashCode() * 31 + to.hashCode();
		}

		@Override
		public boolean equals(Object obj) {//起点和终点都相同才是相同边
			Edge<V, E> edge = (Edge<V, E>) obj;
			return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
		}

		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
		}

		public EdgeInfo<V, E> info() {
			return new EdgeInfo<V,E>(from.value,to.value,weight);
		}
	}
	
	@Override
	public int edgesSize() {
		return edges.size();
	}

	@Override
	public int verticesSize() {
		return vertices.size();
	}

	@Override
	public void addVertex(V v) {
		//添加逻辑：vertices集合中存在就返回，不存在就直接put,将v作为key,Vertex对象作为value
		if (vertices.containsKey(v)) return;
		vertices.put(v, new Vertex<>(v));
	}

	@Override
	public void addEdge(V from, V to) {
		addEdge(from, to, null);
	}

	@Override
	public void addEdge(V from, V to, E weight) {
		/**
		 * 添加带权边逻辑：
		 * 	1.先检查from、to顶点是否存在，不存在先添加定点
		 * 	2.删除from中outEdges集合、to中toEdges集合以及edges中的旧数据
		 * 	3.将新的边edge添加到from中outEdges集合、to中toEdges集合以及edges中
		 *  4.将新的边添加到edges中
		 */
		Vertex<V, E> fromVertex = vertices.get(from);
		if (fromVertex == null) {//获取from起点，不存在就创建
			fromVertex = new Vertex<>(from);
			vertices.put(from, fromVertex);
		}
		Vertex<V, E> toVertex = vertices.get(to);
		if (toVertex == null) {//获取to终点，不存在就创建
			toVertex = new Vertex<>(to);
			vertices.put(to, toVertex);
		}
		Edge<V, E> edge = new Edge<>(fromVertex, toVertex);//创建新边
		edge.weight = weight;
		if (fromVertex.outEdges.remove(edge)) {//删除成功说明边存在，继续删除toVertex.inEdges和edges中的edge。否则往下执行
			toVertex.inEdges.remove(edge);
			edges.remove(edge);
		}
		fromVertex.outEdges.add(edge);
		toVertex.inEdges.add(edge);
		edges.add(edge);
	}

	@Override
	public void removeVertex(V v) {
		/**
		 * 删除顶点逻辑：
		 * 	1.vertex中删除v
		 * 	2.删除以v为起点的所有边,即删除edges中在v的outEdges中的边，同时删除以v为起点的终点的inEdges中的边
		 * 	3.删除以v为终点的所有边,即删除edges中在v的inEdges中的边，同时删除以v为终点的起点的outEdges中的边
		 */
		Vertex<V, E> vertex = vertices.remove(v);//删除顶点v
		if (vertex == null) return;
		//删除以v为起点的所有边,即删除edges中在v的outEdges中的边，同时删除以v为起点的终点的inEdges中的边
		for (Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext();) {
			Edge<V, E> edge = iterator.next();
			edge.to.inEdges.remove(edge);
			// 将当前遍历到的元素edge从集合vertex.outEdges中删掉
			iterator.remove();
			edges.remove(edge);
		}
		//删除以v为终点的所有边,即删除edges中在v的inEdges中的边，同时删除以v为终点的起点的outEdges中的边
		for (Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator(); iterator.hasNext();) {
			Edge<V, E> edge = iterator.next();
			edge.from.outEdges.remove(edge);
			// 将当前遍历到的元素edge从集合vertex.inEdges中删掉
			iterator.remove();
			edges.remove(edge);
		}
	}

	@Override
	public void removeEdge(V from, V to) {
		/**
		 * 删除边逻辑：
		 * 	2.删除fromVertex中的边
		 * 	3.删除toVertex中的边
		 * 	4.删除edges的边
		 */
		Vertex<V, E> fromVertex = vertices.get(from);
		if (fromVertex == null) return;
		Vertex<V, E> toVertex = vertices.get(to);
		if (toVertex == null) return;
		Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
		if (fromVertex.outEdges.remove(edge)) {
			toVertex.inEdges.remove(edge);
			edges.remove(edge);
		}
	}

	public void print() {
		System.out.println("[顶点]-------------------");
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			System.out.println(v);
			System.out.println("out-----------");
			System.out.println(vertex.outEdges);
			System.out.println("in-----------");
			System.out.println(vertex.inEdges);
		});

		System.out.println("[边]-------------------");
		edges.forEach((Edge<V, E> edge) -> {
			System.out.println(edge);
		});
	}
	
	//之前所学的二叉树层序遍历就是一种广度优先搜索
	public void bfs(V begin, VertexVisitor<V> visitor) {
		if (visitor == null) return;
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return;
		
		Queue<Vertex<V, E>> queue = new LinkedList<>();
		Set<Vertex<V, E>> visitedVertices = new HashSet<>();
		queue.offer(beginVertex);
		visitedVertices.add(beginVertex);
		while (!queue.isEmpty()) {
			Vertex<V, E> vertex = queue.poll();
			if (visitor.visit(vertex.value)) return;
			for (Edge<V, E> edge : vertex.outEdges) {
				if (visitedVertices.contains(edge.to)) continue;
				queue.offer(edge.to);
				visitedVertices.add(edge.to);
			}
		}
	}
	
	//之前所学的二叉树前序遍历就是一种深度优先搜索 非递归实现
	public void dfs(V begin, VertexVisitor<V> visitor) {
		if (visitor == null) return;
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return;
		Stack<Vertex<V, E>> stack = new Stack<>();
		Set<Vertex<V, E>> visitedVertices = new HashSet<>();
		stack.push(beginVertex);
		visitedVertices.add(beginVertex);
		if (visitor.visit(beginVertex.value)) return;
		while (!stack.isEmpty()) {
			Vertex<V, E> vertex = stack.pop();
			for (Edge<V, E> edge : vertex.outEdges) {
				if (visitedVertices.contains(edge.to)) continue;
				
				stack.push(edge.from);
				stack.push(edge.to);
				visitedVertices.add(edge.to);
				if (visitor.visit(edge.to.value)) return;
				
				break;//很关键
			}
		}
	}
	
	//递归实现
	public void dfs2(V begin, VertexVisitor<V> visitor) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return;
		dfsVertex(beginVertex,new HashSet<>());
	}

	private void dfsVertex(Vertex<V, E> vertex, Set<Vertex<V, E>> visitedVertices) {
		System.err.println(vertex.value);
		visitedVertices.add(vertex);
		for (Edge<V, E> edge : vertex.outEdges) {
			if (visitedVertices.contains(edge.to)) continue;
			dfsVertex(edge.to,visitedVertices);
		}
	}
	
	//拓扑排序
	public List<V> topologicalSort() {
		/**
		 * 可以使用卡恩算法（Kahn于1962年提出）完成拓扑排序
			假设 L 是存放拓扑排序结果的列表
				① 把所有入度为 0 的顶点放入 L 中，然后把这些顶点从图中去掉
				② 重复操作 ①，直到找不到入度为 0 的顶点
			如果此时 L 中的元素个数和顶点总数相同，说明拓扑排序完成
			如果此时 L 中的元素个数少于顶点总数，说明原图中存在环，无法进行拓扑排序
		 */
		List<V> list = new ArrayList<>();
		Queue<Vertex<V, E>> queue = new LinkedList<>();
		Map<Vertex<V, E>, Integer> ins = new HashMap<>();
		// 初始化（将度为0的节点都放入队列）
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			int in = vertex.inEdges.size();
			if (in == 0) {
				queue.offer(vertex);
			}else {
				ins.put(vertex, in);
			}
		});
		while (!queue.isEmpty()) {
			Vertex<V, E> vertex = queue.poll();
			// 放入返回结果中
			list.add(vertex.value);
			for (Edge<V, E> edge : vertex.outEdges) {
				int toIn = ins.get(edge.to) - 1;
				if (toIn == 0) {
					queue.offer(edge.to);
				}else {
					ins.put(vertex, toIn);
				}
			}
		}
		return list;
	}
	public ListGraph() {}
	public ListGraph(WeightManager<E> weightManager) {
		super(weightManager);
	}
	private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> {
		return weightManager.compare(e1.weight, e2.weight);
	};
	@Override
	public Set<EdgeInfo<V, E>> mst() {
		//如果图的每一条边的权值都互不相同，那么最小生成树将只有一个，否则可能会有多个最小生成树
		return Math.random() > 0.5 ? prim() : kruskal();
	}
	
	private Set<EdgeInfo<V, E>> prim() {
		/**
		 * Prim算法（普里姆算法） – 执行过程
		 * 	◼ 假设 G = (V，E) 是有权的连通图（无向），A 是 G 中最小生成树的边集
		 * 	算法从 S = { u0 }（u0 ∈ V），A = { } 开始，重复执行下述操作，直到 S = V 为止
		 * 	✓ 找到切分 C = (S，V – S) 的最小横切边 (u0，v0) 并入集合 A，同时将 v0 并入集合 S
		 */
		//随机取出一个顶点访问
		Iterator<Vertex<V, E>> it = vertices.values().iterator();
		if (!it.hasNext()) return null;
		Vertex<V, E> vertex = it.next();
		Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
		Set<Vertex<V, E>> addedVertices = new HashSet<>();
		addedVertices.add(vertex);
		MinHeap<Edge<V, E>> heap = new MinHeap<>(vertex.outEdges, edgeComparator);//返回一个最小堆
		int verticesSize = vertices.size();
		while (!heap.isEmpty() && addedVertices.size() < verticesSize) {
			Edge<V, E> edge = heap.remove();
			if (addedVertices.contains(edge.to)) continue;
			addedVertices.add(edge.to);
			edgeInfos.add(edge.info());
			heap.addAll(edge.to.outEdges);
		}
		return edgeInfos;
	}
	
	private Set<EdgeInfo<V, E>> kruskal() {//克鲁斯克尔算法
		/**
		 * 按照边的权重顺序（从小到大）将边加入生成树中，直到生成树中含有 V – 1 条边为止（ V 是顶点数量）
		 * 	若加入该边会与生成树形成环，则不加入该边
		 * 	从第3条边开始，可能会与生成树形成环
		 */
		int edgeSize = vertices.size() - 1;
		if (edgeSize == -1) return null;
		Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
		MinHeap<Edge<V, E>> heap = new MinHeap<>(edges, edgeComparator);//返回一个最小堆
		UnionFind<Vertex<V, E>> uf = new UnionFind<>();
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			uf.makeSet(vertex);
		});
		while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
			Edge<V, E> edge = heap.remove();
			if (uf.isSame(edge.from, edge.to)) continue; 
			edgeInfos.add(edge.info());
			uf.union(edge.from, edge.to);
		}
		return edgeInfos;
	}

	@Override
	public Map<V, E> shortestPath0(V begin) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return null;
		Map<V, E> selectedPaths = new HashMap<>();
		Map<Vertex<V, E>, E> paths = new HashMap<>();
		// 初始化paths
		for (Edge<V, E> edge : beginVertex.outEdges) {
			paths.put(edge.to, edge.weight);
		}
		while (!paths.isEmpty()) {
			Iterator<Entry<Vertex<V, E>, E>> it = paths.entrySet().iterator();
			Entry<Vertex<V, E>, E> minEntry = it.next();//最短横切边
			while (it.hasNext()) {//遍历path,找出path中最短横切边
				Entry<Vertex<V, E>, E> entry = it.next();
				if (weightManager.compare(entry.getValue(), minEntry.getValue()) < 0) {
					minEntry = entry;
				}
			}
			// minVertex离开桌面
			Vertex<V, E> minVertex = minEntry.getKey();
			selectedPaths.put(minVertex.value, minEntry.getValue());
			paths.remove(minVertex);
			// 对minVertex的outEdges进行松弛操作（更新beginVertex到minVertex.outEdges中to的权重）
			for (Edge<V, E> edge : minVertex.outEdges) {
				// 如果edge.to已经离开桌面，就没必要进行松弛操作
				if (selectedPaths.containsKey(edge.to.value)) continue;
				// 新的可选择的最短路径：beginVertex到edge.from的最短路径 + edge.weight
				E newWeight = weightManager.add(minEntry.getValue(), edge.weight);
				// 以前的最短路径：beginVertex到edge.to的最短路径
				E oldWeight = paths.get(edge.to);
				if (oldWeight == null || weightManager.compare(newWeight, oldWeight) < 0) {
					paths.put(edge.to, newWeight);
				}
			}
		}
		selectedPaths.remove(begin);
		return selectedPaths;
	}

	@Override
	public Map<V, PathInfo<V, E>> shortestPath(V begin) {
		return dijkstra(begin);
	}

	private Map<V, PathInfo<V, E>> dijkstra(V begin) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return null;
		Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
		Map<Vertex<V, E>, PathInfo<V, E>> paths = new HashMap<>();
		paths.put(beginVertex, new PathInfo<>(weightManager.zero()));
		// 初始化paths
//		for (Edge<V, E> edge : beginVertex.outEdges) {
//			PathInfo<V, E> path = new PathInfo<>();
//			path.weight = edge.weight;
//			path.edgeInfos.add(edge.info());
//			paths.put(edge.to, path);
//		}
		
		while (!paths.isEmpty()) {
			Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = getMinPath(paths);
			// minVertex离开桌面
			Vertex<V, E> minVertex = minEntry.getKey();
			PathInfo<V, E> minPath = minEntry.getValue();
			selectedPaths.put(minVertex.value, minPath);
			paths.remove(minVertex);
			// 对它的minVertex的outEdges进行松弛操作
			for (Edge<V, E> edge : minVertex.outEdges) {
				// 如果edge.to已经离开桌面，就没必要进行松弛操作
				if (selectedPaths.containsKey(edge.to.value)) continue;
				relaxForDijkstra(edge, minPath, paths);
			}
		}
		
		selectedPaths.remove(begin);
		return selectedPaths;
	}
	
	/**
	 * 松弛
	 * @param edge 需要进行松弛的边
	 * @param fromPath edge的from的最短路径信息
	 * @param paths 存放着其他点（对于dijkstra来说，就是还没有离开桌面的点）的最短路径信息
	 */
	private void relaxForDijkstra(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<Vertex<V, E>, PathInfo<V, E>> paths) {
		// 新的可选择的最短路径：beginVertex到edge.from的最短路径 + edge.weight
		E newWeight = weightManager.add(fromPath.weight, edge.weight);
		// 以前的最短路径：beginVertex到edge.to的最短路径
		PathInfo<V, E> oldPath = paths.get(edge.to);
		if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return;
		if (oldPath == null) {
			oldPath = new PathInfo<>();
			paths.put(edge.to, oldPath);
		}else {
			oldPath.edgeInfos.clear();
		}
		oldPath.weight = newWeight;
		oldPath.edgeInfos.addAll(fromPath.edgeInfos);
		oldPath.edgeInfos.add(edge.info());
	}
	
	@SuppressWarnings("unused")
	private Map<V, PathInfo<V, E>> bellmanFord(V begin) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return null;
		
		Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
		selectedPaths.put(begin, new PathInfo<>(weightManager.zero()));
		
		int count = vertices.size() - 1;
		for (int i = 0; i < count; i++) { // v - 1 次
			for (Edge<V, E> edge : edges) {
				PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
				if (fromPath == null) continue;
				relax(edge, fromPath, selectedPaths);
			}
		}
		
		for (Edge<V, E> edge : edges) {
			PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
			if (fromPath == null) continue;
			if (relax(edge, fromPath, selectedPaths)) {
				System.out.println("有负权环");
				return null;
			}
		}
		
		selectedPaths.remove(begin);
		return selectedPaths;
	}
	/**
	 * 松弛
	 * @param edge 需要进行松弛的边
	 * @param fromPath edge的from的最短路径信息
	 * @param paths 存放着其他点（对于dijkstra来说，就是还没有离开桌面的点）的最短路径信息
	 */
	private boolean relax(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
		// 新的可选择的最短路径：beginVertex到edge.from的最短路径 + edge.weight
		E newWeight = weightManager.add(fromPath.weight, edge.weight);
		// 以前的最短路径：beginVertex到edge.to的最短路径
		PathInfo<V, E> oldPath = paths.get(edge.to.value);
		if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return false;
		
		if (oldPath == null) {
			oldPath = new PathInfo<>();
			paths.put(edge.to.value, oldPath);
		} else {
			oldPath.edgeInfos.clear();
		}

		oldPath.weight = newWeight;
		oldPath.edgeInfos.addAll(fromPath.edgeInfos);
		oldPath.edgeInfos.add(edge.info());
		return true;
	}
	
	//从paths中挑一个最小的路径出来(可以使用最小堆优化)
	private Entry<Vertex<V, E>, PathInfo<V, E>> getMinPath(Map<Vertex<V, E>, PathInfo<V, E>> paths) {
		Iterator<Entry<Vertex<V, E>, PathInfo<V, E>>> it = paths.entrySet().iterator();
		Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = it.next();//最短横切边
		while (it.hasNext()) {//遍历path,找出path中最短横切边
			Entry<Vertex<V, E>, PathInfo<V, E>> entry = it.next();
			if (weightManager.compare(entry.getValue().weight, minEntry.getValue().weight) < 0) {
				minEntry = entry;
			}
		}
		return minEntry;
	}

	@Override
	public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
		Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();
		// 初始化
		for (Edge<V, E> edge : edges) {
			Map<V, PathInfo<V, E>> map = paths.get(edge.from.value);
			if (map == null) {
				map = new HashMap<>();
				paths.put(edge.from.value, map);
			}
			
			PathInfo<V, E> pathInfo = new PathInfo<>(edge.weight);
			pathInfo.edgeInfos.add(edge.info());
			map.put(edge.to.value, pathInfo);
		}

		vertices.forEach((V v2, Vertex<V, E> vertex2) -> {
			vertices.forEach((V v1, Vertex<V, E> vertex1) -> {
				vertices.forEach((V v3, Vertex<V, E> vertex3) -> {
					if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;
					
					// v1 -> v2
					PathInfo<V, E> path12 = getPathInfo(v1, v2, paths);
					if (path12 == null) return;
					
					// v2 -> v3
					PathInfo<V, E> path23 = getPathInfo(v2, v3, paths);
					if (path23 == null) return;
					
					// v1 -> v3 
					PathInfo<V, E> path13 = getPathInfo(v1, v3, paths);
					
					E newWeight = weightManager.add(path12.weight, path23.weight);
					if (path13 != null && weightManager.compare(newWeight, path13.weight) >= 0) return;
					
					if (path13 == null) {
						path13 = new PathInfo<V, E>();
						paths.get(v1).put(v3, path13);
					} else {
						path13.edgeInfos.clear();
					}
					
					path13.weight = newWeight;
					path13.edgeInfos.addAll(path12.edgeInfos);
					path13.edgeInfos.addAll(path23.edgeInfos);
				});
			});
		});
		return paths;
	}
	private PathInfo<V, E> getPathInfo(V from, V to, Map<V, Map<V, PathInfo<V, E>>> paths) {
		Map<V, PathInfo<V, E>> map = paths.get(from);
		return map == null ? null : map.get(to);
	}
}
