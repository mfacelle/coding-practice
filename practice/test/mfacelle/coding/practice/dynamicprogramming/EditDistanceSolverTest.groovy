package mfacelle.coding.practice.dynamicprogramming

import spock.lang.Specification


class EditDistanceSolverTest extends Specification {

    EditDistanceSolver solver

    // ---

    def setup() {
        solver = new EditDistanceSolver() {
            @Override
            int costOfInsert(char c) { return 1 }

            @Override
            int costOfDelete(char c) { return 1 }

            @Override
            int costOfReplace(char c, char r) {
                if (c == r)
                    return 0;
                else
                    return 1;
            }
        }
    }

    // ---

    def "test edit distance solver"() {
        when: "edit distance solver is run for various string combinations"
        int distanceMonkeyMoney = solver.minEditDistance("monkey", "money")
        int distanceYouThou = solver.minEditDistance("you should not", "thou shall not")

        then: "results should be as expected (done by hand or by others)"
        distanceMonkeyMoney == 1
        distanceYouThou == 5
    }
}
