package advent_of_code_2022.problem_09.part2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import advent_of_code_2022.common.Coordinate2D;
import advent_of_code_2022.common.ParseInput;

public class SimulateLongerRope {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_09/input.txt";
    static final Coordinate2D START_COORDINATE = new Coordinate2D(0, 0);
    static final int MAX_SEGMENT_DISTANCE = 1;
    static final int NUM_SEGMENTS = 10;
    static final Map<String, int[]> DIRECTIONS = Map.of(
        "U", new int[]{0, 1},
        "R", new int[]{1, 0},
        "D", new int[]{0, -1},
        "L", new int[]{-1, 0});

    public static void main(String[] args) {
        String[] input = ParseInput.parseInputAsList(INPUT_FILE_NAME);
        System.out.println("The tail has visited " + countTailVisitedCoordinates(input) + " locations");
    }

    public static int countTailVisitedCoordinates(String[] input) {
        // LinkedList simulating a rope
        List<Coordinate2D> rope = new LinkedList<Coordinate2D>();
        for (int i = 0; i < NUM_SEGMENTS; i++) {
            rope.add(START_COORDINATE);
        }

        // Coordinates the tail visited
        Set<Coordinate2D> visited = new HashSet<>();
        visited.add(START_COORDINATE);

        for (String line : input) {
            String[] commands = line.split(" ");
            validateInputLine(commands);
            
            String cardinalDirection = commands[0];
            int distance = Integer.parseInt(commands[1]);
            rope = performCommand(visited, cardinalDirection, distance, rope);
        }

        return visited.size();
    }

    private static List<Coordinate2D> performCommand(Set<Coordinate2D> visited, String cardinalDirection, int distance, List<Coordinate2D> rope) {
        Coordinate2D head = rope.get(0);

        int[] direction = DIRECTIONS.get(cardinalDirection);

        for (int i = 0; i < distance; i++) {
            head = new Coordinate2D(head.getX() + direction[0], head.getY() + direction[1]);
            rope.set(0, head);
            
            for (int j = 1; j < rope.size(); j++) {
                rope.set(j, updateSegmentLocation(rope.get(j-1), rope.get(j)));
            }
            
            visited.add(rope.get(rope.size() - 1));
            System.out.println(rope);
        }

        return rope;
    }

    private static Coordinate2D updateSegmentLocation(Coordinate2D head, Coordinate2D tail) {
        // Head isn't sufficiently far from tail
        if (head.getChebyshevDistance(tail) <= MAX_SEGMENT_DISTANCE) {
            return tail;
        }
        
        int horizontalDistance = head.getX() - tail.getX();
        int verticalDistance = head.getY() - tail.getY();
        // offset = +/- 1 * MAX_SEGMENT_DISTANCE
        // In practice, this is +1 if head.x is negative, and -1 if head.x is positive.
        int offsetX = horizontalDistance == 0 ? 0 : (horizontalDistance / Math.abs(horizontalDistance)) * MAX_SEGMENT_DISTANCE;
        int offsetY = verticalDistance == 0 ? 0 : (verticalDistance / Math.abs(verticalDistance)) * MAX_SEGMENT_DISTANCE;
        
        // Head moved diagonally
        if (Math.abs(horizontalDistance) > MAX_SEGMENT_DISTANCE && Math.abs(verticalDistance) > MAX_SEGMENT_DISTANCE) {
            tail = new Coordinate2D(head.getX() - offsetX, head.getY() - offsetY);
        }
        // Head moved horizontally
        else if (Math.abs(horizontalDistance) > MAX_SEGMENT_DISTANCE) {
            tail = new Coordinate2D(head.getX() - offsetX, head.getY());
        }
        // Head moved vertically 
        else {
            tail = new Coordinate2D(head.getX(), head.getY() - offsetY);
        }
        
        return tail;
    }

    private static void validateInputLine(String[] commands) {
        // Validate input length
        if (commands.length != 2
        || commands[0].length() != 1
        || !DIRECTIONS.containsKey(commands[0])) {
            throw new IllegalArgumentException("Invalid length of commands" + commands);
        }

        // Validate 2nd argument is an integer
        try {
            Integer.parseInt(commands[1]);
            return;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Second argument is not an integer: " + commands[1]);
        }
    }
}
