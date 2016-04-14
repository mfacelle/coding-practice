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
        mergeSort(a, 0, a.length);
        return a;
    }

    /** recursive helper for merge sort (recurses down)
     *  performs merge sort on [start,end-1]
     */
    private static void mergeSort(int[] a, int start, int n) {
        if (n > 1) {
            int n1 = n / 2;
            int n2 = n - n/2;
            // recursively divide in half
            mergeSort(a, start, n1);
            mergeSort(a, start+n1, n2);
            // merge data
            merge(a, start, n1, n2);
        }
    }

    /** merges two partitions */
    private static void merge(int[] a, int start, int n1, int n2) {
        // create temp array
        int[] temp = new int[n1+n2];
        int i  = 0;
        int copiedL = 0;
        int copiedR = 0;

        // merge by copying from left or right side of middle (n2)
        while ((copiedL < n1) && (copiedR < n2))
        {
            // count comparisons here
            if (a[start+copiedL] < a[start+n1+copiedR]) {
                temp[i++] = a[start + (copiedL++)];
            }
            else {
                temp[i++] = a[start + n1 + (copiedR++)];
            }
        }

        // flush out any remaining data
        while (copiedL < n1) {
            temp[i++] = a[start + (copiedL++)];
        }
        while (copiedR < n2) {
            temp[i++] = a[start + n1 + (copiedR++)];
        }

        // copy back to main array
        for (int i = 0; i < n1+n2; i++)
            a[start + i] = temp[i];
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
