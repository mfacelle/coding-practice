package mfacelle.coding.practice.dynamicprogramming

import spock.lang.Specification


class TestKnapsackProblemSolver extends Specification {

    private KnapsackProblemSolver problemSolver;

    // ---

    def setup() {

    }

    // ---

    def "test knapsack problem solver"() {
        given: "a valid knapsack configuration - solved by hand"
        int[] weights = [ 1, 3, 4, 5 ];
        int[] values = [ 1, 4, 5, 7 ];
        int knapsackSize = 7;
        problemSolver = new KnapsackProblemSolver(weights, values, knapsackSize);

        when: "knapsack problem solver is run"
        int knapsackSolution = problemSolver.solve();
        List<KnapsackProblemSolver.KnapsackItem> items = problemSolver.getKnapsackItems();

        then: "result should be as expected (done by hand)"
        knapsackSolution == 9;
        items[0].weight == 4;
        items[0].value == 5;
        items[1].weight == 3;
        items[1].value == 4;
    }

    // -

    def "test knapsack problem solver with no solution"() {
        given: "a valid knapsack configuration - solved by hand"
        int[] weights = [ 90, 91, 92, 93 ];
        int[] values = [ 1, 4, 5, 7 ];
        int knapsackSize = 7;
        problemSolver = new KnapsackProblemSolver(weights, values, knapsackSize);

        when: "knapsack problem solver is run"
        int knapsackSolution = problemSolver.solve();

        then: "result should be as expected (done by hand)"
        knapsackSolution == 0;
    }
}
