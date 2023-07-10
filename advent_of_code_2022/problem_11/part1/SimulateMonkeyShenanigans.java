package advent_of_code_2022.problem_11.part1;

import java.util.ArrayList;

import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.common.PrintArray;

public class SimulateMonkeyShenanigans {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_11/input.txt";
    static final String OPERATOR_MULTIPLY = "MULTIPLY";
    static final String OPERATOR_ADD = "ADD";
    static final String OPERATOR_SUBTRACT = "SUBTRACT";
    static final String OPERATOR_DIVIDE = "DIVIDE";
    static final String OPERATOR_EXPONENT = "EXPONENT";
    
    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The product of the top two monkey's activities is " + productOfTopMonkeyShenanigans(input));
    }

    public static int productOfTopMonkeyShenanigans(String[] input) {
        Monkey[] monkeys = parseInputAndGetMonkeys(input);
        new PrintArray<Monkey>().printArray(monkeys);
        return 0;
    }

    private static Monkey[] parseInputAndGetMonkeys(String[] input) {
        final int NUM_LINES_DEFINING_MONKEY = 6; // Does not inclue the newline separating monkeys
        
        // Group input together depending on monkey
        ArrayList<String[]> monkeyParamList = new ArrayList<>();
        for (int i = 0; i < input.length; i += NUM_LINES_DEFINING_MONKEY + 1) {
            String[] newMonkeyParams = new String[NUM_LINES_DEFINING_MONKEY];
            for (int j = 0; j < newMonkeyParams.length; j++) {
                newMonkeyParams[j] = input[i+j];
            }
            monkeyParamList.add(newMonkeyParams);
        }

        // Build each monkey
        Monkey[] monkeyList = new Monkey[monkeyParamList.size()];
        for (int i = 0; i < monkeyParamList.size(); i++) {
            String[] monkeyParams = monkeyParamList.get(i);
            try {
                String idLine = monkeyParams[0];
                int startIndex = skipPastStart(idLine, "Monkey ");
                int id = getNumber(idLine, startIndex, idLine.indexOf(":"));

                String itemLine = monkeyParams[1];
                startIndex = skipPastStart(itemLine, "Starting items: ");
                String[] totalItems = itemLine.substring(startIndex).split(", ");
                int[] items = new int[totalItems.length];
                for (int itemIndex = 0; itemIndex < totalItems.length; itemIndex++) {
                    items[itemIndex] = Integer.parseInt(totalItems[itemIndex]);
                }

                String operationLine = monkeyParams[2];
                startIndex = skipPastStart(operationLine, "Operation: new = old ");
                String operatorFunction;
                int operatorNumber;
                // Edge case, I really cannot handle this right now.
                if (operationLine.substring(startIndex).equals("* old")) {
                    operatorFunction = OPERATOR_EXPONENT;
                    operatorNumber = 2;
                } else {
                    operatorFunction = determineOperator(operationLine.charAt(startIndex));
                    operatorNumber = getNumber(operationLine, startIndex + 2);
                }

                String testLine = monkeyParams[3];
                String testFunction = determineOperator('/');
                int testNumber = getNumber(testLine, skipPastStart(testLine, "Test: divisible by "));

                String trueMonkeyLine = monkeyParams[4];
                int trueMonkeyID = getNumber(trueMonkeyLine, skipPastStart(trueMonkeyLine, "If true: throw to monkey "));

                String falseMonkeyLine = monkeyParams[5];
                int falseMonkeyId = getNumber(falseMonkeyLine, (skipPastStart(falseMonkeyLine, "If false: throw to monkey ")));

                Monkey newMonkey = new Monkey.Builder(id)
                                             .operationExpression(operatorFunction, operatorNumber)
                                             .testExpression(testFunction, testNumber)
                                             .trueMonkey(trueMonkeyID)
                                             .falseMonkey(falseMonkeyId)
                                             .build();

                for (int item : items) {
                    newMonkey.addItem(item);
                }
                monkeyList[i] = newMonkey;
            }
            catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Tried to parse integer in line. " + ex);
            }
        }

        return monkeyList;
    }

    private static String determineOperator(char operator) {
        if (operator == '+') return OPERATOR_ADD;
        else if (operator == '-') return OPERATOR_SUBTRACT;
        else if (operator == '*') return OPERATOR_MULTIPLY;
        else if (operator == '/') return OPERATOR_DIVIDE;
        else {
            throw new IllegalArgumentException("Unable to determine operator: " + operator);
        }
    }


    // Finds first index of startOfLine in line, and returns the index immediately after it.
    private static int skipPastStart(String line, String startOfLine) {
        int index = line.indexOf(startOfLine);
        if (index == -1) {
            throw new IllegalArgumentException("Expected to begin with \"" + startOfLine + "\" but got \"" + line + "\"");
        }
        return index + startOfLine.length();
    }

    private static int getNumber(String line, int startIndex) {
        return getNumber(line, startIndex, line.length());
    }

    // Returns the integer in line between start and end index. If endIndex >= line.length, then parses from start index to end of string.
    private static int getNumber(String line, int startIndex, int endIndex) {
        try {
            return Integer.parseInt(line.substring(startIndex, endIndex));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Expected number between indices " + startIndex + " and " + endIndex + " of \"" + line + "\"");
        }
    }
}
