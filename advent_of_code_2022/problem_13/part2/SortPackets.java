package advent_of_code_2022.problem_13.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.common.PrintArray;

/* This can actually be solved by finding all packets smaller than the dividers, and returning the product of counts.
However, in the spirit of sorting the packets, I decided to go with the more inefficient approach of sorting the packets
and finding the locations of both dividers.

After all, I interpreted the problem statement as having to sort the packets. This makes sense in the context of part 1,
in which we essentially wrote a compare statement.
To me, it seems like the dividers are just a way to get a integer to verify my answer, instead of the main focus of the problem
*/ 
public class SortPackets {
    public static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_13/input.txt";
    public static final String[] DIVIDER_PACKETS = new String[] {"[[2]]", "[[6]]"};

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The product of divider key indices is " + countSumOfCorrectPacketPairIndices(input) + ".");
    }

    /**
     * Converts a list of packets into a nice format, and sorts them.
     * Also inputs the divider packets into the list. Then finds their indices and returns the product.
     * 
     * A packet starts with [ and ends with ]. It is a comma-separated list whose elements are other comma separated lists or integers.
     * Compare the packets character by character until you reach a discrepancy.
     *  If both are ints and the left is smaller, it is valid. Otherwise invalid.
     *  If both are lists and the left ran out of elements first, it is valid. Otherwise invalid.
     *  If exactly one is an int, turn that one into a one element list [n]. Then keep comparing.
     * 
     * @param input String[] consisting of packets. There may be empty lines which are disregarded.
     * @return The product of the indices of the divider packets, after they are placed and sorted.
     */
    public static int countSumOfCorrectPacketPairIndices(String[] input) {
        ArrayList<LinkedList<String>> packetQueues = parsePacketsAsQueues(input);
        ArrayList<LinkedList<String>> dividerPackets = parsePacketsAsQueues(DIVIDER_PACKETS);
        packetQueues.addAll(dividerPackets);
        Collections.sort(packetQueues, new PacketQueueComparator());

        int product = 1;
        for (LinkedList<String> divider : dividerPackets) {
            // Add 1 because it's 1 indexed
            product *= Collections.binarySearch(packetQueues, divider, new PacketQueueComparator()) + 1;
        }
        return product;
    }

    /**
     * Converts a list of packets into an array of queues. Skips newlines.
     * The queues only recognizes [, ], and integers. Keeps elements in the same order they are found.
     * 
     * @param lines String[], the input to be turned into an array of queues
     * @return An ArrayList in which each element is a queue with values of [, ], and/or integers.
     */
    private static ArrayList<LinkedList<String>> parsePacketsAsQueues(String[] lines) {
        ArrayList<LinkedList<String>> listOfQueues = new ArrayList<>();
        for (String packet : lines) {
            if (packet.length() != 0) {
                LinkedList<String> queue = parseSinglePacketAsQueue(packet);
                listOfQueues.add(queue);
            }
        }
        return listOfQueues;
    }

    /**
     * Extracts [, ] or integers and puts them into the queue in the same order.
     * 
     * @param packet A string to be turned into a queue
     * @return A String queue with only values of [, ] or integers in the same order the packet had them in
     */
    private static LinkedList<String> parseSinglePacketAsQueue(String packet) {
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

    // I think normally I would put the subclass at the top... but considering this has nothing to do with the rest of the main class,
    // I decided to put it at the bottom so that it is not dividing any logic from each other.
    private static class PacketQueueComparator implements Comparator<LinkedList<String>> {
        
        /**
        * A very niche comparer that assumes the LinkedLists are queues in a nice format and compares them in a very specific way
        * 
        * @param leftQueue The first packet of a pair. A queue containing relevant values of the packet ([, ], or an integer)
        * @param rightQueue The second packet of a pair. A queue containing relevant values of the packet ([, ], or an integer)
        * @return Returns -1 if it is a valid pair, 1 if it is not, 0 if equal.
        */
        @Override
        public int compare(LinkedList<String> leftQueue, LinkedList<String> rightQueue) {
            LinkedList<String> left = new LinkedList<>(leftQueue);
            LinkedList<String> right = new LinkedList<>(rightQueue);

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
                        if (leftInt < rightInt) return -1;
                        if (leftInt > rightInt) return 1;
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
                            return 1;
                        } else if (leftValue.equals("]") && rightValue.equals("[")) {
                            // Left ran out of values
                            return -1;
                        } else {
                            // Left != right, they're not ints, they're not [ or ]... so we have an exception
                            // Should never happen
                            throw new IllegalArgumentException("Queue contains a noninteger that's not [ or ].\n" + left + "\n" + right);
                        }
                    }
                }
            }
            if (left.isEmpty() && !right.isEmpty()) return -1;
            else if (!left.isEmpty() && right.isEmpty()) return 1;
            else return 0;
        }
    }
}