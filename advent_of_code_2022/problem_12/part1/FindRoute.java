package advent_of_code_2022.problem_12.part1;

import java.util.Arrays;

import advent_of_code_2022.common.Coordinate2D;
import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.common.PrintArray;

public class FindRoute {
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_12/input.txt";
    static final char START_LOCATION = 'S';
    static final char END_LOCATION = 'E';
    static final Coordinate2D[] directions = new Coordinate2D[]{ new Coordinate2D(0, 1),
                                                                 new Coordinate2D(1, 0),
                                                                 new Coordinate2D(0, -1), 
                                                                 new Coordinate2D(-1, 0) };

    public static void main(String[] args) {
        char[][] input = ParseInput.parseInputAsCharMatrix(INPUT_FILE_NAME);
        System.out.println("The total number of steps required to go up the mountain is " + countStepsToDestination(input));
    }

    /**
     * Finds the total number of steps to the destination given a height map, and starting and ending characters.
     * The height map is represented as a 2D char array, where 'a' is the lowest, 'b' is the next and so on until 'z'.
     * 
     * @param matrix The 2D char array, the height map.
     * @return The number of steps to reach the destination (indicated by a constant)
     */
    public static int countStepsToDestination(char[][] matrix) {
        Coordinate2D start = findPosition(matrix, START_LOCATION);
        Coordinate2D end = findPosition(matrix, END_LOCATION);
        int[][] distanceMatrix = createDistanceMatrix(matrix);
        
        traverse(matrix, distanceMatrix, start, 0);

        return distanceMatrix[end.getX()][end.getY()];
    }

    /**
     * Recursive function that takes steps in any of the 4 directions that are valid. Populates the distance matrix with the current step count.
     * Current is assumed to be a valid location on the matrix, as we are checking for validity before stepping.
     * The step count may not be the shortest path to the current location. If it is not and we found shorter already, the function ends.
     * 
     * @param charMatrix 2D char array that represents the heightmap.
     * @param distanceMatrix 2D int array that is to be populated. Represents fewest steps to get to each location from the start.
     * @param current The current location of the matrix.
     * @param stepCount How many steps it took to get to the current location.
     */
    private static void traverse(char[][] charMatrix, int[][] distanceMatrix, Coordinate2D current, int stepCount) {
        int distancePreviouslyTravelled = distanceMatrix[current.getX()][current.getY()];
        // We previously came here, and faster
        if (distancePreviouslyTravelled != -1 && distancePreviouslyTravelled <= stepCount) return;
        distanceMatrix[current.getX()][current.getY()] = stepCount;

        char currentElevation = charMatrix[current.getX()][current.getY()];
        if (currentElevation == END_LOCATION) return;

        for (Coordinate2D direction : directions) {
            Coordinate2D next = current.add(direction);
            if (isValidStep(charMatrix, currentElevation, next)) {
                traverse(charMatrix, distanceMatrix, next, stepCount + 1);
            }
        }
    }

    /**
     * Finds the first instance of the desired character, and returns its coordinates
     * 
     * @param matrix The 2D matrix to search
     * @param desiredCharacter The character we are looking for
     * @return The coordinate of the first character in the matrix we find that matches the desired
     */
    private static Coordinate2D findPosition(char[][] matrix, char desiredCharacter) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == desiredCharacter) {
                    return new Coordinate2D(i, j);
                }
            }
        }
        throw new IllegalArgumentException("Matrix does not have the character: " + desiredCharacter);
    }

    /**
     * Returns a new 2D int array of the same dimensions as matrix, filled with -1. 
     * 
     * @param matrix The heightmap/grid. Gives the dimensions of the resulting 2D int array.
     * @return A new 2D int array with dimensions equal to matrix, and filled with -1.
     */
    private static int[][] createDistanceMatrix(char[][] matrix) {
        int[][] newMatrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            int[] row = new int[matrix[i].length];
            Arrays.fill(row, -1);
            newMatrix[i] = row;
        }
        return newMatrix;
    }

    /**
     * Determines if the next coordinate is a valid place to go to.
     * This means it is not off the grid, and the elevation is at most one higher than the current elevation.
     * 
     * @param matrix 2D array of characters, the heightmap grid.
     * @param current The current elevation represented by a lowercase letter (or constant).
     * @param next The coordinate of the next desired location.
     * @return True if next is not off the grid and is at most one higher than current elevation. False otherwise.
     */
    private static boolean isValidStep(char[][] matrix, char current, Coordinate2D next) {
        int x = next.getX();
        int y = next.getY();
        return x >= 0 && y >= 0 && x < matrix.length && y < matrix[x].length && isAdjacentOrLower(current, matrix[x][y]);
    }

    /**
     * Determines if the current character is at most one greater than the next character. This means next can be much lower than current.
     * The starting character is elevation 'a' and the ending character is elevation 'z'.
     * 
     * @param current A lowercase letter (or a constant) representing the elevation/character you are currently on.
     * @param next A lowercase letter (or a constant) representing the elevation/character you are trying to go to.
     * @return True if next is at most one higher than current. False otherwise.
     */
    private static boolean isAdjacentOrLower(char current, char next) {
        if (current == START_LOCATION) current = 'a';
        if (next == END_LOCATION) next = 'z';
        return (current >= 'a' && current <= 'z' && next >= 'a' && next <= 'z' && current + 1 >= next);
    }
}
