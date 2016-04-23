package mfacelle.coding.practice.graphs;

/**
 *  Class that finds an independent set (if any) in a graph.
 *  Because it's related, this class can also find the vertex cover set
 */
public class IndependentSetFinder {

    private Graph graph;
    private int numVertices;

    private boolean[] independentSet;   // bit vector containing info on if each vertex is in the independent set

    // ---

    public IndependentSetFinder(Graph g) {
        graph = g;
        numVertices = g.getNumVertices();
    }

    /** initialize independentSet array */
    private void initialize() {
        independentSet = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            independentSet[i] = false;
        }
    }

    // ---

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

        return independentSet;
    }

    // ---

    
}
