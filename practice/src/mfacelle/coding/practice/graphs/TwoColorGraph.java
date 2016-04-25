package mfacelle.coding.practice.graphs;

/**
 *  A bipartite graph
 *
 *  TODO : convert this to extend only GraphSearchAlgorithm !!!
 */
public class TwoColorGraph extends Graph {

    public enum Color { UNCOLORED, WHITE, BLACK }; // enum for coloring graphs

    private boolean isBipartite;    // whether or not the graph is actually bipartite
    private Color[] color;

    private GraphSearchAlgorithms searchAlgorithms;

    // ---

    public TwoColorGraph(int size) {
        this(size, false);
    }

    public TwoColorGraph(int size, boolean directed) {
        super(size, directed);
        isBipartite = colorGraph();
        searchAlgorithms = new BipartiteSearchAlgorithm(this);
    }

    // ---

    /** performs a two-color of the graph.  returns if it is possible or not
     *
     */
    public boolean colorGraph() {
        int n = getNumVertices();
        // initialize colors
        for (int i = 0; i < n; i++) {
            color[i] = Color.UNCOLORED;
        }

        // perform a BFS, starting at each vertex not found by a previous BFS
        for (int i = 0; i < n; i++) {
            // if vertex not yet discovered by any bfs
            if (!searchAlgorithms.isDiscovered(i)) {
                // color it (white, by default) - could have some conflicts in a weakly-connected directed graph...
                color[i] = Color.WHITE;
                searchAlgorithms.breadthFirstSearch(i);
            }
        }

        return isBipartite;
    }


    // ---

    /** returns the opposite color in a white/black two-color system */
    private Color complement(Color c) {
        if (c == Color.WHITE) {
            return Color.BLACK;
        }
        else if (c == Color.BLACK) {
            return Color.WHITE;
        }
        else {
            return Color.UNCOLORED;
        }
    }

    // =======

    /** implements BFS to two-color the graph during the BFS process */
    private class BipartiteSearchAlgorithm extends GraphSearchAlgorithms {

        public BipartiteSearchAlgorithm(Graph g) {
            super(g);
        }

        public void preProcessVertex(int v) {
            // do nothing
        }

        public void postProcessVertex(int v) {
            // do nothing
        }

        /** process an edge by checking if colors already match.
         *  if they dont match, then complement the start vertex and color the end vertex
         */
        public void processEdge(int v, int y) {
            if (color[v] == color[y]) {
                isBipartite = false;
            }
            else {
                color[y] = complement(color[v]);
            }
        }

    }
}
