package main;

public class Vector2 {

    private int x;
    private int y;

    public Vector2(int to_x, int to_y) {
        x = to_x;
        y = to_y;
    }

    public int get_x() {
        return x;
    }

    public void set_x(int x) {
        this.x = x;
    }

    public int get_y() {
        return y;
    }

    public void set_y(int y) {
        this.y = y;
    }

}
