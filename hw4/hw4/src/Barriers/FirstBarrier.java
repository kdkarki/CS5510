package Barriers;

import java.util.concurrent.atomic.AtomicBoolean;

public class FirstBarrier extends Thread implements IBarrier {	

	int currentThreadId;
	private static AtomicBoolean isLocked = new AtomicBoolean(false);
	private boolean printOutput = false;
	private int totalThreads = 0; 
	private volatile static int threadCount = 0;
	private long elapsed;
	
	public FirstBarrier(int totalThread, boolean printResult, Integer threadId){
		totalThreads = totalThread;
		printOutput = printResult;
		currentThreadId = threadId;
	}
	
	@Override
	public void run() {
		
		foo();
		setBarrier();
		bar();
	}
	
	public void foo(){
		if(printOutput){
			System.out.println("Thread " + currentThreadId + " entered foo().");
		}		
	}
	
	public void bar(){
		if(printOutput)
			System.out.println("Thread " + currentThreadId + " entered bar().");		
	}

	@Override
	public void setBarrier() {
		long start = System.currentTimeMillis();
		lock();
		threadCount++;
		if(printOutput)
			System.out.println("Thread " + currentThreadId + " incremented threadcount to " + threadCount + ".");
		unlock();
				
		while(threadCount < (totalThreads)){}

		long end = System.currentTimeMillis();
		elapsed = end - start;
		if (printOutput)
			System.out.println("Time for thread " + currentThreadId + " is " + (end - start) + "ms.");
	}
	
	private void lock(){
		while(true){
			while(isLocked.get()) {};
			if(!isLocked.getAndSet(true))
				return;
		}
	}
	
	private void unlock(){
		isLocked.set(false);
	}
	
	public long getElapsedTime() {
		return elapsed;
	}

}
