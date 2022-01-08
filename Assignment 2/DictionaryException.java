/* CS2210a 2021
 * Assignment 2
 * @author - Mylan Nguyen
 * @date - Oct 18, 2021 
 * 251155416
 * mnguy246 */


public class DictionaryException extends Exception {
	
	/**
	 * Constructor for when Layout object is passed.
	 * @param data - information to be inserted (type: Layout)
	 */
	public DictionaryException(Layout data) {
		super("The layout is already in the dictionary.");
	}
	
	/**
	 * Constructor for when a String is passed.
	 * @param message -  String with error message
	 */
    public DictionaryException (String message){
        super (message);
    }
    

}