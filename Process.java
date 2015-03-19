import java.util.Random;

public class Process{
	private static final int[] PROCESS_SIZES = {5,11,17,31};
	private static final int MAX_DURATION = 5;
	public int pNum, size, duration, index;
	Process(int p){
		pNum = p;
		Random r = new Random(p);
		int index = r.nextInt(PROCESS_SIZES.length);
		System.out.println(index);
		size = PROCESS_SIZES[index];
		duration = 1+r.nextInt(MAX_DURATION);
	}
	public void setIndex(int index) {
		this.index = index;
	}
}