package advent_of_code_2022.problem_14.part2;

import java.util.HashSet;

import advent_of_code_2022.common.Coordinate2D;
import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.problem_14.common.ParseInputIntoRocks;

public class SimulateBlockingSandSource {
    public static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_14/input.txt";
    public static final Coordinate2D SAND_RESIVOIR = new Coordinate2D(500, 0);
    private static final Coordinate2D INVALID_COORDINATE = new Coordinate2D(-1, -1);
    private static final Coordinate2D[] FALLING_DIRECTION = new Coordinate2D[]{
        new Coordinate2D(0, 1),
        new Coordinate2D(-1, 1),
        new Coordinate2D(1, 1)
    };
    
    private static HashSet<Coordinate2D> blockedSpaces;
    private static int lowestElevation;

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println(countRestingSand(input) + " pieces of sand fall to create a pyramid.");
    }

    public static int countRestingSand(String[] input) {
        blockedSpaces = ParseInputIntoRocks.getRocks(input);
        lowestElevation = findLowestBlockedPoint();
        
        int totalRocks = blockedSpaces.size();
        simulateSand();
        int sandAndRocks = blockedSpaces.size();

        return sandAndRocks - totalRocks;
    }

    private static int findLowestBlockedPoint() {
        int lowest = 0;
        for (Coordinate2D coordinate : blockedSpaces) {
            // Note that a low coordinate has a high y value
            lowest = Math.max(lowest, coordinate.getY());
        }
        // The ground floor is 2 below the lowest rock
        return lowest + 2;
    }
    
    private static void simulateSand() {
        Coordinate2D nextSandLocation = findNextRestingSpot();
        while (!nextSandLocation.equals(INVALID_COORDINATE) && !nextSandLocation.equals(SAND_RESIVOIR)) {
            blockedSpaces.add(nextSandLocation);
            nextSandLocation = findNextRestingSpot();
        }

        if (nextSandLocation.equals(SAND_RESIVOIR)) {
            blockedSpaces.add(nextSandLocation);
        }
    }
    
    private static Coordinate2D findNextRestingSpot() {
        Coordinate2D sandParticle = SAND_RESIVOIR;
        
        // Note that a low coordinate has a high y value
        while (sandParticle.getY() < lowestElevation) {
            Coordinate2D next = findNextFallingFrame(sandParticle, lowestElevation);
            if (next.equals(sandParticle)) {
                // Stayed in place
                return sandParticle;
            }
            sandParticle = next;
        }

        return INVALID_COORDINATE;
    }

    private static Coordinate2D findNextFallingFrame(Coordinate2D sandParticle, int lowestElevation) {
        for (Coordinate2D direction : FALLING_DIRECTION) {
            if (sandParticle.getY() >= lowestElevation - 1) {
                return sandParticle;
            }
            if (!blockedSpaces.contains(sandParticle.add(direction))) {
                return sandParticle.add(direction);
            }
        }
        return sandParticle;
    }
}