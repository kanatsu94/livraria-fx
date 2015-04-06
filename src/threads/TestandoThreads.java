package threads;

public class TestandoThreads {
	public static void main(String[] args) {

		new Thread(() -> {
			System.out.println("THREAD!");
		}).start();
		System.out.println("FINISH!");
	}
}