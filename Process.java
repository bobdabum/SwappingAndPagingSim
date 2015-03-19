public class Process{
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
	public boolean timeRunning(){
		timeRunning++;
		return (timeRunning==duration);
	}
}