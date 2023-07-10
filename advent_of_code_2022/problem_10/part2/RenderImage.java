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
     * Creates an image based on the commands to be run. Each command incrememnts the "cycle" by 1 or 2. At each cycle, the image
     * develops further. A '#' will be placed if the register is at most distance one from the column. Otherwise a '.' is placed.
     * 
     * @param input The list of commands to be run. Each element will either be "noop" (move 1 cycle) or "addx num" (add num to the register after 2 cycles)
     * @return The image formed by the commands
     */
    public static String[][] calculateImage(String[] input) {
        int currentCycle = 0;
        int register = 1;
        String[][] image = new String[IMAGE_HEIGHT][IMAGE_WIDTH];

        for (String line : input) {
            String[] command = line.split(" ");
            validateInput(command);

            // Handle commands
            if (command[0].equals("addx")) {
                fillImage(image, currentCycle, register);
                currentCycle++;
                
                fillImage(image, currentCycle, register);
                currentCycle ++;
                register += Integer.parseInt(command[1]);
            } else {
                fillImage(image, currentCycle, register);
                currentCycle++;
            }
            if (currentCycle >= IMAGE_HEIGHT * IMAGE_WIDTH) break;
        }

        return image;
    }

    /**
     * Marks the image if register is a distance 1 or 0 away from the column index.
     * Image marked is determined based on the current cycle.
     * If the cycle is too large, or negative, nothing happens
     * 
     * @param image A 2D string to be updated. Displays an ascii art
     * @param currentCycle The cycle that the machine is on. Determines the indices of the image
     * @param register Determines if a '#' or a '.' should be placed into the image.
     */
    public static void fillImage(String[][] image, int currentCycle, int register) {
        int row = currentCycle / IMAGE_WIDTH;
        int col = currentCycle % IMAGE_WIDTH;
        if (currentCycle < 0 || row >= IMAGE_HEIGHT) return;

        if (Math.abs(register - col) <= 1) {
            image[row][col] = "#";
        } else {
            image[row][col] = ".";
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
