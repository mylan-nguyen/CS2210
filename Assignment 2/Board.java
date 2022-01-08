/* CS2210a 2021
 * Assignment 2
 * @author - Mylan Nguyen
 * @date - Oct 18, 2021 
 */

/*
* This class stores information about the tiles placed on the game-board
* and implements support methods needed by the algorithm that plays the game.
*/

public class Board implements BoardADT {
	
	// declare instance variables
	private int board_size;
	private int empty_positions;
	private int max_levels;
	private char[][] theBoard;
	
	/* Constructor 
	 * @param board size - int variable for size of the board
	 * @param empty_positions - int variable with the number of positions on the board that must remain empty
	 * @param max_levels - int variable that specifies the playing quality 
	 */
	public Board (int board_size, int empty_positions, int max_levels) {
		this.board_size = board_size;
		this.empty_positions = empty_positions;
		this.max_levels = max_levels;
		
		// initialize variable that every entry of theBoard stores 'e'
		theBoard = new char[board_size][board_size];
		// loop through the 2-D array and store 'e'
		// for every column
		for(int i = 0; i < board_size; i++) {
			// for every row in the column
			for(int j = 0; j < board_size; j++) {
				theBoard[i][j] = 'e';
			}
		}
		
	}
	
	
	// METHODS
	
	/* Method returns an empty Dictionary of the size that you have selected
	 */
	public Dictionary makeDictionary() {
		// declare size
		int size = 10000;
		// create a new dictionary of declared size
		Dictionary dict = new Dictionary(size);
		return dict;
		
	}
	
	/* Method represents the content of 2-D array theBoard as a String and checks 
	 * whether there is a data item in the dictionary
	 * returns associated score, otherwise -1
	 * @ param dict - hash dictionary 
	 */
	public int repeatedLayout(Dictionary dict) {
		// declare String s
		String s = "";
		// check if there is a data item in the dictionary referenced by dict with the key s
		
		// for every column 
		for (int i = 0; i < theBoard.length; i++) {
			// for every row in column
			for ( int j = 0; j < theBoard.length; j++) {
				// concatenate elements to a string
				s += theBoard[i][j];
			}
		}
		// return associated score
		return dict.getScore(s);
	}
	
	/* Method represents the content of theBoard as a String and creates a Layout
	 * object storing score in dict
	 * @ param dict - hash dictionary
	 * @ param score - score of the string
	 */
	public void storeLayout(Dictionary dict, int score) {
		// declare String s
		String s = "";
		
		// for every column 
		for (int i = 0; i < theBoard.length; i++) {
			// for every row in column
			for ( int j = 0; j < theBoard.length; j++) {
				// concatenate elements to a string
				s += theBoard[i][j];
			}
		}
		// create a Layout object storing s and score and stores this object in dict
		Layout boardLayout = new Layout(s, score);
		
		// catch exception if data item is already in the dictionary
		try {
			dict.put(boardLayout);
		} 
		catch (DictionaryException error) {
		}
	}
	
	/* Method stores symbol in theBoard[row][col]
	 * @ param row - row of board
	 * @ param col - col of board
	 * @ param symbol - represents position tile type
	 */
	public void saveTile(int row, int col, char symbol) {
		theBoard[row][col] = symbol;
	}
	
	/* Method returns true if theBoard[row][col] is 'e', otherwise false
	 * @ param row - row of board
	 * @ param col - col of board
	 */
	public boolean positionIsEmpty(int row, int col) {
		 // check if a position in a column of a row is 'e' for empty
		if (theBoard[row][col] == 'e') {
			return true;
		}
		// if not empty return false
		else {
			return false;
		}
	}
	
	/* Method returns true if theBoard[row][col] is 'c', otherwise false
	 * @ param row - row of board
	 * @ param col - col of board
	 */
	public boolean isComputerTile (int row, int col) {
		// check if a position in a column of a row is 'c' for computer tile
		if (theBoard[row][col] == 'c') {
			return true;
		}
		// if not a computer tile return false
		else {
			return false;
		}
	}
	
	/* Method returns true if theBoard[row][col] is 'h', otherwise false
	 * @ param row - row of board
	 * @ param col - col of board
	 */
	public boolean isHumanTile (int row, int col) {
		// check if a position in a column of a row is 'h' for human tile
		if (theBoard[row][col] == 'h' ) {
			return true;
		}
		// if not human tile return false
		else {
			return false;
		}
		
	}
	
