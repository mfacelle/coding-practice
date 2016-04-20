package mfacelle.coding.practice.graphs;

/**
 * An edge in a graph - different than an EdgeNode.
 *   While EdgeNode is a linked list object, Edge simply describes one edge in a graph
 */
public class Edge implements Comparable<Edge> {
    public final int start;
    public final int end;
    public final boolean isDirected;    // if true, then edge goes only from start -> end (default:false)
    public final int weight;

    // ---

    /** Constructs */
    public Edge(int x, int y, int w) {
        this(x, y, w, false);
    }

    public Edge(int x, int y, int w, boolean directed) {
        start = x;
        end = y;
        weight = w;
        isDirected = directed;
    }

    // ---

    @Override
    public String toString()

    // ---

    /** copmareTo method (for sorting).
     *  returns:
     *    0  if edge weights are equal
     *   >0  if argument weight is greater than this edge weight
     *   <0  if argument weight is less than this edge weight
     */
    @Override
    public int compareTo(Edge edge) {
        return edge.weight - this.weight;
    }
}
