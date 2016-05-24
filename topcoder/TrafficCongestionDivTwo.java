public class TrafficCongestionDivTwo
{

	/**
	    -----
		There are some cities and some roads connecting them together. 
		The road network has the topology of a perfect binary tree, in which the cities are nodes and the roads are edges.

		You are given the int treeHeight giving the height of the tree. 
		Thus, there are 2^(treeHeight+1)-1 cities and 2^(treeHeight+1)-2 roads in total.
		
		We want to send some cars into the road network. 
		Each car will be traveling from its starting city to its destination city without visiting the same city twice. 
		(Note that the route of each car is uniquely determined by its starting and its destination city.) 
		It is possible for the starting city to be equal to the destination city, in that case the car only visits that single city.

		Our goal is to send out the cars in such a way that each city will be visited by exactly one car. 
		Compute and return the smallest number of cars we need in order to do so.
		
		Notes
		-	The answer will always fit into a 64-bit signed integer data type.
 
		Constraints
		-	treeHeight will be between 0 and 60, inclusive.
		-----
		
	
	 */
	public long theMinCars(int treeHeight) {
	 	// count all size=2 leaf subtrees, then decrease size of tree being looked at
	 	int numCars = 0;
	 	int height = treeHeight;
	 	while (height > 2) {
			// number of leaf subtrees with size=2: (num leaf nodes) / 2  =  2^(n-1) / 2  =  2^(n-2)
	 		numCars += Math.pow(2,height-2);
	 		height -= 2;	// decrease height of tree: now look at the upper portion of tree
	 	}
	 	// when height is less than or equal to 2, only one additional car required
	 	numCars += 1;
	 	
	 	return numCars;
	}
	 
	// THIS WORKS for up to height=5 (and maybe more, didn't test)
	// but... for height=10 or more, it apparently fails... result=341, expected=683
	
	
	/*
	                  o
	           ___---' '---___
	          o               o
	       _-' '-_         _-' '-_
	      o       o       o       o
         / \     / \     / \     / \
		o   o   o   o   o   o   o   o
		
		TEST CASES & SOLUTION WORK
		
		trivial solutions:
		treeHeight=1, solution=1
		treeHeight=2, solution=2
		
		maybe recursively break problem down to subtrees of height=2, add one car for each subtree
		
		always a full binary tree, so the number of these trees can be calculated most likely
		# leaf subtrees: (# leaf nodes)/2 = 2^(n-1) / 2 = 2^(n-2)
		
		1. count all leaf subtrees of size=2
		2. recursively calculate all leaf subtrees for rest of tree, if size > 2
		3. if remaining size is 1, then add one to total
		(probably a better way to do this, in constant time)
	
	 */
}