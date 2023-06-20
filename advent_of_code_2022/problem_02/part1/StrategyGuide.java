package advent_of_code_2022.problem_02.part1;

import advent_of_code_2022.common.ParseInput;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class StrategyGuide {
    static final String INPUT_PATH = "advent_of_code_2022/problem_02/input.txt";
    static final Set<Character> OPPONENT_CHOICES = Set.of('A', 'B', 'C');
    static final Map<Character, Integer> MY_CHOICES = Map.of('X', 1, 'Y', 2, 'Z', 3);


    public static void main(String[] args) {
        String[] inputArray = ParseInput.parseInputAsList(new File(INPUT_PATH));
        int score = 0;
        try {
            score = findTotalScore(inputArray);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        System.out.println("Expected total score: " + score);
    }

    /**
     * Finds the total score of several games
     * 
     * @param input a list of rock-paper-scissors games
     * @return the total score of the games
     */
    public static int findTotalScore(String[] input) {
        int totalScore = 0;
        for (int i = 0; i < input.length; i++) {
            String line = input[i];
            if (!validateInputLine(line)) {
                throw new IllegalArgumentException("Invalid format in line #" + i + ": " + line);
            }
            char opponentAction = line.charAt(0);
            char myAction = line.charAt(2);
            totalScore += calculateScore(opponentAction, myAction);

        }
        return totalScore;
    }

    /**
     * Validates a single game
     * 
     * @param line a single rock-paper-scissors game
     * @return true if the line is in a "p q" format, where p = {A, B, C} and q = {X, Y, Z}
     */
    private static boolean validateInputLine(String line) {
        return
            line.length() == 3 &&
            OPPONENT_CHOICES.contains(line.charAt(0)) &&
            line.charAt(1) == ' ' &&
            MY_CHOICES.containsKey(line.charAt(2));
    }

    /**
     * Calculates the expected score of a single game of rock-paper-scissors.
     * The score is determined by 6 points for a win, and 3 points for a draw.
     * In addition, the player gets 1 point for rock, 2 points for paper and 3 points for scissors.
     * 
     * @param opponentAction the opponent's action ('A': rock, 'B': paper, or 'C': scissors)
     * @param myAction the player's action ('X': rock, 'Y': paper, 'Z': scissors)
     * @return the calculated score based on the actions
     */
    private static int calculateScore(char opponentAction, char myAction) {
        int totalScore = MY_CHOICES.get(myAction);
        // My win
        if (opponentAction == 'A' && myAction == 'Y' ||
            opponentAction == 'B' && myAction == 'Z' || 
            opponentAction == 'C' && myAction == 'X') {
            totalScore += 6;
        }
        // Our draw
        else if (opponentAction == 'A' && myAction == 'X' ||
            opponentAction == 'B' && myAction == 'Y' || 
            opponentAction == 'C' && myAction == 'Z') {
            totalScore += 3;
        }
        return totalScore;
    }
}
