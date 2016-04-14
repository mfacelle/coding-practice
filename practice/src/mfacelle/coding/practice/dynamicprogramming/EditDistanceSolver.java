package mfacelle.coding.practice.dynamicprogramming;


public abstract class EditDistanceSolver {

    // used by algorithm
    private String x = "";
    private String y = "";

    // distance matrix and dimensions
    private int iMax;   // length of x
    private int jMax;   // length of y
    private int[][] distance;

    // ---

    private void initialize() {
        // one more than strlen, in case one string is empty
        iMax = x.length()+1;
        jMax = y.length()+1;

        // initialize first row/column to value of index
        distance = new int[iMax][jMax];
        for (int i = 0; i < iMax; i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j < jMax; j++) {
            distance[0][j] = j;
        }
    }

    // ---

    /** compute the edit distance using dynamic programming matrix */
    public int minEditDistance(String xx, String yy) {
        x = xx;
        y = yy;
        initialize();

        for (int i = 1; i < iMax; i++) {
            for (int j = 1; j < jMax; j++) {
                // use i/j-1 when referencing chars, because of starting at index 1, not 0
                distance[i][j] = min(
                        costOfInsert(x.charAt(i-1)) + distance[i][j-1],
                        costOfDelete(y.charAt(j-1)) + distance[i-1][j],
                        costOfReplace(x.charAt(i-1), y.charAt(j-1)) + distance[i-1][j-1]
                );
            }
        }

        // last value is the edit distance (once both strings have been moved through)
        return distance[iMax-1][jMax-1];
    }

    // ---

    /** recursive version - for comparison (and just to do it) */
    public int minEditDistanceRecursive(String xx, String yy) {
        x = xx;
        y = yy;
        initialize();

        return editDistanceHelper(0,0);
    }

    /** recursive helper method */
    private int editDistanceHelper(int i, int j) {
        // equivalent to first-row/col initialization (in case string is empty)
        if (i == 0) { return j; }
        if (j == 0) { return i; }

        return min(
                costOfInsert(x.charAt(i)) + distance[i][j-1],
                costOfDelete(y.charAt(i)) + distance[i-1][j],
                costOfReplace(x.charAt(i), y.charAt(j)) + distance[i-1][j-1]
        );
    }

    // ---

    /** three-input min() function, since Math.min() doesn't do this */
    private int min(int a, int b, int c) {
        if (a < b) {
            if (a < c) { // < b
                return a;
            }
            else { // c < a < b
                return c;
            }
        }
        else { // b < a
            if (b < c) { // < a
                return b;
            }
            else { // c < b < a
                return c;
            }
        }
    }


    // ---
    // char modification costs - implemented by specific impl

    /** cost of inserting a char */
    public abstract int costOfInsert(char c);

    /** cost of deleting a char */
    public abstract int costOfDelete(char c);

    /** cost of replacing a char - or cost of a match (if both chars are equal) */
    public abstract int costOfReplace(char c, char r);
}
