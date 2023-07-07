package advent_of_code_2022.test.problem_09;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import advent_of_code_2022.problem_09.part2.SimulateLongerRope;

public class TestSimulateLongerRope {

    @Test
    public void test() {
        assertEquals(2, 1 + 1);;
    }

    @Test
    public void shouldMoveInOneDirection() {
        String[] rightOnlyInput = new String[] {"R 10", "R 2", "R 1", "R 0", "R 5"};
        String[] upOnlyInput = new String[] {"U 10", "U 2", "U 1", "U 0", "U 5"};
        String[] leftOnlyInput = new String[] {"L 10", "L 2", "L 1", "L 0", "L 5"};
        String[] downOnlyInput = new String[] {"D 10", "D 2", "D 1", "D 0", "D 5"};
        
        assertEquals(10, SimulateLongerRope.countTailVisitedCoordinates(rightOnlyInput));
        assertEquals(10, SimulateLongerRope.countTailVisitedCoordinates(upOnlyInput));
        assertEquals(10, SimulateLongerRope.countTailVisitedCoordinates(leftOnlyInput));
        assertEquals(10, SimulateLongerRope.countTailVisitedCoordinates(downOnlyInput));
    }

    @Test
    public void shouldMoveInDiagonals() {
        String[] diagonalUpRight3 = new String[] {
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
        };
        assertEquals(1, SimulateLongerRope.countTailVisitedCoordinates(diagonalUpRight3));

        String[] diagonalUpRight9 = new String[] {
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1", // 9 of these
        };

        assertEquals(1, SimulateLongerRope.countTailVisitedCoordinates(diagonalUpRight9));

        String[] diagonalUpRight15 = new String[] {
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1",
            "R 1", "U 1", // 15 of these
        };

        assertEquals(7, SimulateLongerRope.countTailVisitedCoordinates(diagonalUpRight15));
    }
}
