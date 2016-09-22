package edu.vt.ece.locks;

//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
//import java.util.Map;

public class PetersonLockNode implements Comparable<PetersonLockNode> {

	private Integer nodeId;
	private Boolean[] waitingThreads;
	//private HashMap<Integer, Boolean> flags;
	private AtomicInteger victim = new AtomicInteger();
	
	private PetersonLockNode parent, rightChild, leftChild;
	
	public PetersonLockNode(Integer id, PetersonLockNode parentNode, Integer totalThreadCount){
		nodeId = id;
		parent = parentNode;
		waitingThreads = new Boolean[totalThreadCount];
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
	
	/*public void setFlags(Integer lowerBound, Integer upperBound){
		if(lowerBound < upperBound){
			for(Integer i = lowerBound; i < upperBound; i++){
				flags.put(i, false);
			}
		}
	}*/
	
	@Override
	public int compareTo(PetersonLockNode o) {
		return nodeId.compareTo(o.getNodeId());
	}
	
	public void lock(Integer currentThreadId){
		//System.out.println("Thread " + currentThreadId + " entered lock of node " + nodeId);
		waitingThreads[currentThreadId] = true;
		victim.set(currentThreadId);
		
		while(otherThreadsWaiting(currentThreadId) && victim.get() == currentThreadId);
		//System.out.println("Thread " + currentThreadId + " left lock of node " + nodeId);
	}

	private boolean otherThreadsWaiting(Integer currentThreadId) {
		// TODO Auto-generated method stub
		boolean otherThreadWaiting = false;
		for (int i = 0; i < waitingThreads.length; i++) {
			if (waitingThreads[i] != null && waitingThreads[i] && (i != currentThreadId)){
				otherThreadWaiting = true;
				break;
			}
		}
		/*for(Map.Entry<Integer, Boolean> item : flags.entrySet()){
			if(item.getValue() && item.getKey() != currentThreadId)
				return true;
		}*/
		return otherThreadWaiting;
	}
	
	public void unlock(Integer currentThreadId){
		waitingThreads[currentThreadId] = false;
		//System.out.println("Thread " + currentThreadId + " left unlock of node " + nodeId);
	}

}
