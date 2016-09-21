package edu.vt.ece.bench;

import edu.vt.ece.locks.Lock;

/**
 * 
 * @author Mohamed M. Saad
 */
public class SharedCounter extends Counter{
	private Lock lock;

	public SharedCounter(int c, Lock lock) {
		super(c);
		this.lock = lock;
	}
	
	@Override
	public int getAndIncrement() {
		lock.lock();
		int temp = -1;
		try {
			temp = super.getAndIncrement();
		} finally {
			lock.unlock();
			if(temp == 1 || temp % 100 == 0 || temp == 1999){
				long tId = ((ThreadId)Thread.currentThread()).getThreadId();
				System.out.println("Thread " + tId + " updated counter " + temp);
			}
		}
		return temp;
	}

}
