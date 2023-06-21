package advent_of_code_2022.problem_06.part1;

import java.util.HashMap;
import java.util.Map;

import advent_of_code_2022.common.ParseInput;

public class DistinctFourSubstring {
    
    public static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_06/input.txt";
    private static final Integer SUBSTRING_LENGTH = 4;

    public static void main(String[] args) {
        String input = ParseInput.parseInputAsString(INPUT_FILE_NAME);
        System.out.println("You will find a substring length " + SUBSTRING_LENGTH + " with all distinct characters after " + countCharsUntilDistinctFourSubstring(input) + " characters");
    }

    public static int countCharsUntilDistinctFourSubstring(String buffer) {
        int counter = 0;
        Map<Character, Integer> map = new HashMap<>();
        while (counter < buffer.length()) {
            Character newCharacter = buffer.charAt(counter);
            map.put(newCharacter, map.getOrDefault(newCharacter, 0) + 1);
            if (counter >= SUBSTRING_LENGTH) {
                Character oldCharacter = buffer.charAt(counter - SUBSTRING_LENGTH);
                removeFromMap(map, oldCharacter);
            }
            if (map.size() >= 4) {
                return counter + 1;
            }
            counter++;
        }
        return buffer.length();
    }

    private static void removeFromMap(Map<Character, Integer> map, Character c) {
        if (!map.containsKey(c)) {
            throw new IllegalArgumentException("Tried to remove a nonexistent key (" + c + ") from the map: " + map);
        }
        map.put(c, map.get(c) - 1);
        if (map.get(c) <= 0) {
            map.remove(c);
        }
    }
}
