package mfacelle.coding.practice.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs a topological sort on a graph
 */
public class TopologicalSorter extends GraphSearchAlgorithms {

    // Skiena p178 describes four types of BFS/DFS edges possible
    private enum EdgeType {  // for an edge (x,y):
        Tree,       // standard edge in a BFS/DFS tree; parent[y]==x
        Back,       // an edge pointing to a vertex that has been discovered but not processed (denotes a cycle in a DAG)
        Forward,    // an edge where y is processed and was discovered after x
        Cross,      // and edge where y is processed and was discovered before x
        Unknown     // default edge - shouldn't happen
    };

    private List<Integer> topologicalSort;
    private boolean isDAG;


    // ---

    public TopologicalSorter(Graph g) {
        super(g);
        initialize();   // may be redundant, but superconstructor, I believe, calls super.initialize() only
    }

    // ---

    @Override
    public void initialize() {
        super.initialize();
        topologicalSort = new ArrayList<Integer>();
        isDAG = true;
    }

    // ---

    /** Performs a topological sort of the graph in this class.
     *  Does this by performing a modified DFS, starting at each vertex until a full list is found
     *
     */
    public List<Integer> topsort() {
        initialize();

        for (int i = 0; i < numVertices; i++) {
            if (!isDiscovered[i]) {
                depthFirstSearch(i);
            }
        }

        // if graph marked as not a DAG, or topological sort doesn't contain all vertices, return null
        if (!isDAG || topologicalSort.size() != numVertices) {
            return null;
        }

        return topologicalSort;
    }

    // ==========
    // overrides and additional functions

    /** determines the "type" of edge */
    private EdgeType classifyEdge(int x, int y) {
        if (parent[y] == x) {
            return EdgeType.Tree;
        }
        else if (isDiscovered[y] && !isProcessed[y]) {
            return EdgeType.Back;
        }
        else if (isProcessed[y] && entryTime[x] < entryTime[y]) {
            return EdgeType.Forward;
        }
        else if (isProcessed[y] && entryTime[y] < entryTime[x]) {
            return EdgeType.Cross;
        }
        else {
            return EdgeType.Unknown;
        }
    }

    // ---

    @Override
    public void preProcessVertex(int v) {
        return; // do nothing
    }

    @Override
    /** adds the vertex to the front of the topological sort list when done processing */
    public void postProcessVertex(int v) {
        topologicalSort.add(0, v);
    }

    @Override
    /** checks if the edge is a back */
    public void processEdge(int v, int y) {
        if (classifyEdge(v,y) == EdgeType.Back) {
            isDAG = false;
        }
    }



}
