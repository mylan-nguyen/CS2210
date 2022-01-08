/* CS2210a 2021
 * Assignment 2
 * @author - Mylan Nguyen
 * @date - Oct 18, 2021 
 */

/*
 ** This class implements a dictionary using a hash table with separate chaining.
 */

public class Dictionary implements DictionaryADT {
	
	/* 
	 * Declare instance variables
	 * @size - store table size
	 * @hashTable - stores nodes in an array
	 */
	private int size;
	private Node[] hashTable;
	
	
	/*
	 * Constructor that initializes a dictionary with an empty hash table of the specified size.
	 * @param size - int size of the board
	 */
	public Dictionary(int size) {
		this.size = size;
		// initialize hashTable with specified size
		hashTable = new Node[size];
		// create a for loop to fill hashTable with data entries at every available node until full
		for (int i = 0; i < size; i++) { 
			hashTable[i] = null; 
		}
	}
	
	/*
	 * Method that inserts the Layout object with the data provided into hash table.
	 * @param data - information to be inserted (type: Layout)
	 * @exception - DictionaryException thrown if data already exists in dictionary
	 * @return - 0 if list stored already stores at least one element (no collision)
	 * @return - 1 if there is null or an empty list (collision)
	 */
	public int put(Layout data) throws DictionaryException {
		
		// determine index of hashTable  
		int index = hashFunction(data.getBoardLayout());
		
		// initialize the front element of the hash table at index
		Node frontNode = hashTable[index];
		
		// while the frontNode is not null
		while (frontNode != null) {
			// check if dictionary already contains any object with the same key attribute as data
			// if true, throw DictionaryException
			if(frontNode.getData().equals(data.getBoardLayout()) | this.getScore(data.getBoardLayout()) != -1) {
				throw new DictionaryException(data);
			}
			// set the frontNode to the next node from it
			frontNode = frontNode.getNextNode();
		}
		
		// create a new node 
		frontNode = hashTable[index];
		Node newNode = new Node(data, null);
		
		// check if frontNode is null 
		if (frontNode == null) {
			// if so, set newNode as the first element at that index of the hash table
			hashTable[index] = newNode;
			// return 0 since no collision occurred
			return 0;
		}
		else {
			// set newNode after the frontNode
			newNode.setNextNode(frontNode);
			// set newNode as the first element at that index of the hash table
			hashTable[index] = newNode;
			// return 1 since collision occurred
			return 1;
		}
	}

	/*
	 * Method removes object with key boardLayout from the dictionary.
	 * @param boardLayout - String configuration of the board
	 */
	public void remove(String boardLayout) throws DictionaryException {
		// determine index of hashTable  
		int index = hashFunction(boardLayout);
		
		// initialize the front element of the hash table at index
		Node frontNode = hashTable[index];
		
		// initialize the previous element of the hash table as null
		Node prevNode = null;
		
		// check if the first element of the hash table is null 
		if (hashTable[index] == null) {
			throw new DictionaryException("There is no data item in the dictionary with this key.");
		}
		
		// create a while loop for when the frontNode is not null
		while (frontNode != null) {
			// check if string layout is 
			if (frontNode.getData().getBoardLayout().equals(boardLayout)) {
				// break since object has been removed
				break;
			}
			else {
				prevNode = frontNode;
				frontNode = frontNode.getNextNode();
			}
		}		
	}
	
	/*
	 * Method returns the score stored in the object in the dictionary with key boardLayout.
	 * @param boardLayout - String configuration of the board
	 * @return - score stored, otherwise -1 if no object in dictionary with that key
	 */
	public int getScore(String boardLayout) {
		// determine index of hashTable  
		int index = hashFunction(boardLayout);
		
		// initialize the front element of the hash table at index
		Node headNode = hashTable[index];
		
		// create a while loop for when the frontNode is not null
		while(headNode != null) {
			
			// check if the key matches object in the dictionary 
			if(headNode.getData().getBoardLayout().equals(boardLayout)) {
				// return score
				return headNode.getData().getScore();
			}
			else {
				headNode = headNode.getNextNode();
			}
		}
		// return -1 if there is no object in the dictionary with that key
		return -1;

	}
	
	/*
	 * Method determines the hash code (index) of the hash table.
	 * @param str - String configuration used to calculate hash code
	 * @return key - position the node should be put in for the hash table
	 */
	private int hashFunction(String str) {
		// set a prime number
		int M = 43;
		// declare variable used in hash as a multiplier
        int result = 0;
        
        // iterate backwards through the length of the string
        for(int i = str.length() - 1; i >= 0; i--)
        	// polynomial hash to hash each character in string
        	// DO: USE MOD SIZE
            result = ((result + str.charAt(i))* M) % this.size;
        
        // return hashcode for the string
        return result;
	}
	
}
