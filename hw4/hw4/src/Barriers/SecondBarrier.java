package Barriers;

public class SecondBarrier extends Thread implements IBarrier {

	
	private boolean printOutput;
	private long elapsed;
	private volatile static int[] b;
	private int totalThreads;
	private int currentThreadId;

	public SecondBarrier(int totalThread, boolean printResult, Integer threadId){
		totalThreads = totalThread;
		printOutput = printResult;
		currentThreadId = threadId;
		b = new int[totalThread];
	}
	
	@Override
	public void run() {
		foo();
		
		setBarrier();
		
		bar();
	}

	@Override
	public void setBarrier() {
		long start = System.currentTimeMillis();
		lock();

		unlock();	
		long end = System.currentTimeMillis();
		elapsed = end - start;
		if (printOutput)
			System.out.println("Time for thread " + currentThreadId + " is " + (end - start) + "ms");	
	}
	
	void lock(){
		if(currentThreadId == 0){
			b[0] = 1;
		}
		else{
			while(b[currentThreadId - 1] == 0){}
			b[currentThreadId] = 1;
		}
		
		if(currentThreadId < (totalThreads - 1)){
			while(b[totalThreads - 1] != 2){}
			return;
		}
		else
		{
			b[totalThreads - 1] = 2;
			return;
		}
	}
	
	void unlock(){
		
	}
	
	@Override
	public long getElapsedTime() {
		return elapsed;
	}

	public void foo() {
		if(printOutput){
			System.out.println("Thread " + currentThreadId + " entered foo().");
		}
	}

	public void bar() {
		if(printOutput)
			System.out.println("Thread " + currentThreadId + " entered bar().");
	}

}
