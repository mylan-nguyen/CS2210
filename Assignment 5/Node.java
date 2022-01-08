/* CS2210a 2021
 * Assignment 5
 * @author - Mylan Nguyen
 * @date - Dec 7, 2021 
 * 251155416
 * mnguy246 */

/* 
 * This class represents a node of the graph.
 */

public class Node {
	
	// declare instance variables
	private int name; // name of a node is an integer value between 0 and n-1
	private boolean mark; // initially the value of this variable is false
	
	/*
	 * Constructor
	 * Public method that creates an unmarked node with the given name
	 * @param nodeName - The given name to create a node
	 */
	Node (int nodeName) {
		this.name = nodeName;
		mark = false;
	}
	
	/* PUBLIC METHODS */
	
	
	// Public method that marks the node with the specified value
	public void setMark (boolean mark) {
		this.mark = mark;
	}
	
	// Public method that returns the value with which the node has been marked
	public boolean getMark() {
		return this.mark;
	}
	
	// Public method that returns the name of the node
	public int getName() {
		return this.name;
	}
	
	// Public method that returns true if this node has the same name as otherNode, otherwise return false
	boolean equals(Node otherNode) {
		if (this.name == otherNode.getName()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
}
