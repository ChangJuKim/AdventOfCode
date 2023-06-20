package advent_of_code_2022.problem_05.part1;

import java.util.ArrayList;

import advent_of_code_2022.common.ParseInput;

public class StackCrates {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_05/input.txt";

    public static void main(String[] args) {
        String[] input = ParseInput.parseInput(INPUT_FILE_NAME);
        System.out.println("The crates at the top of each stack are " + findTopCrates(input));
    }

    public static String findTopCrates(String[] input) {
        ArrayList<ArrayList<String>> crates = getInitialCrateState(input);
        ArrayList<Integer[]> instructions = getInstructions(input);

        return "";
    }

    private static ArrayList<ArrayList<String>> getInitialCrateState(String[] input) {
        ArrayList<ArrayList<String>> crates = new ArrayList<ArrayList<String>>();
        return crates;
    }

    private static ArrayList<Integer[]> getInstructions(String[] input) {
        ArrayList<Integer[]> instructions = new ArrayList<Integer[]>();
        return instructions;
    }
}
