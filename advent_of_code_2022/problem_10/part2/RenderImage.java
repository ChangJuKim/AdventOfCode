package advent_of_code_2022.problem_10.part2;

import java.util.ArrayList;

import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.common.PrintArray;

public class RenderImage {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_10/input.txt";
    static final int IMAGE_WIDTH = 40;
    static final int IMAGE_HEIGHT = 6;

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        String[][] image = calculateImage(input);
        
        System.out.println("The image produced is as follows.");
        new PrintArray<String>().print2DArray(image);
        
    }

    /**
     * 
     * Given a list of commands, calculates the sum of signal strengths at specified breakpoints.
     * A signal strength = cycle (at a breakpoint) * register value.
     * Thus, for breakpoints of {20, 60, 100}, the sum will be 20 * (register at cycle 20) + 60 * (register at 60) + 100 * (register at 100).
     * 
     * @param input The list of commands to be run. Each element will either be "noop" (move 1 cycle) or "addx num" (add num to the register after 2 cycles)
     * @return The total sum of the signal strengths
     */
    public static String[][] calculateImage(String[] input) {
        int currentCycle = 1;
        int register = 1;
        int nextBreakpointIndex = 0;
        ArrayList<String[]> image = new ArrayList<>(IMAGE_HEIGHT);
        String[] imageRow = new String[IMAGE_WIDTH];

        for (String line : input) {
            String[] command = line.split(" ");
            validateInput(command);
            
            

            // Handle command
            if (command[0].equals("addx")) {
                register += Integer.parseInt(command[1]);
                currentCycle += 2;
            } else {
                currentCycle++;
            }

        }

    }

    /**
     * Validates a single line of input. The line must either be "noop" or "addx #" where # is an integer
     * 
     * @param command A line of input that has been split by spaces.
     * @return True if the command follows the criteria above. False otherwise.
     */
    private static boolean validateInput(String[] command) {
        if (command.length == 1 && command[0].equals("noop")) return true;
        if (command.length == 2 && command[0].equals("addx")) {
            try {
                Integer.parseInt(command[1]);
                return true;
            } catch (NumberFormatException ex) {

            }
        }
        return false;
    }
}
