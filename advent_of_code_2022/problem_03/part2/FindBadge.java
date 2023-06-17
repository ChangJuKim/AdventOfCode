package advent_of_code_2022.problem_03.part2;

import java.util.HashSet;

import advent_of_code_2022.common.ParseInput;

public class FindBadge {
   
    static final String FILE_NAME = "advent_of_code_2022/problem_03/input.txt";
    
    public static void main(String[] args) {
        String[] rucksacks = ParseInput.parseInput(FILE_NAME);
        System.out.println("The sum of badge priorities is " + findBadge(rucksacks));
    }

    /**
     * Finds the badge of every 3 rucksacks -- a character is a badge iff all 3 rucksacks have that character. This is unique within 
     * a group of 3. For simplicity, returns the sum of each badge's priority instead of returning each one directly.
     * a through z has priorities 1 through 26
     * A through Z has priorities 27 through 52
     * 
     * @param rucksacks Each string in rucksacks represents a single rucksack, containing lower and upper case English letters.
     * The number of rucksacks are divisible by 3.
     * @return an integer with the sum of the priorities of each badge
     */
    public static int findBadge(String[] rucksacks) {
        int total = 0;
        if (rucksacks.length % 3 != 0) {
            throw new IllegalArgumentException("The " + rucksacks.length + " rucksacks cannot be divided into groups of 3.");
        }
        for (int i = 0; i < rucksacks.length; i += 3) {
            if (!validateInput(rucksacks[i])) {
                throw new IllegalArgumentException("Invalid characters in rucksack " + (i+1) + ": " + rucksacks[i]);
            }
            total += findBadgeInRucksacks(rucksacks[i], rucksacks[i+1], rucksacks[i+2]);
        }
        return total;
    }

    /**
     * Calculates the priority of the single badge in the 3 rucksacks.
     * Puts contents of rucksack1 in a hashset(1). Puts contents of rucksack2 that's also in rucksack1 in a new hashset(2).
     * Finds the duplicate by comparing rucksack 3's contents with hashset(2).
     * 
     * @param rucksack A string of lower or upper case English letters
     * @param rucksack2 Likewise for rucksack1
     * @param rucksack3 Likewise for rucksack1 and 2. All three rucksacks share exactly one character.
     * @return the priority of the shared character (the badge)
     */
    public static int findBadgeInRucksacks(String rucksack1, String rucksack2, String rucksack3) {
        HashSet<Character> set1 = new HashSet<>();
        for (int i = 0; i < rucksack1.length(); i++) {
            set1.add(rucksack1.charAt(i));
        }

        // Contains characters in both rucksacks 1 and 2
        HashSet<Character> set2 = new HashSet<>();
        for (int i = 0; i < rucksack2.length(); i++) {
            if (set1.contains(rucksack2.charAt(i))) {
                set2.add(rucksack2.charAt(i));
            }
        }

        int badgePriority = 0;
        int totalDuplicates = 0;
        for (int i = 0; i < rucksack3.length(); i++) {
            if (set2.contains(rucksack3.charAt(i))) {
                set2.remove(rucksack3.charAt(i));
                badgePriority += priority(rucksack3.charAt(i));
                totalDuplicates += 1;
            }
        }

        if (totalDuplicates != 1) {
            throw new IllegalArgumentException("Within the 3 rucksacks, there were an improper amount of badges (" + totalDuplicates + " badges found).");
        }

        return badgePriority;
    }

    /**
     * Validates the string. It must consist of English upper and lower case letters.
     * 
     * @param rucksack String to be validated
     * @return true if valid, false otherwise
     */
    private static boolean validateInput(String rucksack) {
        String regex = "[a-zA-Z]*";
        return rucksack.matches(regex);
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
