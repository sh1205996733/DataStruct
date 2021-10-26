package cn.shangyc;

public class Student implements Comparable<Student> {
	public int score;
	public int age;
	public String name;
	
	public Student(int score, int age) {
		this.score = score;
		this.age = age;
	}

	public Student(int score, String name) {
		this.score = score;
		this.name = name;
	}
	
	@Override
	public int compareTo(Student o) {
		return age - o.age;
	}

	@Override
	public String toString() {
		return "Student [score=" + score + ", name=" + name + "]";
	}
	
}
