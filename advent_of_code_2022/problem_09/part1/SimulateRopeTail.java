package advent_of_code_2022.problem_09.part1;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import advent_of_code_2022.common.Coordinate2D;
import advent_of_code_2022.common.Pair;
import advent_of_code_2022.common.ParseInput;

public class SimulateRopeTail {
    
    static final String INPUT_FILE_NAME = "advent_of_code_2022/problem_09/input.txt";
    static final Coordinate2D START_COORDINATE = new Coordinate2D(0, 0);
    static final int MAX_DISTANCE_HEAD_TAIL = 1;
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
        // Head and tail pair
        Pair<Coordinate2D, Coordinate2D> rope = new Pair<>(START_COORDINATE, START_COORDINATE);
        // Coordinates the tail visited
        Set<Coordinate2D> visited = new HashSet<>();
        visited.add(rope.getSecond());

        for (String line : input) {
            String[] commands = line.split(" ");
            validateInputLine(commands);
            
            String cardinalDirection = commands[0];
            int distance = Integer.parseInt(commands[1]);
            rope = performCommand(visited, cardinalDirection, distance, rope);
        }

        return visited.size();
    }

    private static Pair<Coordinate2D, Coordinate2D> performCommand(Set<Coordinate2D> visited, String cardinalDirection, int distance, Pair<Coordinate2D, Coordinate2D> rope) {
        Coordinate2D head = rope.getFirst();
        Coordinate2D tail = rope.getSecond();
        
        int[] direction = DIRECTIONS.get(cardinalDirection);

        for (int i = 0; i < distance; i++) {
            head = new Coordinate2D(head.getX() + direction[0], head.getY() + direction[1]);
            tail = updateTailLocation(head, tail);
            visited.add(tail);
        }

        return new Pair<Coordinate2D, Coordinate2D>(head, tail);
    }

    private static Coordinate2D updateTailLocation(Coordinate2D head, Coordinate2D tail) {
        // Head isn't sufficiently far from tail
        if (head.getChebyshevDistance(tail) <= MAX_DISTANCE_HEAD_TAIL) {
            return tail;
        }
        
        // Head moved to the right
        int horizontalDistance = head.getX() - tail.getX();
        if (Math.abs(horizontalDistance) > MAX_DISTANCE_HEAD_TAIL) {
            int sign = (horizontalDistance / Math.abs(horizontalDistance));
            int offset = sign * MAX_DISTANCE_HEAD_TAIL;
            tail = new Coordinate2D(head.getX() - offset, head.getY());
        } else {
            int verticalDistance = head.getY() - tail.getY();
            int sign = (verticalDistance / Math.abs(verticalDistance));
            int offset = sign * MAX_DISTANCE_HEAD_TAIL;
            tail = new Coordinate2D(head.getX(), head.getY() - offset);
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
