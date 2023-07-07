package advent_of_code_2022.problem_08.part1;

import advent_of_code_2022.common.ParseInput;

public class VisibleTrees {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_08/input.txt";
    static final Integer[][] DIRECTIONS = new Integer[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static void main(String[] args) {
        int[][] input = ParseInput.parseInputAsPositiveIntegerMatrix(INPUT_FILE_NAME);
        System.out.println("The number of visible trees from outside the grid is " + countVisibleTrees(input));
    }

    /**
     * Counts the number of visible trees in the forest. A tree is visible if all trees to the north, east, west, or south of it are 
     * shorter (a smaller integer value) than it. A tree only needs to be visible from one direction to be considered visible.
     * 
     * @param forest A 2d int rectangular matrix. 
     * @return The number of visible trees
     */
    public static int countVisibleTrees(int[][] forest) {
        boolean[][] isVisible = new boolean[forest.length][forest[0].length];
        // Check for visibility from the north, east, west, and south
        for (Integer[] direction : DIRECTIONS) {
            updateVisiblity(forest, isVisible, direction[0], direction[1]);
        }

        return countVisible(isVisible);
    }

    /**
     * Checks whether the forest is visible from a certain direction. Currently, directions are limited to (0, 1), (1, 0), (0, -1), (-1, 0).
     * 
     * @param forest The matrix of tree heights
     * @param isVisible Boolean matrix to be updated
     * @param deltaX A value of 0, 1, or -1. How much to move the row
     * @param deltaY A value of 0, 1, or -1. How much to move the col
     */
    private static void updateVisiblity(int[][] forest, boolean[][] isVisible, int deltaX, int deltaY) {
        // Traversing left to right or right to left
        if (deltaX == 0) {
            int startCol = deltaY == -1 ? isVisible[0].length - 1 : 0;
            int endCol = deltaY == -1 ? -1 : isVisible[0].length;

            for (int i = 0; i < isVisible.length; i++) {
                int maxHeight = -1;
                for (int j = startCol; j != endCol; j += deltaY) {
                    if (forest[i][j] > maxHeight) {
                        isVisible[i][j] = true;
                        maxHeight = forest[i][j];
                    }
                }
            }
        }
        
        // Traversing top down or down top
        else if (deltaY == 0) {
            int startRow = deltaX == -1 ? isVisible.length - 1 : 0;
            int endRow = deltaX == -1 ? -1 : isVisible.length;    

            for (int j = 0; j < isVisible[0].length; j++) {
                int maxHeight = -1;
                for (int i = startRow; i != endRow; i += deltaX) {
                    if (forest[i][j] > maxHeight) {
                        isVisible[i][j] = true;
                        maxHeight = forest[i][j];
                    }
                }
            }
        }
    }

    private static int countVisible(boolean[][] isVisible) {
        int numVisible = 0;
        for (int i = 0; i < isVisible.length; i++) {
            for (int j = 0; j < isVisible[i].length; j++) {
                if (isVisible[i][j]) numVisible++;
            }
        }
        return numVisible;
    }
}
