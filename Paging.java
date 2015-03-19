import java.util.Arrays;
import java.util.Random;


public class Paging {
	private static final int NUM_PAGES_TOTAL = 10;
	private static final int NUM_PAGES_MEMORY = 4;
	private static final int NUM_REFS = 100;
	private static final int NUM_RUNS = 5;
	private static final int RAND_SEED = 0;
	
	public static void main(String arg[]){
		int missTot = 0;
		for(int i=0;i<NUM_RUNS;i++)
			missTot+= runFIFO(RAND_SEED+i);
		System.out.println("Number of misses:"+missTot+" Miss Rate:"+((double)missTot/((double)(NUM_REFS*NUM_RUNS))));
		
		missTot = 0;
		for(int i=0;i<NUM_RUNS;i++)
			missTot+= runLRU(RAND_SEED+i);
		System.out.println("Number of misses:"+missTot+" Miss Rate:"+((double)missTot/((double)(NUM_REFS*NUM_RUNS))));

		missTot = 0;
		for(int i=0;i<NUM_RUNS;i++)
			missTot+= runLRU(RAND_SEED+i);
		System.out.println("Number of misses:"+missTot+" Miss Rate:"+((double)missTot/((double)(NUM_REFS*NUM_RUNS))));

		missTot = 0;
		for(int i=0;i<NUM_RUNS;i++)
			missTot+= runLFU(RAND_SEED+i);
		System.out.println("Number of misses:"+missTot+" Miss Rate:"+((double)missTot/((double)(NUM_REFS*NUM_RUNS))));

		missTot = 0;
		for(int i=0;i<NUM_RUNS;i++)
			missTot+= runMFU(RAND_SEED+i);
		System.out.println("Number of misses:"+missTot+" Miss Rate:"+((double)missTot/((double)(NUM_REFS*NUM_RUNS))));

		missTot = 0;
		for(int i=0;i<NUM_RUNS;i++)
			missTot+= runRAND(RAND_SEED+i);
		System.out.println("Number of misses:"+missTot+" Miss Rate:"+((double)missTot/((double)(NUM_REFS*NUM_RUNS))));
	}
	private static int runFIFO(int seed){
		System.out.println("\nRunning FIFO:");
		
		Random r = new Random(seed);
		int ref = r.nextInt(NUM_PAGES_TOTAL);
		int start = 0;
		int numMisses = 0;
		int[] pages = new int[NUM_PAGES_MEMORY];
		Arrays.fill(pages, -1);
		
		for(int i=0;i<NUM_REFS;i++){
			print(ref, pages);
			if(indexInArray(ref, pages)==-1){
				System.out.println("Page Fault: "+ref+" not found. Replacing page: "+pages[start]+".");
				pages[start] = ref;
				numMisses++;
				start=(start+1)%NUM_PAGES_MEMORY;
			}
			ref = getNextReference(ref, r);
		}
		return numMisses;
	}
	private static int runLRU(int seed){
		System.out.println("\nRunning LRU:");
		
		Random r = new Random(seed);
		int ref = r.nextInt(NUM_PAGES_TOTAL);
		int numMisses = 0;
		int[] pages = new int[NUM_PAGES_MEMORY];
		int[] lastUsed = new int[NUM_PAGES_MEMORY];
		Arrays.fill(pages, -1);
		Arrays.fill(lastUsed, -1);
		
		for(int i=0;i<NUM_REFS;i++){
			print(ref, pages);
			int index = indexInArray(ref, pages);
			if(index==-1){
				int toReplace = findIndexOfLargest(false, lastUsed);
				System.out.println("Page Fault: "+ref+" not found. Replacing page: "+pages[toReplace]+".");
				pages[toReplace] = ref;
				lastUsed[toReplace] = i;
				numMisses++;
			}
			else
				lastUsed[index] = i;
			ref = getNextReference(ref, r);
		}
		
		return numMisses;
	}
	private static int runLFU(int seed){
		System.out.println("\nRunning LFU:");
		
		Random r = new Random(seed);
		int ref = r.nextInt(NUM_PAGES_TOTAL);
		int numMisses = 0;
		int[] pages = new int[NUM_PAGES_MEMORY];
		int[] useCount = new int[NUM_PAGES_MEMORY];
		Arrays.fill(pages, -1);
		Arrays.fill(useCount, -1);
		
		for(int i=0;i<NUM_REFS;i++){
			print(ref, pages);
			int index = indexInArray(ref, pages);
			if(index==-1){
				int toReplace = findIndexOfLargest(false, useCount);
				System.out.println("Page Fault: "+ref+" not found. Replacing page: "+pages[toReplace]+".");
				pages[toReplace] = ref;
				useCount[toReplace] = 0;
				numMisses++;
			}
			else
				useCount[index]++;
			ref = getNextReference(ref, r);
		}
		
		return numMisses;	
	}
	private static int runMFU(int seed){
		System.out.println("\nRunning MFU:");
		
		Random r = new Random(seed);
		int ref = r.nextInt(NUM_PAGES_TOTAL);
		int numMisses = 0;
		int[] pages = new int[NUM_PAGES_MEMORY];
		int[] useCount = new int[NUM_PAGES_MEMORY];
		Arrays.fill(pages, -1);
		Arrays.fill(useCount, NUM_REFS);
		
		for(int i=0;i<NUM_REFS;i++){
			print(ref, pages);
			int index = indexInArray(ref, pages);
			if(index==-1){
				int toReplace = findIndexOfLargest(true, useCount);
				System.out.println("Page Fault: "+ref+" not found. Replacing page: "+pages[toReplace]+".");
				pages[toReplace] = ref;
				useCount[toReplace] = 0;
				numMisses++;
			}
			else
				useCount[index]++;
			ref = getNextReference(ref, r);
		}
		
		return numMisses;
	}
	private static int runRAND(int seed){
		System.out.println("\nRunning RAND:");
		
		Random r = new Random(seed);
		int ref = r.nextInt(NUM_PAGES_TOTAL);
		int numMisses = 0;
		int numPages = 0;
		int[] pages = new int[NUM_PAGES_MEMORY];
		Arrays.fill(pages, -1);
		
		for(int i=0;i<NUM_REFS;i++){
			print(ref, pages);
			if(indexInArray(ref, pages)==-1){
				int toReplace = 0;
				//when pages in mem. aren't full
				if(numPages<NUM_PAGES_MEMORY){
					toReplace = numPages;
					numPages++;
				}
				else{
					toReplace = r.nextInt(NUM_PAGES_MEMORY);
				}				
				numMisses++;
				pages[toReplace] = ref;
				System.out.println("Page Fault: "+ref+" not found. Replacing page: "+pages[toReplace]+".");
			}
			ref = getNextReference(ref, r);
		}
		return numMisses;
	}
	private static int getNextReference(int prevRef, Random r){
		int nextRef;
		if(prevRef<7 && prevRef>=0){
			int i = r.nextInt(3)-1;
			nextRef = (prevRef + NUM_PAGES_TOTAL + i)%NUM_PAGES_TOTAL;
		}
		else{
			int i = r.nextInt(7)+2;
			nextRef = (prevRef + i)%NUM_PAGES_TOTAL;
		}
		return nextRef;
	}
	private static void print(int ref,int[] array){
		System.out.print("Trying to reference page: "+ref+". Pages in memory:");
		for(int i:array){
			if(i!=-1)
				System.out.print(i+",");
			else
				System.out.print("-,");
		}
		System.out.println();
	}
	private static int indexInArray(int pageNum, int[] array){
		for(int i=0;i<array.length;i++)
			if(array[i]==pageNum)
				return i;
		return -1;
	}
	private static int findIndexOfLargest(boolean largest, int[] array){
		int index = 0;
		if(largest){
			int max = array[0];
			for(int i = 1; i<array.length; i++){
				if(max<array[i]){
					index = i;
					max = array[i];
				}
			}
		}
		else{
			int min = array[0];
			for(int i = 1; i<array.length; i++){
				if(min>array[i]){
					index = i;
					min = array[i];
				}
			}
		}
		return index;
	}
}