package mfacelle.coding.practice.graphs

import spock.lang.Specification


class IndependentSetFinderTest extends Specification {

    private IndependentSetFinder setFinder
    private Graph g

    // ---

    def setup() {
        // create graph
        g = new Graph(8);

        g.insertEdge(0,1)
        g.insertEdge(0,5)
        g.insertEdge(1,2)
        g.insertEdge(1,3)
        g.insertEdge(1,4)
        g.insertEdge(2,4)
        g.insertEdge(3,6)
        g.insertEdge(4,6)
        g.insertEdge(4,7)
        g.insertEdge(5,6)
        g.insertEdge(6,7)

        setFinder = new IndependentSetFinder(g)
    }

    // ---

    def "test maximal independent set"() {
        when: "independent set finder is run for the graph provided"
        boolean[] independentSet = setFinder.findMaximalIndependentSet()
        int size = 0
        for (int i = 0; i < independentSet.length; i++) {
            size += independentSet[i] ? 1 : 0
        }

        then: "the set should match what was done by hand"
        println("independent set: " + independentSet)
        size == 4
        independentSet[0]
        !independentSet[1]
        independentSet[2]
        independentSet[3]
        !independentSet[4]
        !independentSet[5]
        !independentSet[6]
        independentSet[7]

    }
}
