package advent_of_code_2022.problem_05.part1;

import java.util.ArrayList;
import java.util.LinkedList;

import advent_of_code_2022.common.ParseInput;

public class StackCrates {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_05/input.txt";
    // Hardcoded value -- can do a pass through the crates to find the string size of each crate
    static final Integer CRATE_SIZE = 3;

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The crates at the top of each stack are " + findTopCrates(input));
    }

    public static String findTopCrates(String[] input) {
        ArrayList<LinkedList<Character>> crates = getInitialCrateState(input);
        ArrayList<Integer[]> instructions = getInstructions(input);
        for (int i = 0; i < instructions.size(); i++) {
            crates = moveCrates(crates, instructions.get(i));
        }

        return getCrateTops(crates);
    }

    private static ArrayList<LinkedList<Character>> getInitialCrateState(String[] input) {
        ArrayList<LinkedList<Character>> crates = new ArrayList<LinkedList<Character>>();
        // For when we are on the first line and need to initialize crates
        for (String line : input) {
            // We have finished going through the crates and are now reaching instructions
            if (line.strip().equals("") || line.split(" ")[0].equals("move")) {
                break;
            }

            for (int j = 0; j < line.length(); j += CRATE_SIZE + 1) {
                int crateIndex = j / (CRATE_SIZE + 1);
                if (crates.size() <= crateIndex) {
                    crates.add(new LinkedList<Character>());
                }
                if (line.charAt(j) == '[' && line.charAt(j+2) == ']') {
                    char crateContent = line.charAt(j+1);
                    crates.get(crateIndex).add(crateContent);
                }
            }
        }
        return crates;
    }

    private static ArrayList<Integer[]> getInstructions(String[] input) {
        ArrayList<Integer[]> instructions = new ArrayList<Integer[]>();
        for (String line : input) {
            String[] lineList = line.split(" ");
            if (lineList[0].equals("move")) {
                Integer[] newInstructions = new Integer[]{Integer.parseInt(lineList[1]), Integer.parseInt(lineList[3]), Integer.parseInt(lineList[5])};
                instructions.add(newInstructions);
            }
        }
        return instructions;
    }

    private static String getCrateTops(ArrayList<LinkedList<Character>> crates) {
        String crateTops = "";
        for (int i = 0; i < crates.size(); i++) {
            crateTops += crates.get(i).get(0);
        }
        return crateTops;
    }

    private static ArrayList<LinkedList<Character>> moveCrates(ArrayList<LinkedList<Character>> crates, Integer[] instructions) {
        int numCrates = instructions[0];
        int fromIndex = instructions[1] - 1;
        int toIndex = instructions[2] - 1;
        for (int i = 0; i < numCrates; i++) {
            char selectedCrate = crates.get(fromIndex).removeFirst();
            crates.get(toIndex).addFirst(selectedCrate);
        }
        return crates;
    }
}
