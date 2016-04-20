package mfacelle.coding.practice.graphs;

/** Node that defines an edge used in a Graph */
public class EdgeNode implements Comparable<EdgeNode>
{
    // fields are protected for ease of access
    public final int v;        // what vertex this edge points to
    public final int weight;   // weight of this edge
    private EdgeNode next;    // next pointer (linked list)

    // ---

    public EdgeNode(int vertex, int mWeight) {
        v = vertex;
        weight = mWeight;
    }

    // ---

    public EdgeNode getNext() { return next; }

    public void setNext(EdgeNode newNext) { next = newNext; }

    // ---

    public String toString() {
        return v + "(" + weight + ")";
    }

    // --

    /** copmareTo method (for sorting).
     *  returns:
     *    0  if edge weights are equal
     *   >0  if argument weight is greater than this edge weight
     *   <0  if argument weight is less than this edge weight
     */
    @Override
    public int compareTo(EdgeNode edge) {
        return edge.weight - this.weight;
    }
}