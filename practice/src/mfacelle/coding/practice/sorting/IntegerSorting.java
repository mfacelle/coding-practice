package mfacelle.coding.practice.sorting;

/**
 * Class used for writing several different sorting algorithms
 * For simplicity, it assumes every sort-able object is an int[]
 * Could easily make general by using some datatype that implements Comparabale and using compareTo()
 * But that's just extra complication - this is to practice the algorithms themselves
 */
public class IntegerSorting {

    // ---
    // SELECTION SORT

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
    // INSERTION SORT

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
    // MERGE SORT

    /** performs merge sort, time O(nlogn), space O(n) */
    public static int[] mergeSort(int[] a) {
        mergeSort(a, 0, a.length);
        return a;
    }

    /** recursive helper for merge sort (recurses down)
     *  performs merge sort on [start,start+n-1]
     */
    private static void mergeSort(int[] a, int start, int end) {
        if (end - start > 1) {
            int mid = (start + end) / 2;
            // recursively divide in half
            mergeSort(a, start, mid);
            mergeSort(a, mid, end);
            // merge data
            merge(a, start, mid, end);
        }
    }

    /** merges two partitions */
    private static void merge(int[] a, int start, int mid, int end) {
        // create temp array
        int[] temp = new int[end-start];
        int i  = 0;
        int copiedL = start;
        int copiedR = mid;

        // merge by copying from left or right side of mid point
        while ((copiedL < mid) && (copiedR < end))
        {
            // if left side is less
            if (a[copiedL] < a[copiedR]) {
                temp[i++] = a[copiedL++];   // copy from left
            }
            else {
                temp[i++] = a[copiedR++];   // copy from right
            }
        }

        // flush out any remaining data - only one of these will be true at this point
        while (copiedL < mid) {
            temp[i++] = a[copiedL++];
        }
        while (copiedR < end) {
            temp[i++] = a[copiedR++];
        }

        // copy back to main array
        for (i = 0; i < temp.length; i++)
            a[start+i] = temp[i];
    }

    // ---
    // CS3 MERGE SORT

    /** performs merge sort (from CS3), time O(nlogn), space O(n) */
    public static int[] mergeSort_CS3(int[] a) {
        mergeSort_CS3(a, 0, a.length);
        return a;
    }

    /** recursive helper for merge sort (recurses down)
     *  performs merge sort on [start,start+n-1]
     */
    private static void mergeSort_CS3(int[] a, int start, int n) {
        if (n > 1) {
            int n1 = n / 2;
            int n2 = n - n/2;
            // recursively divide in half
            mergeSort_CS3(a, start, n1);
            mergeSort_CS3(a, start+n1, n2);
            // merge data
            merge_CS3(a, start, n1, n2);
        }
    }

    /** merges two partitions */
    private static void merge_CS3(int[] a, int start, int n1, int n2) {
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
        for (i = 0; i < n1+n2; i++)
            a[start + i] = temp[i];
    }

    // ---
    // QUICK SORT

    /** quicksort algorithm - time O(nlogn) worst case: O(n^2), space O(1) */
    public static int[] quickSort(int[] a) {
        quickSort(a, 0, a.length);
        return a;
    }

    private static void quickSort(int[] a, int start, int end) {
        if (end - start > 1) {
            int pivot = partition(a, start, end);

            quickSort(a, start, pivot);
            quickSort(a, pivot+1, end);
        }
    }

    /** partitions the data and returns the pivot index */
    private static int partition(int[] a, int start, int end) {
        int left = start-1; // because of pre-increment
        int right = end-1;  // starts at pivot because of pre-decrement

        int pivotIndex = end-1;
        // use last piece of data as the pivot
        int pivot = a[pivotIndex];

        while (left < right) {
            // pre-inc/decrecement because of check later on

            // move left index to the right until the data > pivot
            while (a[++left] < pivot);

            // move right index to the left until data < pivot
            while (a[--right] >= pivot);

            // if pointers have crossed, then exit the loop
            //  means everything on each side is sorted
            if (left >= right) {
                break;
            }
            else {
                // not yet crossed: swap these elements, because one is on the wrong side
                swap(a, left, right);
            }
        }

        // left pointer is now at first element greater than the pivot
        //  swap pivot and left pointer
        swap(a, pivotIndex, left);

        return pivotIndex;
    }

//    /** determines the index of the median value
//     *  requires at most 2-3 comparisons
//     */
//    private static int medianIndex(int[] a, int i1, int i2, int i3) {
//        if (a[i2] < a[i1]) {
//            if (a[i1] < a[i3]) {
//                return i1;  // a[i2] < a[i1] < a[i3]
//            }
//            else if (a[i3] < a[i2]) {
//                return i2;  // a[i3] < a[i2] < a[i1]
//            }
//            else {
//                return i3;  // a[i2] < a[i3] < a[i1]
//            }
//        }
//        else {  // a[i1] < a[i2]
//            if (a[i3] < a[i1]) {
//                return i1;  // a[i3] < a[i1] < a[i2]
//            }
//            else if (a[i2] < a[i3]) {
//                return i2;  // a[i1] < a[i2] < a[i3]
//            }
//            else {
//                return i3;  // a[i1] < a[i3] < a[i2]
//            }
//        }
//    }

    // ---
    // UTIL

    /** swaps two elements in an array */
    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    // ---
    // DEBUG

    /** for debugging step-by-step array */
    public static void printArray(int[] a) {
        System.out.print("[");
        for (int i = 0; i < a.length; i++) {
            System.out.print(" " + a[i] + " " );
        }
        System.out.println("]");
    }


}
