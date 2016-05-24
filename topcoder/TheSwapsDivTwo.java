public class TheSwapsDivTwo 
{

	/**
	 *
	 	-------------
	     	John has a sequence of integers. 
	     	Brus is going to choose two different positions in John's sequence and swap the elements at those positions. 
	     	(The two swapped elements may have the same value.) 
	     	Return the number of different sequences Brus can obtain after he makes the swap. 
	     	
	     	Constraints
			-	sequence will contain between 2 and 47 elements, inclusive.
			-	Each element of sequence will be between 1 and 47, inclusive.
		-------------
		
		seems related to combinations/permutations
		but when numbers are repeated more than twice, the answer changes
	 */
	public int find(int[] sequence) {
		// looked at solution online... http://apps.topcoder.com/wiki/display/tc/SRM+575
		// spent too much time on problem
		// wasn't using combinations at all... just counts pairs of different objects
		// O(n^2)
		
		// first see if there is a pair of same-value numbers
		// this means the original sequence can be recovered (one more result in the set)
		int swaps = 0;
		for (int i = 0; i < sequence.length && swaps == 0; i++) {
			for (int j = i+1; j < sequence.length && swaps == 0; j++) {
				if (sequence[i] == sequence[j]) {
					swaps = 1;
				}
			}
		}
		
		// now count the number of different-value pairs
		// these will each give a distinct result
		for (int i = 0; i < sequence.length; i++) {
			for (int j = i+1; j < sequence.length; j++) {
				if (sequence[i] != sequence[j]) {
					swaps++;
				}
			}
		}
			
		return swaps;
	}


	/*
		TEST EXAMPLES  ->  C(n,k) = n! / ( (n-k)! k!)
		
		9 9 9 9  -> C(4,4) = 1
		-------
		9 9 9 9  (0,1) (0,2) (0,3) (1,2) (1,3) (2,3)
		
		
		9 9 9 1  -> C(4,3) = 4
		-------
		9 9 9 1  (0,1) (0,2) (1,2)
		1 9 9 9  (0,3)
		9 1 9 9  (1,3)
		9 9 1 9  (2,3)
		
		
		9 9 1 2  -> C(4,2) = 6
		-------
		9 9 1 2  (0,1)
		1 9 9 2  (0,2)
		2 9 1 9  (0,3)
		9 1 9 2  (1,2)
		9 2 1 9  (1,3)
		9 9 2 1  (2,3)
	
	
		seems to be C(n,k) where k=number of duplicate values
		but what about for multiple numbers with duplicate values?
		
		9 9 1 1   -> 5  
		-------
		9 9 1 1  (0,1) (2,3)
		1 9 9 1  (0,2)
		1 9 1 9  (0,3)
		9 1 9 1  (1,2)
		9 1 1 9  (1,3)
		
	
	*/
}