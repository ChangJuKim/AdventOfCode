package AoC2022.Common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseInput {
    
    // Practice for learning package structure
    public static String identity(String s) {
        return s;
    }

    public static String[] parseInput(String fileName) {
        String[] linesArray = new String[0];
        try (Scanner scanner = new Scanner(new File(fileName))) {
            ArrayList<String> lines = new ArrayList<String>();
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            linesArray = lines.toArray(new String[0]);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + fileName);
        }
        return linesArray;
    }
}
