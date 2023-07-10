package advent_of_code_2022.problem_11.part1;

import java.util.LinkedList;

public class Monkey {
    static final String OPERATOR_MULTIPLY = "MULTIPLY";
    static final String OPERATOR_ADD = "ADD";
    static final String OPERATOR_SUBTRACT = "SUBTRACT";
    static final String OPERATOR_DIVIDE = "DIVIDE";
    static final String OPERATOR_EXPONENT = "EXPONENT";

    private int ID;
    private LinkedList<Integer> heldObjects;
    private String operationFunction;
    private int operationNumber;
    private String testFunction;
    private int testNumber;
    private Monkey trueReceiver;
    private Monkey falseReceiver;
    
    // Temporary variables until we initialize all monkeys
    // I know this is terrible since it goes against builder but not sure right now how to approach this problem
    private int trueReceiverID;
    private int falseReceiverID;



    public void addItem(int item) {
        heldObjects.addLast(item);
    }

    public boolean throwNextItem() {
        if (heldObjects.isEmpty()) return false;
        int itemWorryLevel = heldObjects.removeFirst();
        
        itemWorryLevel = performOperation(itemWorryLevel) / 3;
        
        if (monkeyTest(itemWorryLevel)) {
            trueReceiver.addItem(itemWorryLevel);
        } else {
            falseReceiver.addItem(itemWorryLevel);
        }
        
        return true;
    }

    public int performOperation(int itemWorryLevel) {
        if (operationFunction.equals(OPERATOR_ADD)) {
            return itemWorryLevel + operationNumber;
        } else if (operationFunction.equals(OPERATOR_SUBTRACT)) {
            return itemWorryLevel - operationNumber;
        } else if (operationFunction.equals(OPERATOR_MULTIPLY)) {
            return itemWorryLevel * operationNumber;
        } else if (operationFunction.equals(OPERATOR_DIVIDE)) {
            return itemWorryLevel / operationNumber;
        } else if (operationFunction.equals(OPERATOR_EXPONENT)) {
            return (int)Math.pow(itemWorryLevel, operationNumber);
        } else {
            // Should never happen
            throw new UnsupportedOperationException("Unable to determine operator: " + operationFunction);
        }
    }

    private boolean monkeyTest(int itemWorryLevel) {
        return itemWorryLevel % testNumber == 0;
    }

    public void setTrueReceiver(Monkey trueReceiver) {
        this.trueReceiver = trueReceiver;
    }

    public void setFalseReceiver(Monkey falseReceiver) {
        this.falseReceiver = falseReceiver;
    }

    public int getTrueReceiverID() {
        return trueReceiverID;
    }

    public int getFalseReceiverID() {
        return falseReceiverID;
    }

    @Override
    public String toString() {
        String string = "Monkey " + ID;
        string += "\n\t Items: " + heldObjects + 
                  "\n\t Operation: " + operationFunction + " " + operationNumber +
                  "\n\t Test: " + testFunction + " " + testNumber +
                  "\n\t\t If true: throw to monkey " + trueReceiverID +
                  "\n\t\t If false: throw to monkey " + falseReceiverID + "\n";
        return string;
    }

    

    public static class Builder {
        private final int ID;
        private String operationFunction;
        private int operationNumber;
        private String testFunction;
        private int testNumber;
        private int trueReceiverID;
        private int falseReceiverID;

        public Builder(int ID) {
            this.ID = ID;
        }

        public Builder operationExpression(char function, int number) {
            operationFunction = determineFunction(function);
            operationNumber = number;
            return this;
        }

        public Builder testExpression(char function, int number) {
            testFunction = determineFunction(function);
            testNumber = number;
            return this;
        }

        public Builder trueMonkey(int trueMonkeyID) {
            trueReceiverID = trueMonkeyID;
            return this;
        }

        public Builder falseMonkey(int falseMonkeyID) {
            falseReceiverID = falseMonkeyID;
            return this;
        }

        public Monkey build() {
            return new Monkey(this);
        }

        private String determineFunction(char function) {
            if (function == '+') return OPERATOR_ADD;
            else if (function == '-') return OPERATOR_SUBTRACT;
            else if (function == '*') return OPERATOR_MULTIPLY;
            else if (function == '/') return OPERATOR_DIVIDE;
            else if (function == '^') return OPERATOR_EXPONENT;
            else {
                throw new IllegalArgumentException("Unable to determine operator: " + function);
            }
        }
    }

    public Monkey(Builder builder) {
        this.ID = builder.ID;
        heldObjects = new LinkedList<>();
        operationFunction = builder.operationFunction;
        operationNumber = builder.operationNumber;
        testFunction = builder.testFunction;
        testNumber = builder.testNumber;
        trueReceiverID = builder.trueReceiverID;
        falseReceiverID = builder.falseReceiverID;
    }
}
