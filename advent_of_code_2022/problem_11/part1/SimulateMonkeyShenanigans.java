package advent_of_code_2022.problem_11.part1;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import advent_of_code_2022.common.ParseInput;
import advent_of_code_2022.common.PrintArray;

public class SimulateMonkeyShenanigans {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_11/input.txt";
    static final int NUM_ROUNDS = 20;
    
    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The product of the top two monkey's activities is " + productOfTopMonkeyShenanigans(input));
    }

    public static int productOfTopMonkeyShenanigans(String[] input) {
        Monkey[] monkeys = parseInputAndGetMonkeys(input);
        int[] numTimesMonkeysInspected = countInspectedOverRounds(monkeys);
        return getProductOfTopTwoValues(numTimesMonkeysInspected);
    }

    private static int[] countInspectedOverRounds(Monkey[] monkeys) {
        int[] count = new int[monkeys.length];
        for (int round = 0; round < NUM_ROUNDS; round++) {
            for (int monkeyId = 0; monkeyId < monkeys.length; monkeyId++) {
                Monkey currentMonkey = monkeys[monkeyId];
                while (currentMonkey.throwNextItem()) {
                    count[monkeyId]++;
                }
                
            }
        }

        return count;
    }
    
    private static int getProductOfTopTwoValues(int[] values) {
        if (values.length == 0) throw new NoSuchElementException("Array has 0 values");
        if (values.length == 1) return values[0];
        int max = Math.max(values[0], values[1]);
        int second = Math.min(values[0], values[1]);

        for (int num : values) {
            if (num > max) {
                second = max;
                max = num;
            } else if (num > second) {
                second = num;
            }
        }
        return max * second;
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
                char operatorFunction;
                int operatorNumber;
                // Edge case, I really cannot handle this right now.
                if (operationLine.substring(startIndex).equals("* old")) {
                    operatorFunction = '^';
                    operatorNumber = 2;
                } else {
                    operatorFunction = operationLine.charAt(startIndex);
                    operatorNumber = getNumber(operationLine, startIndex + 2);
                }

                String testLine = monkeyParams[3];
                char testFunction = '/';
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

        // Let each monkey point to each other instead of to their ids
        for (Monkey monkey : monkeyList) {
            int trueReceiverID = monkey.getTrueReceiverID();
            int falseReceiverID = monkey.getFalseReceiverID();
            if (trueReceiverID < 0 || falseReceiverID < 0 || trueReceiverID >= monkeyList.length || falseReceiverID >= monkeyList.length) {
                throw new IllegalArgumentException("Monkeys are throwing to nonexistent monkeys: " + trueReceiverID + " / " + falseReceiverID);
            }
            monkey.setTrueReceiver(monkeyList[trueReceiverID]);
            monkey.setFalseReceiver(monkeyList[falseReceiverID]);
        }

        return monkeyList;
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
