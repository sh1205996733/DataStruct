package cn.shangyc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

//01背包问题
public class Knapsack {
	/**
	 * ◼ 有 n 件物品和一个最大承重为 W 的背包，每件物品的重量是 𝑤i、价值是 𝑣i 在保证总重量不超过 W 的前提下，将哪几件物品装入背包，可以使得背包的总价值最大？
	 * 	注意：每个物品只有 1 件，也就是每个物品只能选择 0 件或者 1 件，因此称为 0-1背包问题
	 * ◼ 如果采取贪心策略，有3个方案
	 * 	① 价值主导：优先选择价值最高的物品放进背包
	 * 	② 重量主导：优先选择重量最轻的物品放进背包
	 * 	③ 价值密度主导：优先选择价值密度最高的物品放进背包（价值密度 = 价值 ÷ 重量）
	 */
	public static void main(String[] args) {
		select("价值主导", (Article a1, Article a2) -> {
			return a2.value - a1.value;
		});
		select("重量主导", (Article a1, Article a2) -> {
			return a1.weight - a2.weight;
		});
		select("价值密度主导", (Article a1, Article a2) -> {
			return Double.compare(a2.valueDensity, a1.valueDensity);
		});
	}

	private static void select(String title, Comparator<Article> cmp) {
		Article[] articles = new Article[] {
			new Article(35, 10), new Article(30, 40),
			new Article(60, 30), new Article(50, 50),
			new Article(40, 35), new Article(10, 40),
			new Article(25, 30)
		};
		Arrays.sort(articles, cmp);
		int capacity = 150, weight = 0, value = 0;
		List<Article> selectedArticles = new LinkedList<>();
		for (int i = 0; i < articles.length && weight < capacity; i++) {
			int newWeight = weight + articles[i].weight;
			if (newWeight <= capacity) {
				value+= articles[i].value;
				weight = newWeight;
				selectedArticles.add(articles[i]);
			}
		}
		System.out.println("【" + title + "】");
		System.out.println("总价值：" + value);
		for (Article article : selectedArticles) {
			System.out.println(article);
		}
		System.out.println("-----------------------------");
	}
}
