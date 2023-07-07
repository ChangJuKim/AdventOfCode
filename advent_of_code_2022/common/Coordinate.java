package advent_of_code_2022.common;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate c = (Coordinate) o;
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
