package advent_of_code_2022.problem_10.part1;

import advent_of_code_2022.common.ParseInput;

public class CalculateSignalStrength {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_10/input.txt";
    static final int[] SIGNAL_STRENGTH_BREAKPOINTS = new int[] {20, 60, 100, 140, 180, 220};

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The total signal strength at the specified breakpoints is " + sumOfSignalStrengthAtBreakpoints(input));
    }

    public static int sumOfSignalStrengthAtBreakpoints(String[] input) {
        int currentCycle = 0;
        int register = 1;
        int nextBreakpointIndex = 0;
        int cycleBreakpoint = SIGNAL_STRENGTH_BREAKPOINTS[nextBreakpointIndex];
        int totalSum = 0;

        for (String line : input) {
            String[] command = line.split(" ");
            validateInput(command);
            System.out.println("(Cycle, register): (" + currentCycle + ", " + register + ") -- line: " + line);

            if (currentCycle == cycleBreakpoint || (command[0].equals("addx") && currentCycle + 2 > cycleBreakpoint)) {
                System.out.println("Adding totalSum by " + register + " * " + cycleBreakpoint + " = " + register * cycleBreakpoint);
                totalSum += register * cycleBreakpoint;
                nextBreakpointIndex++;
                // if (nextBreakpointIndex >= SIGNAL_STRENGTH_BREAKPOINTS.length) {
                //     break;
                // }
                cycleBreakpoint = nextBreakpointIndex < SIGNAL_STRENGTH_BREAKPOINTS.length ? SIGNAL_STRENGTH_BREAKPOINTS[nextBreakpointIndex] : currentCycle * 100;
            }

            if (command[0].equals("addx")) {
                register += Integer.parseInt(command[1]);
                currentCycle += 2;
            } else {
                currentCycle++;
            }

        }

        return totalSum;
    }

    private static boolean validateInput(String[] command) {
        return true;
    }
}
