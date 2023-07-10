package advent_of_code_2022.problem_11.part1;

import java.util.LinkedList;

public class Monkey {

    private int ID;
    private LinkedList<Integer> heldObjects;
    private String operationFunction;
    private int operationNumber;
    private String testFunction;
    private int testNumber;
    private int trueReceiverID;
    private int falseReceiverID;


    public void addItem(int item) {
        heldObjects.addLast(item);
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

        public Builder operationExpression(String function, int number) {
            operationFunction = function;
            operationNumber = number;
            return this;
        }

        public Builder testExpression(String function, int number) {
            testFunction = function;
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
