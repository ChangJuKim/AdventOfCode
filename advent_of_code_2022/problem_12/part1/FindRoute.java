package advent_of_code_2022.problem_12.part1;

import java.util.Arrays;

import advent_of_code_2022.common.Coordinate2D;
import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.common.PrintArray;

public class FindRoute {
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_12/input.txt";
    static final char STARTING_CHARACTER = 'S';
    static final char ENDING_CHARACTER = 'E';
    static final Coordinate2D[] directions = new Coordinate2D[]{ new Coordinate2D(0, 1),
                                                                 new Coordinate2D(1, 0),
                                                                 new Coordinate2D(0, -1), 
                                                                 new Coordinate2D(-1, 0) };

    public static void main(String[] args) {
        char[][] input = ParseInput.parseInputAsCharMatrix(INPUT_FILE_NAME);
        System.out.println("The total number of steps required to go up the mountain is " + findStepsToRoute(input));
    }

    public static int findStepsToRoute(char[][] matrix) {
        Coordinate2D start = findPosition(matrix, STARTING_CHARACTER);
        Coordinate2D end = findPosition(matrix, ENDING_CHARACTER);
        int[][] distanceMatrix = createDistanceMatrix(matrix);
        
        traverse(matrix, distanceMatrix, start, 0);

        return distanceMatrix[end.getX()][end.getY()];
    }

    private static void traverse(char[][] charMatrix, int[][] distanceMatrix, Coordinate2D current, int stepCount) {
        int distancePreviouslyTravelled = distanceMatrix[current.getX()][current.getY()];
        // We previously came here, and faster
        if (distancePreviouslyTravelled != -1 && distancePreviouslyTravelled <= stepCount) return;
        distanceMatrix[current.getX()][current.getY()] = stepCount;

        char currentElevation = charMatrix[current.getX()][current.getY()];
        if (currentElevation == ENDING_CHARACTER) return;

        for (Coordinate2D direction : directions) {
            Coordinate2D next = current.add(direction);
            if (isValidStep(charMatrix, currentElevation, next)) {
                traverse(charMatrix, distanceMatrix, next, stepCount + 1);
            }
        }
    }

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

    private static int[][] createDistanceMatrix(char[][] matrix) {
        int[][] newMatrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            int[] row = new int[matrix[i].length];
            Arrays.fill(row, -1);
            newMatrix[i] = row;
        }
        return newMatrix;
    }

    private static boolean isValidStep(char[][] matrix, char current, Coordinate2D next) {
        int x = next.getX();
        int y = next.getY();
        return x >= 0 && y >= 0 && x < matrix.length && y < matrix[x].length && isAdjacentOrLower(current, matrix[x][y]);
    }

    private static boolean isAdjacentOrLower(char current, char next) {
        if (current == STARTING_CHARACTER) current = 'a';
        if (next == ENDING_CHARACTER) next = 'z';
        return (current >= 'a' && current <= 'z' && next >= 'a' && next <= 'z' && current + 1 >= next);
    }
}
