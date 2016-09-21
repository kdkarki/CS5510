package edu.vt.ece.locks;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

public class PetersonLockNode implements Comparable<PetersonLockNode> {

	private Integer nodeId;
	private Hashtable<Integer,Boolean> flags;
	private AtomicInteger victim = new AtomicInteger();
	
	private PetersonLockNode parent, rightChild, leftChild;
	
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
	
	public void setRightChild(PetersonLockNode rightNode){
		rightChild = rightNode;
	}
	
	public PetersonLockNode getLeftChild(){
		return leftChild;
	}
	
	public void setLeftChild(PetersonLockNode leftNode){
		leftChild = leftNode;
	}
	
	public void setFlags(Integer lowerBound, Integer upperBound){
		if(lowerBound < upperBound){
			for(Integer i = lowerBound; i < upperBound; i++){
				flags.put(i, false);
			}
		}
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
		for(Map.Entry<Integer, Boolean> item : flags.entrySet()){
			if(item.getValue() && item.getKey() != nodeId)
				return true;
		}
		return true;
	}
	
	public void unlock(Integer currentThreadId){
		flags.put(currentThreadId, false);
	}

}
