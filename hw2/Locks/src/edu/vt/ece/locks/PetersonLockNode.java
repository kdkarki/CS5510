package edu.vt.ece.locks;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

public class PetersonLockNode implements Comparable<PetersonLockNode> {

	private Integer nodeId;
	private Hashtable<Integer,Boolean> flags;
	private AtomicInteger victim = new AtomicInteger();
	
	private PetersonLockNode parent, rightChild, LeftChild;
	
	public PetersonLockNode(Integer id, PetersonLockNode parentNode){
		nodeId = id;
		parent = parentNode;
		flags = new Hashtable<>();
	}
	
	public Integer getNodeId(){
		return nodeId;
	}
	
	public PetersonLockNode getParentNode(){
		return parent;
	}
	
	public PetersonLockNode getRightChild(){
		return rightChild;
	}
	
	public PetersonLockNode getLeftChild(){
		return LeftChild;
	}
	
	@Override
	public int compareTo(PetersonLockNode o) {
		return nodeId.compareTo(o.getNodeId());
	}
	
	public void lock(Integer currentThreadId){
		flags.put(currentThreadId, true);
		victim.set(currentThreadId);
		
		while(otherThreadsWaiting(currentThreadId) && victim.get() == currentThreadId);
	}

	private boolean otherThreadsWaiting(Integer currentThreadId) {
		// TODO Auto-generated method stub
		
		return false;
	}

}
