package mfacelle.coding.practice.dynamicprogramming;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *  A class to solve the 0/1 knapsack problem.
 *  Uses int as a datatype instead of float - easier.
 *
 *  Because items must be sorted, min time is O(n logn)
 *  Actual algorithm runs on O(nm), n=number of items; m=knapsack size
 *  so total runtime is O(nlogn + nm)
 */
public class KnapsackProblemSolver {

    private KnapsackItem[] items;
    private int numItems;
    private int knapsackSize;

    List<KnapsackItem> knapsackItems;

    // ---


    /** items must be sorted by weight already */
    public KnapsackProblemSolver(KnapsackItem[] mItems, int size) {
        items = mItems;
        numItems = mItems.length;
        knapsackSize = size;
    }

    // -

    /** items must be sorted by weight already */
    public KnapsackProblemSolver(int[] weights, int[] values, int size) {
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Length of weight and value arrays is not equal");
        }

        items = new KnapsackItem[weights.length];
        numItems = weights.length;
        knapsackSize = size;

        for (int i = 0; i < weights.length; i++) {
            items[i] = new KnapsackItem(weights[i], values[i]);
        }
    }

    // ---

    /**  */
    public int solve() {
        // use knapsackSize+1 because 0 is included
        int[][] solution = new int[numItems][knapsackSize+1];
        boolean[][] keptItems = new boolean[numItems][knapsackSize+1];


        for (int i = 0; i < numItems; i++) {
            for (int j = 0; j <= knapsackSize; j++) {
                KnapsackItem item = items[i];
                // if item is too heavy for this weight, use previous row (provided it's not out of bounds)
                if (item.weight > j) {
                    solution[i][j] = i > 0 ? solution[i-1][j] : 0;
                    keptItems[i][j] = false;
                }
                else {  // choose max of this item plus remaining weight, or best value so far
                    if (i != 0) {
                        int currentValue = item.value + solution[i-1][j-item.weight];
                        if (currentValue >= solution[i-1][j]) {
                            solution[i][j] = currentValue;
                            keptItems[i][j] = true;
                        }
                        else {
                            solution[i][j] = solution[i-1][j];
                            keptItems[i][j] = false;
                        }
                    }
                    else { // first row - just use item's value, can't check previous items
                        solution[i][j] = item.value;
                    }
                }

            }
        }

        // set knapsackItems list to be retrieved later
        knapsackItems = new ArrayList<>();
        int weight = knapsackSize;
        int itemsLeft = numItems-1; // -1 because its an array index
        while (itemsLeft >= 0) {
            if (keptItems[itemsLeft][weight] == true) {
                knapsackItems.add(items[itemsLeft]);
                itemsLeft--;
                weight--;
            }
            else {
                itemsLeft--;
            }
        }

        return solution[numItems-1][knapsackSize];
    }

    // ---

    public List<KnapsackItem> getKnapsackItems() { return knapsackItems; }

    // =====

    /** item to simplify storing weight and value */
    public class KnapsackItem {
        protected final int weight;
        protected final int value;

        public KnapsackItem(int w, int v) {
            weight = w;
            value = v;
        }
    }
}
