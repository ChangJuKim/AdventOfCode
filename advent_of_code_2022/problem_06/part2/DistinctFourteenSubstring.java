package advent_of_code_2022.problem_06.part2;

import java.util.HashMap;
import java.util.Map;

import advent_of_code_2022.common.ParseInput;

public class DistinctFourteenSubstring {
    
    public static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_06/input.txt";
    private static final Integer SUBSTRING_LENGTH = 14;

    public static void main(String[] args) {
        String input = ParseInput.parseInputAsString(INPUT_FILE_NAME);
        System.out.println("You will find a substring length " + SUBSTRING_LENGTH + " with all distinct characters after " + countCharsUntilDistinctFourSubstring(input) + " characters");
    }

    /**
     * Finds the first instance of a SUBSTRING_LENGTH substring that has all distinct characters. Returns how many characters you would have
     * to pass in the buffer (input) to reach the end of that substring.
     * 
     * @param buffer String and input
     * @return The index+1 of the first instance of a desired length substring with all distinct characters.
     */
    public static int countCharsUntilDistinctFourSubstring(String buffer) {
        int counter = 0;
        Map<Character, Integer> map = new HashMap<>();
        while (counter < buffer.length()) {
            addInstanceToMap(map, buffer.charAt(counter));
            if (counter >= SUBSTRING_LENGTH) {
                removeInstanceFromMap(map, buffer.charAt(counter - SUBSTRING_LENGTH));
            }
            if (map.size() >= SUBSTRING_LENGTH) {
                // We have a substring of all distinct characters
                return counter + 1;
            }
            counter++;
        }
        return buffer.length();
    }

    /**
     * Removes a count of a character from a map. If the count is 0, removes it from the map.
     * 
     * @param map Map to be changed
     * @param c Character to be removed
     */
    private static void removeInstanceFromMap(Map<Character, Integer> map, Character c) {
        if (!map.containsKey(c)) {
            throw new IllegalArgumentException("Tried to remove a nonexistent key (" + c + ") from the map: " + map);
        }
        map.put(c, map.get(c) - 1);
        if (map.get(c) <= 0) {
            map.remove(c);
        }
    }

    /**
     * Adds a count of a character to a map
     * 
     * @param map
     * @param c
     */
    private static void addInstanceToMap(Map<Character, Integer> map, Character c) {
        map.put(c, map.getOrDefault(c, 0) + 1);
    }
}
