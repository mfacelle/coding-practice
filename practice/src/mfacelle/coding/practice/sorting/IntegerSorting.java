package mfacelle.coding.practice.sorting;

/**
 * Class used for writing several different sorting algorithms
 * For simplicity, it assumes every sort-able object is an int[]
 * Could easily make general by using some datatype that implements Comparabale and using compareTo()
 * But that's just extra complication - this is to practice the algorithms themselves
 */
public class IntegerSorting {

    /** basic selection sort.  time O(n^2), space O(1)
     *  returns the sorted array (unnecessary, because array is modified during sort)
     */
    public static int[] selectionSort(int[] a) {
        int current;
        for (int i = 0; i < a.length; i++) {
            current = a[i];
            for (int j = i+1; j < a.length; j++) {
                if (a[j] < a[i]) {
                    swap(a, i, j);
                }
            }
        }
        return a;   // unnecessary, but felt like it should return the sorted array anyway
    }

    // ---

    /** basic insertion sort.  time O(n^2), space O(1)
     *
     */
    public static int[] insertionSort(int[] a) {
        int current;
        for (int i = 0; i < a.length; i++) {
            current = a[i];
            for (int j = 0; j < i; j++) {
                if (a[j] > a[i]) {
                    swap(a, i, j);
                }
            }
        }
        return a;
    }

    // ---

    /** performs merge sort, time O(nlogn), space O(n) */
    public static int[] mergeSort(int[] a) {
        System.out.println("merge sort");
        mergeSort(a, 0, a.length, a);
        return a;
    }

    /** recursive helper for merge sort (recurses down)
     *  performs merge sort on [start,end-1]
     */
    private static void mergeSort(int[] a, int start, int end, int[] b) {
        System.out.println("mergsort: " +start+","+end);

        if (end - start < 2) {
            System.out.println("single value i="+start + " : " + a[start]);
            return;
        }

        int mid = (start + end) / 2;    // find midpoint

        mergeSort(a, start, mid, b);   // recurse on left side
        mergeSort(a, mid, end, b);     // recurse on right side

        merge(a, start, mid, end, b);  // merge the two halves

        copyArray(b, start, end, a);
    }

    /** merges two partitions */
    private static void merge(int[] a, int start, int mid, int end, int[] b) {
        System.out.println("MERGE " + start+","+mid+","+end);
        int i = start;
        int j = mid;

        // While there are elements in the left or right runs...
        for (int k = start; k < end; k++) {
            // If left run head exists and is <= existing right run head.
            if (i < mid && (j >= end || a[i] <= a[j])) {
                System.out.println("LEFT copy " + a[i] + " to " + k);
                b[k] = a[i];
                i = i + 1;
            } else {
                System.out.println("RIGHT copy " + a[j] + " to " + k);
                b[k] = a[j];
                j = j + 1;
            }
            printArray(b);
        }
    }

    private static void copyArray(int[] b, int start, int end, int[] a) {
        for (int i = start; i < end; i++) {
            System.out.println("copying i=" + i +": "+b[i]+" to "+a[i]);
            a[i] = b[i];
        }
    }

    // ---

    /** swaps two elements in an array */
    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // ---

    /** simple queue implementation for mergesort's merge method
     *  for ease of understanding code.  makes use of a linked list
     */
    private static class Queue {
        private static class QueueNode {
            protected final int value;
            protected QueueNode next;
            protected QueueNode(int v) {
                value = v;
            }
        }
        // -
        protected int size;
        protected QueueNode head;
        // -
        protected Queue() {
            head = null;
            size = 0;
        }
        // -
        protected int peek() {
            if (head != null) {
                return head.value;
            }
            else {
                throw new RuntimeException("Queue is empty during an attempt to peek a value");
            }
        }
        // -
        protected int pop() {
            if (head != null) {
                size--;
                int headVal = head.value;
                head = head.next;
                return headVal;
            }
            else {
                throw new RuntimeException("Queue is empty during an attempt to pop a value");
            }
        }
        // -
        protected void push(int x) {
            size++;
            QueueNode newHead = new QueueNode(x);
            newHead.next = head;
            head = newHead;
        }
        // -
        protected boolean isEmpty() { return size == 0; }
    }

    // ---

    public static void printArray(int[] a) {
        System.out.print("[");
        for (int i = 0; i < a.length; i++) {
            System.out.print(" " + a[i] + " " );
        }
        System.out.println("]");
    }
}
