package advent_of_code_2022.problem_03.part1;

import java.util.HashSet;

import advent_of_code_2022.common.ParseInput;

public class FindDuplicates {

    static final String FILE_NAME = "advent_of_code_2022/problem_03/input.txt";
    
    public static void main(String[] args) {
        String[] rucksacks = ParseInput.parseInputAsList(FILE_NAME);
        System.out.println("The sum of badge priorities is " + findDuplicates(rucksacks));
    }

    /**
     * Finds the duplicates of each rucksack's compartments. For simplicity, returns the sum of each duplicate's priority
     * instead of returning each one directly.
     * a through z has priorities 1 through 26
     * A through Z has priorities 27 through 52
     * 
     * @param rucksacks Each string in rucksacks represents a single rucksack, containing lower and upper case English letters.
     * Each rucksack is even length, and each half is a different compartment. There is a duplicate between each compartment.
     * @return an integer with the sum of the "priorities" of each duplicate
     */
    public static int findDuplicates(String[] rucksacks) {
        int total = 0;
        for (int i = 0; i < rucksacks.length; i++) {
            if (!validateInput(rucksacks[i])) {
                throw new IllegalArgumentException("Invalid input in line " + (i+1) + ": " + rucksacks[i]);
            }
            total += findDuplicateInRucksack(rucksacks[i]);
        }
        return total;
    }

    /**
     * Calculates the priority of the single duplicate in the rucksack.
     * 
     * @param rucksack A string of even length
     * @return the priority of the duplicate
     */
    public static int findDuplicateInRucksack(String rucksack) {
        HashSet<Character> set = new HashSet<Character>();
        int duplicateCount = 0;
        int duplicatePrioritySum = 0;
        for (int i = 0; i < rucksack.length() / 2; i++) {
            set.add(rucksack.charAt(i));
        }
        for (int i = rucksack.length() / 2; i < rucksack.length(); i++) {
            if (set.contains(rucksack.charAt(i))) {
                duplicatePrioritySum += priority(rucksack.charAt(i));
                duplicateCount += 1;
                set.remove(rucksack.charAt(i));
            }
        }
        if (duplicateCount != 1) {
            throw new IllegalArgumentException(duplicateCount + " duplicates found -- unequal to 1");
        }
        return duplicatePrioritySum;
    }

    /**
     * Validates the string. It must be even length and only consist of English upper and lower case letters.
     * 
     * @param rucksack String to be validated
     * @return true if valid, false otherwise
     */
    private static boolean validateInput(String rucksack) {
        String regex = "[a-zA-Z]";
        return rucksack.length() % 2 == 0 && rucksack.replaceAll(regex, "").equals("");
    }

    /**
     * Returns the priority of a given English letter
     * 
     * @param c an English letter
     * @return 1-26 for a-z, and 27-52 for A-Z
     */
    private static int priority(char c) {
        // Capital letters
        if (c >= 65 && c <= 90) return c - 38;
        // Lowercase letters
        if (c >= 97 && c <= 122) return c - 96;
        // Should never occur as we are validating input in previous function
        throw new IllegalArgumentException("Invalid input: " + c);
    }
}
