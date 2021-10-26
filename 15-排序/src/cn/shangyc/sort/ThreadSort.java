package cn.shangyc.sort;

//史上“最强”排序 – 休眠排序
public class ThreadSort extends Thread{
	private int value;

	public ThreadSort(int value) {
		super();
		this.value = value;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(value);
			System.out.println(value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		int[] array = new int[]{5,4,3,2,1};
		for (int i = 0; i < array.length; i++) {
			new ThreadSort(array[i]).start();
		}
	}
}
