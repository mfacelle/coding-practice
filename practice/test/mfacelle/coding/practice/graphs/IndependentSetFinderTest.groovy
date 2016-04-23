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

    // ---

    def "test independent set finder"() {
        when: "independent set finder is run for the graph provided"
        boolean[] independentSet = setFinder.findIndependentSet()
        int size = 0
        for (int i = 0; i < independentSet.length; i++) {
            size += independentSet[i] ? 1 : 0
        }

        then: "the set should match what was done by hand (same as maximal set)"
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

    // ---

    def "test independent set finder with required vertex"() {
        when: "independent set finder is run for the graph provided, with required vertex = 4"
        boolean[] independentSet = setFinder.findIndependentSet(4)
        int size = 0
        for (int i = 0; i < independentSet.length; i++) {
            size += independentSet[i] ? 1 : 0
        }

        then: "the set should match what was done by hand"
        println("independent set (with vertex 4 required): " + independentSet)
        size == 3
        independentSet[0]
        !independentSet[1]
        !independentSet[2]
        independentSet[3]
        independentSet[4]
        !independentSet[5]
        !independentSet[6]
        !independentSet[7]
    }

    // ---

    def "test minimal vertex cover set"() {
        when: "independent set finder is run for the graph provided"
        boolean[] vertexCover = setFinder.findMinimalVertexCover()
        int size = 0
        for (int i = 0; i < vertexCover.length; i++) {
            size += vertexCover[i] ? 1 : 0
        }

        then: "the set should match what was done by hand"
        println("independent set: " + vertexCover)
        size == 4
        !vertexCover[0]
        vertexCover[1]
        !vertexCover[2]
        !vertexCover[3]
        vertexCover[4]
        vertexCover[5]
        vertexCover[6]
        !vertexCover[7]

    }
}
