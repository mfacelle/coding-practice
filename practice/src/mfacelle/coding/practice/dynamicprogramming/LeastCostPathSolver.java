package mfacelle.coding.practice.dynamicprogramming;

import mfacelle.coding.practice.graphs.EdgeNode;
import mfacelle.coding.practice.graphs.Graph;

/**
 * Class that will find the least-cost path in a graph
 * Implements dijkstras algorithm (may add more later)
 */
public class LeastCostPathSolver {

    private Graph graph;    // graph - must be a DAG with no negative edges
    private int numVertices;

    private int[] distance; // solution dynamic programming matrix (shows shortest path to each point)
    private int[] parent;   // pointers to parent of each vertex in dijkstra path
    private boolean[] isVisited;    // keep track of which nodes have been visited

    // ---

    public LeastCostPathSolver(Graph g) {
        graph = g;
        numVertices = g.getNumVertices();
    }

    // -

    private void initialize() {
        // won't necessarily fill up every square - stops when it hits optimal path length
        distance = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            isVisited[i] = false;
            distance[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }
    }

    // ---

    /** performs dijkstra's algorithm to find the least-cost path */
    public int dijkstra(int start, int end) {
        initialize();

        // set starting node distance to 0
        distance[start] = 0;

        int currentVertex = start;

        EdgeNode edge;

        // keep going until an already-visited vertex is checked
        // basically means we haven't moved vertices
        while (!isVisited[currentVertex]) {
            // mark vertex as visited
            isVisited[currentVertex] = true;

            // traverse through all outgoing edges of this vertex
            edge = graph.getEdges(currentVertex);
            while (edge != null) {
                // if distance to next vertex is less than the current distance recorded for that vertex
                //  then update it
                if (distance[currentVertex]+edge.weight < distance[edge.v]) {
                    distance[edge.v] = distance[currentVertex] + edge.weight;
                    parent[edge.v] = currentVertex;
                }
                edge = edge.getNext();
            }

            // find the next non-visited node with smallest path-weight
            int distanceTemp = Integer.MAX_VALUE;
            for (int i = 0; i < numVertices; i++) {
                // if not visited and distance to vertex is less than the current min distance
                //  then update vertex pointer and min distance
                if (!isVisited[i] && distance[i] < distanceTemp) {
                    distanceTemp = distance[i];
                    currentVertex = i;
                }
            }
        }
        return distance[end];
    }
}
