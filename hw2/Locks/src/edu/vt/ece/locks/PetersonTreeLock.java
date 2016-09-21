package edu.vt.ece.locks;

import java.util.ArrayList;
import java.util.List;

public class PetersonTreeLock {

	private PetersonLockNode rootNode;
	private List<PetersonLockNode> leafNodes = new ArrayList<>();

	public PetersonTreeLock(int numberOfThreads) {
		createLockTree(numberOfThreads);
	}
	
	private void createLockTree(int numberOfThreads){
		//The assumption is that the number of threads will be power of 2
		int rootNodeId = (numberOfThreads/2) - 1;
		rootNode = new PetersonLockNode(rootNodeId, null);
		rootNode.setFlags(0, numberOfThreads - 1);
		createLeftTree(0, rootNodeId - 1, rootNode);
		createRightTree(rootNodeId + 1, numberOfThreads - 2, rootNode);
	}

	private void createLeftTree(int lowerBound, int upperBound, PetersonLockNode parentNode) {
		int currentNodeId = (lowerBound + upperBound) / 2;
		PetersonLockNode currentNode = new PetersonLockNode(currentNodeId, parentNode);
		parentNode.setLeftChild(currentNode);
		if(lowerBound == upperBound){			
			leafNodes.add(currentNode);
			return;
		}	
		createLeftTree(lowerBound, currentNodeId - 1, currentNode);
		createRightTree(currentNodeId + 1, upperBound, currentNode);
	}

	private void createRightTree(int lowerBound, int upperBound, PetersonLockNode parentNode) {
		int currentNodeId = (lowerBound + upperBound) / 2;
		PetersonLockNode currentNode = new PetersonLockNode(currentNodeId, parentNode);
		parentNode.setRightChild(currentNode);
		if(lowerBound == upperBound){			
			leafNodes.add(currentNode);
			return;
		}	
		createLeftTree(lowerBound, currentNodeId - 1, currentNode);
		createRightTree(currentNodeId + 1, upperBound, currentNode);
		
	}

	public PetersonLockNode getRoot() {
		return rootNode;
	}
	
	private PetersonLockNode getThreadLeafNode(int threadId){
		if(threadId % 2 == 1)
			return leafNodes.get(threadId - 1);
		else 
			return leafNodes.get(threadId);
	}
}
