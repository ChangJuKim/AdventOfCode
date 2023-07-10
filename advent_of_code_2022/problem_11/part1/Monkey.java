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
    private Monkey receiverIfTrue;
    private Monkey receiverIfFalse;

    public Monkey(int ID) {
        this.ID = ID;
        heldObjects = new LinkedList<>();
    }

    /**
     * Adds items to the monkey's list of held items
     * @param line Expected to be in the format "Starting itms: 13, 23, 15" where after "items:" are comma and space separated integers
     */
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
    public void setTestCondition(String testLine) {
        String START_OF_TEST_LINE = "Test: divisble by ";

        int index = skipPastStart(testLine, START_OF_TEST_LINE);
        testFunction = OPERATOR_DIVIDE;
        testNumber = getNumber(testLine, index, testLine.length());
    }

    public void setMonkey(Monkey trueMonkey, Monkey falseMonkey) {
        receiverIfTrue = trueMonkey;
        receiverIfFalse = falseMonkey;
    }

    public int returnDesiredMonkey(String trueOrFalseLine) {
        String START_OF_TRUE_LINE = "If true: throw to monkey ";
        String START_OF_FALSE_LINE = "If false: throw to monkey ";

        try {
            int index = skipPastStart(trueOrFalseLine, START_OF_TRUE_LINE);
            return getNumber(START_OF_TRUE_LINE, index, trueOrFalseLine.length());
        } catch (IllegalArgumentException ex) {
        }

        try {
            int index = skipPastStart(trueOrFalseLine, START_OF_FALSE_LINE);
            return getNumber(START_OF_FALSE_LINE, index, trueOrFalseLine.length());
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
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
