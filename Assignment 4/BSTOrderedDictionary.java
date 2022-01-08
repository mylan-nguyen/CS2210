import java.util.ArrayList;

/* CS2210a 2021
 * Assignment 2
 * @author - Mylan Nguyen
 * @date - Oct 18, 2021 
 * 251155416
 * mnguy246 */

/*
 * This class implements an ordered dictionary using a binary search tree. 
 * In the binary search tree only the internal nodes will store information.
 */

public class BSTOrderedDictionary implements BSTOrderedDictionaryADT {
	
	// Declare instance variables
	
	// The root of the binary search tree.
	private BSTNode root; 
	// The number of internal nodes in this tree.
	private int numInternalNodes; 
	
	/*
	 * Constructor
	 * Creates an empty BSTOrderedDictionary in which the root is a
	 * leaf BSTNode and numInternalNodes = 0.
	 */
	public BSTOrderedDictionary() {
		this.root = null;
		this.numInternalNodes = 0;
	}
	
	//PUBLIC METHODS
	
	// Method returns the root of this tree.
	public BSTNode getRoot() {
		return root;
	}
	
	// Method returns the value of numInternalNodes.
	public int getNumInternalNodes() {
		return numInternalNodes;
	}
	
	// Method that finds in the tree with root r the BSTNode with the given key 
	// attribute key and it returns the list of MultimediaItem objects stored in it; 
	// it returns null if no node in the tree stores the given key.
	public ArrayList<MultimediaItem> get (BSTNode r, String key) {
		// make sure key is lowercase for query
		key = key.toLowerCase();
		// returns null if no node in the tree stores the given key
		if (root == null) {
			return null;
		}
		// return media if key looking for is found at root
		if (r.getData().getName().compareTo(key) == 0) {
			return r.getData().getMedia();
		}
			
		// create a temp node to hold the BSTNode r
		BSTNode temp = r;
		// check if the temp is not a leaf
		while(temp.isLeaf() != true) {	

			// check if the temp value is less than the key
			if (key.compareTo(temp.getData().getName()) < 0)    
				// travel left and get the left child
				temp = temp.getLeftChild();
			// check if the temp value is greater than the key
			else if (key.compareTo(temp.getData().getName()) > 0) 
				// travel right and get the right child
				temp = temp.getRightChild();
			// return the list of MultimediaItem objects stored in it
			else                                                 
				return temp.getData().getMedia();
		
		}		
		return null;
		
	}
	

	// Method adds the given information to the tree with root r. 
	public void put (BSTNode r, String key, String content, int type) {
		// make sure key is lowercase for query
		key = key.toLowerCase();
		// if the tree is empty, set the key as the root node of the tree
		// need to check both to accommodate both scenarios when original tree root is null or the root r passing in is also null
		if ( root == null || r == null) { 
			// create root node with key 
			NodeData rootNode = new NodeData(key);
			
			// insert multimedia items into rootnode
			rootNode.add(new MultimediaItem(content, type));
			
			// define root as a new BSTNode
			this.root = new BSTNode();
			
			// store the rootNode in the root
			root.setData(rootNode);
			
			/// set right and left child of root to null
			root.setLeftChild(new BSTNode());
			root.setRightChild(new BSTNode());
			
			// increase number of internal nodes by 1 
			numInternalNodes = 1;
			return;
		}
		
		// create a temp variable to keep track of root
		BSTNode temp = r;
		// create a parentNode variable to keep track
		BSTNode parentNode = null;
		char c = 'c';
		
		// while the temp node is not null (the tree is not empty 
		while (!temp.isLeaf()) {

			//check if the key is smaller than the node, if so make it the left child of the key node
			if (temp.getData().getName().compareTo(key) > 0) {
				parentNode = temp;
				temp = temp.getLeftChild();
				c = 'l';
				
				
			//check if the key is greater than the node, if so make it the right child of the key node
			} else if (temp.getData().getName().compareTo(key) < 0) {
				parentNode = temp;
				temp = temp.getRightChild();
				c = 'r';
				
			
			// if the key values are equal, then a new MultimediaItem object storing the given
			// content and type is added to the Arraylist stored in the node
			} else { 
				c = 'e';				
				temp.getData().add(new MultimediaItem(content, type));			
				// Exit the tree traversal when the node is found and new media item is already added
				return; 
			}
			
		}
		
		// once the spot needed to add the node is found, put it in the tree accordingly
		NodeData setterNode = new NodeData(key);
		// add the media to the node made
		setterNode.add(new MultimediaItem(content, type));
		// create a temp to hold the node with media items 
		temp = new BSTNode(parentNode, new BSTNode(), new BSTNode(), setterNode);
		// increase number of internal nodes by 1 since node was added to tree
		numInternalNodes++; 
		// set the child of the node with temp
		if ( c == 'l')
			parentNode.setLeftChild(temp);
		else if ( c == 'r')
			parentNode.setRightChild(temp);

	}
		
