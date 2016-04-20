package mfacelle.coding.practice.graphs

import spock.lang.Specification


class WeightedGraphAlgorithmsTest extends Specification {

    private Graph g
    private WeightedGraphAlgorithms algorithms

    // ---

    def setup() {
        // create new graph identical to Skiena p196
        // undirected weighted graph with 7 vertices
        // numbered 0-6 going clockwise from A (center is last vertex)
        g = new Graph(7)

        g.insertEdge(0,1,5)
        g.insertEdge(0,6,7)
        g.insertEdge(1,2,7)
        g.insertEdge(1,6,9)
        g.insertEdge(2,3,5)
        g.insertEdge(2,6,4)
        g.insertEdge(2,4,2)
        g.insertEdge(3,4,2)
        g.insertEdge(4,5,7)
        g.insertEdge(4,6,3)
        g.insertEdge(5,0,12)
        g.insertEdge(5,6,4)

        algorithms = new WeightedGraphAlgorithms(g)
    }

    // ---

    def "test prim algorithm"() {
        given: "printing out information"
        println("\nPRIM'S ALGORITHM")
        println("GRAPH:\n"+g)

        when: "prims algorithm is run on the graph from vertex 0"
        long startTime = System.currentTimeMillis()
        int spanningTree = algorithms.primMinimumSpanningTree(0)
        long endTime = System.currentTimeMillis()

        int[] parent = algorithms.getParent()
        int[] distance = algorithms.getDistance()
        long dt = endTime - startTime

        println("parent   : " + parent)
        println("distance : " + distance)
        println("time(ms) : " + dt)

        then: "parents array should be formatted as reported in Skiena (and done by hand)"
        spanningTree == 23;
        // apparently this constructs the kruskal minimum spanning tree...
        parent[0] == -1
        parent[1] == 0
        parent[2] == 1
        parent[3] == 4
        parent[4] == 2
        parent[5] == 6
        parent[6] == 4
        distance[0] == 0
        distance[1] == 5
        distance[2] == 7
        distance[3] == 2
        distance[4] == 2
        distance[5] == 4
        distance[6] == 3
    }

    // ---

    def "test kruskal algorithm"() {
        given: "printing out information"
        println("\nKRUSKAL'S ALGORITHM")
        println("GRAPH:\n"+g)

        when: "prims algorithm is run on the graph from vertex 0"
        long startTime = System.currentTimeMillis()
        int spanningTree = algorithms.kruskalMinimumSpanningTree()
        long endTime = System.currentTimeMillis()

        int[] parent = algorithms.getParent()
        int[] distance = algorithms.getDistance()
        long dt = endTime - startTime

        println("parent   : " + parent)
        println("distance : " + distance)
        println("time(ms) : " + dt)

        then: "parents array should be formatted as reported in Skiena (and done by hand)"
        spanningTree == 23;
        // apparently this constructs the kruskal minimum spanning tree... still same dist=23
        parent[0] == -1
        parent[1] == 0
        parent[2] == 1
        parent[3] == 4
        parent[4] == 2
        parent[5] == 6
        parent[6] == 4
        distance[0] == 0
        distance[1] == 5
        distance[2] == 7
        distance[3] == 2
        distance[4] == 2
        distance[5] == 4
        distance[6] == 3
    }
}
