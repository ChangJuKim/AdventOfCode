package advent_of_code_2022.problem_02.part1;

import advent_of_code_2022.common.ParseInput;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class StrategyGuide {
    static final String INPUT_PATH = "advent_of_code_2022/problem_02/input.txt";
    static final Set<Character> opponentChoices = Set.of('A', 'B', 'C');
    static final Map<Character, Integer> myChoices = Map.of('X', 1, 'Y', 2, 'Z', 3);


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
            char myAction = line.charAt(2);
            totalScore += calculateScore(opponentAction, myAction);

        }
        return totalScore;
    }

    // Check if line is in the format of "p q" where p = {A, B, C} and q = {X, Y, Z}
    private static boolean validateInputLine(String line) {
        return
            line.length() == 3 &&
            opponentChoices.contains(line.charAt(0)) &&
            line.charAt(1) == ' ' &&
            myChoices.containsKey(line.charAt(2));
    }

    private static int calculateScore(char opponentAction, char myAction) {
        int totalScore = myChoices.get(myAction);
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
