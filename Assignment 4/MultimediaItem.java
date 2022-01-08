/* CS2210a 2021
 * Assignment 2
 * @author - Mylan Nguyen
 * @date - Oct 18, 2021 
 * 251155416
 * mnguy246 */

/*
 * This class represents each one of the multimedia objects stored 
 * in the arrayList of a node of the binary search tree.
 */

public class MultimediaItem {

	// Declare instance variables
	
	// Descriptor of the multimedia content
	private String content;
	// The type of information represented by instance variable content
	private int type;
	
	/*
	 * Constructor
	 * Creates a new MultimediaItem object whose instance variables have the values specified by the parameters.
	 * @param newContent - 
	 * @param newType - 
	 */
	public MultimediaItem(String newContent, int newType) {
		this.content = newContent;
		this.type = newType;
	}
	
	// PUBLIC METHODS
	
	// Method that returns the content of this node.
	public String getContent() {
		return this.content;
	}
	
	// Method that returns the type of this node.
	public int getType() {
		return this.type;
	}
}
