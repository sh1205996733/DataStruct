package cn.shangyc;

import java.util.Comparator;

import org.junit.Test;

import cn.shangyc.BinarySearchTree.Visitor;
import cn.shangyc.file.Files;
import cn.shangyc.printer.BinaryTreeInfo;
import cn.shangyc.printer.BinaryTrees;

@SuppressWarnings("unused")
public class Main {
	
	private static class PersonComparator implements Comparator<Person> {
		public int compare(Person e1, Person e2) {
			return e1.getAge() - e2.getAge();
		}
	}
	
	private static class PersonComparator2 implements Comparator<Person> {
		public int compare(Person e1, Person e2) {
			return e2.getAge() - e1.getAge();
		}
	}
	
	@Test
	public void test1() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		
		BinaryTrees.println(bst);
	}
	
	@Test
	public void test2() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BinarySearchTree<Person> bst1 = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst1.add(new Person(data[i],"name"+i));
		}
		
		BinaryTrees.println(bst1);
		
		BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new Comparator<Person>() {
			public int compare(Person o1, Person o2) {
				return o2.getAge() - o1.getAge();
			}
		});
		for (int i = 0; i < data.length; i++) {
			bst2.add(new Person(data[i]));
		}
		BinaryTrees.println(bst2);
	}
	
	@Test
	public void test3() {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < 40; i++) {
			bst.add((int)(Math.random() * 100));
		}
		
		String str = BinaryTrees.printString(bst);
		str += "\n";
		Files.writeToFile("F:/1.txt", str, true);
		// BinaryTrees.println(bst);
	}
	
	@Test
	public void test4() {
		BinaryTrees.println(new BinaryTreeInfo() {
			
			@Override
			public Object string(Object node) {
				return node.toString() + "_";
			}
			
			@Override
			public Object root() {
				return "A";
			}
			
			@Override
			public Object right(Object node) {
				if (node.equals("A")) return "C";
				if (node.equals("C")) return "E";
				return null;
			}
			
			@Override
			public Object left(Object node) {
				if (node.equals("A")) return "B";
				if (node.equals("C")) return "D";
				return null;
			}
		});
		
		// test3();
		
		
		/*
		 * Java的匿名类，类似于iOS中的Block、JS中的闭包（function）
		 */
		
//		BinarySearchTree<Person> bst1 = new BinarySearchTree<>(new Comparator<Person>() {
//			@Override
//			public int compare(Person o1, Person o2) {
//				return 0;
//			}
//		});
//		bst1.add(new Person(12));
//		bst1.add(new Person(15));
//		
//		BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new PersonComparator());
//		bst2.add(new Person(12));
//		bst2.add(new Person(15));
//
		
		
//		BinarySearchTree<Car> bst4 = new BinarySearchTree<>(new Car);
//		
//		
//		java.util.Comparator<Integer> iComparator;
	}
	
	@Test
	public void test5() {
		BinarySearchTree<Person> bst = new BinarySearchTree<>();
		bst.add(new Person(10, "jack"));
		bst.add(new Person(12, "rose"));
		bst.add(new Person(6, "jim"));
		
		bst.add(new Person(10, "michael"));
		
		BinaryTrees.println(bst);
	}
	
	@Test
	public void test6() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		
//		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//		for (int i = 0; i < 10; i++) {
//			bst.add((int)(Math.random() * 100));
//		}
		BinaryTrees.println(bst);
		System.out.println(bst.isComplete());
		
		// bst.levelOrderTraversal();
		
		/*
		 *       7
		 *    4    9
		    2   5
		 */
		
//		bst.levelOrder(new Visitor<Integer>() {
//			public void visit(Integer element) {
//				System.out.print("_" + element + "_ ");
//			}
//		});
		
//		bst.inorder(new Visitor<Integer>() {
//			public void visit(Integer element) {
//				System.out.print("_" + (element + 3) + "_ ");
//			}
//		});
		
		 System.out.println(bst.height());
	}
	
	@Test
	public void test7() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		
		BinaryTrees.println(bst);
		
		bst.remove(1);
		bst.remove(2);
		bst.remove(3);
		bst.remove(4);
		bst.remove(5);
		bst.remove(7);
		System.out.println("----------------------------------------------------------------");
		BinaryTrees.println(bst);
	}
	
	@Test
	public void test8() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		BinaryTrees.println(bst);
		System.out.println(bst.isComplete());
	}
	
	@Test
	public void test9() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		BinaryTrees.println(bst);
		
		bst.preorderTraversal();
		System.out.println();
		
		bst.inorderTraversal();
		System.out.println();
		
		bst.postorderTraversal();
		System.out.println();
		
		bst.levelOrderTraversal();
		System.out.println();
	}
	
	@Test
	public void test10() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		BinaryTrees.println(bst);
		
		bst.preorder(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 2 ? true : false;
			}
		});
		System.out.println();
		
		bst.inorder(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 4 ? true : false;
			}
		});
		System.out.println();
		
		bst.postorder(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 4 ? true : false;
			}
		});
		System.out.println();
		
		bst.levelOrder(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 9 ? true : false;
			}
		});
		System.out.println();
	}
	
	@Test
	public void test11() {
		Integer data[] = new Integer[] {
				5, 43, 59, 95, 22, 57, 31, 48, 4, 14, 83, 36, 58, 47
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		BinaryTrees.println(bst);
		bst.preorderIterate(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 4000 ? true : false;
			}
		});
		System.out.println();
		
		bst.inorderIterate(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 40000 ? true : false;
			}
		});
		System.out.println();
		
		bst.postorderIterate(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 40000 ? true : false;
			}
		});
		System.out.println();
		bst.levelOrder(new Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 9000 ? true : false;
			}
		});
		System.out.println();
	}
}
