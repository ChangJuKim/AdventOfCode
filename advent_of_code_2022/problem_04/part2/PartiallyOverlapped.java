package advent_of_code_2022.problem_04.part2;

import advent_of_code_2022.common.ParseInput;

public class PartiallyOverlapped {

    static final String FILE_NAME = "advent_of_code_2022/problem_04/input.txt";
    static final String ASSIGNMENT_PAIR_REGEX = "\\d+-\\d+,\\d+-\\d+";
    private static final String INVALID_INPUT_FORMAT_MSG = "Assignment pair does not match the format #-#,#-#: ";
    private static final String INVALID_ASSIGNMENT_TIMES_MSG = "Invalid assignment time: ";
    
    public static void main(String[] args) {
        String[] assignmentsList = ParseInput.parseInput(FILE_NAME);
        System.out.println("There are " + countPartiallyContainedPairs(assignmentsList) + " pairs in which one range partially contains the other.");
    }

    /**
     * Each string forms one pair of timeslots separated by a comma. Counts the number of partially overlapped pairs. 
     * 
     * @param assignmentsList The input list. A list of strings in the following format: #-#,#-#. The first two numbers form one timeslot, the next two another.
     * @return The number of pairs in which one timeslot overlaps with another.
     */
    public static int countPartiallyContainedPairs(String[] assignmentsList) {
        int total = 0;
        for (int i = 0; i < assignmentsList.length; i++) {        
            if (!validateAssignmentPair(assignmentsList[i])) {
                throw new IllegalArgumentException(INVALID_INPUT_FORMAT_MSG + assignmentsList[i]);
            }

            try {
                if (isPartiallyContained(assignmentsList[i])) {
                    total++;
                }
            } catch (NumberFormatException ex) {
                // Should never run as we validate input in validateAssignmentPair function
                throw new IllegalArgumentException(INVALID_INPUT_FORMAT_MSG + assignmentsList[i] + ". " + ex);
            }
        }
        return total;
    }

    /**
     * Extracts the start and end times of the two assignments, and sees if one overlaps with the other
     * 
     * @param assignmentPair A string representing a pair of assignments in the format: #-#,#-#
     * @return True if one pair overlaps with the other, false otherwise
     */
    private static boolean isPartiallyContained(String assignmentPair) {
        int firstDashIndex = assignmentPair.indexOf('-');
        int commaIndex = assignmentPair.indexOf(',');
        int secondDashIndex = assignmentPair.indexOf('-', commaIndex);
        
        int start1 = Integer.parseInt(assignmentPair.substring(0, firstDashIndex));
        int end1 = Integer.parseInt(assignmentPair.substring(firstDashIndex+1, commaIndex));
        int start2 = Integer.parseInt(assignmentPair.substring(commaIndex+1, secondDashIndex));
        int end2 = Integer.parseInt(assignmentPair.substring(secondDashIndex+1));
        
        if (!validateTimeslot(start1, end1) || !validateTimeslot(start2, end2)) {
            throw new IllegalArgumentException(INVALID_ASSIGNMENT_TIMES_MSG + assignmentPair);
        }

        return start1 <= end2 && end1 >= start2;
    }

    /**
     * Ensures the start and end times are valid -- both are nonnegative, and start <= end
     * 
     * @param start The start time of a timeslot
     * @param end The end time of a timeslot
     * @return True if valid, false otherwise
     */
    private static boolean validateTimeslot(int start, int end) {
        return start >= 0 && end >= 0 && start <= end;
    }

    /**
     * Ensures the assignment pair is in the format: #-#,#-#
     * 
     * @param assignmentPair String to be checked
     * @return True if is valid, false otherwise
     */
    private static boolean validateAssignmentPair(String assignmentPair) {
        return assignmentPair.matches(ASSIGNMENT_PAIR_REGEX);
    }
}
