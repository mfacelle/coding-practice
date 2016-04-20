package mfacelle.coding.practice.graphs;

import mfacelle.coding.practice.sorting.Sorting;

/**
 * Class to implement some weighted graph algorithms - min spanning tree (prim/kruskal), shortest path (dijkstra), etc
 */
public class WeightedGraphAlgorithms {

    private Graph graph;
    private int numVertices;

    // used in algorithms
    int[] parent;           // array of parent vertex numbers (for spanning tree)
    boolean[] isVisited;    // if each vertex has been checked or not
    int[] distance;         // min distance to each vertex from start
    int spanningTreeDistance;   // sum of all edges in minimum spanning tree

    // ---

    public WeightedGraphAlgorithms(Graph g) {
        graph = g;
        numVertices = g.getNumVertices();
    }

    // ---

    private void initialize() {
        // create arrays
        parent = new int[numVertices];
        isVisited = new boolean[numVertices];
        distance = new int[numVertices];
        // initialize arrays
        for (int i = 0; i < numVertices; i++) {
            isVisited[i] = false;
            distance[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }
    }

    // ---

    /** Computes the minimum spanning tree (prim's algorithm) of the graph in the class.
     *  Starts from vertex specified
     */
    public int primMinimumSpanningTree(int start) {
        initialize();
        distance[start] = 0;

        // used in loop
        int currentVertex = start;  // pointer to current vertex
        EdgeNode edge;              // adjacency list for current vertex
        int weight;                 // weight of current edge being checked
        int nextVertex;             // next vertex pointer for current edge being checked
        int nextMinDistance;        // for checking next smallest distance to vertex

        // go until the only option left is an already-visited vertex (reaches end of for-loop)
        while (!isVisited[currentVertex]) {
            // set current vertex to visited
            isVisited[currentVertex] = true;
            // iterate over all edges in graph - check for min distance
            edge = graph.getEdges(currentVertex);
            while (edge != null) {
                weight = edge.weight;
                nextVertex = edge.v;
                // if current edge is smaller than previously recorded for that vertex
                if (weight < distance[nextVertex] && !isVisited[nextVertex]) {
                    // record new distance and parent node
                    distance[nextVertex] = weight;
                    parent[nextVertex] = currentVertex;
                }
                else {
                }
                edge = edge.getNext();
            }

            // get the next vertex with smallest distance to it
            // if end of loop is reached, then all nodes have been visited (while loop will then exit)
            nextMinDistance = Integer.MAX_VALUE;
            for (int i = 0; i < numVertices; i++) {
                // if vertex unvisited, and distance to this vertex is smaller than current min
                if (!isVisited[i] && distance[i] < nextMinDistance) {
                    // set current min, and pointer to current vertex
                    nextMinDistance = distance[i];
                    currentVertex = i;
                }
            }
        }
        // compute spanning tree distance
        computeSpanningTreeDistance();
        // return parent array representation of min spanning tree
        return spanningTreeDistance;
    }

    // ---

    /** Performs kruskal's minimum spanning tree algorithm.
     *   don't like it as much - hard to set parent array and distance array,
     *   and root of tree gets distance == inf...
     *
     *   probably a way around this, but I don't want to bother trying right now - prim is nice and clean
     *
     */
    public int kruskalMinimumSpanningTree() {
        initialize();
        UnionFind union = new UnionFind(numVertices);
        // sort the edges by weight (puts in increasing order)
        Edge[] edges = graph.toEdgeArray();
        edges = (new Sorting<Edge>()).mergeSort(edges);

        for (int i = 0; i < edges.length; i++) {
            // if start and end edge don't belong to same set already
            if (!union.isSameSet(edges[i].start, edges[i].end)) {
                // then merge the sets
                union.setUnion(edges[i].start, edges[i].end);
                spanningTreeDistance += edges[i].weight;
                System.out.println("adding edge : " + edges[i]);
                // set parent of this node
                // if parent for end node doesn't yet exist, set it
                if (parent[edges[i].end] == -1) {
                    parent[edges[i].end] = edges[i].start;
                    distance[edges[i].end] = edges[i].weight;
                }
                else {  // set parent of start node - avoid overwriting parents
                    parent[edges[i].start] = edges[i].end;
                    distance[edges[i].start] = edges[i].weight;
                }
            }
        }

        return spanningTreeDistance;
    }

    // ---

    /** sets the spanning tree distance by summing up all edges in the parent array */
    private void computeSpanningTreeDistance() {
        spanningTreeDistance = 0;
        for (int i = 0; i < numVertices; i++) {
            spanningTreeDistance += distance[i];
        }
    }

    // ---

    public int[] getDistance() { return distance; }
    public int[] getParent() { return parent; }
    public int getSpanningTreeDistance() { return spanningTreeDistance; }
}
