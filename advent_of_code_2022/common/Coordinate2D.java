package advent_of_code_2022.common;

public class Coordinate2D {
    private final int x;
    private final int y;

    public Coordinate2D(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate2D add(Coordinate2D other) {
        return new Coordinate2D(x + other.x, y + other.y);
    }

    // Distance if the coordinates can only travel vertically or horizontally
    public int getManhattanDistance(Coordinate2D other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    // Distance if the coordinates can only travel vertically, horizontally or diagonally
    public int getChebyshevDistance(Coordinate2D other) {
        return Math.max(Math.abs(x - other.x), Math.abs(y - other.y));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate2D c = (Coordinate2D) o;
        if (c.x == this.x && c.y == this.y) return true;
        return false;
    }

    @Override
    public int hashCode() {
        // Alternative for if I wanted to introduce many dimensions would be
        // final int[] numbers = {x, y};
        // return Arrays.hashCode(numbers);
        return 31 * x + y;
    }
}
