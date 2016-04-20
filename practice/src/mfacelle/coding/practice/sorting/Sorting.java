package mfacelle.coding.practice.sorting;

/**
 * Generic sorting class (for use in other projects)
 */
public class Sorting <T extends Comparable<T>> {

    public Sorting() {

    }

    // ---
    // MERGE SORT

    /** performs merge sort, time O(nlogn), space O(n) */
    public T[] mergeSort(T[] a) {
        mergeSort(a, 0, a.length);
        return a;
    }

    /** recursive helper for merge sort (recurses down)
     *  performs merge sort on [start,start+n-1]
     */
    private void mergeSort(T[] a, int start, int end) {
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
    private void merge(T[] a, int start, int mid, int end) {
        // create temp array
        T[] temp = (T[]) new Comparable[end-start];
        int i  = 0;
        int copiedL = start;
        int copiedR = mid;

        // merge by copying from left or right side of mid point
        while ((copiedL < mid) && (copiedR < end))
        {
            // if left side is less
            if (a[copiedL].compareTo(a[copiedR]) > 0) {
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
}
