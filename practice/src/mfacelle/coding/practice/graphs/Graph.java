package mfacelle.coding.practice.graphs;


/** A graph implementation using adjacency lists
 *
 */
public class Graph
{
    private static final int DEFAULT_NUM_VERTICES = 10;
    private static final int DEFAULT_WEIGHT = 1;

    private EdgeNode[] edges;       // list of edges for each
    private int[] vertexEdges;      // number of edges per vertex
    private int numVertices;        // total number of vertices (size of arrays)
    private int numEdges;           // total number of edges (sum of vertexEdges
    private boolean isDirected;     // whether or not the graph is directed

    // ---

    public Graph() {
        this(DEFAULT_NUM_VERTICES);
    }

    public Graph(int initVertices) {
        this(initVertices, false);
    }

    public Graph(int initVertices, boolean directed) {
        edges = new EdgeNode[initVertices];
        vertexEdges = new int[initVertices];
        numVertices = initVertices;
        numEdges = 0;
        isDirected = directed;
    }

    // ---

    /** Inserts an edge from x to y in the graph - no weight specified */
    public void insertEdge(int x, int y) {
        insertEdge(x, y, DEFAULT_WEIGHT);
    }

    /** Inserts an edge from x to y in the graph - with the specified weight */
    public void insertEdge(int x, int y, int weight) {
        insertEdge(x, y, weight, isDirected);
    }

    /** Inserts an edge from x to y in the graph - with the specified weight
     *  Assumes that the edge doesn't already exist (could check for it... but that'd be a pain)
     */
    public void insertEdge(int x, int y, int weight, boolean isDirectedEdge) {
        EdgeNode newEdge = new EdgeNode(y, weight);
        newEdge.setNext(edges[x]);
        edges[x] = newEdge;
        numEdges++;
        vertexEdges[x]++;

        // if graph is not directed - insert an edge facing the other direction
        // make sure next edge is directed, or else infinite recursive loop
        if (!isDirected) {
            insertEdge(y, x, weight, true);
        }
    }

    // ---

    public void toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numVertices; i++) {
            sb.append("[ ").append(i).append("  |  ");
            EdgeNode edge = edges[i];
            while (edge != null) {
                sb.append(edge).append("  ");
                edge = edge.getNext();
            }
            sb.append("]\n");
        }

        return sb.toString();
    }

    // ---

    public EdgeNode getEdge(int x, int y) {
        return null;
    }

    // ---

    public void getNumVertices() { return numVertices; }
    public void getNumEdges() { return numEdges; }
    public void getVertex(int vertex) { return edges[vertex]; }
    public EdgeNode getEdges(int vertex) { return edges[vertex]; }
    public boolean isDirected() { return isDirected; }
}
