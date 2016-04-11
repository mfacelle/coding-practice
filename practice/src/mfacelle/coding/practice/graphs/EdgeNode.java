package mfacelle.coding.practice.graphs;

/** Node that defines an edge used in a Graph */
public class EdgeNode
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
}