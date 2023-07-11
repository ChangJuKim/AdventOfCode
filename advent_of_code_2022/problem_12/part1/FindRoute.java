package advent_of_code_2022.problem_12.part1;

import java.util.Arrays;

import advent_of_code_2022.common.Coordinate2D;
import advent_of_code_2022.common.ParseInput;

public class FindRoute {
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_12/input.txt";
    static final char STARTING_CHARACTER = 'S';
    static final char ENDING_CHARACTER = 'E';

    public static void main(String[] args) {
        char[][] input = ParseInput.parseInputAsCharMatrix(INPUT_FILE_NAME);
        System.out.println("The total number of steps required to go up the mountain is " + findStepsToRoute(input));
    }

    public static int findStepsToRoute(char[][] matrix) {
        Coordinate2D start = findPosition(matrix, STARTING_CHARACTER);
        Coordinate2D end = findPosition(matrix, ENDING_CHARACTER);
        int[][] distanceMatrix = createDistanceMatrix(matrix);
        distanceMatrix[start.getX()][start.getY()] = 0;
        
        traverse(matrix, distanceMatrix, start);
        return distanceMatrix[end.getX()][end.getY()];
    }

    private static void traverse(char[][] charMatrix, int[][] distanceMatrix, Coordinate2D current) {
        char currentElevation = charMatrix[current.getX()][current.getY()];

        
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

    private static boolean isValidStep(char[][] matrix, int x, int y, char current) {
        return (x < 0 || y < 0 || x >= matrix.length || y >= matrix[x].length || Math.abs(matrix[x][y] - current) >= 1);
    }
}
