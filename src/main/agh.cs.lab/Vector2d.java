package agh.cs.lab;


import java.util.Objects;

public class Vector2d {

    public final int x;
    public final int y;

    public static final Vector2d[] UNIT_VECTORS = {new Vector2d(1,0), new Vector2d(0,1),
            new Vector2d(0,-1), new Vector2d(-1,0)};

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {

        int x = Math.max(this.x, other.x);
        int y = Math.max(this.y, other.y);

        return new Vector2d(x, y);
    }

    public Vector2d lowerLeft(Vector2d other) {

        int x = Math.min(this.x, other.x);
        int y = Math.min(this.y, other.y);

        return new Vector2d(x, y);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x &&
                y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
