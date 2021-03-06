package mfacelle.coding.practice.graphs.mazecreation;

/**
 *  Vertex in a graph representing a maze.
 *  Intended to be randomly-generated by prim's algorithm.
 *
 *  Each vertex has:
 *      a weight associated with it (the weight of travelling TO it)
 *      a vertexNum pointing to the array slot in the graph (for getting edges)
 *      no x,y positions required, since it is stored in a matrix in the Maze class
 */
public class MazeVertex {
    public final int weight;    // edge weight travelling TO this vertex
    public final int vertexNum; // number in the Graph's EdgeNode[]

    public MazeVertex(int ww, int nn) {
        this.weight = ww;
        this.vertexNum = nn;
    }
}
