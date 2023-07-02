package advent_of_code_2022.problem_07.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;

// Custom tree class that keeps track of directory sizes and dependencies
public class DirectoryNode {
    private String path;
    private HashMap<String, DirectoryNode> children;
    private HashSet<String> claimedFiles;
    private BigInteger sizeOfLevel;

    public DirectoryNode(String nodePath) {
        path = nodePath;
        children = new HashMap<>();
        claimedFiles = new HashSet<>();
        sizeOfLevel = BigInteger.ZERO;
    }

    public void addDirectory(DirectoryNode child) {
        children.put(child.path, child);
    }

    // Recursively gets the size of children and adds it to current directory's size.
    public BigInteger getSize() {
        BigInteger totalSize = sizeOfLevel;
        for (DirectoryNode child : children.values()) {
            totalSize = totalSize.add(child.getSize());
        }
        return totalSize;
    }

    public void addFile(String fileName, int fileSize) {
        if (!claimedFiles.contains(fileName)) {
            claimedFiles.add(fileName);
            sizeOfLevel = sizeOfLevel.add(BigInteger.valueOf(fileSize));
        }
    }

    public String toString() {
        return path;
    }


}
