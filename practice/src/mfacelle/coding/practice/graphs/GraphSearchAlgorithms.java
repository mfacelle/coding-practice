package mfacelle.coding.practice.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *  Class for writing graph algorithms (search, etc) - based on Skiena book
 */
public abstract class GraphSearchAlgorithms {

    /** the graph to be evaluated */
    protected Graph graph;
    /** number of vertices in the graph */
    protected int numVertices;
    /** processed state of each vertex - processed if all edges have been processed */
    protected boolean[] isProcessed;
    /** discovered state of each vertex - discovered if visited at least once */
    protected boolean[] isDiscovered;
    /** parent information on each vertex - represents BFS or DFS tree */
    protected int[] parent;
    /** "time" that the vertex was first visited (basically just the order) */
    protected int[] entryTime;
    /** "time" that the vertex was done processing (basically just the order) */
    protected int[] exitTime;
    /** variable to keep track of processing times */
    protected int time;
    /** a finished marker for when something is found (for implementations) */
    protected boolean isFinished;

    // ---

    public GraphSearchAlgorithms(Graph g) {
        graph = g;
        initialize();
    }

    // ---

    public void initialize() {
        time = -1;  // start at -1 because ++time is used
        isFinished = false;
        numVertices = graph.getNumVertices();
        isProcessed = new boolean[numVertices];
        isDiscovered = new boolean[numVertices];
        parent = new int[numVertices];
        entryTime = new int[numVertices];
        exitTime = new int[numVertices];
        // initialize all arrays
        for (int i = 0; i < numVertices; i++) {
            isProcessed[i] = false;
            isDiscovered[i] = false;
            parent[i] = -1;
            entryTime[i] = 0;
            exitTime[i] = 0;
        }
    }

    // ---

    /** Performs a breadth-first search of the specified graph, from the specified starting point.
     *  Returns the parent information array (essentially the BFS tree)
     */
    public int[] breadthFirstSearch(int start) {
        // just using a java.util queue class, rather than my own impl
        Queue<Integer> q = new LinkedBlockingDeque<>();

        // perform search
        q.add(start);
        isDiscovered[start] = true;

        Integer currentVertex;
        Integer nextVertex;
        EdgeNode edge;
        while (!q.isEmpty()) {
            // dequeue and process the next vertex
            currentVertex = q.remove();
            preProcessVertex(currentVertex);
            isProcessed[currentVertex] = true;
            // iterate over edges, and process each edge
            edge = graph.getEdges(currentVertex);
            while (edge != null) {
                nextVertex = edge.v;
                // process next edge if not yet processed - or if graph is directed
                if (!isProcessed[nextVertex] || graph.isDirected()) {
                    processEdge(currentVertex, nextVertex);
                }
                // if next vertex has not been discovered, enqueue it and set its parent to current vertex
                if (!isDiscovered[nextVertex]) {
                    q.add(nextVertex);
                    isDiscovered[nextVertex] = true;
                    parent[nextVertex] = currentVertex;
                }
                edge = edge.getNext();
            }
            postProcessVertex(currentVertex);
        }

        return parent;
    }

    // ---

    /** Performs a depth-first search of the specified graph, from the specified starting point.
     *  Passes lots of information to the recursive method.
     *  Returns the array of parent node information (basically the DFS tree).
     *
     *  Keeps track of processing time (order in which vertices are processed)
     */
    public int[] depthFirstSearch(int currentVertex) {
        // if some condition has been met, don't continue
        if (isFinished) {
            return new int[0];
        }

        // mark entry time of vertex, and process on entry
        isDiscovered[currentVertex] = true;
        entryTime[currentVertex] = ++time;
        preProcessVertex(currentVertex);

        EdgeNode edge = graph.getEdges(currentVertex);
        int nextVertex;
        while (edge != null) {
            nextVertex = edge.v;
            // if next vertex has not yet been discovered, record info, process, and recursively DFS
            if (!isDiscovered[nextVertex]) {
                parent[nextVertex] = currentVertex;
                processEdge(currentVertex, nextVertex);
                depthFirstSearch(nextVertex);
            }
            // process the edge if it's not already processed - or if graph is directed
            else if (!isProcessed[nextVertex] || graph.isDirected()) {
                processEdge(currentVertex, nextVertex);
            }
            // if some condition was met in processing edge, then exit
            if (isFinished) {
                return new int[0];
            }
            edge = edge.getNext();
        }
        // end processing of vertex, mark exit time
        postProcessVertex(currentVertex);
        isProcessed[currentVertex] = true;
        exitTime[currentVertex] = ++time;

        return parent;
    }

    // ---

    /** find the path from start to end, in an unweighted graph, given an array of vertex parent (obtained by BFS).
     *  the vertex parent array should be generated by a BFS with start as the root, or this will NOT find paths
     */
    public List<Integer> findPath(int start, int end) {
        return findPath(start, end, new ArrayList<Integer>());
    }

    /** recursive helper method for findPath */
    private List<Integer> findPath(int start, int end, List<Integer> path) {
        // if only one node in list, or the endpoint for this node is -1 (no parent)
        // then add this vertex to the list, and return the list
        if (start == end || end == -1) {
            path.add(start);
            return path;
        }
        // call findPath recursively, to add nodes to the list
        findPath(start, parent[end], path);
        path.add(end);
        return path;

    }

    // ---
    // "process" functions - to be implemented by a specific type of algorithm implementation

    public abstract void preProcessVertex(int v);

    public abstract void postProcessVertex(int v);

    public abstract void processEdge(int v, int y);

    // ---
    // get/set

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() { return graph; }
    public int getNumVertices() { return numVertices; }
    public boolean[] getIsProcessed() { return isProcessed; }
    public boolean[] getIsDiscovered() { return isDiscovered; }
    public int[] getParents() { return parent; }

    public boolean isDiscovered(int vertex) { return isDiscovered[vertex]; }
    public boolean isProcessed(int vertex)  { return isProcessed[vertex]; }
    public int getParent(int vertex)    { return parent[vertex]; }
}
