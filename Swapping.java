import java.util.ArrayList;

public class Swapping {
	private static final int NUM_PROCESSES = 100;
	private static ArrayList<Process> pList = new ArrayList<Process>(NUM_PROCESSES);
	public static void main(String args[]){
			reset();
			for(Process p:pList)
				System.out.println("PSize: " + p.size);
	}
	private static void reset(){
		for(int i = 0; i< NUM_PROCESSES; i++)
			pList.add(new Process(i));
	}
}