	//  Method removes from the binary search tree with root r the BSTNode storing the given key attribute key
	// Method throws a DictionaryException if no node stores the given key
	public void remove(BSTNode r, String k) throws DictionaryException {
		// make sure key is lowercase for query
		k = k.toLowerCase();
		// check if key exists in tree, else throw exception
		if (this.get(r, k) == null)
			throw new DictionaryException("key " + k + " does not exist in dictionary");
		// internal node was removed, so the value of numInternalNodes must be decreased
		this.numInternalNodes--;
		// call private helper method to remove found key and rebuild tree
		root = recursive(r, k);
	}
	
	// Private method to recursively call to remove the wanted node in the tree
	private BSTNode recursive(BSTNode root, String key)  { 
        // Base Case to stop the recursive
        if (root.isLeaf()) {
        	return root; 
        }
        // Recursively loop through the tree from root until finding the node with matched key
        // check if the key is less than the node, if so set leftchild with leftchild of root recursively
        if (key.compareTo(root.getData().getName()) < 0) {      
        	root.setLeftChild(recursive(root.getLeftChild(), key));
        }

        // check if the key is less than the node, if so set rightchild with rightchild of root recursively
        else if (key.compareTo(root.getData().getName()) > 0) {  
        	root.setRightChild(recursive(root.getRightChild(), key));
            
        }
        // Scenario with node contains only one child
        else  { 
            // if leftchild of root is a leaf, use the right child as as root
            if (root.getLeftChild().isLeaf()) {
                return root.getRightChild(); 
            }
            // if rightchild of root is a leaf, use the left child as as root
            else if (root.getRightChild().isLeaf()) {
                return root.getLeftChild(); 
            }
   
            // Scenario with node contains two children;
            // call private minvalue method to find the smallest key
            BSTNode successor = minValue(root.getRightChild());
            
            // set success with minvalue found
            root.setData(successor.getData());
            // set rightchild of root recursively with successor to remove the node
            root.setRightChild(recursive(root.getRightChild(), successor.getData().getName()));
        } 
  
        return root; 
    }
	// private helper method to find the smallest key in the tree of BSTNode (instead of NodeData)
    private BSTNode minValue(BSTNode root)  { 
    	// use while loop to make sure leftchild of root is not a leaf and set the leftchild
        while (root.getLeftChild().isLeaf() != true) {
            root = root.getLeftChild(); 
        } 
        return root; 
    }
	
	
	// Method removes from the tree with root r all MultimediaItem objects of the type specified by the third
	// parameter stored in the ArrayList of the node with key attribute key. 
	public void remove(BSTNode r, String k, int type) throws DictionaryException {
		System.out.println("***** " + k);
		// check if the key exists in the tree
		if (this.get(r, k) == null)
			throw new DictionaryException("key " + k + " does not exist in dictionary");
		
		// if it exists, then loop through its multimedia contents
		ArrayList<MultimediaItem> list = get(r, k);
		// find correct type wanted to be removed from the node 
		for (int i = 0; i < list.size(); i++) {	
			System.out.println("in remove 2nd method, we try to remove " + k + " type: " + type);
			if (type == list.get(i).getType()) {
				System.out.println("this media will be remove <" + list.get(i).getContent() + "> type = " + list.get(i).getType());
				list.remove(i);
				i--;
			}
		}
		// if the array list is empty after removal, delete the node
		if (list.size() == 0) 
			remove(r, k);	
		
	}
		
