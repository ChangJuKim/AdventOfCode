package advent_of_code_2022.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseInput {
    
    // Practice for learning package structure
    public static String identity(String s) {
        return s;
    }

    public static String[] parseInputAsList(String fileName) {
        File file = new File(fileName);
        return parseInputAsList(file);
    }

    public static String[] parseInputAsList(File file) {
        String[] linesArray = new String[0];
        try (Scanner scanner = new Scanner(file)) {
            ArrayList<String> lines = new ArrayList<String>();
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            linesArray = lines.toArray(new String[0]);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + file);
        }
        return linesArray;
    }

    public static String parseInputAsString(String fileName) {
        File file = new File(fileName);
        return parseInputAsString(file);
    }

    public static String parseInputAsString(File file) {
        StringBuilder builder = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + file);
        }
        return builder.toString();
    }

    public static int[][] parseInputAsPositiveIntegerMatrix(String fileName) {
        File file = new File(fileName);
        return parseInputAsPositiveIntegerMatrix(file);
    }

    public static int[][] parseInputAsPositiveIntegerMatrix(File file) {
        ArrayList<Integer[]> tempList = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Integer[] row = new Integer[line.length()];

                for (int i = 0; i < line.length(); i++) {
                    row[i] = Integer.parseInt(line.substring(i,i+1));
                    if (row[i] < 0 || row[i] > 9) {
                        throw new IllegalArgumentException("File content is not numeric");
                    }
                }

                tempList.add(row);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + file);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }

        int[][] integerArray = new int[tempList.size()][];

        for (int i = 0; i < integerArray.length; i++) {
            Integer[] row = tempList.get(i);
            integerArray[i] = new int[row.length];

            for (int j = 0; j < row.length; j++) {
                integerArray[i][j] = row[j];
            }
        }

        return integerArray;
    }
}
