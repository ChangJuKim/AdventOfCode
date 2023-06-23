package advent_of_code_2022.problem_07.util;

import java.util.ArrayList;
import java.util.HashMap;

// Custom tree class that keeps track of directory sizes and dependencies
public class DirectoryNode {
    private String name;
    private HashMap<String, DirectoryNode> children;
    private int currentLevelSize;

    public DirectoryNode(String nodeName) {
        name = nodeName;
        children = new HashMap<>(children);
        currentLevelSize = 0;
    }

    public void addDirectory(DirectoryNode child) {
        children.put(child.name, child);
    }

    // Recursively gets the size of children and adds it to current directory's size.
    public int getSize() {
        int totalSize = currentLevelSize;
        for (DirectoryNode child : children.values()) {
            totalSize += child.getSize();
        }
        return totalSize;
    }

    public void addFile(int fileSize) {
        currentLevelSize += fileSize;
    }


}
