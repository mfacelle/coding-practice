package mfacelle.coding.practice.graphs;

/**
 * Union Find data structure.
 *  Basically just an array of parent pointers (tree pointing backwards)
 */
public class UnionFind {

    public static final int ROOT = -1;  // index corresponding to a ROOT of a set

    private int[] parent;       // pointer to immediate parent of each element
    private int[] numChildren;  // number of children for each element
    private int numElements;    // size of this structure (number of elements)

    // ---

    public UnionFind(int n) {
        numElements = n;
        parent = new int[n];
        numChildren = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = ROOT;
            numChildren[i] = 0;
        }
    }

    // ---

    /** finds the root of the set tree containing x, recursively */
    public int find(int x) {
        // if parent of x is root (or itself, according to Skiena), return this node
        if (parent[x] == x || parent[x] == ROOT) {
            return x;
        }
        else {  // recurse upwards until found
            return find(parent[x]);
        }
    }

    // ---

    /** determines if two objects are in the same set */
    public boolean isSameSet(int x, int y) {
        return find(x) == find(y);
    }

    // ---

    /** performs the union of set containing x and set containing y (if different).
     *   if not the same set, the smaller set is added to the larger set.
     *   returns the root of the set now containing both x and y
     */
    public int setUnion(int x, int y) {
        // get root of set for x and y
        int xRoot = find(x);
        int yRoot = find(y);

        // same root: x and y are already in the same set
        if (xRoot == yRoot) {
            return xRoot;
        }

        // add the smaller root to the bigger root
        if (numChildren[xRoot] >= numChildren[yRoot]) {
            numChildren[xRoot] += numChildren[yRoot];   // increase size
            parent[yRoot] = xRoot;                      // point small root to big root
            return xRoot;
        }
        else {
            numChildren[yRoot] += numChildren[xRoot];   // increase size
            parent[xRoot] = yRoot;                      // point small root to big root
            return xRoot;
        }
    }

    // ---

    public int[] getParent() { return parent; }
    public int[] getNumChildren() { return numChildren; }
    public int getNumElements() { return numElements; }
}
