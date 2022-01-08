public class Odd {
	
	// declare counter
	int count = 0;
	
	public int numOdd(Node r) {
	  
	  // if node is not a leaf and the number of children of a node is odd, then increment count
	  if (r.isLeaf() == false && r.numChildren() % 2 == 1) {
		  count++;
	  }
	  
	  // get all the children of a current node
	  Node[] children = r.getChildren();
	  
	  for (Node u : children) {
		  if(u.numChildren() != 0) {
			  // determine the degree of internal node
			  numOdd(u);
		  }
	  }
	  return count;

  /* Input: Root r of a tree
     Output: true if all internal nodes of the tree have odd degree; false otherwise
     
     You can use the following methods from class Node:
        - numChildren() returns the number of children of a node.
        - isLeaf(): returns true if a node is a leaf and returns false otherwise
        
     To translate the following pseudocode
     
        for each child u of r do { ... }
        
     use the following java code:
     
        Node[] children = r.getChildren();
        for (Node u : children) { ... }
   */

  }
}
