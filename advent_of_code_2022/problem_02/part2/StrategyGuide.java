package advent_of_code_2022.problem_02.part2;

import advent_of_code_2022.common.ParseInput;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class StrategyGuide {
    static final String INPUT_PATH = "advent_of_code_2022/problem_02/input.txt";
    static final Set<Character> opponentChoices = Set.of('A', 'B', 'C');
    static final Map<Character, Integer> desiredOutcome = Map.of('X', 0, 'Y', 3, 'Z', 6);


    public static void main(String[] args) {
        String[] inputArray = ParseInput.parseInput(new File(INPUT_PATH));
        int score = findTotalScore(inputArray);
        System.out.println("Expected total score: " + score);
    }

    public static int findTotalScore(String[] input) {
        int totalScore = 0;
        for (int i = 0; i < input.length; i++) {
            String line = input[i];
            if (!validateInputLine(line)) {
                System.out.println("Error: invalid format in line #" + i + ": " + line);
                return -1;
            }
            char opponentAction = line.charAt(0);
            char outcome = line.charAt(2);
            totalScore += calculateScore(opponentAction, outcome);

        }
        return totalScore;
    }

    // Check if line is in the format of "p q" where p = {A, B, C} and q = {X, Y, Z}
    private static boolean validateInputLine(String line) {
        if (
            line.length() == 3 &&
            opponentChoices.contains(line.charAt(0)) &&
            line.charAt(1) == ' ' &&
            desiredOutcome.containsKey(line.charAt(2))) {
                return true;
            }
        return false;
    }

    private static int calculateScore(char opponentAction, char outcome) {
        int totalScore = desiredOutcome.get(outcome);
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
