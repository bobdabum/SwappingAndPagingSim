import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Swapping {
	private static final int[] PROCESS_SIZES = {5,11,17,31};
	private static final int MAX_DURATION = 5;
	private static final int NUM_PROCESSES = 200;
	private static final int NUM_RUNS = 5;
	private static final int NUM_SECS = 60;
	private static final int SIZE_MEMORY = 100;
	private static final int RAND_SEED = 0;
	private static ArrayList<Process> pList = new ArrayList<Process>(NUM_PROCESSES);
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
	private static void reset(int run){
		pInMemList = new ArrayList<Process>(NUM_PROCESSES);
		pList = new ArrayList<Process>(NUM_PROCESSES);
		Random r = new Random(RAND_SEED+run);
		for(int i = 0; i< NUM_PROCESSES; i++)
			pList.add(new Process(i,PROCESS_SIZES[r.nextInt(PROCESS_SIZES.length)],1+r.nextInt(MAX_DURATION)));
	}
	private static void runFF(boolean compaction){
		int numProcesses = 0;
		for(int run = 0; run < NUM_RUNS; run++)
		{
			reset(run);
			for(int j=0;j<NUM_SECS;j++){
				//TODO FINISH REST
			}
		}
		System.out.println("Average number of processes processed: "+((double)numProcesses/NUM_RUNS));
	}
	private static void runNF(boolean compaction){
		int numProcesses = 0;
		for(int run = 0; run < NUM_RUNS; run++)
		{
			reset(run);
			for(int j=0;j<NUM_SECS;j++){
				//TODO FINISH REST
			}
		}
		System.out.println("Average number of processes processed: "+((double)numProcesses/NUM_RUNS));
	}
	private static void runBF(boolean compaction){
		int numProcesses = 0;
		for(int run = 0; run < NUM_RUNS; run++)
		{
			reset(run);
			int processInd = 0;
			for(int sec=0;sec<NUM_SECS;sec++){
				//Runs and removes processes
				numProcesses = runAllProcesses(numProcesses);
				
				//compacts if at second 30
				if (compaction && sec == 30)
					compact();
				
				//adds processes to pMemList
				addProcessToSmallestHole();
				
				printPInMemList("Running BF(compaction="+compaction+") Run "+run+" Second "+sec);
			}
		}
		System.out.println("Average number of processes processed: "+((double)numProcesses/NUM_RUNS));
	}
	private static void runWF(boolean compaction){
		int numProcesses = 0;
		for(int run = 0; run < NUM_RUNS; run++)
		{
			reset(run);
			for(int sec=0;sec<NUM_SECS;sec++){
				//Runs and removes processes
				numProcesses = runAllProcesses(numProcesses);
				
				//compacts if at second 30
				if (compaction && sec == 30)
					compact();
				
				//adds processes to pMemList
				addProcessToLargestHole();
				
				printPInMemList("Running WF(compaction="+compaction+") Run "+run+" Second "+sec);
			}
		}
		System.out.println("Average number of processes processed: "+((double)numProcesses/NUM_RUNS));
	}
	private static void printPInMemList (String input) {
		String toPrint = input + ": ";
		int lastProcessEnd = 0;
		int currProcessStart = 0;
		for (int i = 0; i < pInMemList.size(); i++) {
			currProcessStart = pInMemList.get(i).index;

			// Find when last process ended and current process starts and fill with dots.
			int dotCount = currProcessStart - lastProcessEnd;
			for(int j = 0; j < dotCount; j++) 
				toPrint += ".";

			// Put letters for size of the process.
			char currLetter = (char)(65 + pInMemList.get(i).pNum%26);
			for(int k = 0; k < pInMemList.get(i).size; k++) {
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
	private static int runAllProcesses(int numProcesses){
		for(int k = 0; k< pInMemList.size();k++){
			//If process.run returns true, means that the process has finished running
			if(pInMemList.get(k).run()){
				pInMemList.remove(k);
				numProcesses++;
				k--;
			}
		}
		Collections.sort(pInMemList);
		return numProcesses;	
	}
	private static void addProcessToSmallestHole(){
		//Adds processes until can't add first in queue
		boolean addedProcess = true;
		while(addedProcess){
			addedProcess = false;

			if(pInMemList.size()==0){
				pList.get(0).index = 0;
				pInMemList.add(pList.remove(0));
				addedProcess = true;
				Collections.sort(pInMemList);
			}
			else{
				boolean holeFound = false;
				int smallestHole = SIZE_MEMORY;
				int holeIndex = 0;
				int lastIndex = 0;
				//Finds smallest holes between processes.
				for(Process p:pInMemList){
					int hole = p.index - lastIndex;
					if(smallestHole>hole && hole>=pList.get(0).size){
						holeFound = true;
						holeIndex = lastIndex;
						smallestHole = hole;
					}
					lastIndex = p.index+p.size;
				}
				//Finds holes between the last process and the end
				int hole = SIZE_MEMORY - 1 - lastIndex;
				if(smallestHole>hole && hole>=pList.get(0).size){
					holeFound = true;
					holeIndex = lastIndex;
					smallestHole = hole;
				}
				
				if(holeFound){
					pList.get(0).index = holeIndex;
					pInMemList.add(pList.remove(0));
					addedProcess = true;
					Collections.sort(pInMemList);
				}
			}
		}
	}
	private static void addProcessToLargestHole(){
		//Adds processes until can't add first in queue
		boolean addedProcess = true;
		while(addedProcess){
			addedProcess = false;

			if(pInMemList.size()==0){
				pList.get(0).index = 0;
				pInMemList.add(pList.remove(0));
				addedProcess = true;
				Collections.sort(pInMemList);
			}
			else{
				boolean holeFound = false;
				int largestHole = 0;
				int holeIndex = 0;
				int lastIndex = 0;
				//Finds largest holes between processes.
				for(Process p:pInMemList){
					int hole = p.index - lastIndex;
					if(largestHole<hole && hole>=pList.get(0).size){
						holeFound = true;
						holeIndex = lastIndex;
						largestHole = hole;
					}
					lastIndex = p.index+p.size;
				}
				//Finds holes between the last process and the end
				int hole = SIZE_MEMORY - 1 - lastIndex;
				if(largestHole<hole && hole>=pList.get(0).size){
					holeFound = true;
					holeIndex = lastIndex;
					largestHole = hole;
				}
				
				if(holeFound){
					pList.get(0).index = holeIndex;
					pInMemList.add(pList.remove(0));
					addedProcess = true;
					Collections.sort(pInMemList);
				}
			}
		}
	}
}