package mfacelle.coding.practice.graphs.mazecreation;

/**
 *  Simple point class containing x,y values
 */
public class Point {
    public final int x;
    public final int y;

    public Point(int xx, int yy) {
        this.x = xx;
        this.y = yy;
    }

    @Override
    public String toString() {
        return "["+x+","+y+"]";
    }
}
