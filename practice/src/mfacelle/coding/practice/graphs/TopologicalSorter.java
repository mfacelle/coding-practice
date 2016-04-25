package mfacelle.coding.practice.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs a topological sort on a graph
 */
public class TopologicalSorter {

    private Graph graph;
    private int numVertices;
    private List<Integer> topologicalSort;

    private boolean[] isVisited;
    private boolean[] isProcessed;

    // ---

    public TopologicalSorter(Graph g) {
        graph = g;
        numVertices = g.getNumVertices();
    }

    // ---

    private void initialize() {
        topologicalSort = new ArrayList<Integer>();
        isVisited = new boolean[numVertices];
        isProcessed = new boolean[numVertices];
    }

    // ---

    /** Performs a topological sort of the graph in this class.
     *  Does this by performing a modified DFS, starting at each vertex until a full list is found
     *
     */
    public List<Integer> topsort() {
        // try each vertex as the source vertex, since not all starting points form a topological sort
        for (int i = 0; i < numVertices; i++) {
            System.out.println("\nSTARTING TOPSORT on v"+i);
            initialize();
            // if full topological sort from this node was possible, then exit loop - topsort has been found
            if (topsortDFS(i) && topologicalSort.size() == numVertices) {
                break;
            }
            System.out.println("moving on to next vertex");
        }

        return topologicalSort;
    }

    // ---

    /** A modified DFS to add vertices to the topsort list.
     *  Modified to check for non-DAG graph, set appropriate flags, and not process edges
     *  Because of recursion, the root node will be added to the top of the list, and further-out nodes will
     *   be added to the end.
     *
     */
    private boolean topsortDFS(int currentVertex) {
        System.out.println("topsortDFS on v"+currentVertex);
        // if vertex has already been visited, then this is not a DAG
        if (isVisited[currentVertex]) {
            System.out.println("   already visited");
            return false;
        }

        // only perform DFS if this vertex has not been processed
        if (!isProcessed[currentVertex]) {
            System.out.println("   not already visited");
            // mark vertex as visited
            isVisited[currentVertex] = true;

            // iterate over all edges and recurse further into DFS
            EdgeNode edge = graph.getEdges(currentVertex);
            int nextVertex;
            while (edge != null) {
                nextVertex = edge.v;
                // if recursion finds graph is not a DAG, return false
                if (!topsortDFS(nextVertex)) {
                    return false;
                }
                edge = edge.getNext();
            }
            // mark vertex as processed and add it to the head of the topsort list
            isProcessed[currentVertex] = true;
            topologicalSort.add(currentVertex);

            // unmark as visited and start from another node
            isVisited[currentVertex] = false;
        }

        // get next unprocessed vertex to start DFS on
        int nextVertex;
        for (int i = 0; i < numVertices; i++) {
            if (!isProcessed[i]) {
                nextVertex = i;
                break;
            }
        }

        return true;
    }
}
