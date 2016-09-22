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
		int tId = ((ThreadId)Thread.currentThread()).getThreadId();
		lock.lock(tId);
		int temp = -1;
		try {
			temp = super.getAndIncrement();
			if(temp == 1 || temp % 100 == 0 || temp == 1999 || temp == 63999){
				System.out.println("Thread " + tId + " updated counter " + temp);
			}
		} finally {
			lock.unlock(tId);
		}
		return temp;
	}

}
