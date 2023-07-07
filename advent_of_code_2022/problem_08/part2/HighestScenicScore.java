package advent_of_code_2022.problem_08.part2;

import java.util.Stack;

import advent_of_code_2022.common.ParseInput;

public class HighestScenicScore {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_08/input.txt";
    static final Integer[][] DIRECTIONS = new Integer[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static void main(String[] args) {
        int[][] input = ParseInput.parseInputAsPositiveIntegerMatrix(INPUT_FILE_NAME);
        System.out.println("The highest scenic score is " + maxScenicScore(input));
    }

    /**
     * Finds the maximum scenic score of the trees in the forest. A tree's scenic score is the product of the number of trees in each
     * cardinal direction that it can see. A tree on the border has a scenic score of 0. 
     * A tree will stop its vision at the next tree of equal or higher height than it.
     * A tree that sees 2, 2, 1, 3 trees in the North, East, South, West respectively has a score of 2*2*1*3 = 12.
     * 
     * @param forest A 2d int rectangular matrix. 
     * @return The maximum score among the trees
     */
    public static int maxScenicScore(int[][] forest) {
        int[][] treeScores = new int[forest.length][forest[0].length];
        for (int i = 0; i < treeScores.length; i++) {
            for (int j = 0; j < treeScores[i].length; j++) {
                treeScores[i][j] = 1;
            }
        }

        // Update visibility from all 4 directions
        for (Integer[] direction : DIRECTIONS) {
            updateScore(forest, treeScores, direction[0], direction[1]);
        }

        int maxScore = 0;
        for (int i = 0; i < treeScores.length; i++) {
            for (int j = 0; j < treeScores[i].length; j++) {
                maxScore = Math.max(maxScore, treeScores[i][j]);
            }
        }
        return maxScore;
    }
    
    /**
     * Updates the scores of all trees when they look in a certain direction.
     * Currently, directions are limited to (0, 1), (1, 0), (0, -1), (-1, 0).
     * 
     * @param forest The matrix of tree heights
     * @param treeScores int matrix containing scores that is to be updated
     * @param deltaX A value of 0, 1, or -1. How much to move the row
     * @param deltaY A value of 0, 1, or -1. How much to move the col
     */
    private static void updateScore(int[][] forest, int[][] treeScores, int deltaX, int deltaY) {
        // Traversing left to right or right to left
        if (deltaX == 0) {
            int startCol = deltaY == -1 ? treeScores[0].length - 1 : 0;
            int endCol = deltaY == -1 ? -1 : treeScores[0].length;

            for (int i = 0; i < treeScores.length; i++) {
                // First is height of the tree, second is visibility of that tree
                Stack<Pair<Integer, Integer>> stack = new Stack<>();
                for (int j = startCol; j != endCol; j += deltaY) {
                    int height = forest[i][j];
                    // Border trees should see 0 visible trees. All others see at least 1 (the tree in front of them)
                    int numVisible = j == startCol ? 0 : 1;
                    while (!stack.empty() && stack.peek().getFirst() < height) {
                        numVisible += stack.pop().getSecond();
                    }
                    treeScores[i][j] *= numVisible;
                    stack.push(new Pair<Integer, Integer>(height, numVisible));
                }
            }
        }
        
        // Traversing top down or down top
        else if (deltaY == 0) {
            int startRow = deltaX == -1 ? treeScores.length - 1 : 0;
            int endRow = deltaX == -1 ? -1 : treeScores.length;
            
            for (int j = 0; j < treeScores.length; j++) {
                Stack<Pair<Integer, Integer>> stack = new Stack<>();
                for (int i = startRow; i != endRow ; i += deltaX) {
                    int height = forest[i][j];
                    // Border trees should see 0 visible trees. All others see at least 1 (the tree in front of them)
                    int numVisible = i == startRow ? 0 : 1;

                    while (!stack.empty() && stack.peek().getFirst() < height) {
                        numVisible += stack.pop().getSecond();
                    }
                    
                    treeScores[i][j] *= numVisible;
                    stack.push(new Pair<Integer, Integer>(height, numVisible));
                }
            }   
        }
    }
}
