/* CS2210a 2021
 * Assignment 2
 * @author - Mylan Nguyen
 * @date - Oct 18, 2021 
 * 251155416
 * mnguy246 */

/*
* This class represents each board layout as a String.
*/

public class Layout {
	
	// declare instance variables 
	private String boardLayout;
	private int score;
	
	/*
	 * Constructor that initializes a new Layout object with the data provided.
	 * @param boardLayout - String configuration of the board
	 * @param score - int score associated with this configuration
	 */
	public Layout(String boardLayout, int score) {
		this.boardLayout = boardLayout;
		this.score = score;
	}
	
	/*
	 * @return - returns boardLayout key attribute stored in a Layout object
	 */
	public String getBoardLayout() {
		return boardLayout;
	}
	
	/*
	 * @return - returns the score stored in a Layout object
	 */
	public int getScore() {
		return score;
	}
	
}
