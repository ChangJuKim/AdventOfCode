package advent_of_code_2022.problem_14.part1;

import java.util.HashSet;

import advent_of_code_2022.common.Coordinate2D;
import advent_of_code_2022.common.ParseInput;

public class SimulateSand {
    public static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_14/input.txt";
    public static final Coordinate2D SAND_RESIVOIR = new Coordinate2D(500, 0);
    private static final Coordinate2D[] FALLING_DIRECTION = new Coordinate2D[]{
        new Coordinate2D(0, 1),
        new Coordinate2D(-1, 1),
        new Coordinate2D(1, 1)
    };

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println(countRestingSand(input) + " pieces of sand come to a rest before falling to the void.");
    }

    /**
     * Counts the units of sand that come to rest when falling down a rock formation.
     * This is done by using a set to identify all of the locations of the rocks and finding the size.
     * Then the sand pieces are placed into the set (as once they're at rest, they no longer move).
     * The resulting pieces of sand at at rest is the difference of the two sizes.
     * 
     * @param input The locations of the rocks
     * @return The number of units of sand that come to a rest on top of the rocks.
     */
    public static int countRestingSand(String[] input) {
        HashSet<Coordinate2D> blocked = new HashSet<Coordinate2D>();
        // Parse through input to find all rocks
        for (String line : input) {
            addRocksToSet(blocked, line);
        }
        int totalRocks = blocked.size();
        // For some reason, a high y value means a low point in space -- axis is reversed
        int highestPoint = findHighestYValue(blocked);

        // Simulates sand trickling down the rock formation
        simulateSand(blocked, highestPoint);
        int sandAndRocks = blocked.size();
        return sandAndRocks - totalRocks;
    }

    /**
     * Takes in Coordinates separated by -> that represent horizontal or vertical lines. Adds all coordinates in those lines to the set
     * 
     * @param blocked The set to be updated. Each coordinate in there represents a blocked space.
     * @param line A string of Coordinates separated by ->
     */
    private static void addRocksToSet(HashSet<Coordinate2D> blocked, String line) {
        if (line.isEmpty()) return;
        String[] lineCoordinates = line.split("->");
        if (lineCoordinates.length == 0) return;
        
        // Parse through input to find all rocks
        Coordinate2D start = getCoordinate(lineCoordinates[0]);
        blocked.add(start);
        for (String coordinateDetails : lineCoordinates) {
            Coordinate2D next = getCoordinate(coordinateDetails);
            Coordinate2D difference = next.subtract(start);
            int deltaX = difference.getX() == 0 ? 0 : difference.getX() / Math.abs(difference.getX());
            int deltaY = difference.getY() == 0 ? 0 : difference.getY() /  Math.abs(difference.getY());

            for (int diffX = 0; diffX != difference.getX(); diffX += deltaX) {
                blocked.add(new Coordinate2D(start.getX() + diffX, start.getY()));
            }
            for (int diffY = 0; diffY != difference.getY(); diffY += deltaY) {
                blocked.add(new Coordinate2D(start.getX(), start.getY() + diffY));
            }
            blocked.add(next);
            start = next;
        }
    }

    /**
     * Simulates all of the sand pieces trickling down the rocks formation. Stops once a piece of sand has no resting spot.
     * 
     * @param blocked The locations of the rocks.
     * @param highestY An integer representing the lowest point of a rock. A sand below this point is considered to be falling forever.
     */
    private static void simulateSand(HashSet<Coordinate2D> blocked, int highestY) {
        int blockedSize = blocked.size();
        do {
            blockedSize = blocked.size();
            Coordinate2D nextSandLocation = findNextSandLocation(blocked, highestY);
            if (nextSandLocation != null) blocked.add(nextSandLocation);
        } while (blockedSize != blocked.size());
    }

    /**
     * Returns the resulting Coordinate of the resting spot for the next piece of sand.
     * Returns null if the sand is not caught by any rocks or sand, and falls to the void.
     * 
     * @param blocked A set containing all the locations of the rocks and sand. We assume these pieces cannot move.
     * @param highestYCoord An integer indicating the lowest point of a rock. A sand below this point is effectively falling into the void.
     * @return A Coordinate2D of the resting spot of the next sand piece. Returns null if it will fall forever.
     */
    private static Coordinate2D findNextSandLocation(HashSet<Coordinate2D> blocked, int highestYCoord) {
        Coordinate2D sandParticle = SAND_RESIVOIR;
        while (sandParticle.getY() <= highestYCoord) {
            Coordinate2D next = null;
            boolean foundNextSpot = false;
            // Check straight down, down left, and then down right for a valid location
            for (Coordinate2D direction : FALLING_DIRECTION) {
                if (!blocked.contains(sandParticle.add(direction))) {
                    next = sandParticle.add(direction);
                    foundNextSpot = true;
                    break;
                }
            }
            if (foundNextSpot) {
                sandParticle = next;
            }
            else return sandParticle;
        }
        // We past all the rocks, now there's just the void left
        return null;
    }

    // Loops through the set and returns the highest Y coordinate
    // Keep in mind that for some reason, a higher y coordinate means a lower point in space
    private static int findHighestYValue(HashSet<Coordinate2D> blocked) {
        int highest = 0;
        for (Coordinate2D coordinate : blocked) {
            highest = Math.max(highest, coordinate.getY());
        }
        return highest;
    }

    // Returns a coordinate from a ABC,DE format string
    private static Coordinate2D getCoordinate(String coordinateDetails) {
        coordinateDetails = coordinateDetails.trim();
        try {
            int commaIndex = coordinateDetails.indexOf(',');
            int x = Integer.parseInt(coordinateDetails.substring(0, commaIndex));
            int y = Integer.parseInt(coordinateDetails.substring(commaIndex+1));
            return new Coordinate2D(x, y);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Coordinate is not in int,int format: " + coordinateDetails);
        }
    }
}