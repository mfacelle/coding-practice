package mfacelle.coding.practice.graphs

import spock.lang.Specification


class TopologicalSorterTest extends Specification {

    private Graph g
    private TopologicalSorter topsorter

    // ---

    def setup() {
        // create DAG from Skiena p481
        g = new Graph(10, true)

        g.insertEdge(1,0)
        g.insertEdge(2,0)
        g.insertEdge(4,0)
        g.insertEdge(6,0)
        g.insertEdge(3,1)
        g.insertEdge(5,1)
        g.insertEdge(9,1)
        g.insertEdge(5,2)
        g.insertEdge(8,2)
        g.insertEdge(7,3)
        g.insertEdge(9,4)

        topsorter = new TopologicalSorter(g)
    }

    // ---

    def "test topological sort"() {
        when: "graph is topologically sorted"
        List<Integer> sort = topsorter.topsort()
        println("TOPOLOGICAL SORT: " + sort)

        then: "results should equal a version worked out by hand (multiple possible versions, though)"
        // this is the version that will occur due to the algorithm and graph structure used
        sort.size() == 10
        sort.get(0) == 5
        sort.get(1) == 9
        sort.get(2) == 4
        sort.get(3) == 7
        sort.get(4) == 3
        sort.get(5) == 1
        sort.get(6) == 8
        sort.get(7) == 2
        sort.get(8) == 6
        sort.get(9) == 0
    }
}
