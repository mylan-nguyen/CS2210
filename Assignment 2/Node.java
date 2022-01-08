/* CS2210a 2021
 * Assignment 2
 * @author - Mylan Nguyen
 * @date - Oct 18, 2021 
 * 251155416
 * mnguy246 */

/*
* This class stores an object of type Layout and a pointer to an object of type Node
* to construct the linked list associated to an entry of the hash table.
*/

public class Node {
	// declare instance variables
    private Layout data;
    private Node nextNode;

    /**
     * Constructor for the Node class that creates a new Node object with the specified Configuration data and reference to the next node.
     * @param data - information to be inserted (type: Layout)
     * @param nextNode - reference to the next node in the linked list (type: Node)
     */
    public Node(Layout data, Node nextNode){
        this.data = data;
        this.nextNode = nextNode;
    }

    /**
     * Method returns the Layout data in the Node
     * @return data 
     */
    public Layout getData(){
        return data;
    }
    
    /**
     * Method gets the reference to the next node
     * @return nextNode
     */
    public Node getNextNode(){
        return nextNode;
    }

    /**
     * Method sets new Layout data for a Node
     * @param newData - new data to set a node
     */
    public void setData(Layout newData){
        data = newData;
    }

    /**
     * Method that sets the next node
     * @param newNode - new node to be linked to
     */
    public void setNextNode(Node newNode){
        nextNode = newNode;
    }


}



