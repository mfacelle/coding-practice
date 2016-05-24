package mfacelle.coding.practice.graphs.mazecreation;

import mfacelle.coding.practice.graphs.EdgeNode;

/**
 *  Prints the path through a maze to stdout
 */
public class MazePathPrinter {

    /** Generates and prints a string representing the maze, with a path through it
     *   Prints a 'o' at each vertex and  '---' or '|' to show horizontal/vertical edges.
     *   Prints a 'x' along the path from start to end, indicated by a Point[], and a 'X' at vertices in the path
     *
     *  i.e. maze and path from 0,0 to 4,2
     *  XxxxX   o---o   o
     *  |   x   |   |   |
     *  o   XxxxX   o---o
     *  |       x
     *  o---o   XxxxXxxxX
     *
     *  Very expensive, due to multiple graph adjacency list queries...
     *      Even more expensive due to many queries of isInPath()
     *  Will only be used during tests and for debugging, anyway - not a real function to be used.
     *  Will use these algorithms in Unity to actually display the maze
     */
    public static String mazePathToString(Maze maze, Point[] path) {
        StringBuilder sb = new StringBuilder();

        int vertexNum;
        Point nextVertex;
        EdgeNode edge;
        boolean hasNextEdge;   // if the vertex has an edge pointing to the right

        for (int y = 0; y < maze.getSizeY(); y++) {
            // print vertices and horizontal edges
            for (int x = 0; x < maze.getSizeX(); x++) {
                if (isInPath(path, x, y)) {
                    sb.append("O"); // mark the vertex as a path vertex
                }
                else {
                    sb.append("o"); // mark the vertex
                }
                hasNextEdge = false;
                vertexNum = maze.pointToVertexNum(x,y);
                edge = maze.getEdges(vertexNum);
                while (edge != null) {
                    nextVertex = maze.vertexNumToPoint(edge.v);
                    if (nextVertex.x == x+1) {
                        hasNextEdge = true;
                    }
                    edge = edge.getNext();
                }

                if (hasNextEdge) {
                    // draw path "xxx" if current vertex is in path and so is next one
                    if (isInPath(path, x, y) && isInPath(path, x+1, y)) {
                        sb.append("xxx");
                    }
                    else {
                        sb.append("---");
                    }
                }
                else {
                    sb.append("   ");
                }
            }
            sb.append("\n");
            // print all vertical edges
            for (int x = 0; x < maze.getSizeX(); x++) {
                hasNextEdge = false;
                vertexNum = maze.pointToVertexNum(x,y);
                edge = maze.getEdges(vertexNum);
                while (edge != null) {
                    nextVertex = maze.vertexNumToPoint(edge.v);
                    if (nextVertex.y == y+1) {
                        hasNextEdge = true;
                    }
                    edge = edge.getNext();
                }

                if (hasNextEdge) {
                    // draw path 'x' if current vertex is in path, and so is vertex below
                    if (isInPath(path, x, y) && isInPath(path, x, y+1)) {
                        sb.append("x");
                    }
                    else {
                        sb.append("|");
                    }
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


    // ---

    /** determines if a point (x,y) is in the path array */
    private static boolean isInPath(Point[] path, int x, int y) {
        Point p = new Point(x,y);
        for (int i = 0; i < path.length; i++) {
            if (path[i].equals(p)) {
                return true;
            }
        }
        return false;
    }

}
