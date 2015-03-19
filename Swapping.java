import java.util.ArrayList;
import java.util.Random;

public class Swapping {
	private static final int[] PROCESS_SIZES = {5,11,17,31};
	private static final int MAX_DURATION = 5;
	private static final int NUM_PROCESSES = 100;
	private static final int NUM_RUNS = 5;
	private static final int NUM_SECS = 60;
	private static final int SIZE_MEMORY = 100;
	private static final int RAND_SEED = 0;
	private static Process[] pList = new Process[NUM_PROCESSES];
	private static ArrayList<Process> pInMemList = new ArrayList<Process>(NUM_PROCESSES);

	public static void main(String args[]){
		runFF(false);
		runFF(true);
		
		runNF(false);
		runNF(false);
		
		runBF(false);
		runBF(true);
		
		runWF(false);
		runWF(true);
	}
	private static void initialize(int run){
		Random r = new Random(RAND_SEED+run);
		for(int i = 0; i< NUM_PROCESSES; i++)
			pList[i] = new Process(i,PROCESS_SIZES[r.nextInt(PROCESS_SIZES.length)],1+r.nextInt(MAX_DURATION));
	}
	private static void runFF(boolean compaction){
		int numProcesses = 0;
		for(int i = 0; i < NUM_RUNS; i++)
		{
			initialize(i);
			for(int j=0;j<NUM_SECS;j++){
				reset();
				//TODO FINISH REST
			}
		}
		System.out.println("Average number of processes processed: "+((double)numProcesses/NUM_RUNS));
	}
	private static void runNF(boolean compaction){
		int numProcesses = 0;
		for(int i = 0; i < NUM_RUNS; i++)
		{
			initialize(i);
			for(int j=0;j<NUM_SECS;j++){
				reset();
				//TODO FINISH REST
			}
		}
		System.out.println("Average number of processes processed: "+((double)numProcesses/NUM_RUNS));
	}
	private static void runBF(boolean compaction){
		int numProcesses = 0;
		for(int i = 0; i < NUM_RUNS; i++)
		{
			initialize(i);
			for(int j=0;j<NUM_SECS;j++){
				reset();
				//TODO FINISH REST
			}
		}
		System.out.println("Average number of processes processed: "+((double)numProcesses/NUM_RUNS));
	}
	private static void runWF(boolean compaction){
		int numProcesses = 0;
		for(int i = 0; i < NUM_RUNS; i++)
		{
			initialize(i);
			for(int j=0;j<NUM_SECS;j++){
				reset();
				//TODO FINISH REST
			}
		}
		System.out.println("Average number of processes processed: "+((double)numProcesses/NUM_RUNS));
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
	private static void compact(){
		int newIndex = 0;
		for(Process p: pInMemList){
			p.setIndex(newIndex);
			newIndex += p.size;
		}
	}
}