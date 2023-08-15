package advent_of_code_2022.problem_14.common;

import java.util.HashSet;

import advent_of_code_2022.common.Coordinate2D;

public class ParseInputIntoRocks {
    private static HashSet<Coordinate2D> rocksSet;
    
    public static HashSet<Coordinate2D> getRocks(String[] input) {
        rocksSet = new HashSet<>();
        for (String line : input) {
            addRocksFromLine(line);
        }
        return rocksSet;
    }

    private static void addRocksFromLine(String line) {
        String[] cornerPoints = line.split("->");
        if (line.length() == 0 || cornerPoints.length == 0) {
            return;
        }
        
        Coordinate2D start = parseAsCoordinate(cornerPoints[0]);
        for (String coordinateDetails : cornerPoints) {
            Coordinate2D next = parseAsCoordinate(coordinateDetails);
            addRocksToSet(start, next);
            start = next;
        }
    }
    
    private static Coordinate2D parseAsCoordinate(String coordinateDetails) {
        coordinateDetails = coordinateDetails.trim();
        try {
            int commaIndex = coordinateDetails.indexOf(',');
            int x = Integer.parseInt(coordinateDetails.substring(0, commaIndex));
            int y = Integer.parseInt(coordinateDetails.substring(commaIndex+1));
            return new Coordinate2D(x, y);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Coordinate is not in int,int format: " + coordinateDetails);
        }
    }

    private static void addRocksToSet(Coordinate2D start, Coordinate2D end) {
        if (start.equals(end)) {
            rocksSet.add(start);
            return;
        }

        if (start.getX() == end.getX() && start.getY() > end.getY()) {
            addRocksInLoop(start, end, 0, -1);
        } 
        else if (start.getX() == end.getX() && start.getY() < end.getY()) {
            addRocksInLoop(start, end, 0, 1);
        } 
        else if (start.getX() > end.getX() && start.getY() == end.getY()) {
            addRocksInLoop(start, end, -1, 0);
        }
        else if (start.getX() < end.getX() && start.getY() == end.getY()) {
            addRocksInLoop(start, end, 1, 0);
        }
        else {
            throw new IllegalArgumentException("Input has diagonal lines: " + start + " --> " + end);
        }
    }
    
    private static void addRocksInLoop(Coordinate2D start, Coordinate2D end, int xOffset, int yOffset) {
        Coordinate2D current = start;
        while (!current.equals(end)) {
            rocksSet.add(current);
            current = new Coordinate2D(current.getX() + xOffset, current.getY() + yOffset);
        }
        rocksSet.add(current);
    }
}
