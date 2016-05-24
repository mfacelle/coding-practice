public class LittleElephantAndBallsAgain
{
	/**
	-----    	
	Little Elephant from the Zoo of Lviv likes balls. He has some balls arranged in a row. Each of those balls has one of three possible colors: red, green, or blue.

	You are given a String S. This string represents all the balls that are initially in the row (in the order from left to right). Red, green, and blue balls are represented by characters 'R', 'G', and 'B', respectively. In one turn Little Elephant can remove either the first ball in the row, or the last one.

	Little Elephant wants to obtain a row in which all balls have the same color. Return the smallest number of turns in which this can be done. 
	-----

	 * seems to boil down to a longest substring problem, where longest substring can be made up of only one char (R, G, or B)
	 * then the answer will be the number of characters surrounding this substring
	 * this will run in O(n) time
	 */
	public int getNumber(String s) {
		// find longest substring start index and length
		int maxSubstringStart = 0, substringStart = 0;
		int maxSubstringLength = 0, substringLength = 0;
	
		int length = s.length();
		int i = 0;
		while (i < length) {
			// start at i and check following chars for matching substring
			substringStart = i;
			substringLength = 1;
			for (int j = i+1; j < length; j++) {
				// if next char does not match start of substring, start new substring
				if (s.charAt(j) != s.charAt(i)) {
					i = j;
					break;
				}		
				substringLength++;
				// if current substring longer than longest so far, record it
				if (substringLength >= maxSubstringLength) {
					maxSubstringStart = substringStart;
					maxSubstringLength = substringLength;
				}
			}
		}
	
		// once substring obtained, count number of characters surrounding it
		// substring occupies [ maxSubstringStart, maxSubstringStart+maxSubstringLength-1 ]
		int numTurns = 0;
		numTurns += maxSubstringStart;	// count all elements up to the substring start index
		numTurns += length - (maxSubstringStart+maxSubstringLength);	// count all elements following the substring
	
		return numTurns;
	}
}