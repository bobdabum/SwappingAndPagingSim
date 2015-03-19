import java.util.ArrayList;
import java.util.Random;

public class Swapping {
	private static final int[] PROCESS_SIZES = {5,11,17,31};
	private static final int MAX_DURATION = 5;
	private static final int NUM_PROCESSES = 100;
	private static final int NUM_RUNS = 5;
	private static final int SIZE_MEMORY = 100;
	private static final int RAND_SEED = 0;
	private static Process[] pList = new Process[NUM_PROCESSES];
	private static ArrayList<Process> pInMemList = new ArrayList<Process>(NUM_PROCESSES);

	public static void main(String args[]){
		int numProcess = 0;
		for(int i = 0; i < NUM_RUNS; i++)
		{
			initialize(i);
			numProcess += runFF();
		}
		System.out.println("Average number of processes processed: "+((double)numProcess/NUM_RUNS));
		numProcess = 0;
		for(int i = 0; i < NUM_RUNS; i++)
		{
			initialize(i);
			numProcess += runNF();
		}
		System.out.println("Average number of processes processed: "+((double)numProcess/NUM_RUNS));
		numProcess = 0;
		for(int i = 0; i < NUM_RUNS; i++)
		{
			initialize(i);
			numProcess += runBF();
		}
		System.out.println("Average number of processes processed: "+((double)numProcess/NUM_RUNS));
		numProcess = 0;
		for(int i = 0; i < NUM_RUNS; i++)
		{
			initialize(i);
			numProcess += runWF();
		}
		System.out.println("Average number of processes processed: "+((double)numProcess/NUM_RUNS));
		numProcess = 0;
	}
	private static void initialize(int run){
		Random r = new Random(RAND_SEED+run);
		for(int i = 0; i< NUM_PROCESSES; i++)
			pList[i] = new Process(i,PROCESS_SIZES[r.nextInt(PROCESS_SIZES.length)],1+r.nextInt(MAX_DURATION));
	}
	private static int runFF(){
		reset();
		return 0;
	}
	private static int runNF(){
		reset();
		return 0;
	}
	private static int runBF(){
		reset();
		return 0;
	}
	private static int runWF(){
		reset();
		return 0;
	}
	private static void reset(){
		pInMemList = new ArrayList<Process>(NUM_PROCESSES);      
	}
	private static void printPInMemList (String input) {
		String toPrint = input + ": ";
		int lastProcessEnd = 0;
		int currProcessStart = 0;
		for (int i = 0; i < pInMemList.size(); i++) {
			currProcessStart = pInMemList.get(i).index;
			
			// Find when last process ended and current process starts and fill with dots.
			int dotCount = currProcessStart - lastProcessEnd;
			for(i = 0; i < dotCount; i++) 
				toPrint += ".";
			
			// Put letters for size of the process.
			char currLetter = (char)(65 + pInMemList.get(i).pNum%26);
			for(i = 0; i < pInMemList.get(i).size; i++) {
				toPrint += currLetter;
			}
			lastProcessEnd = pInMemList.get(i).index + pInMemList.get(i).size;
		}
		// Fill in rest between last process and end of memory with dots.
		int dotCount = SIZE_MEMORY-lastProcessEnd;
		for(int i = 0; i < dotCount; i++)
			toPrint += ".";
		
		System.out.println(toPrint);
	}
}