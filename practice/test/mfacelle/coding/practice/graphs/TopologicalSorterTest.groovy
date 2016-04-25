package mfacelle.coding.practice.graphs

import spock.lang.Specification


class TopologicalSorterTest extends Specification {

    private Graph g1
    private Graph g2
    private TopologicalSorter topsorter

    // ---

    def setup() {
        // create DAG from Skiena p481
        g1 = new Graph(10, true)
        g1.insertEdge(1,0)
        g1.insertEdge(2,0)
        g1.insertEdge(4,0)
        g1.insertEdge(6,0)
        g1.insertEdge(3,1)
        g1.insertEdge(5,1)
        g1.insertEdge(9,1)
        g1.insertEdge(5,2)
        g1.insertEdge(8,2)
        g1.insertEdge(7,3)
        g1.insertEdge(9,4)

        // create DAG with only one topsort from Skiena p179
        g2 = new Graph(7, true)
        g2.insertEdge(0,1)
        g2.insertEdge(0,2)
        g2.insertEdge(1,2)
        g2.insertEdge(1,3)
        g2.insertEdge(2,4)
        g2.insertEdge(2,5)
        g2.insertEdge(4,3)
        g2.insertEdge(5,4)
        g2.insertEdge(6,0)
        g2.insertEdge(6,5)
    }

    // ---

    def "test topological sort"() {
        when: "graph is topologically sorted"
        topsorter = new TopologicalSorter(g1)
        List<Integer> sort1 = topsorter.topsort()
        println("\nGraph 1:\n" + g1)
        println("TOPOLOGICAL SORT 1: " + sort1 + "\n")

        topsorter = new TopologicalSorter(g2)
        List<Integer> sort2 = topsorter.topsort()
        println("\nGraph 2:\n" + g2)
        println("TOPOLOGICAL SORT 2: " + sort2)


        then: "results should equal a version worked out by hand (multiple possible versions for g1, though)"
        // this is the version that will occur due to the algorithm and graph structure used
        sort1.size() == 10
        sort1.get(0) == 9
        sort1.get(1) == 8
        sort1.get(2) == 7
        sort1.get(3) == 6
        sort1.get(4) == 5
        sort1.get(5) == 4
        sort1.get(6) == 3
        sort1.get(7) == 2
        sort1.get(8) == 1
        sort1.get(9) == 0

        // g2 has only one topsort
        sort2.size() == 7
        sort2.get(0) == 6
        sort2.get(1) == 0
        sort2.get(2) == 1
        sort2.get(3) == 2
        sort2.get(4) == 5
        sort2.get(5) == 4
        sort2.get(6) == 3
    }
}
