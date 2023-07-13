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

    public static char[][] parseInputAsCharMatrix(String fileName) {
        File file = new File(fileName);
        ArrayList<Character[]> tempList = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Character[] row = new Character[line.length()];

                for (int i = 0; i < line.length(); i++) {
                    row[i] = line.charAt(i);
                }

                tempList.add(row);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + file);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }

        char[][] charMatrix = new char[tempList.size()][];

        for (int i = 0; i < charMatrix.length; i++) {
            Character[] row = tempList.get(i);
            charMatrix[i] = new char[row.length];

            for (int j = 0; j < row.length; j++) {
                charMatrix[i][j] = row[j];
            }
        }

        return charMatrix;
    } 

    public static int[][] parseInputAsPositiveIntegerMatrix(String fileName) {
        File file = new File(fileName);
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

        int[][] integerMatrix = new int[tempList.size()][];

        for (int i = 0; i < integerMatrix.length; i++) {
            Integer[] row = tempList.get(i);
            integerMatrix[i] = new int[row.length];

            for (int j = 0; j < row.length; j++) {
                integerMatrix[i][j] = row[j];
            }
        }

        return integerMatrix;
    }
}
