public class Process implements Comparable<Process>{
	public int pNum, size, duration, index, timeRunning;
	Process(int p, int s, int d){
		pNum = p;
		size = s;
		duration = d;
		timeRunning = 0;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean run(){
		timeRunning++;
		return (timeRunning==duration);
	}
	@Override
	public int compareTo(Process other) {
		if(other.index > index)
			return -1;
		else if(other.index == index)
			return 0;
		else
			return 1;
	}
}