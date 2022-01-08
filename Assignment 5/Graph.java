import java.util.ArrayList;

/* CS2210a 2021
 * Assignment 5
 * @author - Mylan Nguyen
 * @date - Dec 7, 2021 
 * 251155416
 * mnguy246 */

/* 
 * This class represents an undirected graph.
 */

public class Graph implements GraphADT {
	
	// declare instance variables
	private Node[] nArray;
	private Edge[][] eMatrix;
	private int numNodes;
	
	/*
	 * Constructor
	 * Creates an empty graph with n nodes and no edges
	 * The names of the nodes are 0, 1, . . . , n−1
	 * @param n - size of node array and edge array
	 */
	public Graph(int n) {
		this.nArray = new Node[n];
		this.eMatrix = new Edge[n][n];
		this.numNodes = n;
		
		// name nodes from 0 to n-1 in the graph
		for (int i = 0; i < n; i++) {
			Node newNode = new Node(i);
			nArray[i] = newNode;
			
			// create edge matrix
			for (int j = 0; j < n; j++) {
				eMatrix[i][j] = null;
			}
		}	
	}
	
	/*
	 * Adds to the graph an edge connecting nodes u and v
	 * The type for this new edge is indicated by last parameters
	 * Throws a GraphException if either node does not exist or if there is already an edge connecting the given nodes
	 * @param u - node u that connects with edge
	 * @param v - node v that connects with edge
	 * @param edgeType - type of edge to be inserted
	 */
	public void insertEdge(Node u, Node v, int edgeType) throws GraphException {
		// check if name is not between range of 0 and size of array, else throw exception
		if (u.getName() < 0 || v.getName() < 0 || u.getName() > nArray.length-1 || v.getName() > nArray.length-1) {
			throw new GraphException("Invalid node.");
		} 
		else {
			// make an edge of Edge as a new eMatrix with names of the nodes
			Edge edge = eMatrix[u.getName()][v.getName()];
			
			// check if edge is not null 
			if (edge != null) {
				throw new GraphException("Edge already exists.");
			}
			else {
				eMatrix[u.getName()][v.getName()] = new Edge(u, v, edgeType);
				eMatrix[v.getName()][u.getName()] = new Edge(v, u, edgeType);
			}
		}
	}
	
	/*
	 * Returns the node with the specified name
	 * If no node with this name exists, the method should throw a GraphException
	 * @param name - name of the node that will be returned
	 */
	public Node getNode(int name) throws GraphException {
		// check if name is not between range of 0 and size of array, else throw exception
		if (name < 0 || name > numNodes) {
			throw new GraphException("Node does not exist.");
		}
		// return node at index name
		else {
			return nArray[name];
		}
	}
	
	/*
	 * Returns the node with the specified name
	 * If no node with this name exists, throw a GraphException
	 * @param u - node u that connects with edge
	 */
	public ArrayList<Edge> incidentEdges(Node u) throws GraphException {
		// check if node is in the array
		if (u.getName() < 0 || u.getName() > nArray.length-1) {
			throw new GraphException("Invalid node.");
		}
		else {
			// create an array list
			ArrayList<Edge> list = new ArrayList<Edge>();
			// loop through the column of the array and add it to arrayList
			for (int i = 0; i < nArray.length; i++) {
				if(eMatrix[u.getName()][i] != null) {
					list.add(eMatrix[u.getName()][i]);
				}
			}
			// return null if the list is empty
			if(list.isEmpty()) {
				return null;
		}
		//otherwise return the list
		return list;
		}
	}
	
	/*
	 * Returns edge connecting nodes u and v
	 * Method throws a GraphException if there is no edge between u and v
	 * @param u - node u that connects with edge
	 * @param v - node v that connects with edge
	 */
	public Edge getEdge(Node u, Node v) throws GraphException {
		// check if name is not between range of 0 and size of array, else throw exception
		if (u.getName() < 0 || v.getName() < 0 || u.getName() > nArray.length-1 || v.getName() > nArray.length-1) {
			throw new GraphException("Invalid node.");
		}
		else if (eMatrix[u.getName()][v.getName()] != null) {
			return eMatrix[u.getName()][v.getName()];
		}
		else {
			throw new GraphException("Edge does not exist.");
		}
	}
	/*
	 * Returns true if and only if nodes u and v are adjacent
	 * Method throws a GraphException if u and v are not nodes of the graph
	 * @param u - node u that connects with edge
	 * @param v - node v that connects with edge
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		// check if name is not between range of 0 and size of array, else throw exception
		if (u.getName() < 0 || v.getName() < 0 || u.getName() > nArray.length-1 || v.getName() > nArray.length-1) {
			throw new GraphException("Invalid node.");
		}
		else if (eMatrix[u.getName()][v.getName()] != null) {
			return true;
		}
		else {
			return false;
		}				
	}
}
