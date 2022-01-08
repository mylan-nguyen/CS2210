import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/* CS2210a 2021
 * Assignment 2
 * @author - Mylan Nguyen
 * @date - Oct 18, 2021 
 * 251155416
 * mnguy246 */

/*
 * This class implements a text-based application that allows 
 * the user to access the information in the multimedia ordered dictionary.
 */

public class Query {

	
	private static void get(BSTOrderedDictionary dict, BSTNode n, String key) throws MultimediaException {
		
		// use the dictionary the create a multimediaitem list
		ArrayList<MultimediaItem> list = dict.get(n, key);
		
		// if the list is null, print that the ordered dictionary does not have that key
		if (list == null) {
			System.out.println("The word " + key +" is not in the ordered dictionary.");
			
			// transfer tree into a list 
			// created a temp arrayList to keep track of all the values from small to large
			ArrayList<NodeData> listNode = new ArrayList<NodeData>();
			
			// find the smallest in that dictionary
			NodeData smallest = dict.smallest(n);
			
			// find the largest in that dictionary
			NodeData largest = dict.largest(n);
			
			// add the smallest found to the temp arrayList made
			listNode.add(smallest);
			
			// get the successor of the smallest to add in to the arrayList
			NodeData successor = dict.successor(n, smallest.getName());
			// while, the successor of the smallest of the is not the largest
			while (successor != largest) {
				// add the successor found into the listnode 
				listNode.add(successor);			
				// find the successor of the current node
				successor = dict.successor(n, successor.getName());
			}
			// add the successor found to the listNode created
			listNode.add(successor);
			// declare prev and next string 
			String prev ="", next ="";
			
			// check case if the key is smaller than the smallest item in the list
			if (key.compareTo(listNode.get(0).getName()) < 0) {
				System.out.println("Preceding word: " + prev);
				System.out.println("Following word: " + listNode.get(0).getName());
				return;
			}
			
			// create a for loop to track the previous and next node in the list
			for (int i = 1; i < listNode.size(); i++) {
				prev = listNode.get(i-1).getName();
				next = listNode.get(i).getName();
				
				// if find a node with a value less than the key, print its previous and next node
				if (key.compareTo(listNode.get(i).getName()) < 0) {
					System.out.println("Preceding word: " + prev);
					System.out.println("Following word: " + next);
					// exit the method
					return;
				}
			}
			// check case if the key is greater than the largest item in the list
			System.out.println("Preceding word: " + next);
			System.out.println("Following word: ");			
			// exit the method
			return;
		}
		
		// create a for loop to check the type of file is in the temp arrayList or not 
		for (int i = 0; i < list.size(); i++) {
			try {			
				// get the type of i in the list
				int type = list.get(i).getType();
				if ( type == 2) {
					File tempFile = new File(list.get(i).getContent());
					boolean exists = tempFile.exists();
					System.out.println("is the file exist = " + exists);
					if ( exists == true) {
						SoundPlayer player = new SoundPlayer();
					    player.play(list.get(i).getContent());
					} else {
						System.out.println("The audio media file " + list.get(i).getContent() + " does not exist");
						throw new MultimediaException("file not found");
					}
                // if type 3 is found, display the picture
				} else if ( type == 3) {
					File tempFile = new File(list.get(i).getContent());
					boolean exists = tempFile.exists();
					System.out.println("is the file exist = " + exists);
                    // if the file name exist. display the image found in key
					if ( exists == true) {
						PictureViewer viewer = new PictureViewer();
					    viewer.show(list.get(i).getContent());
                    // for case if file is not in key
                    // if the file name exist. display the image found in key
					} else {
						System.out.println("The image media file " + list.get(i).getContent() + " does not exist");
						throw new MultimediaException("file not found");
					}
                // if type 4 is found, display the html
				} else if ( type == 4) {
					File tempFile = new File(list.get(i).getContent());
					boolean exists = tempFile.exists();
					System.out.println("is the file exist = " + exists);
					if ( exists == true) {
						ShowHTML open = new ShowHTML();
						open.show(list.get(i).getContent());
					} else {
						System.out.println("The html file " + list.get(i).getContent() + " does not exist");
						throw new MultimediaException("file not found");
					}
                // print the text contents in file 
				} else {
					System.out.println(list.get(i).getContent());	
				}
			} catch (MultimediaException e) {
			    System.out.println(e.getMessage());
			}
		}	
	}
 
	
	public static void main(String[] args) throws Exception {
		
		// create a new dictionary of BSTOrderedDictionary
		BSTOrderedDictionary dict = new BSTOrderedDictionary();
		// define r as the root of the dictionary
		BSTNode r = dict.getRoot();
		
		try {
			// read in the file 
			File file = new File(args[0]); 
			FileReader fr = new FileReader(file); 
			BufferedReader br = new BufferedReader(fr);  
			String key = "", content = "";
			int type = 0;
			
			// while the file is not null
			while ((key = br.readLine()) != null) {
				// determine what type of file is being looked for
				content = br.readLine();
				// for audio files
				if (content.endsWith(".wav") || content.endsWith(".mid")) {
					type = 2;
				}
				// for image files
				else if (content.endsWith(".jpg")  || content.endsWith(".gif")) {
					type = 3;
				}
				// for web files
				else if (content.endsWith(".html")) {
					type = 4;
				}	
				// for text
				else {
					type = 1;
				}
				// use the put method the put items in the dictionary created and build the tree
				dict.put(r, key, content, type);
				 // set number of internal nodes to 1 to reset the tree root to the first node of the tree and avoid null
				if (dict.getNumInternalNodes() == 1)
					r = dict.getRoot();
			}
			// close the file
			fr.close(); 
			
			// read in the string typed by used
			StringReader keyboard = new StringReader();
			// prompt to enter command
			String line = keyboard.read("Enter next command: ");	
			// while the user does not type in "end" continue the while loop
			while (!line.equals("end")) {
				
				// use string tokenizer to split the line with command wanted
				StringTokenizer st = new StringTokenizer(line);
				// find command
				String command = st.nextToken();
				
				/* ADD */
				// If no node in the tree stores key k then a new node p with that key is added to the tree;
				// otherwise your program finds the node p storing key k. 
				// A MultimediaItem storing content c and type t is added to the ArrayList of p.
				if (command.equals("add")) {
					// find the key
					String kKey = st.nextToken();
					// get the content wanted
					String cContent = st.nextToken();
					// define the type of file to add
					int tType = 0;
					try {
						tType = Integer.parseInt(st.nextToken());
					// make sure type is an option (1-4 only)
					} catch (NumberFormatException e) {
						System.err.println("Invalid input, expect an input integer for type.");
					}
					// add(dict, r, kKey, cContent, tType);	
					dict.put(r, kKey, cContent, tType);  
				
				}	
				/* GET */
				// If the BSTOrderedDictionary has a node p with key attribute k, then each one of the
				// MultimediaItem objects in the ArrayList stored in that node will be processed in the following manner.
				else if (command.equals("get")) {
					// find key
					String kKey = st.nextToken();	
					// calll get method of BSTOrderedDictionary
					get(dict, r, kKey);		
					
									
				/* REMOVE */
				// Removes from the ordered dictionary the node with key attribute k, or if no node stores this key your program must print
				} else if (command.equals("remove")) {
					// find the key
					String kKey = st.nextToken();
					
					// check if the use included too many arguments in its input
					if (st.hasMoreTokens()) {
						System.out.println("invalid input, too many arguments. Please enter: remove key");
						break;
					}
					
					try {
						// remove the node asked by the user
						dict.remove(r, kKey);
						
					// catch expections if the key is not in the dictionary
					} catch (NullPointerException e) {
						System.out.println("No record in the ordered dictionary has key " + kKey + ".");
					} catch (DictionaryException e) {
						System.out.println("No record in the ordered dictionary has key " + kKey + ".");
					}
				
				// CALLING THE SECOND REMOVE METHOD IN THE ORDERED DICTIONARY HERE
				/* REMOVE PART 2*/
				// If a node p with key attribute k is in the tree, this command must delete from the ArrayList
				// of p all the MultimediaItem objects with type t
				} else if (command.equals("delete")) {
					// find the key entered by user
					String kKey = st.nextToken();
					// find type to delete in the array list of the key
					int deleteType = Integer.parseInt(st.nextToken());
					// create a string the delete contents of specific type
					String deleteMedia = "";
					
					try {
						// call to remove the specific file type from node
						dict.remove(r, kKey, deleteType);
						
					// catch expections if the key is not in the dictionary
					} catch (NullPointerException e) {
						System.out.println("No record in the ordered dictionary has key " + kKey+ ".");
					} catch (DictionaryException e) {
						System.out.println("No record in the ordered dictionary has key " + kKey + ".");
					}
					
				/*SUCCESSOR*/
				/* This command must find the node p with key attribute k or, if this node does not exist, the leaf
					node p where the key attribute k should have been stored. Then your program must find the
					d successor nodes of p (if they exist) and print the key attribute of p (if p is an internal node)
					and the key attributes of the d successors (if they exist) in lexicographic increasing order */
					
				} else if (command.equals("next")) {
					// find key entered by user
					String kKey = st.nextToken();
					// create a counter for number of successor wanted to be found
					int numSuccessor = 1;
					// create a string to hold the successor(s)
					String successor = "";
					
					// check if the input is valid
					boolean isInputValid = true;
					
					try {
						numSuccessor = Integer.parseInt(st.nextToken());
					// catch case if the input of integers is valid (must be a number)
					} catch (NumberFormatException e) {
						isInputValid = false;
						System.err.println("Invalid input, expect an input integer for type.");
					}
					
					// follow try catch if the input is valid
					if ( isInputValid = true) {
						try {
							
							// find successor of the key wanted
							NodeData successorNode = dict.successor(r, kKey); 
							// if there is a successor, print the key
							if ( successorNode != null) {
								System.out.print(kKey + " ");
							}
							
							// checks the case when the key is not in the dictionary
							if ( successorNode == null ) {
								// then we will need to return the smallest node as successor for this condition
								if (kKey.compareTo(dict.smallest(r).getName()) < 0 ) {
									successorNode = dict.smallest(r);
								} else {
									// put in the key into the dictionary if the key is not smaller that the smallest node
									dict.put(r, kKey, "", 1);
									successorNode = dict.successor(r, kKey);
								}
								
							}
							// get name of successor to print out and display in console
							successor = successorNode.getName();						
							System.out.print(successor);
							
							// use a for loop to print the following successor's successor wanted based on count described by the user
							for (int i = 0; i < numSuccessor - 1; i++) {
								successor = dict.successor(r, successor).getName();
								System.out.print(" " + successor);
								
							}
							System.out.println();
						// catch exceptions if there are no larger keys than or equal to the key wanted
						} catch (NullPointerException e) {
							if (successor.equals(""))
								System.out.println("\nThere are no larger keys than or equal to " + kKey);
							else
								System.out.println("\nThere are no larger keys than or equal to " + successor);
						}  catch (Exception e) {
							System.out.println("\nInvalid input.");
						}
					}
				/*PREV*/
					/*
					 * This command must find the node p with key attribute k or, if this node does not exist, the
					 * leaf node p where the key attribute k should have been stored. Then your program must find
					 * the d predecessor nodes of p (if they exist) and print the key attribute of p (if p is an internal
					 * node) and the key attributes of the d predecessors (if they exist) in lexicographic decreasing order
					 */
				} else if (command.equals("prev")) {
					// find the key wanted by the user
					String kKey = st.nextToken();
					// create a counter for number of predecessors wanted to be found
					int numPredecessor = 1;
					// create a string to hold the predecessor(s)
					String predecessor = "";
					
					// check if the input is valid
					boolean isInputValid = true;
					
					try {
						numPredecessor = Integer.parseInt(st.nextToken());
					} catch (NumberFormatException e) {
						isInputValid = false;
						System.err.println("Invalid input, expect an input integer for type.");
					}
					
					//System.out.println("hello " + numSuccessor);
					if ( isInputValid = true) {
						try {
							// get the predecessor of node wanted
							NodeData predecessorNode = dict.predecessor(r, kKey); 
							
							// if the predecessor is not null, print the key
							if ( predecessorNode != null) {
								System.out.print(kKey + " ");
							}
							
							// if the predecessor is null
							if ( predecessorNode == null ) {
								// then we will need to return the largest node as predecessor for this condition
								if (kKey.compareTo(dict.largest(r).getName()) > 0 ) {
									predecessorNode = dict.largest(r);
								} else {
									// put in the key into the dictionary if the key is not larger that the largest node
									dict.put(r, kKey, "", 1);
									predecessorNode = dict.predecessor(r, kKey);
								}
								
							}
							// get name of predecessor to print out and display in console
							predecessor = predecessorNode.getName();						
							System.out.print(predecessor);
							// find the predecessor's predecessor with a for loop based on count described by the user
							for (int i = 0; i < numPredecessor - 1; i++) {
								predecessor = dict.predecessor(r, predecessor).getName();
								System.out.print(" " + predecessor);
							}
							System.out.println();
							
						// catch exceptions if there are no smaller keys than or equal to the key wanted
						} catch (NullPointerException e) {
							if (predecessor.equals(""))
								System.out.println("\nThere are no smaller keys than or equal to " + kKey);
							else
								System.out.println("\nThere are no smaller keys than or equal to " + predecessor);
						}  catch (Exception e) {
								System.out.println("\nInvalid input.");
						}
					}	
					
				/*FIRST*/
				} else if (command.equals("first")) {
					// print the smallest key attribute in the ordered dictionary
					try {
						System.out.println(dict.smallest(r).getName());
					// If the ordered dictionary is empty your program must print the following
					} catch (NullPointerException e) {
						System.out.println("The ordered dictionary is empty.");
					}
					
				/*LAST*/
				} else if (command.equals("last")) {
					// print the largest key attributes in the ordered dictionary
					try {
						System.out.println(dict.largest(r).getName());
					// If the ordered dictionary is empty your program must print the following
					} catch (NullPointerException e) {
						System.out.println("The ordered dictionary is empty.");
					}
					
				/*SIZE*/
				} else if (command.equals("size")) {
					// prints the number of internal nodes in the binary search tree implementing the ordered dictionary
					System.out.println("There are " + (dict.getNumInternalNodes()) + " in the ordered dictionary." );
				} else {
					//If an invalid command is entered your program must print the following message
					System.out.println("Invalid command");
				}
					
				// read in next command again and repeat till user types in end
				keyboard = new StringReader();
				line = keyboard.read("\nEnter next command: ");
			}
		// catch to make sure exceptions not found are caught before end of program
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
