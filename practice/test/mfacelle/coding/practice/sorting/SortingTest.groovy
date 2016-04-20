package mfacelle.coding.practice.sorting

import mfacelle.coding.practice.graphs.Edge
import mfacelle.coding.practice.graphs.EdgeNode
import spock.lang.Specification


class SortingTest extends Specification {

    private int[] a;
    private int[] big;
    private int bigArraySize = 100;

    // ---

    def setup() {
        println("")

        a = [ 4, 5, 9, 1, 3, 8, 0, 7, 6, 2 ]
        println("Unsorted array: \n\t" + a)

        Random r = new Random()
        big = new int[bigArraySize]
        for (int i = 0; i < big.length; i++) {
            big[i] = r.nextInt(bigArraySize);
        }
        println("Unsorted random array: \n\t" + big)
    }

    // ---

    def "test selection sort"() {
        when: "selection sort performed"
        println("\nSELECTION SORT")
        IntegerSorting.selectionSort(a)
        long startTime = System.currentTimeMillis()
        IntegerSorting.selectionSort(big)
        long endTime = System.currentTimeMillis()

        println("Sorted array: \n\t" + a)
        println("Sorted random array: \n\t" + big)
        println("Elapsed time to sort big array: " + (endTime-startTime))

        then: "arrays should be sorted"
        // a array goes [0,9]
        for (int i = 0; i < a.length; i++) {
            a[i] == i
        }
        // big array must be in order
        for (int i = 1; i < big.length; i++) {
            big[i-1] <= big[i]
        }
    }

    // ---

    def "test insertion sort"() {
        when: "insertion sort performed"
        println("\nINSERTION SORT")
        IntegerSorting.insertionSort(a)
        long startTime = System.currentTimeMillis()
        IntegerSorting.insertionSort(big)
        long endTime = System.currentTimeMillis()

        println("Sorted array: \n\t" + a)
        println("Sorted random array: \n\t" + big)
        println("Elapsed time to sort big array: " + (endTime-startTime))

        then: "arrays should be sorted"
        // a array goes [0,9]
        for (int i = 0; i < a.length; i++) {
            a[i] == i
        }
        // big array must be in order
        for (int i = 1; i < big.length; i++) {
            big[i-1] <= big[i]
        }
    }

    // ---

    def "test merge sort"() {
        when: "merge sort performed"
        println("\nMERGE SORT")
        IntegerSorting.mergeSort(a)
        long startTime = System.currentTimeMillis()
        IntegerSorting.mergeSort(big)
        long endTime = System.currentTimeMillis()

        println("Sorted array: \n\t" + a)
        println("Sorted random array: \n\t" + big)
        println("Elapsed time to sort big array: " + (endTime-startTime))

        then: "arrays should be sorted"
        // a array goes [0,9]
        for (int i = 0; i < a.length; i++) {
            a[i] == i
        }
        // big array must be in order
        for (int i = 1; i < big.length; i++) {
            big[i-1] <= big[i]
        }
    }

    // -

    def "test merge sort CS3 version"() {
        when: "merge sort performed"
        println("\nMERGE SORT CS3")
        IntegerSorting.mergeSort_CS3(a)
        long startTime = System.currentTimeMillis()
        IntegerSorting.mergeSort_CS3(big)
        long endTime = System.currentTimeMillis()

        println("Sorted array: \n\t" + a)
        println("Sorted random array: \n\t" + big)
        println("Elapsed time to sort big array: " + (endTime-startTime))

        then: "arrays should be sorted"
        // a array goes [0,9]
        for (int i = 0; i < a.length; i++) {
            a[i] == i
        }
        // big array must be in order
        for (int i = 1; i < big.length; i++) {
            big[i-1] <= big[i]
        }
    }

    // ---

    def "test quick sort"() {
        when: "quick sort performed"
        println("\nQUICK SORT")
        IntegerSorting.quickSort(a)
        long startTime = System.currentTimeMillis()
        IntegerSorting.quickSort(big)
        long endTime = System.currentTimeMillis()

        println("Sorted array: \n\t" + a)
        println("Sorted random array: \n\t" + big)
        println("Elapsed time to sort big array: " + (endTime-startTime))

        then: "arrays should be sorted"
        // a array goes [0,9]
        for (int i = 0; i < a.length; i++) {
            a[i] == i
        }
        // big array must be in order
        for (int i = 1; i < big.length; i++) {
            big[i-1] <= big[i]
        }
    }


    // ---

    def "test generic sorting (mergesort)"() {
        given: "a list of EdgeNodes to be sorted"
        println("\nGENERIC MERGESORT")
        EdgeNode[] edgeNodes = [ new EdgeNode(6,6), new EdgeNode(2,2), new EdgeNode(4,4), new EdgeNode(7,7),
                             new EdgeNode(0,0), new EdgeNode(3,3), new EdgeNode(1,1), new EdgeNode(5,5), ]
        Edge[] edges = [ new Edge(0,6,6), new Edge(0,2,2), new Edge(0,4,4), new Edge(0,7,7),
                         new Edge(0,0,0), new Edge(0,3,3), new Edge(0,1,1), new Edge(0,5,5), ]


        println("BEFORE SORT (nodes) : " + edgeNodes)
        println("BEFORE SORT (edges) : " + edges)

        when: "generic mergesort called on the unsorted edges"
        EdgeNode[] sortedEdgeNodes = (new Sorting()).mergeSort(edgeNodes)
        Edge[] sortedEdges = (new Sorting()).mergeSort(edges)


        println("AFTER SORT (nodes) : " + sortedEdgeNodes)
        println("AFTER SORT (edges) : " + sortedEdges)


        then: "edges will be sorted"
        for (int i = 0; i < sortedEdgeNodes.length; i++) {
            sortedEdgeNodes[i].weight == i
            sortedEdges[i].weight == i
        }

    }
}
