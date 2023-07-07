package advent_of_code_2022.problem_09.part1;

import java.util.HashSet;
import java.util.Set;

import advent_of_code_2022.common.Coordinate;
import advent_of_code_2022.common.ParseInput;

public class SimulateRopeTail {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_09/input.txt";
    static final Coordinate START_COORDINATE = new Coordinate(0, 0);

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The tail has visited " + countTailVisitedCoordinates(input) + " locations");
    }

    public static int countTailVisitedCoordinates(String[] input) {
        Set<Coordinate> visited = new HashSet<>();
        for (String line : input) {
            String[] commands = line.split(" ");
            
        }
        return visited.size();
    }
}
