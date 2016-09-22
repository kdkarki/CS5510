package edu.vt.ece.locks;

import java.util.ArrayList;
import java.util.List;

public class PetersonTreeLock implements Lock {

	private PetersonLockNode rootNode;
	private List<PetersonLockNode> leafNodes = new ArrayList<>();

	public PetersonTreeLock(int numberOfThreads) {
		createLockTree(numberOfThreads);
	}
	
	private void createLockTree(int numberOfThreads){
		//The assumption is that the number of threads will be power of 2
		int rootNodeId = (numberOfThreads/2) - 1;
		if (rootNodeId < 0) rootNodeId = 0;
		rootNode = new PetersonLockNode(rootNodeId, null, numberOfThreads);
		//rootNode.setFlags(0, numberOfThreads - 1);
		if(rootNodeId > 0){
			createLeftTree(0, rootNodeId - 1, rootNode, numberOfThreads);
			createRightTree(rootNodeId + 1, numberOfThreads - 2, rootNode, numberOfThreads);
		}
		else
			leafNodes.add(rootNode);
	}

	private void createLeftTree(int lowerBound, int upperBound, PetersonLockNode parentNode, Integer numberOfThreads) {
		int currentNodeId = (lowerBound + upperBound) / 2;
		PetersonLockNode currentNode = new PetersonLockNode(currentNodeId, parentNode, numberOfThreads);
		parentNode.setLeftChild(currentNode);
		if(lowerBound == upperBound){			
			leafNodes.add(currentNode);
			return;
		}	
		createLeftTree(lowerBound, currentNodeId - 1, currentNode, numberOfThreads);
		createRightTree(currentNodeId + 1, upperBound, currentNode, numberOfThreads);
	}

	private void createRightTree(int lowerBound, int upperBound, PetersonLockNode parentNode, Integer numberOfThreads) {
		int currentNodeId = (lowerBound + upperBound) / 2;
		PetersonLockNode currentNode = new PetersonLockNode(currentNodeId, parentNode, numberOfThreads);
		parentNode.setRightChild(currentNode);
		if(lowerBound == upperBound){			
			leafNodes.add(currentNode);
			return;
		}	
		createLeftTree(lowerBound, currentNodeId - 1, currentNode, numberOfThreads);
		createRightTree(currentNodeId + 1, upperBound, currentNode, numberOfThreads);
		
	}

	public PetersonLockNode getRoot() {
		return rootNode;
	}
	
	private PetersonLockNode getThreadLeafNode(int threadId){
		return leafNodes.get(threadId/2);
	}

	@Override
	public void lock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lock(int threadId) {
		// TODO Auto-generated method stub
		PetersonLockNode threadNode = getThreadLeafNode(threadId);
		while(threadNode != null){
			threadNode.lock(threadId);
			threadNode = threadNode.getParentNode();
		}
	}

	@Override
	public void unlock(int threadId) {
		// TODO Auto-generated method stub
		/*PetersonLockNode threadNode = getThreadLeafNode(threadId);
		PetersonLockNode currentNode = rootNode;
		while(currentNode != threadNode){
			currentNode.unlock(threadId);
		}
		threadNode.unlock(threadId);
		*/
		PetersonLockNode currentNode = rootNode;
		PetersonLockNode leafNode = getThreadLeafNode(threadId);
		
		while(currentNode != null){
			currentNode.unlock(threadId);
			if(currentNode.getNodeId() > (leafNode.getNodeId()))
				currentNode = currentNode.getLeftChild();
			else
				currentNode = currentNode.getRightChild();
		}
	}
}