	/* Method returns true if there are n adjacent tiles of type symbol in same
	 * row, column, or diagonal of theBoard
	 * @ param symbol - represents position tile type
	 */
	public boolean winner (char symbol) {
		// CHECK DIFFERENT ADJACENT POSITIONS
		
		// SITUATION 1: HORIZONTAL
		// for every column
		for (int i = 0; i < theBoard.length; i++) {
			// for every row in column
			for (int j = 0; j < theBoard.length; j++) {
				// check if the row is not the matching symbol
				if (theBoard[i][j] != symbol) {
					break;
				} 
				// check if there is 1 symbol spot left in a row needed to win
				if (j == theBoard.length-1) {
					return true;
				}
			}
		}
		
		// SITUATION 2: VERTICAL
		// for every column
		for (int i = 0; i < theBoard.length; i++) {
			// for every row in column
			for (int j = 0; j < theBoard.length; j++) {
				// check if the column is not the matching symbol
				if (theBoard[j][i] != symbol) {
					break;
				} 
				// check if there is 1 symbol spot left in a row needed to win
				if (j == theBoard.length-1) {
					return true;
				}
			}
		}
		
		// SITUATION 3: CHECK DIAGONALS 
		// Check diagonal from top left to bottom right
		// declare variable to increment in loop to count number of positions in a row with matching symbol
		int leftToRight = 0;
		for(int i = 0; i < theBoard.length; i++) {
			// check if position matches symbol
			if(theBoard[i][i] == symbol) {
				// if so, increment variable
				leftToRight++;
			}	
		}
		// if variable to count number of matching symbols in a diagonal equal the board length
		if(leftToRight == theBoard.length) {
			// there is a diagonal win
			return true;
		}
					
		// Check diagonal from bottom left to top right
		// declare variable to increment in loop to count number of positions in a row with matching symbol
		int bottomLeftTopRight = 0;
		for(int i = 0; i < theBoard.length; i++) {
			// check if position matches symbol
			if(theBoard[i][theBoard.length-i-1] == symbol) {
				// if so, increment variable
				bottomLeftTopRight++;
			}
		}
		// if variable to count number of matching symbols in a diagonal equal the board length
		if(bottomLeftTopRight == theBoard.length) {
			// there is a diagonal win
			return true;
		}
		// if no situation is satisfied, there is no win
		return false;
	}
	
	/* Method returns true if the game layout corresponding to theBoard is a draw 
	 * assuming that the player that must perform the next move uses tiles of that type specified by symbol
	 * @ param symbol - represents position tile type
	 * @ param empty_positions - represents the number of positions of the game-board that must remain empty
	 */
	public boolean isDraw(char symbol, int empty_positions) {
		// check if there are spots available in the board for a player to move
		int spotsAvailable = 0;
		// for every column
		for ( int i = 0; i < theBoard.length; i++) {
			// for every row in column
			for (int j = 0; j < theBoard.length; j++) {
				// check if position is not marked empty 'e'
				if (theBoard[i][j] != 'e') {
					// if not, then increment amount of spots available to move
					spotsAvailable++;
				}
			}
		}
		// check if there are no empty positions left on the board
		if (empty_positions == 0 && spotsAvailable == (theBoard.length * theBoard.length)) {
			return true;
		}
		// check if the number of empty positions on the game board is equal to empty_positions
		// and none of the empty positions on the game board has a tile of the type
		// specified by symbol adjacent to it
		else if (empty_positions > 0 && spotsAvailable == (theBoard.length * theBoard.length) - empty_positions) {
			// for every column
			for (int i = 0; i < theBoard.length; i++) {
				// for every row in column
				for ( int j = 0; j < theBoard.length; j++) {
					// if there is an empty spot available
					if (theBoard[i][j] == 'e') {
						try {
							// check position under empty cell
							if (theBoard[i+1][j] == symbol) {
								return false;
							}
							// check position above empty cell
							if (theBoard[i-1][j] == symbol) {
								return false;
							}
							// check right of empty cell
							if (theBoard[i][j+1] == symbol) {
								return false;
							}
							//check left of empty cell
							if (theBoard[i][j-1] == symbol) {
								return false;
							}
							// check top right around empty cell
							if (theBoard[i+1][j+1] == symbol) {
								return false;
							}
							// check bottom left around empty cell
							if (theBoard[i-1][j-1] == symbol) {
								return false;
							}
							// check bottom right around empty cell
							if (theBoard[i+1][j-1] == symbol) {
								return false;
							}
							// check bottom left around empty cell
							if (theBoard[i-1][j+1] == symbol) {
								return false;
							}
						}
						catch (IndexOutOfBoundsException e) {
							System.out.println("Out of bounds.");
						}
					}
					// if criteria is met, return true
					else {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/* Method returns a score after evaluating the board play options
	 * @param symbol - represents position tile type
	 * @ param empty_positions - represents the number of positions of the game-board that must remain empty
	 */
	public int evaluate(char symbol, int empty_positions) {
		// SITUATION 1: Computer wins, return 3
		if (winner('c') == true) {
			return 3;
		}
		// SITUATION 2: Human wins, return 0
		else if (winner('h') == true) {
			return 0;
		}
		// SITUATION 3: Game is a draw, return 2
		else if (isDraw(symbol, empty_positions) == true) {
			return 2;
		}
		// SITUATION 4: Game is still undecided, return 1
		else {
			return 1;
		}
	}

}
