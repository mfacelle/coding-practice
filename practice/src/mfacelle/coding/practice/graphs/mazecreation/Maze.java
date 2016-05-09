package mfacelle.coding.practice.graphs.mazecreation;

import mfacelle.coding.practice.graphs.EdgeNode;
import mfacelle.coding.practice.graphs.Graph;
import mfacelle.coding.practice.graphs.GraphSearchAlgorithms;

import java.util.List;
import java.util.Random;

/**
 *  A class used for maze generation.
 *  Generates a random rectangular maze of size (x,y)
 */
public class Maze {

    private static final int RNG_MAX = 10;    // RNG max value for random edge-weight creation

    private Graph maze;                 // Graph representation of the maze
    private int numVertices;            // total number of vertices
    private int sizeX;  // size in x-dimension
    private int sizeY;  // size in y-dimension

    // ---

    public Maze(int xx, int yy) {
        sizeX = xx;
        sizeY = yy;
        numVertices = xx * yy;
        maze = new Graph(numVertices);
    }

    // ---



    // ===========
    // GENERATION

    /**
     *  Generates a random rectangular maze of dimensions (sizeX,sizeY), represented by a Graph structure.
     *  Does so by creating random-weight vertices at each i,j and running prim's algorithm
     *   to determine actual edges between vertices.
     *
     *  Can be re-run to generate a new maze with the same dimensions
     *
     */
    public Graph generateMaze() {
        // generate graph with random edge weights
        maze = new Graph(numVertices);
        boolean[][] isVisited = new boolean[sizeX][sizeY];  // default initialized to all false

        int vertexNum;      // pointer to array slot of each x,y vertex
        int nextVertexNum;  // pointer to array slot of each next x,y vertex (edge pointer)
        Random rand = new Random(); // RNG for random edge-weight creation

        // iterate over all vertices.  If any adjacent vertex is unvisited, then set a random edge weight
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                isVisited[x][y] = true;
                vertexNum = pointToVertexNum(x,y);

                // UP    (x,   y-1)
                if (y > 0 && !isVisited[x][y-1]) {
                    nextVertexNum = pointToVertexNum(x, y-1);
                    maze.insertEdge(vertexNum, nextVertexNum, rand.nextInt(RNG_MAX));
                }
                // DOWN  (x,   y+1)
                if (y < sizeY-1 && !isVisited[x][y+1]) {
                    nextVertexNum = pointToVertexNum(x, y+1);
                    maze.insertEdge(vertexNum, nextVertexNum, rand.nextInt(RNG_MAX));
                }
                // LEFT  (x-1, y)
                if (x > 0 && !isVisited[x-1][y]) {
                    nextVertexNum = pointToVertexNum(x-1, y);
                    maze.insertEdge(vertexNum, nextVertexNum, rand.nextInt(RNG_MAX));
                }
                // RIGHT (x+1, y)
                if (x < sizeX-1 && !isVisited[x+1][y]) {
                    nextVertexNum = pointToVertexNum(x+1, y);
                    maze.insertEdge(vertexNum, nextVertexNum, rand.nextInt(RNG_MAX));
                }
            }
        }

        // create minimum spanning tree, using prim's algorithm
        int[] parent = prim();

        // reconstruct the actual graph, now using the minimum spanning tree
        maze = new Graph(numVertices);
        // start at i=1, since parent[0] == -1 (root is always 0,0)
        // avoids testing for parent[i]==-1 every time
        for (int i = 1; i < numVertices; i++) {
            maze.insertEdge(i, parent[i]);  // add edge from vertex to parent (undirected, weight=1)
        }

        return maze;
    }

    // ---

    /** Modified prim's algorithm.  Computes the minimum spanning tree, but modifies the original graph.
     *  When next vertex found for MST, removes all other outgoing unvisited edges from that node,
     *   keeping only the edges in the MST, thus creating the maze.
     *
     *   Returns the parent array representing the MST
     *
     */
    private int[] prim() {
        int[] distance = new int[numVertices];          // min distance to each vertex
        boolean[] isVisited = new boolean[numVertices]; // if each vertex has been checked or not
        int[] parent = new int[numVertices];            // parent array representing the MST
        for (int i = 0; i < numVertices; i++) {
            distance[i] = Integer.MAX_VALUE;
            isVisited[i] = false;
            parent[i] = -1;
        }
        distance[0] = 0; // maze starts at 0,0

        // used in loop
        int currentVertex = 0;  // pointer to current vertex
        EdgeNode edge;              // adjacency list for current vertex
        int weight;                 // weight of current edge being checked
        int nextVertex;             // next vertex pointer for current edge being checked
        int nextMinDistance;        // for checking next smallest distance to vertex

        // go until the only option left is an already-visited vertex (reaches end of for-loop)
        while (!isVisited[currentVertex]) {
            // set current vertex to visited
            isVisited[currentVertex] = true;
            // iterate over all edges - check for min distance
            edge = maze.getEdges(currentVertex);
            while (edge != null) {
                weight = edge.weight;
                nextVertex = edge.v;
                // if current edge is smaller than previously recorded for that vertex
                if (weight < distance[nextVertex] && !isVisited[nextVertex]) {
                    // record new distance and parent node
                    distance[nextVertex] = weight;
                    parent[nextVertex] = currentVertex;
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

        return parent;
    }

    // ---

    /** Finds path from start point to end point - using only int arguments */
    public Point[] findPath(int startX, int startY, int endX, int endY) {
        return findPath(new Point(startX, startY), new Point(endX, endY));
    }

    /** Finds the path from start point to end point.
     *  Returns an array of points where [0]=start, and [length-1]=end.
     *
     *  Uses BFS since maze is an unweighted graph
     *
     */
    public Point[] findPath(Point start, Point end) {
        // create BFS algorithm impl - don't care about processing edges and vertices
        GraphSearchAlgorithms algorithms = new GraphSearchAlgorithms(maze) {
            @Override
            public void preProcessVertex(int v) {   return; }
            @Override
            public void postProcessVertex(int v) {  return; }
            @Override
            public void processEdge(int v, int y) { return; }
        };

        int startVertex = pointToVertexNum(start);
        int endVertex = pointToVertexNum(end);

        // create the bfs tree array representation - starting at vertex number represented by start point
        algorithms.breadthFirstSearch(startVertex);
        // recreate the path, using vertex numbers
        List<Integer> vertexPath = algorithms.findPath(startVertex, endVertex);

        // create the path in terms of points
        Point[] path = new Point[vertexPath.size()];
        for (int i = 0; i < path.length; i++) {
            Integer vertex = vertexPath.get(i);
            path[i] = vertexNumToPoint(vertex);
        }

        return path;
    }

    // ===========
    // util functions

    public int getSizeX() { return sizeX; }
    public int getSizeY() { return sizeY; }
    public EdgeNode getEdges(int vertex) { return maze.getEdges(vertex); }

    //
    // converts a point to array num (and vice versa)
    // points are added along each x, then to next y and along x
    // i.e: [0,0]=0,  [0,1]=1,  [sizeX-1,sizeY-1]=numVertices
    //
    // assumes all values passed will be valid (ie, no n > numVertices, or x > sizeX, y > sizeY, etc)

    public int pointToVertexNum(Point p) {
        return pointToVertexNum(p.x, p.y);
    }

    public int pointToVertexNum(int x, int y) {
        return y*sizeX + x;
    }

    public Point vertexNumToPoint(int n) {
        int x = n % sizeX;
        int y = n / sizeX;
        return new Point(x,y);
    }

    // ---

    public String graphToString() {
        return maze.toString();
    }

    /** Generates a string representing the maze.
     *   Prints a 'o' at each vertex and  '---' or '|' to show horizontal/vertical edges.
     *
     *  i.e.
     *  o---o   o---o   o
     *  |   |   |   |   |
     *  o   o---o   o---o
     *  |       |
     *  o---o   o---o---o
     *
     *  Very expensive, due to multiple graph adjacency list queries...
     *  Will only be used during tests, anyway.
     *  Will use these algorithms in Unity to actually display the maze
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int vertexNum;
        Point nextVertex;
        EdgeNode edge;
        boolean hasNextEdge;   // if the vertex has an edge pointing to the right

        for (int y = 0; y < sizeY; y++) {
            // print vertices and horizontal edges
            for (int x = 0; x < sizeX; x++) {
                sb.append("o"); // mark the vertex
                hasNextEdge = false;
                vertexNum = pointToVertexNum(x,y);
                edge = maze.getEdges(vertexNum);
                while (edge != null) {
                    nextVertex = vertexNumToPoint(edge.v);
                    if (nextVertex.x == x+1) {
                        hasNextEdge = true;
                    }
                    edge = edge.getNext();
                }

                if (hasNextEdge) {
                    sb.append("---");
                }
                else {
                    sb.append("   ");
                }
            }
            sb.append("\n");
            // print all vertical edges
            for (int x = 0; x < sizeX; x++) {
                hasNextEdge = false;
                vertexNum = pointToVertexNum(x,y);
                edge = maze.getEdges(vertexNum);
                while (edge != null) {
                    nextVertex = vertexNumToPoint(edge.v);
                    if (nextVertex.y == y+1) {
                        hasNextEdge = true;
                    }
                    edge = edge.getNext();
                }

                if (hasNextEdge) {
                    sb.append("|");
                }
                else {
                    sb.append(" ");
                }
                sb.append("   ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }


}
