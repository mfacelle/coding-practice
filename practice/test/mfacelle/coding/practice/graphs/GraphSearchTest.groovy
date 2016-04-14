package mfacelle.coding.practice.graphs

import spock.lang.Specification


/** class to test graph creation, BFS and DFS algorithms */
class GraphSearchTest extends Specification {

    private Graph graph
    private Graph directedGraph

    private GraphSearchAlgorithms graphAlgorithms;
    private GraphSearchAlgorithms directedGraphAlgorithms;


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

        graphAlgorithms = new GraphSearchAlgorithms(graph) {
            @Override
            void preProcessVertex(int v) { System.out.println("["+v+"] PRE-PROCESS VERTEX"); }

            @Override
            void postProcessVertex(int v) { System.out.println("["+v+"] POST-PROCESS VERTEX"); }

            @Override
            void processEdge(int v, int y) { System.out.println("["+v+"->"+y+"] PROCESS EDGE"); }
        }
        directedGraphAlgorithms = new GraphSearchAlgorithms(directedGraph) {
            @Override
            void preProcessVertex(int v) { System.out.println("["+v+"] PRE-PROCESS VERTEX"); }

            @Override
            void postProcessVertex(int v) { System.out.println("["+v+"] POST-PROCESS VERTEX"); }

            @Override
            void processEdge(int v, int y) { System.out.println("["+v+"->"+y+"] PROCESS EDGE"); }
        }
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
        println("\nBFS of graph\n" + graph)
        int[] parents = graphAlgorithms.breadthFirstSearch(0)
        println("\nBFS of directed graph\n" + directedGraph)
        int[] directedParents = directedGraphAlgorithms.breadthFirstSearch(0)

        then: "the parents array (BFS tree representation) should be correct - verified by hand"
        parents[0] == -1
        parents[1] == 0
        parents[2] == 0
        parents[3] == 2
        parents[4] == 2
        parents[5] == 1

        directedParents[0] == -1
        directedParents[1] == 0
        directedParents[2] == 0
        directedParents[3] == 2
        directedParents[4] == 2
        directedParents[5] == 4
    }

    // ---

    def "test DFS"() {
        when: "DFS method is called"
        println("\nDFS of graph\n" + graph)
        int[] parents = graphAlgorithms.depthFirstSearch(0)
        println("\nDFS of directed graph\n" + directedGraph)
        int[] directedParents = directedGraphAlgorithms.depthFirstSearch(0)

        then: "the parents array (DFS tree representation) should be correct - verified by hand"
        parents[0] == -1
        parents[1] == 5
        parents[2] == 0
        parents[3] == 4
        parents[4] == 2
        parents[5] == 4

        directedParents[0] == -1
        directedParents[1] == 5
        directedParents[2] == 0
        directedParents[3] == 2
        directedParents[4] == 2
        directedParents[5] == 4
    }
    
    // ---
    
    def "test find path"() {
        given: "a BFS parents array is created"
        int[] parents = graphAlgorithms.breadthFirstSearch(0)
        int[] directedParents = directedGraphAlgorithms.breadthFirstSearch( 0)

        when: "findPath is called for various paths"
        List<Integer> path_0_5 = graphAlgorithms.findPath(0, 5)
        List<Integer> directedPath_0_5 = directedGraphAlgorithms.findPath(0, 5)

        then: "the path returned should be equivalent to the one worked out by hand"
        path_0_5.size() == 3
        path_0_5[0] == 0
        path_0_5[1] == 1
        path_0_5[2] == 5

        directedPath_0_5.size() == 4
        directedPath_0_5[0] == 0
        directedPath_0_5[1] == 2
        directedPath_0_5[2] == 4
        directedPath_0_5[3] == 5
    }
}
