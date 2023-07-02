package advent_of_code_2022.problem_07.part2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.problem_07.util.DirectoryNode;
import advent_of_code_2022.problem_07.util.Constants;

public class FindLargestSmallDirectory {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_07/input.txt";
    static final String INPUT_FILE_SAMPLE = "advent_of_code_2022/problem_07/sampleInput.txt";
    static final BigInteger MAX_SIZE = BigInteger.valueOf(70000000);
    static final BigInteger NECESSARY_SPACE = BigInteger.valueOf(30000000);
    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The smallest large directory is: " + findSumOfSmallDirectories(input));
    }

    public static BigInteger findSumOfSmallDirectories(String[] input) {
        String currentLocation = "";
        HashMap<String, DirectoryNode> directoryMap = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            String[] line = input[i].split(" ");
            if (line[0].equals(Constants.START_OF_COMMAND) && line[1].equals(Constants.CHANGE_DIRECTORY_COMMAND)) {
                if (line[2].equals(Constants.ROOT_DIRECTORY)) {
                    currentLocation = Constants.ROOT_DIRECTORY;
                } else if (line[2].equals(Constants.PREVIOUS_DIRECTORY)) {
                    currentLocation = getPreviousDirectory(currentLocation);
                } else if (line[2].equals(Constants.CURRENT_DIRECTORY)) {
                    // do nothing
                } else {
                    currentLocation = currentLocation + "/" + line[2];
                }
                directoryMap.put(currentLocation, directoryMap.getOrDefault(currentLocation, new DirectoryNode(currentLocation)));
            } else if (line[0].equals(Constants.START_OF_COMMAND) && line[1].equals(Constants.LIST_DIRECTORIES_COMMAND)) {
                i = handleLSCommandAndReturnIndex(input, i, currentLocation, directoryMap);
            } else {
                throw new IllegalArgumentException("Error in input: " + line[i]);
            }
        }

        ArrayList<BigInteger> sizes = new ArrayList<>();
        for (DirectoryNode node : directoryMap.values()) {
            sizes.add(node.getSize());
        }
        sizes.sort(Comparator.reverseOrder());
        BigInteger spaceNeededToBeFreed = NECESSARY_SPACE.subtract(MAX_SIZE.subtract(sizes.get(0)));
        if (spaceNeededToBeFreed.compareTo(BigInteger.ZERO) <= 0) {
            // No need to delete any directories
            return BigInteger.ZERO;
        }

        return findSmallestIntegerGreaterThanThreshold(spaceNeededToBeFreed, sizes);
    }

    private static String getPreviousDirectory(String path) {
        int result = path.lastIndexOf('/');
        if (result == -1) {
            throw new IllegalArgumentException("Tried to cd .. out of a top level directory");
        }
        return path.substring(0, result);
    }

    private static int handleLSCommandAndReturnIndex(String[] input, int inputIndex, String currentLocation, HashMap<String, DirectoryNode> directoryMap) {
        // Increment inputIndex to handle the output after ls
        for (inputIndex = inputIndex + 1; inputIndex < input.length; inputIndex++) {
            String[] line = input[inputIndex].split(" ");
            String command = line[0];
            String argument = line[1];
            if (command.equals(Constants.START_OF_COMMAND)) break;

            DirectoryNode node = directoryMap.get(currentLocation);
            if (node == null) {
                // Should never happen
                throw new IllegalArgumentException("Node does not exist");
            }
            if (command.equals(Constants.DIRECTORY)) {
                String directoryPath = currentLocation + "/" + argument;
                directoryMap.put(directoryPath, directoryMap.getOrDefault(directoryPath, new DirectoryNode(directoryPath)));
                node.addDirectory(directoryMap.get(directoryPath));
            } else {
                try {
                    int sizeOfFile = Integer.parseInt(command);
                    String nameOfFile = argument;
                    node.addFile(nameOfFile, sizeOfFile);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Result of ls is neither a directory nor a file: " + input[inputIndex] + ". " + ex);
                }
            }
        }
        // for loop will increment by 1 so we decrement by 1 here
        return inputIndex - 1;
    }

    // Assumes ary is sorted in descending order
    private static BigInteger findSmallestIntegerGreaterThanThreshold(BigInteger threshold, ArrayList<BigInteger> ary) {
        System.out.println(ary);
        System.out.println(threshold);
        if (ary.isEmpty()) return BigInteger.ZERO;
        BigInteger result = ary.get(0);
        for (BigInteger size : ary) {
            if (size.compareTo(threshold) >= 0 && size.compareTo(result) < 0) {
                result = size;
            }
        }
        return result;
    }
}
