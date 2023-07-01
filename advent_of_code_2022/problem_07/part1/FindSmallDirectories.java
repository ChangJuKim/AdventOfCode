package advent_of_code_2022.problem_07.part1;

import java.math.BigInteger;
import java.util.HashMap;

import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.problem_07.util.DirectoryNode;

public class FindSmallDirectories {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_07/input.txt";
    static final String INPUT_FILE_SAMPLE = "advent_of_code_2022/problem_07/sampleInput.txt";
    static final BigInteger SMALL_DIRECTORY_THRESHOLD = new BigInteger("" + 100000);

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The total sum of the sizes of small directories is " + findSumOfSmallDirectories(input));
    }

    public static BigInteger findSumOfSmallDirectories(String[] input) {
        String currentLocation = "";
        HashMap<String, DirectoryNode> directoryMap = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            String[] line = input[i].split(" ");
            if (line[0].equals("$") && line[1].equals("cd")) {
                if (line[2].equals("/")) {
                    currentLocation = "/";
                } else if (line[2].equals("..")) {
                    currentLocation = getPreviousDirectory(currentLocation);
                } else if (line[2].equals(".")) {
                    // do nothing
                } else {
                    currentLocation = currentLocation + "/" + line[2];
                }
                DirectoryNode current = directoryMap.get(currentLocation);
                directoryMap.put(currentLocation, directoryMap.getOrDefault(currentLocation, new DirectoryNode(currentLocation)));
            } else if (line[0].equals("$") && line[1].equals("ls")) {
                i = handleLSCommandAndReturnIndex(input, i, currentLocation, directoryMap);
            } else {
                throw new IllegalArgumentException("Error in input: " + line[i]);
            }
        }

        BigInteger totalSize = BigInteger.ZERO;
        // Find small directory sizes
        for (DirectoryNode node : directoryMap.values()) {
            BigInteger size = node.getSize();
            if (size.compareTo(SMALL_DIRECTORY_THRESHOLD) <= 0) {
                totalSize = totalSize.add(size);
            }
        }
        return totalSize;
    }

    private static String getPreviousDirectory(String path) {
        int slowIndex = -1;
        int fastIndex = path.indexOf('/');
        while (fastIndex != -1) {
            slowIndex = fastIndex;
            fastIndex = path.indexOf('/', fastIndex + 1);
        }
        if (slowIndex == -1) {
            throw new IllegalArgumentException("Tried to cd .. out of a top level directory");
        }
        return path.substring(0, slowIndex);
    }

    private static int handleLSCommandAndReturnIndex(String[] input, int inputIndex, String currentLocation, HashMap<String, DirectoryNode> directoryMap) {
        // Increment inputIndex to handle the output after ls
        for (inputIndex = inputIndex + 1; inputIndex < input.length; inputIndex++) {
            String[] line = input[inputIndex].split(" ");
            if (line[0].equals("$")) break;

            DirectoryNode node = directoryMap.get(currentLocation);
            if (node == null) {
                // Should never happen
                throw new IllegalArgumentException("Node does not exist");
            }
            if (line[0].equals("dir")) {
                String directoryPath = currentLocation + "/" + line[1];
                directoryMap.put(directoryPath, directoryMap.getOrDefault(directoryPath, new DirectoryNode(directoryPath)));
                node.addDirectory(directoryMap.get(directoryPath));
            } else {
                try {
                    int sizeOfFile = Integer.parseInt(line[0]);
                    String nameOfFile = line[1];
                    node.addFile(nameOfFile, sizeOfFile);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Result of ls is neither a directory nor a file: " + input[inputIndex] + ". " + ex);
                }
            }
        }
        // for loop will increment by 1
        return inputIndex - 1;
    }
}
