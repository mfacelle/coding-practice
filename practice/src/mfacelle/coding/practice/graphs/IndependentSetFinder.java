package mfacelle.coding.practice.graphs;

/**
 *  Class that finds an independent set (if any) in a graph.
 *  Because it's related, this class can also find the vertex cover set
 */
public class IndependentSetFinder {

    private Graph graph;
    private int numVertices;

    private boolean[] independentSet;   // bit vector containing info on if each vertex is in the independent set
    private boolean[] vertexCoverSet;   // bit vector containing info on if each vertex is in the vertex cover set

    private boolean independentSetRun; // if the maximal independent set algorithm has been run

    // ---

    public IndependentSetFinder(Graph g) {
        graph = g;
        numVertices = g.getNumVertices();
        independentSetRun = false;
    }

    /** initialize independentSet array */
    private void initialize() {
        independentSetRun = false;
        independentSet = new boolean[numVertices];
        vertexCoverSet = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            independentSet[i] = false;
        }
    }

    // ---
    // INDEPENDENT SET ALGORITHM

    /** Finds (if any) an independent set in the graph set in this class.
     *  Finds the lowest-degree vertex, deletes it and all adjacent vertices, then repeats process until graph empty
     */
    public boolean[] findMaximalIndependentSet() {
        initialize();

        // check what vertices have been processed
        int numVerticesProcessed = 0;
        boolean[] isProcessed = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            isProcessed[i] = false; // unnecessary, but doing initialization to be sure
        }

        int lowestDegree = Integer.MAX_VALUE;
        int currentVertex = 0;

        // used in inner for-loop
        EdgeNode edge;

        do {
            lowestDegree = Integer.MAX_VALUE;
            // find the lowest degree vertex that has not yet been processed
            // O(n)
            for (int i = 0; i < numVertices; i++) {
                // if vertex has not been processed and degree less than lowest degree
                if (!isProcessed[i] && graph.getNumEdges(i) < lowestDegree) {
                    currentVertex = i;
                    lowestDegree = graph.getNumEdges(i);
                }
            }

            // add current vertex to the independent set and marked as processed
            independentSet[currentVertex] = true;
            isProcessed[currentVertex] = true;
            numVerticesProcessed++;

            // mark all adjacent vertices as processed, but dont add them to the set
            // O(nm)
            for (int i = 0; i < numVertices; i++) {
                // if not the current vertex and this one hasnt been processed yet
                if (i != currentVertex && !isProcessed[i]) {
                    // iterate over all edges and if any touch the current vertex, mark as processed but do not add
                    edge = graph.getEdges(i);
                    while (edge != null) {
                        // touches current vertex in set: process it, but do not add
                        if (edge.v == currentVertex) {
                            isProcessed[i] = true;
                            numVerticesProcessed++;
                            edge = null;    // set to null to break out of loop
                        }
                        else {
                            edge = edge.getNext();
                        }
                    }
                }
            }

        } while (numVerticesProcessed < numVertices);

        // compute vertex cover set (just the inverse)
        for (int i = 0; i < numVertices; i++) {
            vertexCoverSet[i] = !independentSet[i];
        }

        independentSetRun = true;
        return independentSet;
    }

    // ---

    /** Finds the largest independent set for the graph in this class.
     *  Iterates over each vertex, computing the maximal independent set with each vertex included.
     *  Returns the set with the largest size
     *
     */
    public boolean[] findIndependentSet() {
        initialize();

        boolean[] set;
        int maxSize = 0;

        // iterate over each vertex as a starting point
        for (int i = 0; i < numVertices; i++) {
            // find the maximal independent set with this vertex included
            set = findIndependentSet(i);
            // if set size is bigger than the current largest set, record it
            if (setSize(set) > maxSize) {
                maxSize = setSize(set);
                independentSet = set;
            }
        }
        independentSetRun = true;

        // compute vertex cover set (just the inverse)
        for (int i = 0; i < numVertices; i++) {
            vertexCoverSet[i] = !independentSet[i];
        }

        // return the current largest set
        return independentSet;
    }

    // ---

    /** Finds the maximal independent set including the vertex specified.
     *
     */
    public boolean[] findIndependentSet(int includedVertex) {
        boolean[] set = new boolean[numVertices];
        // check what vertices have been processed
        int numVerticesProcessed = 0;
        boolean[] isProcessed = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            isProcessed[i] = false; // unnecessary, but doing initialization to be sure
        }

        int lowestDegree = Integer.MAX_VALUE;
        int currentVertex = includedVertex; // start at the mandatory-included vertex

        // used in inner for-loop
        EdgeNode edge;

        while (numVerticesProcessed < numVertices) {
            // add current vertex to the independent set and marked as processed
            set[currentVertex] = true;
            isProcessed[currentVertex] = true;
            numVerticesProcessed++;

            // mark all adjacent vertices as processed, but dont add them to the set
            // O(nm)
            for (int i = 0; i < numVertices; i++) {
                // if not the current vertex and this one hasnt been processed yet
                if (i != currentVertex && !isProcessed[i]) {
                    // iterate over all edges and if any touch the current vertex, mark as processed but do not add
                    edge = graph.getEdges(i);
                    while (edge != null) {
                        // touches current vertex in set: process it, but do not add
                        if (edge.v == currentVertex) {
                            isProcessed[i] = true;
                            numVerticesProcessed++;
                            edge = null;    // set to null to break out of loop
                        }
                        else {
                            edge = edge.getNext();
                        }
                    }
                }
            }

            lowestDegree = Integer.MAX_VALUE;
            // find the lowest degree vertex that has not yet been processed
            // O(n)
            for (int i = 0; i < numVertices; i++) {
                // if vertex has not been processed and degree less than lowest degree
                if (!isProcessed[i] && graph.getNumEdges(i) < lowestDegree) {
                    currentVertex = i;
                    lowestDegree = graph.getNumEdges(i);
                }
            }

        }

        return set;
    }

    // ---
    // VERTEX COVER ALGORITHMS

    public boolean[] findMinimalVertexCover() {
        // if independent set hasn't been run yet, run it (otherwise, vertex cover already stored)
        if (!independentSetRun) {
            findMaximalIndependentSet();
        }
        return vertexCoverSet;
    }

    // ---

    public boolean[] findVertexCover() {
        // if independent set hasn't been run yet, run it (otherwise, vertex cover already stored)
        if (!independentSetRun) {
            findIndependentSet();
        }
        return vertexCoverSet;
    }

    // ---

    public boolean[] findVertexCover(int includedVertex) {
        boolean[] independentSetWithVertex = findIndependentSet(includedVertex);

        boolean[] vertexCoverSetWithVertex = new boolean[numVertices];
        // compute vertex cover set (just the inverse)
        for (int i = 0; i < numVertices; i++) {
            vertexCoverSetWithVertex[i] = !independentSetWithVertex[i];
        }

        return vertexCoverSetWithVertex;
    }


    // ---

    /** gets the size of the set by checking which elements are true (in the set) */
    public static int setSize(boolean[] set) {
        int size = 0;
        for (int i = 0; i < set.length; i++) {
            size += set[i] ? 1 : 0;
        }
        return size;
    }

}
