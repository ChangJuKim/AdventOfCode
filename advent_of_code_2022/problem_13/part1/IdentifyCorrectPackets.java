package advent_of_code_2022.problem_13.part1;

import java.util.ArrayList;
import java.util.LinkedList;

import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.common.PrintArray;

public class IdentifyCorrectPackets {
    public static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_13/input.txt";

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The sum of packet indices in the right order is " + countSumOfCorrectPacketPairIndices(input) + ".");
    }

    /**
     * Identifies the pairs of packets that are valid. Returns the sum of their indices.
     * A packet starts with [ and ends with ]. It is a comma-separated list whose elements are other comma separated lists or integers.
     * Compare the packets character by character until you reach a discrepancy.
     *  If both are ints and the left is smaller, it is valid. Otherwise invalid.
     *  If both are lists and the left ran out of elements first, it is valid. Otherwise invalid.
     *  If exactly one is an int, turn that one into a one element list [n]. Then keep comparing.
     * 
     * @param input String[] consisting of pairs of packets: two lines of packets (defined above), followed by an empty line.
     * @return The sum of indices of valid packet pairs. The pairs are 1-indexed.
     */
    public static int countSumOfCorrectPacketPairIndices(String[] input) {
        ArrayList<String[]> pairsOfPackets = getPairsOfPackets(input);
        int sumOfIndices = 0;
        int index = 1;
        
        for (String[] pair : pairsOfPackets) {
            LinkedList<String> leftQueue = parsePacketAsQueue(pair[0]);
            LinkedList<String> rightQueue = parsePacketAsQueue(pair[1]);

            if (isValidPair(leftQueue, rightQueue)) {
                sumOfIndices += index;
            }
            index++;
        }

        return sumOfIndices;
    }

    /**
     * Returns true if it is a valid pair, false otherwise. Validity is explained above.
     * 
     * @param left The first packet of a pair. A queue containing relevant values of the packet ([, ], or an integer)
     * @param right The second packet of a pair. A queue containing relevant values of the packet ([, ], or an integer)
     * @return true if it is a valid pair, false otherwise
     */
    private static boolean isValidPair(LinkedList<String> left, LinkedList<String> right) {
        while (!left.isEmpty() && !right.isEmpty()) {
            String leftValue = left.poll();
            String rightValue = right.poll();
            if (leftValue.equals(rightValue)) {
                continue;
            }

            try {
                int leftInt = Integer.parseInt(leftValue);
                try {
                    // Both are ints
                    int rightInt = Integer.parseInt(rightValue);
                    if (leftInt < rightInt) return true;
                    if (leftInt > rightInt) return false;
                } catch (NumberFormatException ex) {
                    // Left only is int
                    left.addFirst("]");
                    left.addFirst(leftValue);
                    left.addFirst("[");
                    right.addFirst(rightValue);
                }
            } catch (NumberFormatException ex) {
                try {
                    // Right only is int
                    Integer.parseInt(rightValue);
                    right.addFirst("]");
                    right.addFirst(rightValue);
                    right.addFirst("[");
                    left.addFirst(leftValue);
                } catch (NumberFormatException otherEx) {
                    // Neither are ints
                    if (leftValue.equals("[") && rightValue.equals("]")) {
                        // Right ran out of values
                        return false;
                    } else if (leftValue.equals("]") && rightValue.equals("[")) {
                        // Left ran out of values
                        return true;
                    } else {
                        // Left != right, they're not ints, they're not [ or ]... so we have an exception
                        // Should never happen
                        throw new IllegalArgumentException("Queue contains a noninteger that's not [ or ].\n" + left + "\n" + right);
                    }
                }
            }
        }
        return left.isEmpty();
    }

    /**
     * Extracts [, ] or integers and puts them into the queue in the same order.
     * 
     * @param packet A string to be turned into a queue
     * @return A String queue with only values of [, ] or integers in the same order the packet had them in
     */
    private static LinkedList<String> parsePacketAsQueue(String packet) {
        LinkedList<String> queue = new LinkedList<>();
        for (int i = 0; i < packet.length(); i++) {
            String current = packet.substring(i, i+1);
            if (current.equals("[") || current.equals("]")) {
                queue.addLast(current);
            } else {
                try {
                    // Get the next integer. As the next integer isn't necessarily a 1 digit number,
                    // we want to find the next index of either a ',' or ']' -- whichever comes first
                    int endIndex = packet.indexOf(',', i);
                    int endIndexBracket = packet.indexOf(']', i);
                    if (endIndex == -1 || (endIndexBracket != -1 && endIndex >= endIndexBracket)) {
                        endIndex = endIndexBracket;
                    }
                    String shouldBeAnInt = packet.substring(i, endIndex);

                    Integer.parseInt(shouldBeAnInt);
                    queue.addLast(shouldBeAnInt);
                    // If endIndex is at a comma, then it will skip it anyways. If it's at a ']', we want it in the queue
                    i = endIndex - 1;
                } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                    // Do nothing if it's not a '[', ']' or an int
                }
            }
        }
        return queue;
    }


    /**
     * Identifies pairs of packets and puts them in an ArrayList. Confirms that every 2 lines is followed by a new line.
     * 
     * @param lines String[], the input. Every third line must be a newline to identify pairs.
     * @return An ArrayList in which each element contains a String[] size 2 representing a pair.
     */
    private static ArrayList<String[]> getPairsOfPackets(String[] lines) {
        ArrayList<String[]> pairsOfPackets = new ArrayList<>();
        String[] currentPair = new String[2];
        for (String line : lines) {
            if (line.equals("")) {
                currentPair = new String[2];
            } else {
                if (currentPair[0] == null) {
                    currentPair[0] = line;
                } else if (currentPair[1] == null) {
                    currentPair[1] = line;
                    pairsOfPackets.add(currentPair);
                } else {
                    throw new IllegalArgumentException("Tried to add 3 packets to one pair in line: " + line);
                }
            }
        }
        return pairsOfPackets;
    }
}