	// Method returns the NodeData object storing the smallest key in the
	// tree with root r; returns null if the tree is empty.
	public NodeData smallest (BSTNode r) {
		// check if root is null 
		if (r == null) {
			return null;
		// if root is not null
		} else {
			// create a temp of root
			BSTNode temp = r;
			// make sure the leftchild of temp is not a leaf, if so, continue to find smallest leftchild
			while(temp.getLeftChild().isLeaf() != true) {
				temp = temp.getLeftChild();
			}
			// return the child found
			return temp.getData();
		}
	}
	
	// Method returns the NodeData object storing the largest key in the tree with root r; 
	// returns null if the tree is empty.
	public NodeData largest (BSTNode r) {
		// check if root is null 
		if (r == null) {
			return null;
		} else {
			// create a temp of root
			BSTNode temp = r;
			// make sure the rightchild of temp is not a leaf, if so, continue to find largest rightchild
			while (temp.getRightChild().isLeaf() != true) {
				temp = temp.getRightChild();
			}
			// return the child found
			return temp.getData();
		}
	}
	
	//Private method to find the correct node we are looking for in the tree
	
	private BSTNode find (BSTNode r, String key) {
		// make sure the key is not null
		if (this.get(r, key) == null)
			return null;
		
		// create a temp node to hold the BSTNode r
		BSTNode temp = r;
		// returns null if no node in the tree stores the given key
		if (r.isLeaf()) {
			return r;
		}
        else {
        	// if the key matches the temp, return the node
            if (key.compareTo(temp.getData().getName()) == 0) {
            	return r;
            }
            // if the key is less than the temp, recursively find the leftchild
            else if (key.compareTo(temp.getData().getName()) < 0) {
            	return find(r.getLeftChild(), key);
            }
            // if the key is greater than the temp, recursively find the rightchild
            else {
            	return find(r.getRightChild(), key);
            }
        }
    }
	
	// Method returns the NodeData objects stored in the successor of the node storing key 
	// attribute key in the tree with root r; returns null if the successor does not exist.
	public NodeData successor (BSTNode r, String key) {
		// make sure key is lowercase for query
		key = key.toLowerCase();
		// if there is only 0 or 1 nodes in the tree, there is no successor
		if (r.isLeaf()) {
			return null;
		} 
		// call find method to find the node we are looking for
		BSTNode p = find(r,key);
		
		// make sure node is not null
		if (p == null)
			return null;
		
		// return smallest rightchild if the node is not a leaf and its rightchild is not a leaf
		if (!p.isLeaf() && !p.getRightChild().isLeaf()) {
			return smallest(p.getRightChild());
		}
		else {
			// get parent of the node we are looking for
			BSTNode pParent = p.getParent();
			// make sure the parent is not null and its rightchild is the node we are looking for
			while (pParent != null && p == pParent.getRightChild()) {
				// set node as its parent
				p = pParent;
				// set parent and its parent
				pParent = pParent.getParent();
			}
			// return newly set parent
			return pParent.getData();
		}
				
	}

	// Method returns the NodeData objects stored in the predecessor of the node storing key attribute key 
	// in the tree with root r; returns null if the predecessor does not exist.
	public NodeData predecessor(BSTNode r, String key) {
		// make sure key is lowercase for query
		key = key.toLowerCase();
		// if there is only 0 or 1 nodes in the tree, there is no successor
		if (r.isLeaf()) {
			return null;
		} 
		// call find method to find the node we are looking for
		BSTNode p = find(r,key);
		
		// make sure node is not null
		if (p == null)
			return null;
		
		// return largest leftchild if the node is not a leaf and its leftchild is not a leaf
		if (!p.isLeaf() && !p.getLeftChild().isLeaf()) {
			return largest(p.getLeftChild());
		}
		else {
			// get parent of the node we are looking for
			BSTNode pParent = p.getParent();
			// make sure the parent is not null and its leftchild is the node we are looking for
			while (pParent != null && p == pParent.getLeftChild()) {
				// set node as its parent
				p = pParent;
				// set parent and its parent
				pParent = pParent.getParent();
			}
			// return newly set parent
			return pParent.getData();
		}	
	}

	
}