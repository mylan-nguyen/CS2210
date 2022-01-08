/* CS2210a 2021
 * Assignment 4
 * @author - Mylan Nguyen
 * @date - Nov 14, 2021 
 */

/* 
 * This class represents the nodes of the binary search tree implementing the ordered dictionary.
 */

public class BSTNode {

	// Declare instance variables
	private BSTNode parent, leftChild, rightChild;
	private NodeData data; 
	
	/*
	 * Constructor 
	 * Creates a new BSTNode object in which all its instance variables have value null.
	 */
	public BSTNode () {
		this.parent = null;
		this.leftChild = null;
		this.rightChild = null;
		this.data = null;
	}
	
	/*
	 * Creates a BSTNode in which the instance variables take the values specified in parameters.
	 * @param newParent 
	 * @param newLeftChild
	 * @param newRightChild
	 * @param newData
	 */
	public BSTNode (BSTNode newParent, BSTNode newLeftChild, BSTNode newRightChild, NodeData newData) {
		this.parent = newParent;
		this.leftChild = newLeftChild;
		this.rightChild = newRightChild;
		this.data = newData;
	}
	// PUBLIC METHODS
	
	// Returns the parent of the node
	public BSTNode getParent() {
		return this.parent;
	}
	
	// Returns the left child of the node
	public BSTNode getLeftChild() {
		return this.leftChild;
	}

	// Returns the right child of the node
	public BSTNode getRightChild() {
		return this.rightChild;
	}
	
	// Returns the NodeData object stored in this node
	public NodeData getData() {
		return this.data;
	}
	
	// Sets the parent of this node to the specified value
	public void setParent(BSTNode newParent) {
		this.parent = newParent;
	}

	// Sets the left child of this node to the specified value
	public void setLeftChild(BSTNode leftChild) {
		this.leftChild = leftChild;
	}
	//sets the right child of the node//
	public void setRightChild(BSTNode rightChild) {
		this.rightChild = rightChild;
	}
	//stores the given NodeData in the node// 
	public void setData(NodeData newData) {
		this.data = newData;
	}
	//returns true if this node is a leaf, false otherwise//
	public boolean isLeaf() {
		if (this.leftChild == null && this.rightChild == null && this.data == null) {
			return true;
		} else {
			return false;
		}
	}
	
}
