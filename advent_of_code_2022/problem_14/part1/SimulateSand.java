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

    public static int countRestingSand(String[] input) {
        HashSet<Coordinate2D> blocked = new HashSet<Coordinate2D>();
        for (String line : input) {
            addRocksToSet(blocked, line);
        }
        int totalRocks = blocked.size();
        // For some reason, a high y value means a low point in space -- axis is reversed
        int highestPoint = findHighestYValue(blocked);

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

    private static void simulateSand(HashSet<Coordinate2D> blocked, int highestY) {
        int blockedSize = blocked.size();
        do {
            Coordinate2D nextSandLocation = findNextSandLocation(blocked, highestY);
            System.out.println(nextSandLocation);
            if (nextSandLocation != null) blocked.add(nextSandLocation);
        } while (blockedSize != blocked.size());
    }

    private static Coordinate2D findNextSandLocation(HashSet<Coordinate2D> blocked, int highestYCoord) {
        Coordinate2D sandParticle = SAND_RESIVOIR;
        boolean foundNextSpot = false;
        while (sandParticle.getY() <= highestYCoord) {
            Coordinate2D next = null;
            for (Coordinate2D direction : FALLING_DIRECTION) {
                if (!blocked.contains(sandParticle.add(direction))) {
                    next = sandParticle.add(direction);
                    foundNextSpot = true;
                }
            }
            if (foundNextSpot) sandParticle = next;
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