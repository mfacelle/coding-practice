package mfacelle.coding.practice.graphs;


/** A graph implementation using adjacency lists
 *
 */
public class Graph
{
    private static final int DEFAULT_NUM_VERTICES = 10;
    private static final int DEFAULT_WEIGHT = 1;

    private EdgeNode[] edges;       // list of edges for each
    private int[] degree;      // number of edges per vertex
    private int numVertices;        // total number of vertices (size of arrays)
    private int numEdges;           // total number of edges (sum of degree
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
        degree = new int[initVertices];
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
        degree[x]++;

        // if graph is not directed - insert an edge facing the other direction
        // make sure next edge is directed, or else infinite recursive loop
        if (!isDirectedEdge) {
            insertEdge(y, x, weight, true);
        }
    }

    // ---

    /** Removes edges from x->y (and from y->x if graph is undirected) */
    public void removeEdge(int x, int y) {
        removeEdge(x, y, isDirected);
    }

    /** Removes an edge from x->y.  If not directed, also removes the edge y->x.
     *  Basically just performs a linked list find-and-remove
     */
    public void removeEdge(int x, int y, boolean isDirectedEdge) {

        // find this particular edge
        EdgeNode edge = getEdges(x);
        EdgeNode nextEdge;
        while (edge.getNext() != null) {
            nextEdge = edge.getNext();
            // if edge found: remove it (point current edge next to the following node)
            if (nextEdge.v == y) {
                edge.setNext(nextEdge.getNext());
            }
            edge = edge.getNext();
        }

        // degrease number of edges in graph and degree of vertex
        numEdges--;
        degree[x]--;

        if (!isDirectedEdge) {
            removeEdge(y, x, true);
        }
    }

    // ---

    public String toString() {
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

    public static void processVertexEarly(int v) {
        System.out.println("["+v+"] Processed before edges");
    }

    public static void processVertexLate(int v) {
        System.out.println("["+v+"] Processed after edges");
    }

    public static void processEdge(int v, int y, EdgeNode edge) {
        System.out.println("["+v+"->"+y+"] Processed edge.  weight="+edge.weight);
    }

    // ---

    public int getNumVertices() { return numVertices; }
    public int getNumEdges() { return numEdges; }
    public int getNumEdges(int vertex) { return degree[vertex]; }
    public EdgeNode getEdges(int vertex) { return edges[vertex]; }
    public boolean isDirected() { return isDirected; }

    // ---

    /** Creates an array of Edge objects describing this graph.
     *   Useful in some algorithms (like kruskal minimum spanning tree).
     *   Note that this adds duplicate edges for undirected graphs.
     *   Could filter these out, but can't figure out an efficient way to do so
     */
    public Edge[] toEdgeArray() {
        Edge[] edges = new Edge[numEdges];
        int i = 0;
        int currentVertex = 0;
        EdgeNode edge;

        // go until all edges have been added
        while (i < edges.length && currentVertex < numVertices) {
            // iterate over all edges on one vertex
            edge = getEdges(currentVertex);
            while (edge != null) {
                edges[i++] = new Edge(currentVertex, edge.v, edge.weight, isDirected);
                edge = edge.getNext();
            }
            currentVertex++;
        }
        return edges;
    }
}
