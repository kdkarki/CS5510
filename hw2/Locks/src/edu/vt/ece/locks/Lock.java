package edu.vt.ece.locks;

public interface Lock {
	public void lock();
	public void unlock();
	public void lock(int threadId);
	public void unlock(int threadId);
}
