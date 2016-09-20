package edu.vt.ece.locks;

import java.util.concurrent.atomic.AtomicInteger;

public class PetersonLockNode implements Comparable<PetersonLockNode> {

	private Integer nodeId;
	private boolean[] flags;
	private AtomicInteger victim = new AtomicInteger();
	
	PetersonLockNode parent, rightChild, LeftChild;
	
	public PetersonLockNode(Integer id, PetersonLockNode parentNode, Integer totalThreads){
		nodeId = id;
		parent = parentNode;
	}
	
	public Integer getNodeId(){
		return nodeId;
	}
	
	@Override
	public int compareTo(PetersonLockNode o) {
		return nodeId.compareTo(o.getNodeId());
	}
	
	public void lock(Integer currentThreadId){
		
	}

}
