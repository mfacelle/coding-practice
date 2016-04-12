package mfacelle.coding.practice.graphs;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *  Class for writing graph algorithms (search, etc) - based on Skiena book
 */
public class GraphAlgorithms {

    /** Performs a breadth-first search of the specified graph, from the specified starting point.
     *  Returns the parent information array (essentially the BFS tree)
     */
    public static int[] breadthFirstSearch(Graph g, int start) {
        // initialize search variables - using two boolean arrays instead of a "state" variable (enum)
        int numVertices = g.getNumVertices();
        boolean[] isProcessed = new boolean[numVertices];
        boolean[] isDiscovered = new boolean[numVertices];
        int[] parent = new int[numVertices];
        Queue<Integer> q = new LinkedBlockingDeque<>();

        // initialize all arrays
        for (int i = 0; i < numVertices; i++) {
            isProcessed[i] = false;
            isDiscovered[i] = false;
            parent[i] = -1;
        }

        // perform search
        q.add(start);
        isDiscovered[start] = true;

        Integer currentVertex;
        Integer nextVertex;
        EdgeNode edge;
        while (!q.isEmpty()) {
            // dequeue and process the next vertex
            currentVertex = q.remove();
            processVertexEarly(currentVertex);
            isProcessed[currentVertex] = true;
            // iterate over edges, and process each edge
            edge = g.getEdges(currentVertex);
            while (edge != null) {
                nextVertex = edge.v;
                // process next edge if not yet processed - or if graph is directed
                if (!isProcessed[nextVertex] || g.isDirected()) {
                    processEdge(currentVertex, nextVertex, edge);
                }
                // if next vertex has not been discovered, enqueue it and set its parent to current vertex
                if (!isDiscovered[nextVertex]) {
                    q.add(nextVertex);
                    isDiscovered[nextVertex] = true;
                    parent[nextVertex] = currentVertex;
                }
                edge = edge.getNext();
            }
            processVertexLate(currentVertex);
        }

        return parent;
    }

    // ---

    /** Performs a depth-first search of the specified graph, from the specified starting point.
     *  Passes lots of information to the recursive method.
     *  Returns the array of parent node information (basically the DFS tree)
     */
    public static int[] depthFirstSearch(Graph g, int start) {
        // initialize isDicovered and isProcessed arrays - to keep track of visited nodes in DFS
        int numVertices = g.getNumVertices();
        // initialize parent array to -1, to represent no parent vertex
        int[] parent = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            parent[i] = -1;
        }
        return depthFirstSearch(g, start, new boolean[numVertices], new boolean[numVertices], parent);
    }

    /** Recursive DFS method */
    private static int[] depthFirstSearch(Graph g, int currentVertex, boolean[] isDiscovered, boolean[] isProcessed, int[] parent) {
        isDiscovered[currentVertex] = true;
        processVertexEarly(currentVertex);

        EdgeNode edge = g.getEdges(currentVertex);
        int nextVertex;
        while (edge != null) {
            nextVertex = edge.v;
            // if next vertex has not yet been discovered, record info, process
            //  and recursively DFS
            if (!isDiscovered[nextVertex]) {
                parent[nextVertex] = currentVertex;
                processEdge(currentVertex, nextVertex, edge);
                depthFirstSearch(g, nextVertex, isDiscovered, isProcessed, parent);
            }
            // process the edge if it's not already processed - or if graph is directed
            else if (!isProcessed[nextVertex] || g.isDirected()) {
                processEdge(currentVertex, nextVertex, edge);
            }
            edge = edge.getNext();
        }
        processVertexLate(currentVertex);
        isProcessed[currentVertex] = true;

        return parent;
    }

    // ---
    // "process" functions - dummy functions for now

    public static void processVertexEarly(int v) {
        System.out.println("["+v+"] Processed before edges");
    }

    public static void processVertexLate(int v) {
        System.out.println("["+v+"] Processed after edges");
    }

    public static void processEdge(int v, int y, EdgeNode edge) {
        System.out.println("["+v+"->"+y+"] Processed edge.  weight="+edge.weight);
    }
}
