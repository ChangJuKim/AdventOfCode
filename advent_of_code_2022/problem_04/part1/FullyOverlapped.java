package advent_of_code_2022.problem_04.part1;

import advent_of_code_2022.common.ParseInput;

public class FullyOverlapped {

    static final String FILE_NAME = "advent_of_code_2022/problem_04/input.txt";
    static final String ASSIGNMENT_PAIR_REGEX = "\\d+-\\d+,\\d+-\\d+";
    
    public static void main(String[] args) {
        String[] assignmentsList = ParseInput.parseInputAsList(FILE_NAME);
        System.out.println("There are " + countFullyContainedPairs(assignmentsList) + " pairs in which one range fully contains the other.");
    }

    /**
     * Each string forms one pair of timeslots separated by a comma. A pair is overlapping if one timeslot fully contains the other. 
     * 
     * @param assignmentsList The input list. A list of strings in the following format: #-#,#-#. The first two numbers form one timeslot, the next two another.
     * @return The number of pairs in which one timeslot overlaps with another.
     */
    public static int countFullyContainedPairs(String[] assignmentsList) {
        int total = 0;
        for (int i = 0; i < assignmentsList.length; i++) {        
            if (!validateAssignmentPair(assignmentsList[i])) {
                throw new IllegalArgumentException("Assignment pair " + i + " does not match the format #-#,#-#: " + assignmentsList[i]);
            }

            try {
                if (isFullyContained(assignmentsList[i])) {
                    total++;
                }
            } catch (NumberFormatException ex) {
                // Should never run as we validate input in validateAssignmentPair function
                throw new IllegalArgumentException("Invalid input for " + assignmentsList[i] + ". " + ex);
            }
        }
        return total;
    }

    /**
     * Extracts the start and end times of the two assignments, and sees if one encompasses the other
     * 
     * @param assignmentPair A string representing a pair of assignments in the format: #-#,#-#
     * @return True if one pair encompasses the other, false otherwise
     */
    private static boolean isFullyContained(String assignmentPair) {
        int firstDashIndex = assignmentPair.indexOf('-');
        int commaIndex = assignmentPair.indexOf(',');
        int secondDashIndex = assignmentPair.indexOf('-', commaIndex);
        
        int start1 = Integer.parseInt(assignmentPair.substring(0, firstDashIndex));
        int end1 = Integer.parseInt(assignmentPair.substring(firstDashIndex+1, commaIndex));
        int start2 = Integer.parseInt(assignmentPair.substring(commaIndex+1, secondDashIndex));
        int end2 = Integer.parseInt(assignmentPair.substring(secondDashIndex+1));
        
        if (!validateTimeslot(start1, end1) || !validateTimeslot(start2, end2)) {
            throw new IllegalArgumentException("Invalid assignment time: " + assignmentPair);
        }

        return start1 <= start2 && end1 >= end2 || start1 >= start2 && end1 <= end2;
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
