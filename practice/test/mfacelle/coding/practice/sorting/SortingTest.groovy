package mfacelle.coding.practice.sorting

import spock.lang.Specification


class SortingTest extends Specification {

    private int[] a;
    private int[] big;
    private int bigArraySize = 10000;

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
        IntegerSorting.selectionSort(a)
        long startTime = System.currentTimeMillis()
        IntegerSorting.selectionSort(big)
        long endTime = System.currentTimeMillis()

        println("")
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
        IntegerSorting.insertionSort(a)
        long startTime = System.currentTimeMillis()
        IntegerSorting.insertionSort(big)
        long endTime = System.currentTimeMillis()

        println("")
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
        IntegerSorting.mergeSort(a)
        long startTime = System.currentTimeMillis()
        IntegerSorting.mergeSort(big)
        long endTime = System.currentTimeMillis()

        println("")
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
}
