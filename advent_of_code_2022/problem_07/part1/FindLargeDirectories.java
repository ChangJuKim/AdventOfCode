package advent_of_code_2022.problem_07.part1;

import advent_of_code_2022.common.ParseInput;

public class FindLargeDirectories {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_07/input.txt";

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The total sum of the sizes of large directories is " + findSumOfLargeDirectories(input));
    }

    public static int findSumOfLargeDirectories(String[] input) {
        for (int i = 0; i < input.length; i++) {
            // Handle commands here
        }
        return 0;
    }
}
