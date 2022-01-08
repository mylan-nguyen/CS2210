import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;


/* CS2210a 2021
 * Assignment 5
 * @author - Mylan Nguyen
 * @date - Dec 7, 2021 
 * 251155416
 * mnguy246 */

/* 
 * This class represents the Labyrinth.
 */


public class Solver {
	
	// declare instance variables
	
	private Graph graph;
	private int scale, width, length;
	private int blastBomb, meltBomb;
	private Node startNode, endNode;
	
	/*
	 * Constructor
	 * Builds a labyrinth from input file
	 * If the input file does not exist, this method should throw a LabyrinthException
	 * @param inputFile - map 
	 */
	
	public Solver (String inputFile) throws LabyrinthException {
		try {
			
			// read in file
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			
			// Read in the text file information
			scale = Integer.parseInt(input.readLine());
			width = Integer.parseInt(input.readLine());
			length = Integer.parseInt(input.readLine());
			blastBomb = Integer.parseInt(input.readLine());
			meltBomb = Integer.parseInt(input.readLine());
			
			// define width and length of graph
			int w = width*2 - 1;
			int l = length*2 - 1;
			
			// create a matrix of size w and l
			char [][] labMap = new char[w][l];
			
			// read in the first line
			String lineInput = input.readLine();
			// define row 
			int row = 0;
			
			// create a loop to read in line by line while it is not null
			while (lineInput != null) {		
				// loop through the length of the line and translates its character in the graph
				for (int i = 0; i < lineInput.length(); i++) {
					labMap[i][row] = lineInput.charAt(i);
				}
				// read in the next line and increment row count to go to next row in file
				lineInput = input.readLine();
				row++;
			}
			
			// create a new graph of size w and l
			graph = new Graph(width*length);
			
			// define a counter
			int counter = 0;
			
			// loop through the entire length of the graph
			for (int i = 0; i < l; i++) {
				// and loop though the width of the graph
				for(int j = 0 ; j < w; j++) {
					// check even row
					if ( i % 2 == 0) { // we know it is a room here
						// check if position in row is even and increment count
						if (j % 2 == 0) {
							counter++;
							// determine if position is the entrance or exit and set it at the start or end node
							if (labMap[j][i] == 'e') // we know this is where the Entrance is at 
								startNode = graph.getNode(counter-1);
							if (labMap[j][i] == 'x')  // we know this is the Exit
								endNode = graph.getNode(counter-1);
						// if not even, row position is odd
						} else  // corridor area
							// Locate the Node from the position 
							graph.insertEdge(graph.getNode(counter-1), graph.getNode(counter), convertCharToType(labMap[j][i]));
						
					}
					// check odd row
					else { //when it is not a room
						if (j % 2 == 0) 
							//Locate the Node from the position, without width+j/2
							graph.insertEdge(graph.getNode(counter-width+j/2), graph.getNode(counter+j/2), convertCharToType(labMap[j][i]));
					}
				} // for j
			} // for i
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}

	// Private helper method to convert each P, H or V to 1,2,3,4 types of edges
	private int convertCharToType(char c) {
		// for horizontal or vertical corridors
		if (c == '-' || c == '|') {
			return 1;
		}
		// for horizontal or vertical brick walls
		else if (c == 'b' || c == 'B') {
			return 2;
		}
		// for horizontal or vertical rock walls
		else if (c == 'r' || c == 'R') {
			return 3;
		}
		// for horizontal or vertical metal walls
		else if (c == 'm' || c == 'M') {
			return 4;
		}
		// otherwise return zero
		return 0;
	}
	
	/*
	 * Returns a reference to the graph representing the labyrinth 
	 * Throws a Labyrinth Exception if the graph is not defined
	 */
	public Graph getGraph() throws LabyrinthException{
		// check if graph is null and throw exception error, otherwise return graph
		if (this.graph == null) {
			throw new LabyrinthException("Graph does not exist."); 
		} else {
			return this.graph;
		}
	}
	
	/*
	 * Returns a java Iterator containing the nodes along the path from the entrance to the exit of the labyrinth, if such a path exists
	 * If the path does not exist, this method returns the value null
	 */
	
	public Iterator<Node> solve() {

		try {
			// create a flag to add to path if found
			boolean flagForAddToPath = false;
			
			//create an array list to keep track of path taken
			ArrayList<Node> paths = new ArrayList<Node>();
			// set a tempNode and tempEdge to null
			Node tempNode = null;
			Edge tempEdge = null;
			
			//create another array stack to track the safest route to take
			ArrayList<Node> safeTravel = new ArrayList<Node>();
			// insert startNode into list
			safeTravel.add(startNode);
			//mark startNode at true 
			startNode.setMark(true);
			// define count as zero
			int count = 0;
			
			// create a while loop for when safeTravel array stack is not empty
			while (safeTravel.isEmpty() != true) {
				// set the tempNode to the last position
				tempNode = (Node)safeTravel.get(safeTravel.size()-1);
				// check if the room we are in is the exit door, if so then we stop traveling  
				if (tempNode.equals(endNode) == true) {
					return safeTravel.iterator();
				}

				// set flag to false
				flagForAddToPath = false;
				// create a list that holds the edges of the graph
				ArrayList<Edge> array = graph.incidentEdges(tempNode);
				// loop through the entire array size
				for (int i = 0; i < array.size(); i++) {
					// set tempEdge to the first position in array and tempNode to the secondEndpoint in the tempEdge
					tempEdge = array.get(i);
					tempNode = tempEdge.secondEndpoint();
					
					//determine type of edge
					//if the tempNode mark is false
					if (tempNode.getMark() == false) {
						// found a corridor
						if (tempEdge.getType() == 1) {
							tempNode.setMark(true);
							paths.add(tempNode);
							flagForAddToPath = true;
						// found a brick wall, can use a blast bomb to break it
						} else if (tempEdge.getType() == 2 && count > 2 && blastBomb > 0) {
							tempNode.setMark(true);
							paths.add(tempNode);
							flagForAddToPath = true;
						// found a rock wall, must have 2 blast bombs to break it
						} else if (tempEdge.getType() == 3 && blastBomb >= 2) {
							tempNode.setMark(true);
							paths.add(tempNode);
							flagForAddToPath = true;
						// found a metal wall, can only use 1 meltbomb to break it
						} else if (tempEdge.getType() == 4 && meltBomb > 0) {
							tempNode.setMark(true);
							paths.add(tempNode);
							flagForAddToPath = true;
						}
					}
				}
				
				// To travel back ward in event we could no longer continue on the same path
				if (flagForAddToPath != true) {
					// create a while loop
					while (safeTravel.size() > 0 && paths.size() > 0 && !graph.areAdjacent((Node)safeTravel.get(safeTravel.size()-1),(Node)paths.get(paths.size()-1))) {
						// set tmpNode to last node in safeTravel array
						Node tmpNode = (Node) safeTravel.remove(safeTravel.size()-1);
						// set tmpEdge to edge of tmpNode and last node in safeTravel array
						Edge tmpEdge = graph.getEdge(tmpNode, (Node) safeTravel.get(safeTravel.size()-1));
						// mark tmpNode as false
						tmpNode.setMark(false);
						// if brick wall is reached, increment blastBomb count
						if (tmpEdge.getType() == 2)
							blastBomb++;
						// if rock wall is reached, increment blastBomb count by 2
						else if (tmpEdge.getType() == 3)
							blastBomb = blastBomb + 2;
						// if metal wall is reached, increment meltBomb count
						else if (tmpEdge.getType() == 4)
							meltBomb++;
					}
					// return null is path size or safeTravel size equals zero
					if (paths.size() == 0 || safeTravel.size() == 0)
						return null;

				}
				// check if reached a brick wall, if so decrement blastBomb count
				if (graph.getEdge((Node)safeTravel.get(safeTravel.size()-1),(Node)paths.get(paths.size() - 1)).getType() == 2) {
					blastBomb--;
				// check if reached rock wall, if so decrement blastBomb count by 2
				} else if (graph.getEdge((Node)safeTravel.get(safeTravel.size()-1),(Node)paths.get(paths.size() - 1)).getType() == 3) {
					blastBomb = blastBomb - 2;
				// check if reached metal wall, if so decrement meltBomb count
				} else if (graph.getEdge((Node)safeTravel.get(safeTravel.size()-1),(Node)paths.get(paths.size() - 1)).getType() == 4) {
					meltBomb--;
				}
				// add the last path position removed to the safeTravel array list
				safeTravel.add(paths.remove(paths.size() - 1));
				// increment count
				count++;
			}
			return null;
		// catch exception error
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}
	
}
