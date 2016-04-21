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
        // parent tree is a bit weird (it's a set union - not same as prim)
        // distance tree is not set correctly
    }

    // ---

    def "test dijkstra algorithm"() {
        given: "printing out information"
        println("\nDIJKSTRA'S ALGORITHM")
        println("GRAPH:\n"+g)

        when: "dijkstra algorithm used to find shortest path between some nodes"
        int pathLength_0_3 = algorithms.leastCostPath(0,3)
        int[] path_0_3 = algorithms.getLeastCostPath()

        int pathLength_0_2 = algorithms.leastCostPath(0,2)
        int[] path_0_2 = algorithms.getLeastCostPath()

        then: "path-length and path itself should be what was worked out by hand"
        pathLength_0_3 == 12
        path_0_3.length == 4
        path_0_3[0] == 0
        path_0_3[1] == 6
        path_0_3[2] == 4
        path_0_3[3] == 3

        pathLength_0_2 == 11
        path_0_2.length == 3
        path_0_2[0] == 0
        path_0_2[1] == 6
        path_0_2[2] == 2
    }

}
