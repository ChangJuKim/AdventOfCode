package advent_of_code_2022.problem_02.part2;

import advent_of_code_2022.common.ParseInput;
import java.util.Map;
import java.util.Set;

public class StrategyGuide {
    static final String INPUT_PATH = "advent_of_code_2022/problem_02/input.txt";
    static final Set<Character> OPPONENT_CHOICES = Set.of('A', 'B', 'C');
    static final Map<Character, Integer> OUTCOMES = Map.of('X', 0, 'Y', 3, 'Z', 6);


    public static void main(String[] args) {
        String[] inputArray = ParseInput.parseInputAsList(INPUT_PATH);
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
            char outcome = line.charAt(2);
            totalScore += calculateScore(opponentAction, outcome);

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
            OUTCOMES.containsKey(line.charAt(2));
    }

    /**
     * Calculates the expected score of a single game of rock-paper-scissors.
     * The score is determined by 6 points for a win, and 3 points for a draw.
     * In addition, the player gets 1 point for rock, 2 points for paper and 3 points for scissors.
     * 
     * @param opponentAction the opponent's action ('A': rock, 'B': paper, or 'C': scissors)
     * @param outcome the outcome of the game ('X': lose, 'Y': draw, 'Z': win)
     * @return the calculated score based on the actions
     */
    private static int calculateScore(char opponentAction, char outcome) {
        int totalScore = OUTCOMES.get(outcome);
        // I use rock
        if (opponentAction == 'A' && outcome == 'Y' ||
            opponentAction == 'B' && outcome == 'X' || 
            opponentAction == 'C' && outcome == 'Z') {
            totalScore += 1;
        }
        // I use paper
        else if (opponentAction == 'A' && outcome == 'Z' ||
            opponentAction == 'B' && outcome == 'Y' || 
            opponentAction == 'C' && outcome == 'X') {
            totalScore += 2;
        }
        // I use scissor
        else {
            totalScore += 3;
        }
        return totalScore;
    }
}
