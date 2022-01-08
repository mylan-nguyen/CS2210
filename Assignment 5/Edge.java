/* CS2210a 2021
 * Assignment 5
 * @author - Mylan Nguyen
 * @date - Dec 7, 2021 
 * 251155416
 * mnguy246 */

/* 
 * This class represents an edge of the graph.
 */

public class Edge {
	// declare instance variables
	private Node firstEndpoint, secondEndpoint;
	
	private int type;
	
	/**
	 * Constructor
	 * Creates and edge of the given type connecting nodes u and v
	 * @param u - Edge end point one
	 * @param v - Edge end point two
	 * @param type - The type of edge
	 */
	Edge (Node u, Node v, int edgeType) {
		firstEndpoint = u;
		secondEndpoint = v;
		this.type = edgeType;
	}
	
	/* PUBLIC METHODS */
	
	// Public method that returns the first endpoint of the edge
	public Node firstEndpoint() {
		return this.firstEndpoint;
	}
	
	// Public method that returns the second endpoint of the edge
	public Node secondEndpoint() {
		return this.secondEndpoint;
	}
	
	//Public method that returns the type of edge
	public int getType() {
		return this.type;
	}
	
	//Public method that sets the type of the edge to the specified value
	public void setType (int newType) {
		this.type = newType;
	}
	
	//Public method returns true if this Edge object connects the same two nodes as otherEdge
	public boolean equals(Edge otherEdge) {
		if (otherEdge.firstEndpoint.getName()==secondEndpoint.getName() && otherEdge.secondEndpoint.getName()==firstEndpoint.getName()) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
