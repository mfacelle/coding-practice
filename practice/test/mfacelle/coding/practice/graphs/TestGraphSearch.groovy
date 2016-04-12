package mfacelle.coding.practice.graphs

import spock.lang.Specification


/** class to test graph creation, BFS and DFS algorithms */
class TestGraphSearch extends Specification {

    private Graph graph
    private Graph directedGraph


    // ---

    def setup() {
        // create graph with 6 vertices - not directed
        graph = new Graph(6)
        graph.insertEdge(0,1)
        graph.insertEdge(1,2)
        graph.insertEdge(2,3)
        graph.insertEdge(3,4)
        graph.insertEdge(4,5)
        graph.insertEdge(0,2)
        graph.insertEdge(2,4)
        graph.insertEdge(5,1)

        // create directed graph with 6 vertices (cyclic)
        directedGraph = new Graph(6, true)
        directedGraph.insertEdge(0,1)
        directedGraph.insertEdge(1,2)
        directedGraph.insertEdge(2,3)
        directedGraph.insertEdge(3,4)
        directedGraph.insertEdge(4,5)
        directedGraph.insertEdge(0,2)
        directedGraph.insertEdge(2,4)
        directedGraph.insertEdge(5,1)
    }

    // ---

    def "test setup"() {
        when: "graphs are created and printed"
        println("Undirected Graph: \n" + graph)
        println("Directed Graph: \n" + directedGraph)

        then: "expect sizes and numVertices, etc to be correct"
        graph.getNumVertices() == 6
        graph.getNumEdges() == 16   // double - due to undirected
        graph.getNumEdges(0) == 2
        graph.getNumEdges(1) == 3
        graph.getNumEdges(2) == 4
        graph.getNumEdges(3) == 2
        graph.getNumEdges(4) == 3
        graph.getNumEdges(5) == 2

        directedGraph.getNumVertices() == 6
        directedGraph.getNumEdges() == 8
        directedGraph.getNumEdges(0) == 2
        directedGraph.getNumEdges(1) == 1
        directedGraph.getNumEdges(2) == 2
        directedGraph.getNumEdges(3) == 1
        directedGraph.getNumEdges(4) == 1
        directedGraph.getNumEdges(5) == 1
    }

    // ---

    def "test BFS"() {
        when: "BFS method is called"
        println("BFS of graph\n" + graph)
        int[] parent = GraphAlgorithms.breadthFirstSearch(graph, 0)
        println("\nBFS of directed graph\n" + directedGraph)
        int[] directedParent = GraphAlgorithms.breadthFirstSearch(directedGraph, 0)

        then: "the parent array (BFS tree representation) should be correct - verified by hand"
        parent[0] == -1
        parent[1] == 0
        parent[2] == 0
        parent[3] == 2
        parent[4] == 2
        parent[5] == 1

        directedParent[0] == -1
        directedParent[1] == 0
        directedParent[2] == 0
        directedParent[3] == 2
        directedParent[4] == 2
        directedParent[5] == 4
    }

    // ---

    def "test DFS"() {
        when: "DFS method is called"
        println("DFS of graph\n" + graph)
        int[] parent = GraphAlgorithms.depthFirstSearch(graph, 0)
        println("\nDFS of directed graph\n" + directedGraph)
        int[] directedParent = GraphAlgorithms.depthFirstSearch(directedGraph, 0)

        then: "the parent array (DFS tree representation) should be correct - verified by hand"
        parent[0] == -1
        parent[1] == 5
        parent[2] == 0
        parent[3] == 4
        parent[4] == 2
        parent[5] == 4

        directedParent[0] == -1
        directedParent[1] == 5
        directedParent[2] == 0
        directedParent[3] == 2
        directedParent[4] == 2
        directedParent[5] == 4
    }
}
