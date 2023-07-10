package advent_of_code_2022.problem_11.part1;

import java.util.LinkedList;

public class Monkey {
    static final String OPERATOR_MULTIPLY = "MULTIPLY";
    static final String OPERATOR_ADD = "ADD";
    static final String OPERATOR_SUBTRACT = "SUBTRACT";
    static final String OPERATOR_DIVIDE = "DIVIDE";


    private final int ID;
    private LinkedList<Integer> heldObjects;
    private String operationFunction;
    private int operationNumber;
    private String testFunction;
    private int testNumber;
    private int receiverIfTrue;
    private int receiverIfFalse;

    public Monkey(int ID) {
        this.ID = ID;
        heldObjects = new LinkedList<>();
    }

    // Expects line to be in the format of "Starting items: 13, 23, 15, 100, 21, 2" where after items: are comma and space separated integers
    // Adds items to the heldObjects list
    public void setHeldObjects(String line) {
        String START_OF_LINE = "Starting items: ";

        int index = skipPastStart(line, START_OF_LINE);

        String[] objects = line.substring(index).split(", ", index);
        try {
            for (String object : objects) {
                heldObjects.add(Integer.parseInt(object));
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Expected numbers after \"Starting items: \" but got " + line);
        }
    }

    // Expects line to be in the format of "Operation: new = old * 11". * can be replaced with -+/ and 11 can be any integer
    // Sets the operationFunction and operationNumber variables
    public void setOperation(String line) {
        String START_OF_LINE = "Operation: new = old ";

        int index = skipPastStart(line, START_OF_LINE);

        char operator = line.charAt(index);
        if (operator == '+') operationFunction = OPERATOR_ADD;
        else if (operator == '-') operationFunction = OPERATOR_SUBTRACT;
        else if (operator == '*') operationFunction = OPERATOR_MULTIPLY;
        else if (operator == '/') operationFunction = OPERATOR_DIVIDE;
        else {
            throw new IllegalArgumentException("Invalid operator: " + line);
        }
        
        index += 2;
        operationNumber = getNumber(line, index, line.length());
    }

    // Expects testLine to be in the format of "Test: divisble by 13" where divisible should just be divisble and 13 can be any integer
    // Expects trueLine to be in the format of "If true: throw to monkey 2" where 2 can be any integer
    // Expects falseLine to be in the format of "If false: throw to monkey 4" where 4 can be any integer
    // Sets testFunction, testNumber, receiverIfTrue and receiverIfFalse fields
    public void setTests(String testLine, String trueLine, String falseLine) {
        String START_OF_TEST_LINE = "Test: divisble by ";
        String START_OF_TRUE_LINE = "If true: throw to monkey ";
        String START_OF_FALSE_LINE = "If false: throw to monkey ";

        int index = skipPastStart(testLine, START_OF_TEST_LINE);
        testFunction = OPERATOR_DIVIDE;
        testNumber = getNumber(testLine, index, testLine.length());

        index = skipPastStart(trueLine, START_OF_TRUE_LINE);
        receiverIfTrue = getNumber(START_OF_TRUE_LINE, index, trueLine.length());

        index = skipPastStart(falseLine, START_OF_FALSE_LINE);
        receiverIfFalse = getNumber(START_OF_FALSE_LINE, index, falseLine.length());
    }

    // Finds first index of startOfLine in line, and returns the index immediately after it.
    private int skipPastStart(String line, String startOfLine) {
        int index = line.indexOf(startOfLine);
        if (index == -1) {
            throw new IllegalArgumentException("Expected to begin with " + startOfLine + " but got " + line);
        }
        return index + startOfLine.length() + 1;
    }

    // Returns the integer in line between start and end index. If endIndex >= line.length, then parses from start index to end of string.
    private int getNumber(String line, int startIndex, int endIndex) {
        try {
            return Integer.parseInt(line.substring(startIndex, endIndex));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Expected number between indices " + startIndex + " and " + endIndex + " of " + line);
        }
    }


}
