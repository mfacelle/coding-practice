package mfacelle.coding.practice.sorting;

import java.util.ArrayList;
import java.util.List;

/**
 *  A simple min-heap class consisting of only integers.  The lowest value integer is stored at the top.
 *  For practice on heap data structure without worrying about generic stuff.
 *
 *  With heap array index starting at 0, children are located at: (2n+1), (2n+2).
 *
 *  Underlying data structure is a List, for ease of inserting elements (don't need to re-create array and copy all elements)
 *
 *  TODO - unfinished
 */
public class PriorityQueue {

    private static final int DEFAULT_SIZE = 10;

    private List<Integer> heap;
    private int size;

    // ---

    public PriorityQueue() {
        size = 0;
        heap = new ArrayList<Integer>();
    }

    // ---

    /** removes the smallest element from the heap */
    public int removeMin() {
        if (size == 0) {
            throw new IllegalStateException("heap is currently empty");
        }
        int minValue = heap.get(0);

        // overwrite root with last element in heap
        heap.set(0, heap.get(size-1));
        // remove last element since it has now been moved
        heap.remove(size-1);
        // decrement size, since an element has been removed
        size--;
        // bubble the element down until heap condition is satisfied
        bubbleDown(0);

        return minValue;
    }

    // ---

    /** recursively move the element at index i down, until heap property is satisfied */
    private void bubbleDown(int i) {
        int leftChildIndex = getLeftChildIndex(i);

        // if at the last leaf node, then stop bubble down procedure
        if (leftChildIndex >= size) {
            return;
        }

        // choose the smaller of the two children (if the right child can exist at all)
        int smallerChildIndex = i;
        // iterate over both children
        for (int j = 0; j <= 1; j++) {
            // if child exists
            if (leftChildIndex+j < size) {
                // set smallerChildIndex to swap only if smaller than current min element
                if (heap.get(leftChildIndex+j) < heap.get(smallerChildIndex)) {
                    smallerChildIndex = leftChildIndex+j;
                }
            }
        }

        // if heap condition not satisfied, swap elements and recursively bubble down further
        if (i != smallerChildIndex) {
            swap(i, smallerChildIndex);
            bubbleDown(smallerChildIndex);
        }
    }

    // ---

    /** return index of parent element at i */
    public static int getParent(int i) {
        if (i == 0) {
            return -1;
        }
        else {
            return (int) i/2;
        }
    }

    // ---



    // ---

    /** return index of left child for element at i */
    public static int getLeftChildIndex(int i) {
        return 2*i + 1;
    }

    /** return index of right child for element at i */
    public static int getRightChildIndex(int i) {
        return 2*i + 2;
    }

    // ---

    /** swaps elements i and j in the heap */
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // ---

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }


}
