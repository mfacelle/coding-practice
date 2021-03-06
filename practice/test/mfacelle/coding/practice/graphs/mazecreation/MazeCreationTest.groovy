package mfacelle.coding.practice.graphs.mazecreation

import mfacelle.coding.practice.graphs.Graph
import spock.lang.Specification


class MazeCreationTest extends Specification {

    private Maze maze

    private int sizeX = 20;
    private int sizeY = 10;

    // ---

    def setup() {
        maze = new Maze(sizeX,sizeY)
    }

    // ---

    def "test maze generation"() {
        when: "maze is generated"
        Graph g = maze.generateMaze()

        then: "just visually inspect it"
        println("GRAPH: \n" + g)
        println("\nMAZE: \n" + maze)
    }

    // ---

    def "test maze pathfinding"() {
        given: "a generated maze"
        long tBeforeMazeGen = System.currentTimeMillis()
        Graph g = maze.generateMaze()
        long mazeGenTime = System.currentTimeMillis() - tBeforeMazeGen

        println("maze generation ["+sizeX+"x"+sizeY+"] time taken: " + mazeGenTime + "ms")
        // print maze for inspection
        println("\nMAZE: \n" + maze)

        when: "path is found from top-left to bottom-right"
        long tBeforePathFind = System.currentTimeMillis()
        Point[] path = maze.findPath(0,0, sizeX-1, sizeY-1)
        long pathFindTime = System.currentTimeMillis() - tBeforePathFind
        println("path finding ["+sizeX+"x"+sizeY+"] time taken: " + pathFindTime + "ms")

        then: "path should be valid - visually inspect"
        println(path)
        println(MazePathPrinter.mazePathToString(maze, path))
    }

    // ---

    def "test point to vertex num conversion"() {
        when: "various points are converted to array slots and vice versa"
        Point slot0 = maze.vertexNumToPoint(0)
        Point slot5 = maze.vertexNumToPoint(5)
        Point slot64 = maze.vertexNumToPoint(64)
        Point slot82 = maze.vertexNumToPoint(82)
        Point slot100 = maze.vertexNumToPoint(100)

        int n_0_0 = maze.pointToVertexNum(0,0)
        int n_5_0 = maze.pointToVertexNum(5,0)
        int n_4_6 = maze.pointToVertexNum(4,6)
        int n_2_8 = maze.pointToVertexNum(2,8)
        int n_0_10 = maze.pointToVertexNum(0,10)

        then: "results should be as done by hand"
        slot0.x == 0
        slot0.y == 0
        slot5.x == 5
        slot5.y == 0
        slot64.x == 4
        slot64.y == 6
        slot82.x == 2
        slot82.y == 8
        slot100.x == 0
        slot100.y == 10
        n_0_0 == 0
        n_5_0 == 5
        n_4_6 == 64
        n_2_8 == 82
        n_0_10 == 100
    }
}
