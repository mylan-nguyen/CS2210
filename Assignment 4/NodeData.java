import java.util.ArrayList;

/* CS2210a 2021
 * Assignment 2
 * @author - Mylan Nguyen
 * @date - Oct 18, 2021 
 * 251155416
 * mnguy246 */

/*
* This class represents the data items that will be stored in the 
* internal nodes of the binary search tree implementing the ordered dictionary.
*/

public class NodeData {

	// Declare instance variables
	
	//This is the key attribute for the data stored in a node.
	private String name;
	private ArrayList<MultimediaItem> media;
	
	
	/* Constructor
	 * Creates a new NodeData object with the given key attribute and an empty media list.
	 * @param newName - string
	 */
	NodeData(String newName) {
		this.name = newName;
		this.media = new ArrayList<MultimediaItem>();
	}
	
	// PUBLIC METHODS
	
	// Method that adds newItem to the media list of this object.
	public void add(MultimediaItem newItem) {
		this.media.add(newItem);
	}
		
	// Method that returns the name attribute of this object.
	public String getName() {
		return name;
	}
	
	// Method that returns the media list stored in this object.
	public ArrayList<MultimediaItem> getMedia() {
		return media;
	}
	
}
